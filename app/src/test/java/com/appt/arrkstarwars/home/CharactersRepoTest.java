package com.appt.arrkstarwars.home;

import com.appt.arrkstarwars.models.Character;
import com.appt.arrkstarwars.models.GsonPeopleResponse;

import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.observers.TestObserver;

import static junit.framework.Assert.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class CharactersRepoTest {
    private final StarWarsService service = mock(StarWarsService.class);
    private final String name1 = "Luke";
    private final Integer height1 = 999;
    private final Integer weight1 = 123;
    private final Date date1 = new Date();
    private final Character character1 = new Character(name1, height1, weight1, date1);
    private final String name2 = "Darth";
    private final Integer height2 = 888;
    private final Integer weight2 = 222;
    private final Date date2 = new Date();
    private final Character character2 = new Character(name2, height2, weight2, date2);

    private CharactersRepo repo;

    @Before
    public void setUp() {
        repo = new CharactersRepo(service);
    }

    @Test
    public void shouldReturnCharacterList_whenApiReturnsResults() {
        List<Character> characters = new ArrayList<>();
        characters.add(character1);
        characters.add(character2);

        when(service.getPeople()).thenReturn(response(characters));

        TestObserver<List<Character>> test = repo.getCharacters().test();
        test.assertComplete();
        test.assertValue(characters);
    }

    @Test
    public void shouldReturnEmptyList_whenApiReturnsNoResults() {
        when(service.getPeople()).thenReturn(response(null));

        TestObserver<List<Character>> test = repo.getCharacters().test();
        test.assertComplete();
        test.assertValueCount(1);
        List<Character> result = test.values().get(0);
        assertEquals(0, result.size());
    }

    private Single<GsonPeopleResponse> response(List<Character> characters) {
        GsonPeopleResponse response = new GsonPeopleResponse(characters);
        return Single.just(response);
    }
}