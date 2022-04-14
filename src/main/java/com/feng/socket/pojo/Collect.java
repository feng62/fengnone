package com.feng.socket.pojo;

public class Collect {
    Integer me ;
    Integer w_id;



    public Collect(Integer me, Integer w_id) {
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



    @Override
    public String toString() {
        return "Collect{" +
                "me=" + me +
                ", w_id=" + w_id +
                '}';
    }
}
