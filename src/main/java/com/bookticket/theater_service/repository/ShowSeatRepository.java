package com.bookticket.theater_service.repository;

import com.bookticket.theater_service.Entity.ShowSeat;
import com.bookticket.theater_service.dto.ShowSeatResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowSeatRepository extends JpaRepository<ShowSeat,Long> {
    // Get Seat Map API
//    List<ShowSeat> findByShowId(Long showId);
    @Query("SELECT new com.bookticket.theater_service.dto.ShowSeatResponse(" +
            "ss.id, CONCAT(s.row, s.column), s.seatType, ss.price, ss.status) " +
            "FROM ShowSeat ss " +
            "JOIN ss.seat s " +
            "WHERE ss.show.id = :showId " +
            "ORDER BY s.row, s.column") // Order by row and then column
    List<ShowSeatResponse> findShowSeatsByShowId(@Param("showId") Long showId);
}
