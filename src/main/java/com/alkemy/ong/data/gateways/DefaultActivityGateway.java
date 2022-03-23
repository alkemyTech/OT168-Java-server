package com.alkemy.ong.data.gateways;

import com.alkemy.ong.data.entities.ActivityEntity;
import com.alkemy.ong.data.repositories.ActivityRepository;
import com.alkemy.ong.domain.activity.ActivityGateway;
import com.alkemy.ong.domain.activity.Activity;
import com.alkemy.ong.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Component;

@Component
public class DefaultActivityGateway implements ActivityGateway {

    private final ActivityRepository activityRepository;

    public DefaultActivityGateway(ActivityRepository activityRepository) {
        this.activityRepository = activityRepository;
    }

    @Override
    public Activity save(Activity activity) {
        return toModel(activityRepository.save(toEntity(activity)));
    }

    @Override
    public Activity findById(Long id) {
        return toModel(activityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist.")));
    }

    @Override
    public Activity update(Long id, Activity activity) {
        ActivityEntity updateActivity = activityRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("The ID doesn't exist."));
        updateActivity.setName(activity.getName());
        updateActivity.setContent(activity.getContent());
        updateActivity.setImage(activity.getImage());
        return toModel(activityRepository.save(updateActivity));
    }

    private ActivityEntity toEntity (Activity activity){
        return ActivityEntity.builder()
                .id(activity.getId())
                .name(activity.getName())
                .content(activity.getContent())
                .image(activity.getImage())
                .deleted(false)
                .build();
        }

    private Activity toModel (ActivityEntity activityEntity){
        return Activity.builder()
                .id(activityEntity.getId())
                .name(activityEntity.getName())
                .content(activityEntity.getContent())
                .image(activityEntity.getImage())
                .createdAt(activityEntity.getCreatedAt())
                .updatedAt(activityEntity.getUpdatedAt())
                .build();
        }
  }
