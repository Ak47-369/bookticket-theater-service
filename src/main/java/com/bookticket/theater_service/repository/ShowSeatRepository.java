package com.bookticket.theater_service.repository;

import com.bookticket.theater_service.Entity.ShowSeat;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
    // Get Seat Map API
    List<ShowSeat> findByShowId(Long showId);
}
