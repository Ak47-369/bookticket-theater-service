package com.bookticket.theater_service.repository;

import com.bookticket.theater_service.Entity.Show;
import com.bookticket.theater_service.dto.ShowResponse;
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

    @Query("SELECT new com.bookticket.theater_service.dto.ShowResponse(" +
            "s.id, s.startTime, s.endTime, s.movieId, " +
            "s.movieTitle, s.movieBannerUrl, " +
            "t.name, t.address, sc.name) " +
            "FROM Show s " +
            "JOIN s.screen sc " +
            "JOIN sc.theater t " +
            "WHERE s.movieId = :movieId " +
            "AND t.city = :city " +
            "AND DATE(s.startTime) = :date")
    List<ShowResponse> findShowsByMovieAndCityAndDate(
            @Param("movieId") String movieId,
            @Param("city") String city,
            @Param("date") java.time.LocalDate date
    );

}
