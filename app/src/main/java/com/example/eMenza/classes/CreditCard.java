package com.example.eMenza.classes;

import android.os.Parcel;
import android.os.Parcelable;

public class CreditCard implements Parcelable {

    private String cardNumber;
    private String dateExpiration;
    private String cvc;

    public CreditCard() {

    }
    public CreditCard(String cardNumber, String dateExpiration, String cvc) {
        this.cardNumber = cardNumber;
        this.dateExpiration = dateExpiration;
        this.cvc = cvc;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public String getDateExpiration() {
        return dateExpiration;
    }

    public void setDateExpiration(String dateExpiration) {
        this.dateExpiration = dateExpiration;
    }

    public String getCvc() {
        return cvc;
    }

    public void setCvc(String cvc) {
        this.cvc = cvc;
    }

    // Parcel part
    public CreditCard(Parcel in) {
        this.cardNumber = in.readString();
        this.dateExpiration = in.readString();
        this.cvc = in.readString();
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(this.cardNumber);
        parcel.writeString(this.dateExpiration);
        parcel.writeString(this.cvc);
    }

    public static final Parcelable.Creator<CreditCard> CREATOR= new Parcelable.Creator<CreditCard>() {

        @Override
        public CreditCard createFromParcel(Parcel source) {
            return new CreditCard(source);  //using parcelable constructor
        }

        @Override
        public CreditCard[] newArray(int size) {
            return new CreditCard[size];
        }
    };
}
