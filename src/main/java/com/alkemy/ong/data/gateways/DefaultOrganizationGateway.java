package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.OrganizationEntity;
import com.alkemy.ong.data.repositories.OrganizationRepository;
import com.alkemy.ong.data.repositories.SlidesRepository;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationGateway;

import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.Optional;

@Component
public class DefaultOrganizationGateway implements OrganizationGateway {

    private final OrganizationRepository organizationRepository;
    private final SlidesRepository slidesRepository;

    public DefaultOrganizationGateway(OrganizationRepository organizationRepository,
                                      SlidesRepository slidesRepository) {
        this.organizationRepository = organizationRepository;
        this.slidesRepository = slidesRepository;
    }

    @Override
    public Organization findById(Long idOrganization) {
        OrganizationEntity organizationEntity = organizationRepository.findById(idOrganization)
                .orElseThrow(() -> new ResourceNotFoundException("No organization with id: " + idOrganization + " exists."));
        return toModel(organizationEntity);
    }

    @Override
    public Organization updateOrganization(Organization organization) {
        OrganizationEntity entity = organizationRepository.findById(organization.getIdOrganization())
                .orElseThrow(() -> new ResourceNotFoundException("No organization with id: " + organization.getIdOrganization() + " exists."));
        return toModel(organizationRepository.save(updateEntity(entity, organization)));
    }

    @Override
    public Organization updateSocialContact(Organization organization) {
        OrganizationEntity entity = organizationRepository.findById(organization.getIdOrganization())
                .orElseThrow(() -> new ResourceNotFoundException("No organization with id: " + organization.getIdOrganization() + " exists."));
        return toModel(organizationRepository.save(updateSocial(entity, organization)));
    }

    public static Organization toModel(OrganizationEntity entity) {
        return Organization.builder()
                .idOrganization(entity.getIdOrganization())
                .name(entity.getName())
                .image(entity.getImage())
                .phone(entity.getPhone())
                .address(entity.getAddress())
                .email(entity.getEmail())
                .aboutUsText(entity.getAboutUsText())
                .welcomeText(entity.getWelcomeText())
                .facebookUrl(entity.getFacebookUrl())
                .linkedinUrl(entity.getLinkedinUrl())
                .instagramUrl(entity.getInstagramUrl())
                .slidesEntityList(entity.getSlidesEntityList())
                .build();
    }

    private OrganizationEntity updateEntity(OrganizationEntity entity, Organization organization) {
        entity.setIdOrganization(organization.getIdOrganization());
        entity.setName(organization.getName());
        entity.setImage(organization.getImage());
        entity.setAddress(organization.getAddress());
        entity.setPhone(organization.getPhone());
        entity.setEmail(organization.getEmail());
        entity.setAboutUsText(organization.getAboutUsText());
        entity.setWelcomeText(organization.getWelcomeText());
        entity.setFacebookUrl(organization.getFacebookUrl());
        entity.setLinkedinUrl(organization.getLinkedinUrl());
        entity.setInstagramUrl(organization.getInstagramUrl());
        entity.setSlidesEntityList(organization.getSlidesEntityList());
        return entity;
    }

    private OrganizationEntity updateSocial(OrganizationEntity entity, Organization organization) {
        entity.setIdOrganization(organization.getIdOrganization());
        entity.setFacebookUrl(organization.getFacebookUrl());
        entity.setLinkedinUrl(organization.getLinkedinUrl());
        entity.setInstagramUrl(organization.getInstagramUrl());
        entity.setSlidesEntityList(organization.getSlidesEntityList());
        return entity;
    }

    public static OrganizationEntity toEntity(Organization organization) {
        return OrganizationEntity.builder().
                idOrganization(organization.getIdOrganization()).
                name(organization.getName()).
                image(organization.getImage()).
                address(organization.getAddress()).
                phone(organization.getPhone()).
                email(organization.getEmail()).
                aboutUsText(organization.getAboutUsText()).
                welcomeText(organization.getWelcomeText()).
                facebookUrl(organization.getFacebookUrl()).
                linkedinUrl(organization.getLinkedinUrl()).
                instagramUrl(organization.getInstagramUrl()).
                build();
    }
}