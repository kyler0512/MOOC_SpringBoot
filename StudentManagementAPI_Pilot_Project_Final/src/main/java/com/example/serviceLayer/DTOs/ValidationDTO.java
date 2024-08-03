package com.example.serviceLayer.DTOs;

public class ValidationDTO {
    private String source;
    private String destination;
    private String message;

    public ValidationDTO(String source, String destination, String message)
    {
        this.source = source;
        this.destination = destination;
        this.message = message;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getDestination() {
        return destination;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
