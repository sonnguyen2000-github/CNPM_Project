package model;


import java.sql.Date;

public class User{
    private String username, password, fullname, address, phone;
    private int priority;
    private Date dob;

    public User(){}

    public User(String username, String password, int priority){
        this.username = username;
        this.password = password;
        this.priority = priority;
    }

    public User(String username, String password, String fullname, String address, String phone, int priority,
                Date dob){
        this.username = username;
        this.password = password;
        this.fullname = fullname;
        this.address = address;
        this.phone = phone;
        this.priority = priority;
        this.dob = dob;
    }

    @Override
    public String toString(){
        return getUsername();
    }

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }

    public String getFullname(){
        return fullname;
    }

    public void setFullname(String fullname){
        this.fullname = fullname;
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public String getPhone(){
        return phone;
    }

    public void setPhone(String phone){
        this.phone = phone;
    }

    public int getPriority(){
        return priority;
    }

    public void setPriority(int priority){
        this.priority = priority;
    }

    public Date getDob(){
        return dob;
    }

    public void setDob(Date dob){
        this.dob = dob;
    }
}
