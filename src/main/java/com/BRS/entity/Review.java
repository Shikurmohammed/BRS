package com.BRS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Review {// to receive ratings, and comments extra about the book
    private Long id;
    private Long bookId;
    private Long userId;
    private int rating;//1-5
    private String comment;
}
