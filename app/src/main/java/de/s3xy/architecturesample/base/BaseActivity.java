package de.s3xy.architecturesample.base;

import android.app.ProgressDialog;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;

import butterknife.Unbinder;
import de.s3xy.architecturesample.R;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.25.2016
 */
public abstract class BaseActivity extends AppCompatActivity implements BaseNetworkView {
    protected abstract Unbinder getUnbinder();
    protected abstract Presenter getPresenter();

    private ProgressDialog mProgressDialog;

    @Override
    protected void onDestroy() {
        super.onDestroy();
        getPresenter().detachView();
        getUnbinder().unbind();
    }

    @Override
    public void showError(ErrorType type) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.error_title)
                .setMessage(type.getMessageResource())
                .setPositiveButton(android.R.string.ok, null);
        builder.create().show();
    }

    @Override
    public void showLoading() {
        if (mProgressDialog == null) {
            mProgressDialog = ProgressDialog.show(this, getString(R.string.wait), getString(R.string.working), true, false);
        } else if (!mProgressDialog.isShowing()) {
            mProgressDialog.show();
        }
    }

    @Override
    public void hideLoading() {
        if (mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }
}
