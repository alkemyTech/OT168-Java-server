package com.alkemy.ong.domain.activities;

import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    private final ActivityGateway activityGateway;

    public ActivityService(ActivityGateway activityGateway){
        this.activityGateway = activityGateway;
    }

    public Activity saveActivity(Activity activity) {
            return activityGateway.save(activity);
    }

    public Activity updateActivity(Long id, Activity activity) {
        return activityGateway.update(id, activity);
    }
}
