package com.BRS.entity;

import lombok.Data;

@Data
public class Book {
    
    private Long id;
    
    private String title;
    private String author;
    private String iSBN;
    private String publicationDate;
    private String genre;
    private String type;
    private String registrationDate;
    private int numberOfCopies;
    private String edition;

    // Getters and Setters
}
