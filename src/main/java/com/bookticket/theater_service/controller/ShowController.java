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

    @PostMapping("/screens/{screenId}")
    public ResponseEntity<ShowResponse> createShow(@PathVariable Long screenId,@Valid @RequestBody CreateShowRequest createShowRequest) {
        return new ResponseEntity<>(showService.createShow(screenId, createShowRequest), HttpStatus.CREATED);
    }

    /**
     * Finds shows with flexible filtering options
     * Supports combinations of: city, theaterId, movieId, date
     *
     * Examples:
     * - GET /api/v1/shows?city=Mumbai
     * - GET /api/v1/shows?city=Mumbai&date=2025-11-15
     * - GET /api/v1/shows?theaterId=5
     * - GET /api/v1/shows?theaterId=5&date=2025-11-15
     * - GET /api/v1/shows?city=Mumbai&movieId=movie123
     * - GET /api/v1/shows?city=Mumbai&movieId=movie123&date=2025-11-15
     *
     * Date filter: If not provided, returns shows for next 15 days
     */
    @GetMapping
    public ResponseEntity<List<ShowResponse>> getShows(
            @RequestParam(required = false) String city,
            @RequestParam(required = false) Long theaterId,
            @RequestParam(required = false) String movieId,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate date) {

        List<ShowResponse> showResponses = showService.getShowsByFilters(city, theaterId, movieId, date);
        return ResponseEntity.ok(showResponses);
    }

    // Service to Service Call
    @GetMapping("/internal/movie-ids")
    public ResponseEntity<List<String>> getMovieIdsByCity(@RequestParam String city) {
//        return ResponseEntity.ok(List.of("1", "2", "3"));
        return ResponseEntity.ok(showService.getMovieIdsByCity(city));
    }

    @GetMapping("/screens/{screenId}")
    public ResponseEntity<List<ShowResponse>> getShowsByScreenId(@PathVariable Long screenId) {
        return ResponseEntity.ok(showService.getShowsByScreenId(screenId));
    }

    @GetMapping("/{showId}")
    public ResponseEntity<ShowResponse> getShowById(@PathVariable Long showId) {
        return ResponseEntity.ok(showService.getShowById(showId));
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
