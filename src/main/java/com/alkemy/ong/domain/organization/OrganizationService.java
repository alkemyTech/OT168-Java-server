package com.alkemy.ong.domain.organization;

import com.alkemy.ong.domain.slides.Slides;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrganizationService {

    private final OrganizationGateway organizationGateway;

    public OrganizationService(OrganizationGateway organizationGateway) {
        this.organizationGateway = organizationGateway;
    }

    /*public List<Organization> filterByOrder(Slides slides, Organization organization){
        return organizationGateway.filterByOrder(slides, organization);
    }*/

    public Organization findById(Long id){
        return organizationGateway.findById(id);
    }

    public Organization updateOrganization(Organization organization){
        return organizationGateway.updateOrganization(organization);
    }

    public Organization updateSocialcontact(Organization organization){
        return organizationGateway.updateSocialContact(organization);
    }
}
