package com.bookticket.theater_service.controller;

import com.bookticket.theater_service.dto.CreateTheaterRequest;
import com.bookticket.theater_service.dto.TheaterResponse;
import com.bookticket.theater_service.service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/admin/theaters")
public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PostMapping
    public ResponseEntity<TheaterResponse> createTheater(@Valid @RequestBody CreateTheaterRequest createTheaterRequest) {
       return new ResponseEntity<>(theaterService.createTheater(createTheaterRequest), HttpStatus.CREATED);
    }
}
