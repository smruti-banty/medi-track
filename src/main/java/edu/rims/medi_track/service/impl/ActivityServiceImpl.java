package edu.rims.medi_track.service.impl;

import edu.rims.medi_track.constants.ActivityType;
import edu.rims.medi_track.entity.Activity;
import edu.rims.medi_track.repository.ActivityRepository;
import edu.rims.medi_track.repository.UserRepository;
import edu.rims.medi_track.service.ActivityService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class ActivityServiceImpl implements ActivityService {

    private final ActivityRepository activityRepository;
    private final UserRepository userRepository;

    @Override
    public void logActivity(String userId, ActivityType activityType, String description) {
        var user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        var activity = new Activity();
        activity.setUser(user);
        activity.setActivityType(activityType);
        activity.setDescription(description);

        activityRepository.save(activity);
    }

    @Override
    public List<Activity> getRecentActivities(String userId) {
        return activityRepository.findByUserIdOrderByCreatedDateDesc(userId);
    }

    @Override
    public List<Activity> getRecent5Activities(String userId) {
        return activityRepository.findTop5ByUserIdOrderByCreatedDateDesc(userId);
    }
}
