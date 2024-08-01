package com.keyin.Activities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface ActivityRepository extends CrudRepository<Activity,Long> {
    @NonNull
    List<Activity> findAll();
}


