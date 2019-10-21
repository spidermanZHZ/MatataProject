package com.example.administrator.matata_android.httputils;

/**
 * 网络请求数据bean 最外层封装
 * @param <T>
 *     定义固定的JSON格式{ "status": "error","code": 400,"message": "账号密码错误"}
 */
public class BaseBeanResponse<T> {

    private int code;
    private String status;
    private T message;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public T getMessage() {
        return message;
    }

    public void setMessage(T message) {
        this.message = message;
    }
}
