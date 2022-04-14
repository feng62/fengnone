package com.feng.socket.pojo.main;

public class LikeWork {
    Integer u_id;
    Integer w_id;
    Boolean like;

    public LikeWork() {
    }

    public LikeWork(Integer u_id, Integer w_id) {
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public Integer getW_id() {
        return w_id;
    }

    public void setW_id(Integer w_id) {
        this.w_id = w_id;
    }

    public Boolean getLike() {
        return like;
    }

    public void setLike(Boolean like) {
        this.like = like;
    }

    @Override
    public String toString() {
        return "LikeWork{" +
                "u_id=" + u_id +
                ", w_id=" + w_id +
                ", like=" + like +
                '}';
    }
}
