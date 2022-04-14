package com.feng.socket.pojo.WebSocket;

public class Fa {
    String name;
    String state;
    String data;
    public Fa() {
    }

    public Fa(String name, String state) {
        this.name = name;
        this.state = state;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "Fa{" +
                "name='" + name + '\'' +
                ", state='" + state + '\'' +
                ", data='" + data + '\'' +
                '}';
    }
}
