package com.feng.socket.pojo;

public class WorkData {

    Integer u_id;
    Integer w_id;
    String name;
    String account;
    String title;
    String type;
    String time;
    String time1;
    String time2;
    Integer whichPage;
    Integer Page;

    public Integer getWhichPage() {
        return whichPage;
    }

    public void setWhichPage(Integer whichPage) {
        this.whichPage = whichPage;
    }

    public Integer getPage() {
        return Page;
    }

    public void setPage(Integer page) {
        Page = page;
    }

    public WorkData() {
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getTime1() {
        return time1;
    }

    public void setTime1(String time1) {
        this.time1 = time1;
    }

    public String getTime2() {
        return time2;
    }

    public void setTime2(String time2) {
        this.time2 = time2;
    }

    @Override
    public String toString() {
        return "WorkData{" +
                "u_id=" + u_id +
                ", w_id=" + w_id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", title='" + title + '\'' +
                ", type='" + type + '\'' +
                ", time='" + time + '\'' +
                ", time1='" + time1 + '\'' +
                ", time2='" + time2 + '\'' +
                ", whichPage=" + whichPage +
                ", Page=" + Page +
                '}';
    }
}
