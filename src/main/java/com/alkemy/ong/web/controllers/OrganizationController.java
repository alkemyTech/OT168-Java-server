package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.exceptions.WebRequestException;
import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/organizations")
public class OrganizationController {

    private final OrganizationService organizationService;

    public OrganizationController(OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @GetMapping("/public/{id}")
    public ResponseEntity<OrganizationDTO> getOrganization(@PathVariable Long id){
        Organization organization =  organizationService.findById(id);
        return ResponseEntity.ok(toDto(organization));
    }

    @PutMapping("/public/{id}")
    public ResponseEntity<OrganizationFullDTO> update(@PathVariable Long id, @RequestBody OrganizationFullDTO fullDTO){
        if(id !=fullDTO.getIdOrganization()) {
            throw new WebRequestException("ID: " + id + " in Path Variable is different than ID: " + fullDTO.getIdOrganization() + " in Body Request.");
        }
        OrganizationFullDTO organization  = toFullDto(organizationService.updateOrganization(toModel(fullDTO)));
        return  ResponseEntity.ok(organization);
    }

    @PatchMapping("/public/{id}")
    public ResponseEntity<SocialDto> updateSocial(@PathVariable Long id, @RequestBody SocialDto social){
        if(id !=social.getIdOrganization()) {
            throw new WebRequestException("ID: " + id + " in Path Variable is different than ID: " + social.getIdOrganization() + " in Body Request.");
        }
        SocialDto organization  = toModelSocial(organizationService.updateSocialcontact(toSocialModel(social)));
        return  ResponseEntity.ok(organization);
    }

    private  OrganizationDTO toDto(Organization organization){

        return OrganizationDTO.builder()
                                            .name(organization.getName())
                                            .image(organization.getImage())
                                            .address(organization.getAddress())
                                            .phone(organization.getPhone())
                                            .facebookUrl(organization.getFacebookUrl())
                                            .linkedinUrl(organization.getLinkedinUrl())
                                            .instagramUrl(organization.getInstagramUrl())
                                            .build();
    }

    public static OrganizationFullDTO toFullDto(Organization organization){
        return OrganizationFullDTO.builder()
                .idOrganization(organization.getIdOrganization())
                .name(organization.getName())
                .image(organization.getImage())
                .phone(organization.getPhone())
                .address(organization.getAddress())
                .email(organization.getEmail())
                .aboutUsText(organization.getAboutUsText())
                .welcomeText(organization.getWelcomeText())
                .createdAt(organization.getCreatedAt())
                .updatedAt(organization.getUpdatedAt())
                .deleted(organization.getDeleted())
                .facebookUrl(organization.getFacebookUrl())
                .linkedinUrl(organization.getLinkedinUrl())
                .instagramUrl(organization.getInstagramUrl())
                .build();
    }

    private Organization toModel(OrganizationFullDTO fullDto){

        return Organization.builder()
                .idOrganization(fullDto.getIdOrganization())
                .name(fullDto.getName())
                .image(fullDto.getImage())
                .phone(fullDto.getPhone())
                .address(fullDto.getAddress())
                .email(fullDto.getEmail())
                .aboutUsText(fullDto.getAboutUsText())
                .welcomeText(fullDto.getWelcomeText())
                .createdAt(fullDto.getCreatedAt())
                .updatedAt(fullDto.getUpdatedAt())
                .deleted(fullDto.getDeleted())
                .facebookUrl(fullDto.getFacebookUrl())
                .linkedinUrl(fullDto.getLinkedinUrl())
                .instagramUrl(fullDto.getInstagramUrl())
                .build();

    }

    private Organization toSocialModel(SocialDto social){
        return Organization.builder()
                .idOrganization(social.getIdOrganization())
                .facebookUrl(social.getFacebookUrl())
                .linkedinUrl(social.getLinkedinUrl())
                .instagramUrl(social.getInstagramUrl())
                .build();
    }

    private SocialDto toModelSocial(Organization organization){
        return SocialDto.builder()
                .idOrganization(organization.getIdOrganization())
                .facebookUrl(organization.getFacebookUrl())
                .linkedinUrl(organization.getLinkedinUrl())
                .instagramUrl(organization.getInstagramUrl())
                .build();
    }

    @Data
    @Builder
    public static class OrganizationDTO{
        private String name;
        private String image;
        private Long phone;
        private String address;
        private String facebookUrl;
        private String linkedinUrl;
        private String instagramUrl;
    }

    @Data
    @Builder
    public static class OrganizationFullDTO{
        private Long idOrganization;
        private String name;
        private String image;
        private Long phone;
        private String address;
        private String email;
        private String aboutUsText;
        private String welcomeText;
        private LocalDateTime createdAt;
        private LocalDateTime updatedAt;
        private String facebookUrl;
        private String linkedinUrl;
        private String instagramUrl;
        private Boolean deleted;
    }
    @Data
    @Builder
    public static class SocialDto{
        private Long idOrganization;
        private String facebookUrl;
        private String linkedinUrl;
        private String instagramUrl;
    }
}
