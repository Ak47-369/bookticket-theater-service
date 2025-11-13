package com.bookticket.theater_service.controller;

import com.bookticket.theater_service.dto.CreateSeatTemplateRequest;
import com.bookticket.theater_service.dto.SeatTemplateResponse;
import com.bookticket.theater_service.service.SeatService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
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
