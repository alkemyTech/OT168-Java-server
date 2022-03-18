package com.alkemy.ong.domain.activity;

import org.springframework.stereotype.Service;

@Service
public class ActivityService {

    private final ActivityGateway activityGateway;

    public ActivityService(ActivityGateway activityGateway){
        this.activityGateway = activityGateway;
    }

    public Activity saveActivity(Activity activity) throws Exception {
        try {
           validation(activity);
            activityGateway.save(activity);
            return activity;
        } catch (Exception e) {
            throw e;
        }
    }

    public void validation(Activity activity) throws Exception {
        if (activity.getName().isEmpty() || activity.getName().equals("")){
            throw new Exception("Name cannot be empty");
        }
        if (activity.getContent().isEmpty() || activity.getContent().equals("")){
            throw new Exception("Content cannot be empty");
        }
    }
}
