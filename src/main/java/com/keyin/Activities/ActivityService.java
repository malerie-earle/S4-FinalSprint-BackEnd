package com.keyin.Activities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ActivityService {

    @Autowired
    private ActivityRepository activityRepository;

    public List<Activity> getAllActivities() {
        return activityRepository.findAll();
    }

    public Optional<Activity> getActivityById(Long activityId) {
            return activityRepository.findById(activityId);
    }

    public Activity createActivity(Activity newActivity) {
        return activityRepository.save(newActivity);
    }
}
