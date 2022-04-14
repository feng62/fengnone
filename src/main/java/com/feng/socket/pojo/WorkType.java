package com.feng.socket.pojo;

public class WorkType {

    Integer wt_id;
    String wt_type;
    String icon;
    String router;

    public WorkType(Integer wt_id, String wt_type, String icon) {
        this.wt_id = wt_id;
        this.wt_type = wt_type;
        this.icon = icon;
    }

    public Integer getWt_id() {
        return wt_id;
    }

    public void setWt_id(Integer wt_id) {
        this.wt_id = wt_id;
    }

    public String getWt_type() {
        return wt_type;
    }

    public void setWt_type(String wt_type) {
        this.wt_type = wt_type;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public String getRouter() {
        return router;
    }

    public void setRouter(String router) {
        this.router = router;
    }

    @Override
    public String toString() {
        return "WorkType{" +
                "wt_id=" + wt_id +
                ", wt_type='" + wt_type + '\'' +
                ", icon='" + icon + '\'' +
                '}';
    }
}
