package com.keyin.Activities;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface ActivityRepository extends CrudRepository<Activity,Long> {
    @NonNull
    List<Activity> findAll();
    Optional<Activity> findByName(String name);
}


