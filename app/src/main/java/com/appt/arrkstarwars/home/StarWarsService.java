package com.appt.arrkstarwars.home;

import com.appt.arrkstarwars.models.GsonPeopleResponse;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface StarWarsService {
    @GET("/people")
    Single<GsonPeopleResponse> getPeople();
}
