package com.bookticket.theater_service.controller;

import com.bookticket.theater_service.repository.ShowRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shows")
public class ShowController {
    private final ShowRepository showRepository;

    public ShowController(ShowRepository showRepository) {
        this.showRepository = showRepository;
    }

    // Service to Service Call
    @GetMapping("/internal/movie-ids")
    public ResponseEntity<List<String>> getMovieIdsByCity(@RequestParam String city) {
        return ResponseEntity.ok(showRepository.findDistinctMovieIdsByCity(city));
    }
}
