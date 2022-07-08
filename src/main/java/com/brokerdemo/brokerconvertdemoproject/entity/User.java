package com.brokerdemo.brokerconvertdemoproject.entity;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Date;
import java.util.List;

@Document("users")
public class User {
    @Id
    private String userName;
    private String passWord;
    private Date registerDate;
    private boolean isDisable;
    private Date lastLogin;
    private String firstName;
    private String lastName;
    private String email;
    private List<String> privilage;

    public User() {

    }

    public List<String> getPrivilage() {
        return privilage;
    }

    public User(String userName, String passWord, Date registerDate, boolean isDisable, String firstName, String lastName, String email) {
        this.userName = userName;
        this.passWord = passWord;
        this.registerDate = registerDate;
        this.isDisable = isDisable;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        //this.privilage = privilage;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPrivilage(List<String> privilage) {
        this.privilage = privilage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWord() {
        return passWord;
    }

    public void setPassWord(String passWord) {
        this.passWord = passWord;
    }

    public Date getRegisterDate() {
        return registerDate;
    }

    public void setRegisterDate(Date registerDate) {
        this.registerDate = registerDate;
    }

    public boolean isDisable() {
        return isDisable;
    }

    public void setDisable(boolean disable) {
        isDisable = disable;
    }

    public Date getLastLogin() {
        return lastLogin;
    }

    public void setLastLogin(Date lastLogin) {
        this.lastLogin = lastLogin;
    }

    @Override
    public String toString() {
        return "User{" +
                "userName='" + userName + '\'' +
                ", passWord='" + passWord + '\'' +
                ", registerDate=" + registerDate +
                ", isDisable=" + isDisable +
                ", lastLogin=" + lastLogin +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", email='" + email + '\'' +
                ", privilage=" + privilage +
                '}';
    }
}

