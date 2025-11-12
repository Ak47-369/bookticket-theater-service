package com.bookticket.theater_service.dto;

import com.bookticket.theater_service.enums.ScreenType;
import jakarta.validation.constraints.NotNull;

public record CreateScreenRequest(
        @NotNull(message = "Screen Name is required")
        String name,
        @NotNull(message = "Theater Id is required")
        Long theaterId,
        @NotNull(message = "Rows are required")
        int rows,
        @NotNull(message = "Columns are required")
        int columns,
        ScreenType screenType
) {
}
