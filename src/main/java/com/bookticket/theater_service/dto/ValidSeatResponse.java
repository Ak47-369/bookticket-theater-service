package com.bookticket.theater_service.dto;

public record ValidSeatResponse(
        Long seatId,
        Boolean isAvailable,
        String seatNumber,
        String seatType,
        Double seatPrice
) {
}
