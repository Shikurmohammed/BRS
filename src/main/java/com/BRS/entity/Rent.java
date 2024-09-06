package com.BRS.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Rent {
    private Long id;
    private Long bookId;
    private Long clientId;
    private LocalDateTime rentDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    private Float rentAmount;
    private String status;

}
