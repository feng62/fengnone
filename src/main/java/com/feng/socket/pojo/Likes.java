package com.feng.socket.pojo;

public class Likes {
    Integer me;
    Integer w_id;

    public Likes(Integer me, Integer w_id) {
        this.me = me;
        this.w_id = w_id;
    }

    public Integer getMe() {
        return me;
    }

    public void setMe(Integer me) {
        this.me = me;
    }

    public Integer getW_id() {
        return w_id;
    }

    public void setW_id(Integer w_id) {
        this.w_id = w_id;
    }
}
