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

    @Override
    public Organization updateOrganization(Organization organization) {
        OrganizationEntity entity = organizationRepository.findById(organization.getIdOrganization())
                .orElseThrow(()-> new ResourceNotFoundException("No organization with id: " + organization.getIdOrganization() + " exists."));
        return toModel(organizationRepository.save(updateEntity(entity, organization)));
    }

    @Override
    public Organization updateSocialContact(Organization organization){
        OrganizationEntity entity = organizationRepository.findById(organization.getIdOrganization())
                .orElseThrow(()-> new ResourceNotFoundException("No organization with id: " + organization.getIdOrganization() + " exists."));
        return toModel(organizationRepository.save(updateSocial(entity, organization)));
    }

    public static Organization toModel(OrganizationEntity entity){
        return Organization.builder()
                                                .idOrganization(entity.getIdOrganization())
                                                .name(entity.getName())
                                                .image(entity.getImage())
                                                .phone(entity.getPhone())
                                                .address(entity.getAddress())
                                                .email(entity.getEmail())
                                                .aboutUsText(entity.getAboutUsText())
                                                .welcomeText(entity.getWelcomeText())
                                                .createdAt(entity.getCreatedAt())
                                                .updatedAt(entity.getUpdatedAt())
                                                .deleted(entity.getDeleted())
                                                .facebookUrl(entity.getFacebookUrl())
                                                .linkedinUrl(entity.getLinkedinUrl())
                                                .instagramUrl(entity.getInstagramUrl())
                                                .build();
    }

    private OrganizationEntity updateEntity (OrganizationEntity entity, Organization organization){
        entity.setIdOrganization(organization.getIdOrganization());
        entity.setName(organization.getName());
        entity.setImage(organization.getImage());
        entity.setAddress(organization.getAddress());
        entity.setPhone(organization.getPhone());
        entity.setEmail(organization.getEmail());
        entity.setAboutUsText(organization.getAboutUsText());
        entity.setWelcomeText(organization.getWelcomeText());
        entity.setCreatedAt(entity.getCreatedAt());
        entity.setUpdatedAt(organization.getUpdatedAt());
        entity.setDeleted(Boolean.FALSE);
        entity.setFacebookUrl(organization.getFacebookUrl());
        entity.setLinkedinUrl(organization.getLinkedinUrl());
        entity.setInstagramUrl(organization.getInstagramUrl());
        return entity;
    }

    private OrganizationEntity updateSocial (OrganizationEntity entity, Organization organization){
        entity.setIdOrganization(organization.getIdOrganization());
        entity.setUpdatedAt(organization.getUpdatedAt());
        entity.setFacebookUrl(organization.getFacebookUrl());
        entity.setLinkedinUrl(organization.getLinkedinUrl());
        entity.setInstagramUrl(organization.getInstagramUrl());
        return entity;
    }


}
