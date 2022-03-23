package com.alkemy.ong.domain.activity;

public interface ActivityGateway {

    Activity save(Activity activity);
    Activity findById(Long id);
    Activity update (Long id, Activity activity);
}
