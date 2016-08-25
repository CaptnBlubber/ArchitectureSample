package de.s3xy.architecturesample.search.ui;

import java.util.List;

import de.s3xy.architecturesample.base.BaseNetworkView;
import de.s3xy.architecturesample.github.model.Repository;
import rx.Observable;

/**
 * @author Angelo Rüggeberg <s3xy4ngc@googlemail.com>
 */


public interface SearchRepositoriesView extends BaseNetworkView {
    void showRepositories(List<Repository> repositories);

    Observable<CharSequence> getQueryTextObservable();
}
