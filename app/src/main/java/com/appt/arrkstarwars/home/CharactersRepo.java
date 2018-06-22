package com.appt.arrkstarwars.home;

import com.appt.arrkstarwars.models.Character;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;

public class CharactersRepo {
    private final StarWarsService starWarsService;

    public CharactersRepo(StarWarsService starWarsService) {
        // Would normally create using Dagger but it seems overkill for the scope of this app
        //retrofit().create(StarWarsService.class);
        this.starWarsService = starWarsService;
    }

    @SuppressWarnings("Convert2MethodRef")
    public Single<List<Character>> getCharacters() {
        return starWarsService.getPeople()
                .filter(response -> response.getResults() != null)
                .map(response -> response.getResults())
                .defaultIfEmpty(new ArrayList<>())
                .toSingle();
    }
}
