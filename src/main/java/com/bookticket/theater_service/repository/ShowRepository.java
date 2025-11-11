package com.bookticket.theater_service.repository;

import com.bookticket.theater_service.Entity.Show;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ShowRepository extends JpaRepository<Show,Long> {
    // Query to find distinct movie IDs by city for upcoming shows
    @Query("SELECT DISTINCT s.movieId FROM Show s " +
            "JOIN s.screen sc " +
            "JOIN sc.theater t " +
            "WHERE t.city = :city AND s.startTime > CURRENT_TIMESTAMP")
    List<String> findDistinctMovieIdsByCity(@Param("city") String city);
}
