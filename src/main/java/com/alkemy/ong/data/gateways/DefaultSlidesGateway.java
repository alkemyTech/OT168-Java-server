package com.alkemy.ong.data.gateways;

import com.alkemy.ong.cloud.AwsGateway;
import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.data.repositories.SlidesRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.exceptions.WebRequestException;
import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.slides.SlidesGateway;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItem;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;


import java.io.*;
import java.util.Base64;
import java.util.List;

@Component
public class DefaultSlidesGateway implements SlidesGateway {

    private final SlidesRepository slidesRepository;
    private final DefaultOrganizationGateway defaultOrganizationGateway;
    private final AwsGateway awsGateway;

    public DefaultSlidesGateway(SlidesRepository slidesRepository, DefaultOrganizationGateway defaultOrganizationGateway, AwsGateway awsGateway) {
        this.slidesRepository = slidesRepository;
        this.defaultOrganizationGateway = defaultOrganizationGateway;
        this.awsGateway = awsGateway;
    }

    @Override
    public List<Slides> findAll() {
        List<SlidesEntity> slidesEntityList = slidesRepository.findAll();
        return slidesEntityList.stream()
                .map(slides -> toModel(slides))
                .toList();
    }

    @Override
    public Slides findById(Long id) {
        SlidesEntity entity = slidesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No slide with id: " + id + " exists."));
        return toModel(entity);
    }

    @Override
    public Slides create(Slides slides) throws IOException {
        // Campo 'order'
        slides.setOrder(checkAndSetOrder(slides.getOrder()));
        // Decodificación de imagen y guardado en Amazon S3.
        slides.setImageUrl(decodeSlideImage(slides));
        return toModel(slidesRepository.save(toEntity(slides)));
    }

    @Override
    public Slides update(Slides slides) {
        SlidesEntity entity = slidesRepository.findById(slides.getIdSlides())
                .orElseThrow(()-> new ResourceNotFoundException("No slide with id: " + slides.getIdSlides() + " exists."));
        return toModel(slidesRepository.save(updateEntity(entity,slides)));
    }

    @Override
    public void delete(Long id) {
        SlidesEntity entity = slidesRepository.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("No slide with id: " + id + " exists."));
        entity.setDeleted(Boolean.TRUE);
        slidesRepository.save(entity);
    }

    private Integer checkAndSetOrder(Integer order) {
        List<SlidesEntity> slides = slidesRepository.findAll(Sort.by(Sort.Direction.ASC, "order"));
        if (order == null) {
            Integer lastOrder = slides.get(slides.size() - 1).getOrder();
            if (lastOrder != null) {
                order = lastOrder + 1;
            } else {
                order = 1;
            }
        }
        for (SlidesEntity slide : slides) {
            if (slide.getOrder() == order) {
                throw new WebRequestException("The order indicated already exists.");
            }
        }
        return order;
    }

    private String decodeSlideImage(Slides slides) throws IOException {
        String dataType;
        String fileName = "slide";
        if (slides.getImageUrl().indexOf("data:image/png;") != -1){
            dataType = "image/png";
            slides.setImageUrl(slides.getImageUrl().replace("data:image/png;base64,",""));
            fileName += ".png";
        } else {
            dataType = "image/jpeg";
            slides.setImageUrl(slides.getImageUrl().replace("data:image/jpeg;base64,",""));
            fileName += ".jpeg";
        }
        byte[] imageByte= Base64.getDecoder().decode(slides.getImageUrl());
        FileItem fileItem = new DiskFileItem(new String(imageByte), dataType, true, fileName, DiskFileItemFactory.DEFAULT_SIZE_THRESHOLD, new java.io.File(System.getProperty("java.io.tmpdir")));
        InputStream input =  new ByteArrayInputStream(imageByte);
        OutputStream os = fileItem.getOutputStream();
        int ret = input.read();
        while ( ret != -1 )
        {
            os.write(ret);
            ret = input.read();
        }
        os.flush();
        MultipartFile image = new CommonsMultipartFile(fileItem);
        return awsGateway.uploadFile(image);
    }

    private Slides toModel(SlidesEntity entity){
        return Slides.builder()
                .idSlides(entity.getIdSlides())
                .imageUrl(entity.getImageUrl())
                .text(entity.getText())
                .order(entity.getOrder())
                .idOrganization(entity.getOrganization().getIdOrganization())
                .build();
    }
    private SlidesEntity updateEntity(SlidesEntity entity, Slides slides){
        entity.setIdSlides(slides.getIdSlides());
        entity.setImageUrl(slides.getImageUrl());
        entity.setText(slides.getText());
        entity.setOrder(slides.getOrder());
        return entity;
    }

    private SlidesEntity toEntity(Slides slides) {
        return SlidesEntity.builder().
                idSlides(slides.getIdSlides()).
                imageUrl(slides.getImageUrl()).
                text(slides.getText()).
                order(slides.getOrder()).
                organization(DefaultOrganizationGateway.toEntity(defaultOrganizationGateway.findById(slides.getIdOrganization()))).
                build();
    }
}