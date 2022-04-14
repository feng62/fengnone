package com.feng.socket.pojo;


public class User {

    Integer u_id ;
    String name;
    String account;
    String password;
    String birthday;
    String sex;
    String phone;
    String email;
    String address;
    String zan;
    Integer t_id;
    String head;
    String about;
    String background;

    public User() {
    }

    public User(Integer u_id) {
        this.u_id = u_id;
    }

    public User(String account, String password) {
        this.account = account;
        this.password = password;
    }

    public User( String name, String account, String password, String email) {
        this.name = name;
        this.account = account;
        this.password = password;
        this.email = email;
    }

    public User(Integer u_id, String name, String account, String password, String birthday, String sex, String phone, String email, String address, String zan, Integer t_id, String head, String about, String background) {
        this.u_id = u_id;
        this.name = name;
        this.account = account;
        this.password = password;
        this.birthday = birthday;
        this.sex = sex;
        this.phone = phone;
        this.email = email;
        this.address = address;
        this.zan = zan;
        this.t_id = t_id;
        this.head = head;
        this.about = about;
        this.background = background;
    }

    public Integer getU_id() {
        return u_id;
    }

    public void setU_id(Integer u_id) {
        this.u_id = u_id;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getZan() {
        return zan;
    }

    public void setZan(String zan) {
        this.zan = zan;
    }

    public Integer getT_id() {
        return t_id;
    }

    public void setT_id(Integer t_id) {
        this.t_id = t_id;
    }

    public String getHead() {
        return head;
    }

    public void setHead(String head) {
        this.head = head;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    @Override
    public String toString() {
        return "User{" +
                "u_id=" + u_id +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                ", password='" + password + '\'' +
                ", birthday='" + birthday + '\'' +
                ", sex='" + sex + '\'' +
                ", phone='" + phone + '\'' +
                ", email='" + email + '\'' +
                ", address='" + address + '\'' +
                ", zan='" + zan + '\'' +
                ", t_id=" + t_id +
                ", head='" + head + '\'' +
                ", about='" + about + '\'' +
                ", background='" + background + '\'' +
                '}';
    }
}
