package com.feng.socket.pojo;

public class Friend {
    Integer me;
    Integer who;

    public Friend() {
    }

    public Friend(Integer me) {
        this.me = me;
    }

    public Friend(Integer me, Integer who) {
        this.me = me;
        this.who = who;
    }

    public Integer getMe() {
        return me;
    }

    public void setMe(Integer me) {
        this.me = me;
    }

    public Integer getWho() {
        return who;
    }

    public void setWho(Integer who) {
        this.who = who;
    }

    @Override
    public String toString() {
        return "friend{" +
                "me='" + me + '\'' +
                ", who='" + who + '\'' +
                '}';
    }
}
