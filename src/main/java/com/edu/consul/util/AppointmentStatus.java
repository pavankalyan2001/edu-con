package com.edu.consul.util;

import lombok.Getter;

@Getter
public enum AppointmentStatus {
    PENDING("PENDING"),
    CONFIRMED("CONFIRMED"),
    COMPLETED("COMPLETED"),
    CANCELED("CANCELLED");
    private final String displayName;
    AppointmentStatus(String displayName) {
        this.displayName = displayName;
    }
}
