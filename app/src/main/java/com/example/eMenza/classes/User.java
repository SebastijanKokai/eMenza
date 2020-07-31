package com.example.eMenza.classes;

import android.os.Parcel;
import android.os.Parcelable;

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
    private CreditCard creditCard = null;


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
        this.name = in.readString();
        this.surname = in.readString();
        this.college = in.readString();
        this.cardNumber = in.readString();
        this.dateOfBirth = in.readString();
        this.dateOfIssue = in.readString();
        this.dateOfExpire = in.readString();
        this.email = in.readString();
        this.password = in.readString();
        this.index = in.readString();
        this.numberOfBreakfast = in.readInt();
        this.numberOfLunch = in.readInt();
        this.numberOfDinner = in.readInt();
        this.creditCard = (CreditCard) in.readParcelable(CreditCard.class.getClassLoader());
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.name);
        parcel.writeString(this.surname);
        parcel.writeString(this.college);
        parcel.writeString(this.cardNumber);
        parcel.writeString(this.dateOfBirth);
        parcel.writeString(this.dateOfIssue);
        parcel.writeString(this.dateOfExpire);
        parcel.writeString(this.email);
        parcel.writeString(this.password);
        parcel.writeString(this.index);
        parcel.writeInt(this.numberOfBreakfast);
        parcel.writeInt(this.numberOfLunch);
        parcel.writeInt(this.numberOfDinner);
        parcel.writeParcelable(creditCard, flags);
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

    public CreditCard getCreditCard() {
        return creditCard;
    }

    public void setCreditCard(CreditCard creditCard) {
        this.creditCard = creditCard;
    }

}
