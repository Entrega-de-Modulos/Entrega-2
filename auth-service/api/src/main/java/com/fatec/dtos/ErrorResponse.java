package com.fatec.dtos;

import java.time.Instant;
import java.time.LocalDateTime;

public record ErrorResponse(
    String message,
    int status,
    String path,
    LocalDateTime timestamp
) {
}
