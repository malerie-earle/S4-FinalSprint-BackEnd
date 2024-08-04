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

    public Activity getActivityById(Long activity_id) {
        Optional<Activity> result = activityRepository.findById(activity_id);
        if (result.isPresent()) {
            return result.get();
        }
        return null;
    }

    public Activity createActivity(Activity newActivity) {
        return activityRepository.save(newActivity);
    }
}
