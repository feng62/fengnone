package com.feng.socket.pojo.WebSocket;

public class Code<T> {
    /**
     *  0是发送的消息
     *  1是在线的好友
     *  2是me在线的好友
     * */
    Integer code;
    T data;

    public Code(Integer code, T data) {
        this.code = code;
        this.data = data;
    }


    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public T getT() {
        return data;
    }

    public void setT(T data) {
        this.data = data;
    }
}
