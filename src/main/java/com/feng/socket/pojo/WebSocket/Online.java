package com.feng.socket.pojo.WebSocket;

public class Online {
    String state;
    String user;

    public Online(String state, String user) {
        this.state = state;
        this.user = user;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
