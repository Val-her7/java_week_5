package dev.val.Hospital_Registration_API.model;

import java.time.LocalDateTime;

public class Visit {

    private String name;
    private String lastName;
    private String reasonOfRegistration;
    private LocalDateTime timestamp;

    public Visit(String name, String lastName, String reasonOfRegistration, LocalDateTime timestamp) {
        this.name = name;
        this.lastName = lastName;
        this.reasonOfRegistration = reasonOfRegistration;
        this.timestamp = timestamp;
    }

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getLastName() {
        return lastName;
    }
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
    public String getReasonOfRegistration() {
        return reasonOfRegistration;
    }
    public void setReasonOfRegistration(String reasonOfRegistration) {
        this.reasonOfRegistration = reasonOfRegistration;
    }
    public LocalDateTime getTimestamp() {
        return timestamp;
    }
    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}