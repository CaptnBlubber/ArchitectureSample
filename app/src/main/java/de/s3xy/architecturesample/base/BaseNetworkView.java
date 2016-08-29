package de.s3xy.architecturesample.base;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.25.2016
 */
public interface BaseNetworkView {
    void showLoading();
    void hideLoading();
    void showError(ErrorType type);
}
