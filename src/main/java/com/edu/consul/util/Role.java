package com.edu.consul.util;

import lombok.Getter;

@Getter
public enum Role {
    STUDENT("STUDENT"),
    ADMIN("ADMIN"),
    CONSULTANT("CONSULTANT");
    private final String displayName;
    Role(String displayName) {
        this.displayName = displayName;
    }
}
