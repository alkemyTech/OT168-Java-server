package com.alkemy.ong.domain.organization;

public interface OrganizationGateway {
    Organization findById(Long idOrganization);
    Organization updateOrganization(Organization organization);
    Organization updateSocialContact(Organization organization);
}
