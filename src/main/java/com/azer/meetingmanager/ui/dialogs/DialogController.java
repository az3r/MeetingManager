package com.azer.meetingmanager.ui.dialogs;

import com.azer.meetingmanager.ui.OnCompleteListener;

import javafx.stage.Stage;

/**
 * abstract class to manage dialog result
 * @param <T> the result type
 */
public abstract class DialogController<T> {

    protected static byte STATE_COMPLETED = 1;
    protected static byte STATE_ERROR = 2;
    protected static byte STATE_CANCELLED = 4;

    /**
     * the container that displays this dialog
     */
    private Stage container;
    private T result;
    private Exception error;
    private byte state = STATE_CANCELLED;

    protected void error(Exception error) {
        this.state = STATE_ERROR;
        this.error = error;
    }

    protected void cancelled() {
        this.state = STATE_CANCELLED;

    }

    protected void success(T result) {
        this.state = STATE_COMPLETED;
        this.result = result;
    }

    public T getResult() {
        return result;
    }

    public Exception getError() {
        return error;
    }

    public boolean isCompleted() {
        return (state & STATE_COMPLETED) == STATE_COMPLETED;
    }

    public boolean isCancelled() {
        return (state & STATE_CANCELLED) == STATE_CANCELLED;
    }

    public boolean isError() {
        return (state & STATE_ERROR) == STATE_ERROR;
    }

    public Stage getContainer() {
        return container;
    }

    public void setContainer(Stage container) {
        this.container = container;
    }

    public void showAndWait(OnCompleteListener<T> callback) {
        if (container == null)
            throw new NullPointerException("there is no container to display this dialog");

        getContainer().showAndWait();

        if (isCompleted())
            callback.onCompleted(getResult());
        else if (isError())
            callback.onError(getError());
        else
            callback.onCancelled();
    }
}