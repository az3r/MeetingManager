package com.azer.meetingmanager.data.models;

import javax.persistence.Column;
import javax.persistence.Embeddable;

@Embeddable
public class Account {

    @Column(length = 50, unique = true)
    private String accountName;

    @Column
    private byte[] salt;

    @Column
    private byte[] password;

    public Account() {

    }

    public byte[] getSalt() {
        return salt;
    }

    public void setSalt(byte[] salt) {
        this.salt = salt;
    }

    public byte[] getPassword() {
        return password;
    }

    public void setPassword(byte[] password) {
        this.password = password;
    }

    public String getAccountName() {
        return accountName;
    }

    public void setAccountName(String accountName) {
        this.accountName = accountName;
    }

    public Account(String accountName, byte[] salt, byte[] hashedPassword) {
        this.accountName = accountName;
        this.salt = salt;
        this.password = hashedPassword;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) return false;
        Account account = (Account) obj;
        return account != null ? this.getAccountName().equals(account.getAccountName()) : false;
    }
}