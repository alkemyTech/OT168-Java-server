package com.alkemy.ong.domain.organization;

import java.util.Optional;

public interface OrganizationGateway {
    Organization findById(Long idOrganization);
}
