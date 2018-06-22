package com.appt.arrkstarwars.home;

import com.appt.arrkstarwars.models.Character;
import com.jakewharton.rxrelay2.PublishRelay;

import org.junit.Before;
import org.junit.Test;
import org.mockito.InOrder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.plugins.RxAndroidPlugins;
import io.reactivex.plugins.RxJavaPlugins;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.Single.error;
import static io.reactivex.Single.just;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class HomePresenterTest {
    private final CharactersRepo repo = mock(CharactersRepo.class);
    private final HomePresenter.View view = mock(HomePresenter.View.class);
    private final HomePresenter presenter = new HomePresenter(repo);
    private final InOrder inOrder = inOrder(view);
    private final List<Character> characters = new ArrayList<>();
    private final PublishRelay<Object> retryClicks = PublishRelay.create();

    @Before
    public void setUp() {
        // These could be in a TestRule but this is the only test that requires them
        RxJavaPlugins.setIoSchedulerHandler(s -> Schedulers.trampoline());
        RxAndroidPlugins.setInitMainThreadSchedulerHandler(s -> Schedulers.trampoline());

        when(view.retryClicks()).thenReturn(retryClicks);
    }

    @Test
    public void shouldShowLoading_whenAppFirstLaunches() {
        when(repo.getCharacters()).thenReturn(Single.never());
        presenter.attach(view);
        verify(view).showLoading(true);
    }

    @Test
    public void shouldShowCharacters_whenDataLoaded() {
        when(repo.getCharacters()).thenReturn(just(characters));
        presenter.attach(view);
        inOrder.verify(view).showLoading(true);
        inOrder.verify(view).showLoading(false);
        verify(view).showCharacters(characters);
    }

    @Test
    public void shouldShowError_whenDataFailsToLoad() {
        when(repo.getCharacters()).thenReturn(error(new RuntimeException()));
        presenter.attach(view);
        inOrder.verify(view).showLoading(true);
        inOrder.verify(view).showLoading(false);
        verify(view).showError(true);
    }

    @Test
    public void shouldReloadData_whenRetryClicked() {
        when(repo.getCharacters()).thenReturn(error(new RuntimeException()));
        presenter.attach(view);
        reset(view);

        when(repo.getCharacters()).thenReturn(just(characters));
        retryClicks.accept(new Object());
        verify(view).showError(false);
        inOrder.verify(view).showLoading(true);
        inOrder.verify(view).showLoading(false);
        verify(view).showCharacters(characters);
    }
}