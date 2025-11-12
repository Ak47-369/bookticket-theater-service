package com.bookticket.theater_service.controller;

import com.bookticket.theater_service.dto.CreateTheaterRequest;
import com.bookticket.theater_service.dto.TheaterResponse;
import com.bookticket.theater_service.dto.UpdateTheaterRequest;
import com.bookticket.theater_service.service.TheaterService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/theaters")
public class TheaterController {
    private final TheaterService theaterService;

    public TheaterController(TheaterService theaterService) {
        this.theaterService = theaterService;
    }

    @PostMapping
    public ResponseEntity<TheaterResponse> createTheater(@Valid @RequestBody CreateTheaterRequest createTheaterRequest) {
       return new ResponseEntity<>(theaterService.createTheater(createTheaterRequest), HttpStatus.CREATED);
    }

    @PutMapping("/{theaterId}")
    public ResponseEntity<TheaterResponse> updateTheaterById(@PathVariable Long theaterId, @Valid @RequestBody UpdateTheaterRequest updateTheaterRequest) {
        return new ResponseEntity<>(theaterService.updateTheater(theaterId, updateTheaterRequest), HttpStatus.OK);
    }

    @GetMapping("/{theaterId}")
    public ResponseEntity<TheaterResponse> getTheaterById(@PathVariable Long theaterId) {
        return new ResponseEntity<>(theaterService.getTheater(theaterId), HttpStatus.OK);
    }

    @DeleteMapping("/{theaterId}")
    public ResponseEntity<Void> deleteTheaterById(@PathVariable Long theaterId) {
        theaterService.deleteTheater(theaterId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
