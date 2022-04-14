package com.feng.socket.pojo;

public class Work {
    Integer w_id;
    Integer u_id;
    String title;
    String classify;
    Integer wt_id;
    String w_time;



    public Work() {
    }

    public Work(String classify) {
        this.classify = classify;
    }

    public Work(Integer w_id) {
        this.w_id = w_id;
    }

    public Work(Integer w_id, String w_time) {
        this.wt_id = w_id;
        this.w_time = w_time;
    }

    public Work(Integer u_id, String title, String classify, Integer wt_id) {
        this.u_id = u_id;
        this.title = title;
        this.classify = classify;
        this.wt_id = wt_id;
    }

    public Work(Integer w_id, Integer u_id, String title, String classify, Integer wt_id) {
        this.w_id = w_id;
        this.u_id = u_id;
        this.title = title;
        this.classify = classify;
        this.wt_id = wt_id;
    }

    public Integer getW_id() {
        return w_id;
    }

    public void setW_id(Integer w_id) {
        this.w_id = w_id;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getClassify() {
        return classify;
    }

    public void setClassify(String classify) {
        this.classify = classify;
    }

    public Integer getWt_id() {
        return wt_id;
    }

    public void setWt_id(Integer wt_id) {
        this.wt_id = wt_id;
    }

    public String getW_time() {
        return w_time;
    }

    public void setW_time(String w_time) {
        this.w_time = w_time;
    }

    @Override
    public String toString() {
        return "Work{" +
                "w_id=" + w_id +
                ", u_id=" + u_id +
                ", title='" + title + '\'' +
                ", classify='" + classify + '\'' +
                ", wt_id=" + wt_id +
                ", w_time='" + w_time + '\'' +
                '}';
    }
}
