package com.bookticket.theater_service.controller;

import com.bookticket.theater_service.dto.CreateScreenRequest;
import com.bookticket.theater_service.dto.ScreenResponse;
import com.bookticket.theater_service.service.ScreenService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
}
