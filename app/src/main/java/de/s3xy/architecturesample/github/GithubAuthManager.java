package de.s3xy.architecturesample.github;

import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import javax.inject.Inject;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.25.2016
 */
public class GithubAuthManager {
    private static final String KEY_SHOULD_BE_UNAUTHORIZED = "KEY_SHOULD_BE_UNAUTHORIZED";
    private static final String KEY_ACCESS_TOKEN = "KEY_ACCESS_TOKEN";

    private SharedPreferences mPrefs;

    @Inject
    public GithubAuthManager(SharedPreferences prefs) {
        mPrefs = prefs;
    }

    public void resetAuthentication() {
        mPrefs.edit().clear().apply();
    }

    public boolean shouldBeUnauthorized() {
        return mPrefs.getBoolean(KEY_SHOULD_BE_UNAUTHORIZED, false);
    }

    public void setShouldBeUnauthorized(boolean b) {
        mPrefs.edit().putBoolean(KEY_SHOULD_BE_UNAUTHORIZED, b).apply();
    }

    @Nullable
    public String getAccessToken() {
        return mPrefs.getString(KEY_ACCESS_TOKEN, null);
    }

    public void saveAccessToken(String token) {
        mPrefs.edit().putString(KEY_ACCESS_TOKEN, token).apply();
    }
}
