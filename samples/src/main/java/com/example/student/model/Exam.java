package com.example.student.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by u34744 on 11/9/2016.
 */

public class Exam implements Parcelable {

    public static final String STATUS_YES = "y";
    public static final String STATUS_NO = "n";

    private String qr_data;
    private String service_data;
    private String status;

    public Exam() {
        this.qr_data = "";
        this.service_data = "";
        status = STATUS_NO;

    }

    public Exam(String qr_data,
                String service_data,
                String status
                   ) {
        this.qr_data = qr_data;
        this.service_data = service_data;
        this.status = status;
    }



    public Exam(Parcel parcel) {
        readFromParcel(parcel);
    }

    public static final Parcelable.Creator CREATOR =  new Creator() {
        @Override
        public Exam createFromParcel(Parcel parcel) {
            return new Exam(parcel);
        }

        @Override
        public Exam[] newArray(int size) {
            return new Exam[size];
        }
    };

    public String getQr_data() {
        return qr_data;
    }

    public String getService_data() {
        return service_data;
    }

    public String getStatus()
    {
        return status;
    }



    public void setQr_data(String qr_data) {
        this.qr_data = qr_data;
    }







    public void setService_data(String service_data) {
        this.service_data = service_data;
    }

    public void setStatus(String status)
    {
        this.status = status;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int flags) {
        parcel.writeString(qr_data);
        parcel.writeString(service_data);


    }

    private void readFromParcel(Parcel parcel) {
        qr_data = parcel.readString();
        service_data = parcel.readString();

        parcel.readString();
    }
}
