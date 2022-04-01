package com.alkemy.ong.domain.organization;

import com.alkemy.ong.domain.slides.Slides;

import java.util.List;

public interface OrganizationGateway {
    Organization findById(Long idOrganization);
    Organization updateOrganization(Organization organization);
    Organization updateSocialContact(Organization organization);
}
