package com.BRS.entity;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Log {

    private Integer id;
    private LocalDateTime time;
    private String level;
    private String message;
    private String exception;

}
