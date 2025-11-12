package com.bookticket.theater_service.repository;

import com.bookticket.theater_service.Entity.Seat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SeatRepository extends JpaRepository<Seat,Long> {
    List<Seat> findByScreenId(Long screenId);
}
