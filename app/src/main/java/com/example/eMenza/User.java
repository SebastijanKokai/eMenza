package com.example.eMenza;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class User implements Parcelable {
    private String name;
    private String surname;
    private String college;
    private String cardNumber;
    private String dateOfBirth;
    private String dateOfIssue;
    private String dateOfExpire;
    private String email;
    private String password;
    private String index;
    Integer numberOfBreakfast = 0, numberOfLunch = 0, numberOfDinner = 0;

    public User() {
    }

    public User(String name, String surname, String college, String cardNumber, String dateOfBirth, String dateOfIssue, String dateOfExpire, String email, String password, String index) {
        this.name = name;
        this.surname = surname;
        this.college = college;
        this.cardNumber = cardNumber;
        this.dateOfBirth = dateOfBirth;
        this.dateOfIssue = dateOfIssue;
        this.dateOfExpire = dateOfExpire;
        this.email = email;
        this.password = password;
        this.index = index;
    }

    public User(String name, String surname, String college, String cardNumber, String dateOfBirth, String dateOfIssue, String dateOfExpire, String email, String password, Integer numberOfBreakfast, Integer numberOfLunch, Integer numberOfDinner) {
        this.name = name;
        this.surname = surname;
        this.college = college;
        this.cardNumber = cardNumber;
        this.dateOfBirth = dateOfBirth;
        this.dateOfIssue = dateOfIssue;
        this.dateOfExpire = dateOfExpire;
        this.email = email;
        this.password = password;
        this.numberOfBreakfast = numberOfBreakfast;
        this.numberOfLunch = numberOfLunch;
        this.numberOfDinner = numberOfDinner;
    }

    // Parcel part
    public User(Parcel in) {
        String[] data = new String[13];

        in.readStringArray(data);
        this.name = data[0];
        this.surname = data[1];
        this.college = data[2];
        this.cardNumber = data[3];
        this.dateOfBirth = data[4];
        this.dateOfIssue = data[5];
        this.dateOfExpire = data[6];
        this.email = data[7];
        this.password = data[8];
        this.index = data[9];
        this.numberOfBreakfast = Integer.parseInt(data[10]);
        this.numberOfLunch = Integer.parseInt(data[11]);
        this.numberOfDinner = Integer.parseInt(data[12]);

    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        // TODO Auto-generated method stub

        dest.writeStringArray(new String[]{this.name,this.surname, this.college, this.cardNumber, this.dateOfBirth, this.dateOfIssue, this.dateOfExpire, this.email,
                this.password, this.index, String.valueOf(this.numberOfBreakfast), String.valueOf(this.numberOfLunch), String.valueOf(this.numberOfDinner) });
    }

    public static final Parcelable.Creator<User> CREATOR= new Parcelable.Creator<User>() {

        @Override
        public User createFromParcel(Parcel source) {
            return new User(source);  //using parcelable constructor
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    // Getters and setters

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDateOfBirth() {
        return dateOfBirth;
    }

    public void setDateOfBirth(String dateOfBirth) {
        this.dateOfBirth = dateOfBirth;
    }

    public String getDateOfIssue() {
        return dateOfIssue;
    }

    public void setDateOfIssue(String dateOfIssue) {
        this.dateOfIssue = dateOfIssue;
    }

    public String getDateOfExpire() {
        return dateOfExpire;
    }

    public void setDateOfExpire(String dateOfExpire) {
        this.dateOfExpire = dateOfExpire;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getNumberOfBreakfast() {
        return numberOfBreakfast;
    }

    public void setNumberOfBreakfast(Integer numberOfBreakfast) {
        this.numberOfBreakfast = numberOfBreakfast;
    }

    public Integer getNumberOfLunch() {
        return numberOfLunch;
    }

    public void setNumberOfLunch(Integer numberOfLunch) {
        this.numberOfLunch = numberOfLunch;
    }

    public Integer getNumberOfDinner() {
        return numberOfDinner;
    }

    public void setNumberOfDinner(Integer numberOfDinner) {
        this.numberOfDinner = numberOfDinner;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }
}
