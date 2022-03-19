package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.OrganizationEntity;
import com.alkemy.ong.data.repositories.OrganizationRepository;
import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationGateway;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DefaultOrganizationGateway implements OrganizationGateway {

    private final OrganizationRepository organizationRepository;

    public DefaultOrganizationGateway(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization findById(Long id) {
        Optional<OrganizationEntity> organizationEntity = organizationRepository.findById(id);
        //TODO: orElseThrow  custom exception
        return toModel(organizationEntity.get());
    }

    public static Organization toModel(OrganizationEntity entity){
        return Organization.builder()
                                                .idOrganization(entity.getIdOrganization())
                                                .name(entity.getName())
                                                .image(entity.getImage())
                                                .phone(entity.getPhone())
                                                .address(entity.getAddress())
                                                .email(entity.getEmail())
                                                .about_us_text(entity.getAboutUsText())
                                                .welcome_text(entity.getWelcomeText())
                                                .createdAt(entity.getCreatedAt())
                                                .updatedAt(entity.getUpdatedAt())
                                                .deleted(entity.getDeleted())
                                                //TODO: Falta agregar Slides
                                                .build();
    }
}
