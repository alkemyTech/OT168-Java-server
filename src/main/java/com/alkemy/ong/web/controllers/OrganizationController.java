package com.alkemy.ong.web.controllers;

import com.alkemy.ong.domain.organization.Organization;
import com.alkemy.ong.domain.organization.OrganizationService;
import lombok.Builder;
import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


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

    private  OrganizationDTO toDto(Organization organization){

        return OrganizationDTO.builder()
                                            .name(organization.getName())
                                            .image(organization.getImage())
                                            .address(organization.getAddress())
                                            .phone(organization.getPhone())
                                            .build();
    }

    @Data
    @Builder
    public static class OrganizationDTO{
        private String name;
        private String image;
        private Long phone;
        private String address;
    }
}
