package com.famousquotes;

public class DataClass {
    private final String quote;
    private final String author;

    public DataClass(String quote, String author){
        this.quote = quote;
        this.author = author;
    }

    public String getQuote(){
        return quote;
    }

    public String getAuthor(){
        return author;
    }
}
