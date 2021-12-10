package com.djmakes.a20211205_darrinjones_nycschools.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class School implements Parcelable {
    public static final Creator<School> CREATOR = new Creator<School>() {
        @Override
        public School createFromParcel(Parcel in) {
            return new School(in);
        }

        @Override
        public School[] newArray(int size) {
            return new School[size];
        }
    };
    @SerializedName("dbn")
    @Expose
    private String dbn;
    @SerializedName("school_name")
    @Expose
    private String school_name;
    @SerializedName("location")
    @Expose
    private String location;
    @SerializedName("neighborhood")
    @Expose
    private String neighborhood;
    @SerializedName("phone_number")
    @Expose
    private String phone_number;
    @SerializedName("website")
    @Expose
    private String website;
    @SerializedName("primary_address_line_1")
    @Expose
    private String primary_address_line_1;
    @SerializedName("city")
    @Expose
    private String city;
    @SerializedName("zip")
    @Expose
    private String zip;
    @SerializedName("state_code")
    @Expose
    private String state_code;
    @SerializedName("overview_paragraph")
    @Expose
    private String overview_paragraph;
    @SerializedName("school_email")
    @Expose
    private String school_email;

    public School(String dbn, String school_name, String location, String neighborhood,
                  String phone_number, String website, String primary_address_line_1,
                  String city, String zip, String state_code, String overview_paragraph,
                  String school_email) {
        this.dbn = dbn;
        this.school_name = school_name;
        this.location = location;
        this.neighborhood = neighborhood;
        this.phone_number = phone_number;
        this.website = website;
        this.primary_address_line_1 = primary_address_line_1;
        this.city = city;
        this.zip = zip;
        this.state_code = state_code;
        this.overview_paragraph = overview_paragraph;
        this.school_email = school_email;
    }

    public School() {
    }

    protected School(Parcel in) {
        dbn = in.readString();
        school_name = in.readString();
        location = in.readString();
        neighborhood = in.readString();
        phone_number = in.readString();
        website = in.readString();
        primary_address_line_1 = in.readString();
        city = in.readString();
        zip = in.readString();
        state_code = in.readString();
        overview_paragraph = in.readString();
        school_email = in.readString();
    }

    public static Creator<School> getCREATOR() {
        return CREATOR;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(dbn);
        parcel.writeString(school_name);
        parcel.writeString(location);
        parcel.writeString(neighborhood);
        parcel.writeString(overview_paragraph);
    }

    public String getDbn() {
        return dbn;
    }

    public void setDbn(String dbn) {
        this.dbn = dbn;
    }

    public String getSchool_name() {
        return school_name;
    }

    public void setSchool_name(String school_name) {
        this.school_name = school_name;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getNeighborhood() {
        return neighborhood;
    }

    public void setNeighborhood(String neighborhood) {
        this.neighborhood = neighborhood;
    }

    public String getOverview_paragraph() {
        return overview_paragraph;
    }

    public void setOverview_paragraph(String overview_paragraph) {
        this.overview_paragraph = overview_paragraph;
    }

    public String getWebsite() {
        return website;
    }

    public void setWebsite(String website) {
        this.website = website;
    }

    public String getPrimary_address_line_1() {
        return primary_address_line_1;
    }

    public void setPrimary_address_line_1(String primary_address_line_1) {
        this.primary_address_line_1 = primary_address_line_1;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getZip() {
        return zip;
    }

    public void setZip(String zip) {
        this.zip = zip;
    }

    public String getState_code() {
        return state_code;
    }

    public void setState_code(String state_code) {
        this.state_code = state_code;
    }

    public String getPhone_number() {
        return phone_number;
    }

    public void setPhone_number(String phone_number) {
        this.phone_number = phone_number;
    }

    public String getSchool_email() {
        return school_email;
    }

    public void setSchool_email(String school_email) {
        this.school_email = school_email;
    }

    @Override
    public String toString() {
        return "School{" +
                "dbn='" + dbn + '\'' +
                ", school_name='" + school_name + '\'' +
                ", location='" + location + '\'' +
                ", neighborhood='" + neighborhood + '\'' +
                ", phone_number='" + phone_number + '\'' +
                ", website='" + website + '\'' +
                ", primary_address_line_1='" + primary_address_line_1 + '\'' +
                ", city='" + city + '\'' +
                ", zip='" + zip + '\'' +
                ", state_code='" + state_code + '\'' +
                ", overview_paragraph='" + overview_paragraph + '\'' +
                ", school_email='" + school_email + '\'' +
                '}';
    }
}
