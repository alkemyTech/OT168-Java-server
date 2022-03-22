package com.alkemy.ong.domain.organization;

import org.springframework.stereotype.Service;

@Service
public class OrganizationService {

    private final OrganizationGateway organizationGateway;

    public OrganizationService(OrganizationGateway organizationGateway) {
        this.organizationGateway = organizationGateway;
    }

    public Organization findById(Long id){
        return organizationGateway.findById(id);
    }
}
