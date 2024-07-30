package com.keyin.Room_Booking;

import com.keyin.Rooms.Room;
import com.keyin.Users.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface Room_BookingRepository extends CrudRepository<Room_Booking, Long> {
    public List<Room_Booking> findAllByUser(User user);
    public List<Room_Booking> findAllByRoom(Room room);

    //date is between start and end dates DOESNT WORK
    //public List<Room_Booking> findAllByStartdateLessThanEqualDateAndEnddateGreaterThanEqualDate(Date date);

    //date range is larger than start and end date DOESNT WORK
    //public List<Room_Booking> findAllByStart_DateGreaterThanEqualSDateAndEnd_DateLessThanEqualEDate(Date sdate,Date edate);

}
