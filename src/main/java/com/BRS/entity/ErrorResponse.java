package com.BRS.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ErrorResponse {
    String message;
    String details;
    Integer status;
    LocalDateTime timestamp;

    public ErrorResponse(String message, String details, Integer status) {
        this.message = message;
        this.details = details;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

}
