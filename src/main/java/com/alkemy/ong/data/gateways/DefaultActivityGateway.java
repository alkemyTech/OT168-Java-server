package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.ActivityEntity;
import com.alkemy.ong.data.repositories.ActivityRepository;
import com.alkemy.ong.domain.activity.ActivityGateway;
import com.alkemy.ong.domain.activity.Activity;
import org.springframework.stereotype.Component;

@Component
public class DefaultActivityGateway implements ActivityGateway {

    private final ActivityRepository activityRepository;

    public DefaultActivityGateway(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity save(Activity activity) {
        ActivityEntity activityEntity = toEntity(activity);
        activityRepository.save(activityEntity);
        return activity;
    }

    private ActivityEntity toEntity (Activity activity){
        ActivityEntity activityEntity = ActivityEntity.builder()
                .id(activity.getId())
                .name(activity.getName())
                .content(activity.getContent())
                .image(activity.getImage())
                .deleted(false)
                .build();
        return activityEntity;
    }

  }
