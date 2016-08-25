package de.s3xy.architecturesample.base;

import de.s3xy.architecturesample.R;

/**
 * Created by Vlad Fedorenko <vfedo92@gmail.com>
 * 08.25.2016
 */
public enum ErrorType {
    INVALID_EMAIL_FORMAT(R.string.error_invalid_email_format);

    private int mMessageResource;

    ErrorType(int messageResource) {
        mMessageResource = messageResource;
    }

    public int getMessageResource() {
        return mMessageResource;
    }
}
