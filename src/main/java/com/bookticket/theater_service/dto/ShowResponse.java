package com.bookticket.theater_service.dto;

import java.time.LocalDateTime;

public record ShowResponse(
        LocalDateTime startTime,
        LocalDateTime endTime,
        String movieId,
        String movieTitle,
        String movieBannerUrl
) {
}
