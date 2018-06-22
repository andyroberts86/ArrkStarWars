package com.appt.arrkstarwars.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.appt.arrkstarwars.R;
import com.appt.arrkstarwars.detail.DetailActivity;
import com.appt.arrkstarwars.models.Character;
import com.appt.arrkstarwars.network.StarWarsService;

import java.util.List;

import io.reactivex.Observable;

import static android.view.View.GONE;
import static android.view.View.VISIBLE;
import static com.appt.arrkstarwars.network.RetrofitProvider.provideRetrofit;
import static com.jakewharton.rxbinding2.view.RxView.clicks;

public class HomeActivity extends AppCompatActivity implements HomePresenter.View {
    /**
     * I'd normally handle all the DI using Dagger 2 with Android Injectors but that seems overkill
     * given the size of the app
     */
    private CharactersRepo repo = new CharactersRepo(provideRetrofit().create(StarWarsService.class));
    private HomePresenter presenter = new HomePresenter(repo);
    private View loading;
    private View error;
    private RecyclerView recycler;
    private View retry;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        recycler = findViewById(R.id.characters_list);
        error = findViewById(R.id.error);
        retry = findViewById(R.id.retry);
        loading = findViewById(R.id.loading);

        recycler.addItemDecoration(new DividerItemDecoration(this, DividerItemDecoration.VERTICAL));

        presenter.attach(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.detach();
    }

    @Override
    public void showLoading(boolean show) {
        loading.setVisibility(show ? VISIBLE : GONE);
    }

    @Override
    public void showCharacters(List<Character> characters) {
        recycler.setVisibility(VISIBLE);
        recycler.setAdapter(new CharactersAdapter(characters, character -> {
            Intent intent = new Intent(this, DetailActivity.class);
            intent.putExtra(DetailActivity.NAME_KEY, character.getName());
            intent.putExtra(DetailActivity.HEIGHT_KEY, character.getHeight());
            intent.putExtra(DetailActivity.MASS_KEY, character.getMass());
            intent.putExtra(DetailActivity.CREATED_KEY, character.getCreated());
            startActivity(intent);
        }));
    }

    @Override
    public void showError(boolean show) {
        error.setVisibility(show ? VISIBLE : GONE);
    }

    @Override
    public Observable<Object> retryClicks() {
        return clicks(retry);
    }
}
