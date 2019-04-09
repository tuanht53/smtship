package com.app.smt.shiper.data.model;

public class DataResponse<T> extends ApiError {

    private T data;

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

}