package com.feng.socket.pojo.WebSocket;


import io.swagger.annotations.ApiModelProperty;

public class Socket {

    @ApiModelProperty(value = "谁发的")
    String from;

    @ApiModelProperty(value = "发给谁")
    String to;

    @ApiModelProperty(value = "发送的消息")
    String message;

    /**
     *  sb:发送给个人
     *  broadcast:广播发送
     * */
    @ApiModelProperty(value = "发送的类型")
    String type;

    public Socket() {
    }

    public Socket(String from, String to, String message, String type) {
        this.from = from;
        this.to = to;
        this.message = message;
        this.type = type;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
