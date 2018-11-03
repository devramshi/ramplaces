package com.ramapps.ramplaces.common;

/**
 * @param <T> The expected response type
 * @author Ramsheed on 11/03/2018.
 */
public interface Callback<T> {
    /**
     * Execute the callback with response object.
     *
     * @param response Response object for the callback
     */
    void execute(final T response);
}