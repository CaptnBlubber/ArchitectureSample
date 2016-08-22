package de.s3xy.architecturesample.search.ui;

import java.util.List;

import de.s3xy.architecturesample.github.model.Repository;
import rx.Observable;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public interface SearchRepositoriesView {
    void showRepositories(List<Repository> repositories);

    void showLoading();

    void hideLoading();

    Observable<CharSequence> getQueryTextObservable();
}
