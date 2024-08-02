package com.keyin.Activities;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@CrossOrigin
@RequestMapping("/api")
public class ActivityController {

    @Autowired
    private ActivityService activityService;

    @GetMapping("/activities")
    public List<Activity> getAllActivities(){
        return activityService.getAllActivities();
    }

    @GetMapping("/activities/{activity_id}")
    public Activity getActivityById(@PathVariable Long activity_id){
        return activityService.getActivityById(activity_id);
    }

    @PostMapping("/activity")
    public Activity createActivity(@RequestBody Activity newActivity){
        return activityService.createActivity(newActivity);
    }
}
