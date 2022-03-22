package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.OrganizationEntity;
import com.alkemy.ong.data.repositories.OrganizationRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationGateway;
import org.springframework.stereotype.Component;


@Component
public class DefaultOrganizationGateway implements OrganizationGateway {

    private final OrganizationRepository organizationRepository;

    public DefaultOrganizationGateway(OrganizationRepository organizationRepository) {
        this.organizationRepository = organizationRepository;
    }

    @Override
    public Organization findById(Long id) {
        OrganizationEntity organizationEntity = organizationRepository.findById(id)
                .orElseThrow(()->new ResourceNotFoundException("No organization with id: " + id + " exists."));
        return toModel(organizationEntity);
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
                                                .build();
    }
}
