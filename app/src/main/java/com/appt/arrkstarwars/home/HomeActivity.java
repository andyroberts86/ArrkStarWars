package com.appt.arrkstarwars.home;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.appt.arrkstarwars.R;
import com.appt.arrkstarwars.models.Character;
import com.appt.arrkstarwars.network.StarWarsService;

import java.util.List;

import io.reactivex.Observable;

import static com.appt.arrkstarwars.network.RetrofitProvider.provideRetrofit;

public class HomeActivity extends AppCompatActivity implements HomePresenter.View {
    /**
     * I'd normally handle all the DI using Dagger 2 with Android Injectors but that seems overkill
     * given the size of the app
     */
    private CharactersRepo repo = new CharactersRepo(provideRetrofit().create(StarWarsService.class));
    private HomePresenter presenter = new HomePresenter(repo);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.attach(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        presenter.detach();
    }

    @Override
    public void showLoading(boolean show) {

    }

    @Override
    public void showCharacters(List<Character> characters) {

    }

    @Override
    public void showError(boolean show) {

    }

    @Override
    public Observable<Object> retryClicks() {
        return null;
    }
}
