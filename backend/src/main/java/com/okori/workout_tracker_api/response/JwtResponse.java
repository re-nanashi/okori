package com.okori.workout_tracker_api.response;

import java.util.Map;

public class JwtResponse {
    private Long id;
    private Map<String, String> token;

    public JwtResponse(Long id, Map<String, String> token) {
        this.id = id;
        this.token = token;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<String, String> getToken() {
        return token;
    }

    public void setToken(Map<String, String> token) {
        this.token = token;
    }
}