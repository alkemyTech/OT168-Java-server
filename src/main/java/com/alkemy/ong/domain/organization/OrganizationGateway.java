package com.alkemy.ong.domain.organization;

import java.util.List;

public interface OrganizationGateway {
    List<Organization> findAll();
    List<Organization> findById(Long id);
    Organization updateOrganization(Organization organization);
    Organization updateSocialContact(Organization organization);
}
