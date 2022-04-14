package com.feng.socket.pojo;

public class Admi {
    Integer a_id;
    String a_name;
    String a_account;
    String a_password;
    String a_phone;
    String a_email;
    Integer g_id;
    String g_class;

    @Override
    public String toString() {
        return "Admi{" +
                "a_id=" + a_id +
                ", a_name='" + a_name + '\'' +
                ", a_account='" + a_account + '\'' +
                ", a_password='" + a_password + '\'' +
                ", a_phone='" + a_phone + '\'' +
                ", a_email='" + a_email + '\'' +
                ", g_id=" + g_id +
                ", g_class='" + g_class + '\'' +
                '}';
    }

    public Admi() {
    }

    public Admi(String a_account, String a_password) {
        this.a_account = a_account;
        this.a_password = a_password;
    }

    public Integer getA_id() {
        return a_id;
    }

    public void setA_id(Integer a_id) {
        this.a_id = a_id;
    }

    public String getA_name() {
        return a_name;
    }

    public void setA_name(String a_name) {
        this.a_name = a_name;
    }

    public String getA_account() {
        return a_account;
    }

    public void setA_account(String a_account) {
        this.a_account = a_account;
    }

    public String getA_password() {
        return a_password;
    }

    public void setA_password(String a_password) {
        this.a_password = a_password;
    }

    public String getA_phone() {
        return a_phone;
    }

    public void setA_phone(String a_phone) {
        this.a_phone = a_phone;
    }

    public String getA_email() {
        return a_email;
    }

    public void setA_email(String a_email) {
        this.a_email = a_email;
    }

    public Integer getG_id() {
        return g_id;
    }

    public void setG_id(Integer g_id) {
        this.g_id = g_id;
    }
    public String getG_class() {
        return g_class;
    }

    public void setG_class(String g_class) {
        this.g_class = g_class;
    }
}
