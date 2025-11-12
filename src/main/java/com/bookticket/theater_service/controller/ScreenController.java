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
@RequestMapping("/api/v1")
public class ScreenController {

    private final ScreenService screenService;

    public ScreenController(ScreenService screenService) {
        this.screenService = screenService;
    }

    /**
     * Creates a new screen for a specific theater.
     */
    @PostMapping("/theaters/{theaterId}/screens")
    public ResponseEntity<ScreenResponse> createScreen(@Valid @RequestBody CreateScreenRequest createScreenRequest) {
        return new ResponseEntity<>(screenService.createScreen(createScreenRequest), HttpStatus.CREATED);
    }

    /**
     * Gets all screens belonging to a specific theater.
     */
    @GetMapping("/theaters/{theaterId}/screens")
    public ResponseEntity<List<ScreenResponse>> getScreensByTheaterId(@PathVariable Long theaterId) {
        return new ResponseEntity<>(screenService.getScreensByTheaterId(theaterId), HttpStatus.OK);
    }

    /**
     * Gets a single screen by its unique ID.
     * This endpoint is independent of the theater for direct access.
     */
    @GetMapping("/screens/{screenId}")
    public ResponseEntity<ScreenResponse> getScreenById(@PathVariable Long screenId) {
        return new ResponseEntity<>(screenService.getScreenById(screenId), HttpStatus.OK);
    }

    /**
     * Updates a specific screen.
     */
    @PutMapping("/screens/{screenId}")
    public ResponseEntity<ScreenResponse> updateScreenById(@PathVariable Long screenId, @Valid @RequestBody UpdateScreenRequest updateScreenRequest) {
        return new ResponseEntity<>(screenService.updateScreen(screenId, updateScreenRequest), HttpStatus.OK);
    }

    /**
     * Deletes a specific screen.
     */
    @DeleteMapping("/screens/{screenId}")
    public ResponseEntity<Void> deleteScreenById(@PathVariable Long screenId) {
        screenService.deleteScreen(screenId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
