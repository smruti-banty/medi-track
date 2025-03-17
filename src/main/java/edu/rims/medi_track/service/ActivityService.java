package edu.rims.medi_track.service;

import edu.rims.medi_track.constants.ActivityType;
import edu.rims.medi_track.entity.Activity;

import java.util.List;

public interface ActivityService {
    void logActivity(String userId, ActivityType activityType, String description);

    List<Activity> getRecentActivities(String userId);

    List<Activity> getRecent5Activities(String userId);
}
