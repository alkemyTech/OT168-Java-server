package com.alkemy.ong.domain.organization;

import java.util.List;

public interface OrganizationGateway {
    List<Organization> filterByOrder();
    Organization findById(Long idOrganization);
    Organization updateOrganization(Organization organization);
    Organization updateSocialContact(Organization organization);
}
