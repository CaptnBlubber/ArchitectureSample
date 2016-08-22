package de.s3xy.architecturesample.base;

public interface Presenter<T> {
    void attachView(T view);

    void dettachView();
}