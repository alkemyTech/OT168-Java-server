package com.alkemy.ong.web.controllers;

import com.alkemy.ong.data.entities.SlidesEntity;
import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationService;
import com.alkemy.ong.web.utils.WebUtils;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<Organization> getOrganization(@PathVariable Long id){
        Organization organization = organizationService.findById(id);
        return ResponseEntity.ok(organization);
    }

    @PutMapping("/public/{idOrganization}")
    public ResponseEntity<OrganizationDTO> update(@PathVariable Long idOrganization, @RequestBody OrganizationDTO fullDTO){
        WebUtils.validateDtoIdWithBodyId(idOrganization, fullDTO.getIdOrganization());
        OrganizationDTO organization  = toDto(organizationService.updateOrganization(toModel(fullDTO)));
        return  ResponseEntity.ok(organization);
    }

    @PatchMapping("/public/{id}")
    public ResponseEntity<OrganizationDTO> updateSocial(@PathVariable Long idOrganization, @RequestBody OrganizationDTO organization){
        WebUtils.validateDtoIdWithBodyId(idOrganization, organization.getIdOrganization());
        OrganizationDTO organizationDto  = toDto(organizationService.updateSocialcontact(toModel(organization)));
        return  ResponseEntity.ok(organizationDto);
    }

    private OrganizationDTO toDto(Organization organization){

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
                .slidesEntityList(organization.getSlidesEntityList())
                .build();
    }
    private Organization toModel(OrganizationDTO organization){
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
                .slidesEntityList(organization.getSlidesEntityList())
                .build();
    }

    public static OrganizationSimpleDTO toSimpleDto(Organization organization){
        return OrganizationSimpleDTO.builder()
                .idOrganization(organization.getIdOrganization())
                .name(organization.getName())
                .image(organization.getImage())
                .phone(organization.getPhone())
                .address(organization.getAddress())
                .facebookUrl(organization.getFacebookUrl())
                .linkedinUrl(organization.getLinkedinUrl())
                .instagramUrl(organization.getInstagramUrl())
                .slidesEntityList(organization.getSlidesEntityList())
                .build();
    }

    @Data
    @Builder
    public static class OrganizationDTO{
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
        private List<SlidesEntity> slidesEntityList;
    }

    @Data
    @Builder
    public static class OrganizationSimpleDTO{

        @Schema(example = "1", required = true)
        private Long idOrganization;

        @Schema(example = "Somos más", required = true)
        private String name;

        @Schema(example = "organization.jpg", required = true)
        private String image;

        @Schema(example = "2222-2222", required = true)
        private Long phone;

        @Schema(example = "Balcarce 50", required = true)
        private String address;

        @Schema(example = "www.facebook.com", required = true)
        private String facebookUrl;

        @Schema(example = "www.linkedin.com", required = true)
        private String linkedinUrl;

        @Schema(example = "www.instagram.com", required = true)
        private String instagramUrl;

        private List<SlidesEntity> slidesEntityList;
    }
}