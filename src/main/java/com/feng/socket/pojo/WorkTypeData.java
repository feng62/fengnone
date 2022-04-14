package com.feng.socket.pojo;

public class WorkTypeData {
    Integer wt_id;
    String type;
    String icon;
    String state;
    String router;
    public WorkTypeData() {
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Integer getWt_id() {
        return wt_id;
    }

    public void setWt_id(Integer wt_id) {
        this.wt_id = wt_id;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    @Override
    public String toString() {
        return "WorkTypeData{" +
                "wt_id=" + wt_id +
                ", type='" + type + '\'' +
                ", icon='" + icon + '\'' +
                ", state='" + state + '\'' +
                ", router='" + router + '\'' +
                '}';
    }
}
