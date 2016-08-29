package de.s3xy.architecturesample.login.ui;

import de.s3xy.architecturesample.base.BaseNetworkView;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.25.2016
 */
public interface LoginView extends BaseNetworkView {
    void showWebViewForLogin(String url);
    void goToSearchScreen();
}
