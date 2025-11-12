package com.bookticket.theater_service.controller;

import com.bookticket.theater_service.dto.CreateScreenRequest;
import com.bookticket.theater_service.dto.ScreenResponse;
import com.bookticket.theater_service.dto.UpdateScreenRequest;
import com.bookticket.theater_service.service.ScreenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/theaters/screens")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    @PostMapping
    public ResponseEntity<ScreenResponse> createScreen(@Valid @RequestBody CreateScreenRequest createScreenRequest) {
        return new ResponseEntity<>(screenService.createScreen(createScreenRequest), HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ScreenResponse>> getScreensByTheaterId(@RequestParam Long theaterId) {
        return new ResponseEntity<>(screenService.getScreensByTheaterId(theaterId), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<ScreenResponse> updateScreenById(@PathVariable Long screenId, @Valid @RequestBody UpdateScreenRequest updateScreenRequest) {
        return new ResponseEntity<>(screenService.updateScreen(screenId, updateScreenRequest), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<Void> deleteScreenById(@PathVariable Long screenId) {
        screenService.deleteScreen(screenId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
