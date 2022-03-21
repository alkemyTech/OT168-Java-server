package com.alkemy.ong.domain.activity;

import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    private final ActivityGateway activityGateway;

    public ActivityService(ActivityGateway activityGateway){
        this.activityGateway = activityGateway;
    }

    public Activity saveActivity(Activity activity) {
            activityGateway.save(activity);
            return activity;
    }
}
