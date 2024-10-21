package com.BRS.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookImage {
    private Long id;
    private Long bookId;
    private byte[] image;
    private String imageUrl;
}
