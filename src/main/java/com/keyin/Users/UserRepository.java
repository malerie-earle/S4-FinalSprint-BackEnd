package com.keyin.Users;

import com.keyin.Room_Booking.Room_Booking;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRepository extends CrudRepository<User,Long>{
    public List<User> findAllByUsername(String userName);
}
