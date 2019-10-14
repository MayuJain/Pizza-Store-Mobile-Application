package com.example.pizzastore;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

public class Pizza implements Parcelable {
ArrayList<String> Toppings;
String delivery;

    protected Pizza(Parcel in) {
        Toppings = in.createStringArrayList();
        delivery = in.readString();
    }

    public static final Creator<Pizza> CREATOR = new Creator<Pizza>() {
        @Override
        public Pizza createFromParcel(Parcel in) {
            return new Pizza(in);
        }

        @Override
        public Pizza[] newArray(int size) {
            return new Pizza[size];
        }
    };

    public Pizza(ArrayList<String> toppings, String delivery) {
        Toppings = toppings;
        this.delivery = delivery;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeStringList(Toppings);
        parcel.writeString(delivery.toString());
    }
}
