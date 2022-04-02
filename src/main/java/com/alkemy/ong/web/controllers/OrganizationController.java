package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationService;
import com.alkemy.ong.domain.slides.Slides;
import com.alkemy.ong.domain.slides.SlidesService;
import com.alkemy.ong.web.utils.WebUtils;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<List<OrganizationSimpleDTO>> getOrganization(){
        List<Organization> organizationList = (List<Organization>) organizationService.findAll();
        return ResponseEntity.ok(organizationList.stream()
                .map(organization -> toSimpleDto(organization))
                .collect(toList()));
    }

    @PutMapping("/public/{id}")
    public ResponseEntity<OrganizationDTO> update(@PathVariable Long id, @RequestBody OrganizationDTO fullDTO) {
        WebUtils.validateDtoIdWithBodyId(id, fullDTO.getIdOrganization());
        OrganizationDTO organization = toDto(organizationService.updateOrganization(toModel(fullDTO)));
        return ResponseEntity.ok(organization);
    }

    @PatchMapping("/public/{id}")
    public ResponseEntity<OrganizationDTO> updateSocial(@PathVariable Long id, @RequestBody OrganizationDTO organization) {
        WebUtils.validateDtoIdWithBodyId(id, organization.getIdOrganization());
        OrganizationDTO organizationDto = toDto(organizationService.updateSocialcontact(toModel(organization)));
        return ResponseEntity.ok(organizationDto);
    }

    private OrganizationDTO toDto(Organization organization) {

        return OrganizationDTO.builder()
                .idOrganization(organization.getIdOrganization())
                .name(organization.getName())
                .image(organization.getImage())
                .address(organization.getAddress())
                .phone(organization.getPhone())
                .email(organization.getEmail())
                .aboutUsText(organization.getAboutUsText())
                .welcomeText(organization.getWelcomeText())
                .facebookUrl(organization.getFacebookUrl())
                .linkedinUrl(organization.getLinkedinUrl())
                .instagramUrl(organization.getInstagramUrl())
                .slidesEntity((List<SlidesEntity>) organization.getSlidesEntity())
                .build();
    }

    private Organization toModel(OrganizationDTO organization) {
        return Organization.builder()
                .idOrganization(organization.getIdOrganization())
                .name(organization.getName())
                .image(organization.getImage())
                .phone(organization.getPhone())
                .address(organization.getAddress())
                .email(organization.getEmail())
                .aboutUsText(organization.getAboutUsText())
                .welcomeText(organization.getWelcomeText())
                .facebookUrl(organization.getFacebookUrl())
                .linkedinUrl(organization.getLinkedinUrl())
                .instagramUrl(organization.getInstagramUrl())
                .slidesEntity((SlidesEntity) organization.getSlidesEntity())
                .build();
    }

    public static OrganizationSimpleDTO toSimpleDto(Organization organization) {
        return OrganizationSimpleDTO.builder()
                .idOrganization(organization.getIdOrganization())
                .name(organization.getName())
                .image(organization.getImage())
                .phone(organization.getPhone())
                .address(organization.getAddress())
                .facebookUrl(organization.getFacebookUrl())
                .linkedinUrl(organization.getLinkedinUrl())
                .instagramUrl(organization.getInstagramUrl())
                .slidesEntity(organization.getSlidesEntity())
                .build();
    }

    @Data
    @Builder
    public static class OrganizationDTO {
        private Long idOrganization;
        private String name;
        private String image;
        private Long phone;
        private String address;
        private String email;
        private String aboutUsText;
        private String welcomeText;
        private String facebookUrl;
        private String linkedinUrl;
        private String instagramUrl;
        private List<SlidesEntity> slidesEntity;
    }

    @Data
    @Builder
    public static class OrganizationSimpleDTO {
        private Long idOrganization;
        private String name;
        private String image;
        private Long phone;
        private String address;
        private String facebookUrl;
        private String linkedinUrl;
        private String instagramUrl;
        private SlidesEntity slidesEntity;
    }
}