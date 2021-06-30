package com.randomquotes;

import android.os.Parcel;
import android.os.Parcelable;

public class DataClass implements Parcelable {
    private String quote;
    private String author;

    public DataClass(String quote, String author){
        this.quote = quote;
        this.author = author;
    }

    protected DataClass(Parcel in) {
        quote = in.readString();
        author = in.readString();
    }

    public static final Creator<DataClass> CREATOR = new Creator<DataClass>() {
        @Override
        public DataClass createFromParcel(Parcel in) {
            return new DataClass(in);
        }

        @Override
        public DataClass[] newArray(int size) {
            return new DataClass[size];
        }
    };

    public String getQuote(){
        return quote;
    }

    public String getAuthor(){
        return author;
    }

    public void setQuote(String quote){
        this.quote = quote;
    }
    public void setAuthor(String author){
        this.author = author;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(quote);
        dest.writeString(author);
    }
}
