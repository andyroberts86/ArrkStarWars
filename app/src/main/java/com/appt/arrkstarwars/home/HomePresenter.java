package com.appt.arrkstarwars.home;

import com.appt.arrkstarwars.models.Character;

import java.util.List;

import io.reactivex.Observable;
import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

import static io.reactivex.Observable.just;

/**
 * I'd normally have a generic base class that handles the subscription lifecycle but as I only have
 * a single Presenter in this app it seems redundant
 */
public class HomePresenter {
    private final CompositeDisposable disposable = new CompositeDisposable();
    private final CharactersRepo repo;
    private View view;

    HomePresenter(CharactersRepo repo) {
        this.repo = repo;
    }

    private Observable<Action> loadCharacters() {
        return repo.getCharacters()
                .subscribeOn(Schedulers.io())
                .toObservable()
                .<Action>map(ShowCharacters::new)
                .observeOn(AndroidSchedulers.mainThread())
                .startWith(new ShowLoading())
                .onErrorReturn(t -> new ShowError());
        // NB: For a real app I'd have more sophisticated error handling, but it wasn't in the spec
    }

    protected void attach(View view) {
        this.view = view;

        disposable.add(just(new Object())
                .mergeWith(view.retryClicks())
                .flatMap(i -> loadCharacters())
                .subscribe(Action::render));
    }

    protected void detach() {
        disposable.clear();
    }

    interface View {

        void showLoading(boolean show);

        void showCharacters(List<Character> characters);

        void showError(boolean show);

        Observable<Object> retryClicks();
    }

    /**
     * Recently I've been doing MVI in Kotlin and this Action class (and its subclasses) somewhat
     * replicate that. I'd normally avoid the code duplication by making an immutable ViewModel
     * class but that seemed like excessive complexity for this small app.
     */
    private abstract class Action {
        abstract void render();
    }

    private class ShowLoading extends Action {

        @Override
        void render() {
            HomePresenter.this.view.showLoading(true);
            HomePresenter.this.view.showError(false);
        }
    }

    private class ShowError extends Action {
        @Override
        void render() {
            HomePresenter.this.view.showLoading(false);
            HomePresenter.this.view.showError(true);
        }
    }

    private class ShowCharacters extends Action {
        private final List<Character> characters;

        private ShowCharacters(List<Character> characters) {
            this.characters = characters;
        }

        @Override
        void render() {
            HomePresenter.this.view.showLoading(false);
            HomePresenter.this.view.showCharacters(characters);
        }
    }
}
