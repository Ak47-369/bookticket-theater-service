package com.bookticket.theater_service.controller;

import com.bookticket.theater_service.dto.CreateShowRequest;
import com.bookticket.theater_service.dto.ShowResponse;
import com.bookticket.theater_service.dto.ShowSeatResponse;
import com.bookticket.theater_service.repository.ShowRepository;
import com.bookticket.theater_service.service.ShowService;
import jakarta.validation.Valid;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {
    private final ShowService showService;

    public ShowController(ShowService showService) {
        this.showService = showService;
    }

    @PostMapping
    public ResponseEntity<ShowResponse> createShow(@Valid @RequestBody CreateShowRequest createShowRequest) {
        return new ResponseEntity<>(showService.createShow(createShowRequest), HttpStatus.CREATED);
    }

    // Finds all shows for a given movie, city and date
    // UserFacing
    @GetMapping
    public ResponseEntity<List<ShowResponse>> getShows(@RequestParam String movieId, @RequestParam String city,
                                                       @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {
    List<ShowResponse> showResponses = showService.getShowsByMovieAndCityAndDate(movieId, city, date);
        return ResponseEntity.ok(showResponses);
    }

    // Service to Service Call
    @GetMapping("/internal/movie-ids")
    public ResponseEntity<List<String>> getMovieIdsByCity(@RequestParam String city) {
        return ResponseEntity.ok(List.of("1", "2", "3"));
//        return ResponseEntity.ok(showService.getMovieIdsByCity(city));
    }

    // Get Complete Seat Map (Layout and Availability)
    @GetMapping("/{showId}/seats")
    public ResponseEntity<List<ShowSeatResponse>> getShowSeats(@PathVariable Long showId) {
        List<ShowSeatResponse> showSeatResponses = showService.getShowSeatsByShowId(showId);
        if(showSeatResponses.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(showService.getShowSeatsByShowId(showId));
    }
}
