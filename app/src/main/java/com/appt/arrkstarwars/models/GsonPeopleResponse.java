package com.appt.arrkstarwars.models;

import java.util.List;

public class GsonPeopleResponse {
    private final List<Character> results;

    public GsonPeopleResponse(List<Character> results) {
        this.results = results;
    }

    public List<Character> getResults() {
        return results;
    }
}
