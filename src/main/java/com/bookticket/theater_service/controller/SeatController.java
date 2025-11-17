package com.bookticket.theater_service.controller;

import com.bookticket.theater_service.dto.*;
import com.bookticket.theater_service.service.SeatService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@Slf4j
public class SeatController {
    private final SeatService seatService;

    public SeatController(SeatService seatService) {
        this.seatService = seatService;
    }

    @PostMapping("/screens/{screenId}/seats")
    public ResponseEntity<List<SeatTemplateResponse>> createSeatTemplate(@PathVariable Long screenId,
                                                                         @Valid @RequestBody List<CreateSeatTemplateRequest> createSeatTemplateRequests) {
        return new ResponseEntity<>(seatService.createSeatTemplate(screenId, createSeatTemplateRequests), HttpStatus.CREATED);
    }

    @GetMapping("/screens/{screenId}/seats")
    public ResponseEntity<List<SeatTemplateResponse>> getSeatTemplateByScreenId(@PathVariable Long screenId) {
        return new ResponseEntity<>(seatService.getSeatTemplateByScreenId(screenId), HttpStatus.OK);
    }

    @PostMapping("/shows/internal/seats/verify")
    public ResponseEntity<List<ValidSeatResponse>> verifySeats(@Valid @RequestBody VerfiySeatRequest verifySeatRequest) {
        List<ValidSeatResponse> validSeatResponses = seatService.getSeatByShowAndSeatIds(verifySeatRequest.showId(), verifySeatRequest.seatIds());
        if(validSeatResponses.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        log.info("Validated {} seats", validSeatResponses.toString());
        if(validSeatResponses.size() != verifySeatRequest.seatIds().size()) {
            return new ResponseEntity<>(validSeatResponses,HttpStatus.PARTIAL_CONTENT);
        }
        return new ResponseEntity<>(validSeatResponses, HttpStatus.OK);
    }

    @PostMapping("/shows/internal/seats/lock")
    public ResponseEntity<List<ValidSeatResponse>> lockSeats(@Valid @RequestBody LockSeatsRequest lockSeatsRequest) {
        return new ResponseEntity<>(seatService.lockSeatsByShowAndSeatIds(lockSeatsRequest), HttpStatus.OK);
    }

    @PostMapping("/shows/internal/seats/release")
    public ResponseEntity<List<ValidSeatResponse>> releaseSeats(@Valid @RequestBody ReleaseSeatsRequest releaseSeatsRequest) {
        return new ResponseEntity<>(seatService.releaseSeatsByShowAndSeatIds(releaseSeatsRequest), HttpStatus.OK);
    }

    @PostMapping("/shows/internal/seats/book")
    public ResponseEntity<List<ValidSeatResponse>> bookSeats(@Valid @RequestBody BookSeatsRequest bookSeatsRequest) {
        return new ResponseEntity<>(seatService.bookSeatsByShowAndSeatIds(bookSeatsRequest), HttpStatus.OK);
    }

    @PatchMapping("/screens/{screenId}/seats/{seatId}/price")
    public ResponseEntity<SeatTemplateResponse> updateSeatPrice(@PathVariable Long screenId, @PathVariable Long seatId,
                                                                @RequestParam Double price) {
        return new ResponseEntity<>(seatService.updateSeatPrice(screenId, seatId, price), HttpStatus.OK);
    }

    @PatchMapping("/screens/{screenId}/seats/{seatId}/type")
    public ResponseEntity<SeatTemplateResponse> updateSeatType(@PathVariable Long screenId, @PathVariable Long seatId,
                                                                @RequestParam String seatType) {
        return new ResponseEntity<>(seatService.updateSeatType(screenId, seatId, seatType), HttpStatus.OK);
    }

    @PutMapping("/screens/{screenId}/seats")
    public ResponseEntity<List<SeatTemplateResponse>> updateSeatTemplate(@PathVariable Long screenId,
                                                                                @Valid @RequestBody List<CreateSeatTemplateRequest> createSeatTemplateRequests) {
        return new ResponseEntity<>(seatService.updateSeatTemplate(screenId, createSeatTemplateRequests), HttpStatus.OK);
    }

    @DeleteMapping("/screens/{screenId}/seats/{seatId}")
    public ResponseEntity<Void> deleteSeat(@PathVariable Long screenId, @PathVariable Long seatId) {
        seatService.deleteSeat(screenId, seatId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
