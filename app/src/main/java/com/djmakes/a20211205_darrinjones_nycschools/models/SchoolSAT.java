package com.djmakes.a20211205_darrinjones_nycschools.models;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class SchoolSAT implements Parcelable {


    @SerializedName("dbn")
    @Expose
    private String dbn;
    @SerializedName("school_name")
    @Expose
    private String school_Name;
    @SerializedName("num_of_sat_test_takers")
    @Expose
    private String numOfSatTestTakers;
    @SerializedName("sat_critical_reading_avg_score")
    @Expose
    private String satCriticalReadingAvgScore;
    @SerializedName("sat_math_avg_score")
    @Expose
    private String satMathAvgScore;
    @SerializedName("sat_writing_avg_score")
    @Expose
    private String satWritingAvgScore;

    private List<SchoolSAT> schoolSAT;

    public SchoolSAT(String dbn, String school_Name, String numOfSatTestTakers,
                     String satCriticalReadingAvgScore, String satMathAvgScore,
                     String satWritingAvgScore, List<SchoolSAT> schoolSAT) {
        this.dbn = dbn;
        this.school_Name = school_Name;
        this.numOfSatTestTakers = numOfSatTestTakers;
        this.satCriticalReadingAvgScore = satCriticalReadingAvgScore;
        this.satMathAvgScore = satMathAvgScore;
        this.satWritingAvgScore = satWritingAvgScore;
        this.schoolSAT = schoolSAT;
    }

    public SchoolSAT() {
    }

    protected SchoolSAT(Parcel in) {
        dbn = in.readString();
        school_Name = in.readString();
        numOfSatTestTakers = in.readString();
        satCriticalReadingAvgScore = in.readString();
        satMathAvgScore = in.readString();
        satWritingAvgScore = in.readString();
        schoolSAT = in.createTypedArrayList(SchoolSAT.CREATOR);
    }

    public static final Parcelable.Creator<SchoolSAT> CREATOR = new Parcelable.Creator<SchoolSAT>() {
        @Override
        public SchoolSAT createFromParcel(Parcel in) {
            return new SchoolSAT(in);
        }

        @Override
        public SchoolSAT[] newArray(int size) {
            return new SchoolSAT[size];
        }
    };

    public String getDbn() {
        return dbn;
    }

    public void setDbn(String dbn) {
        this.dbn = dbn;
    }

    public String getSchool_Name() {
        return school_Name;
    }

    public void setSchool_Name(String school_Name) {
        this.school_Name = school_Name;
    }

    public String getNumOfSatTestTakers() {
        return numOfSatTestTakers;
    }

    public void setNumOfSatTestTakers(String numOfSatTestTakers) {
        this.numOfSatTestTakers = numOfSatTestTakers;
    }

    public String getSatCriticalReadingAvgScore() {
        return satCriticalReadingAvgScore;
    }

    public void setSatCriticalReadingAvgScore(String satCriticalReadingAvgScore) {
        this.satCriticalReadingAvgScore = satCriticalReadingAvgScore;
    }

    public String getSatMathAvgScore() {
        return satMathAvgScore;
    }

    public void setSatMathAvgScore(String satMathAvgScore) {
        this.satMathAvgScore = satMathAvgScore;
    }

    public String getSatWritingAvgScore() {
        return satWritingAvgScore;
    }

    public void setSatWritingAvgScore(String satWritingAvgScore) {
        this.satWritingAvgScore = satWritingAvgScore;
    }

    public List<SchoolSAT> getSchoolSAT() {
        return schoolSAT;
    }

    public void setSchoolSAT(List<SchoolSAT> schoolSAT) {
        this.schoolSAT = schoolSAT;
    }

    @Override
    public String toString() {
        return "SchoolSAT{" +
                "dbn='" + dbn + '\'' +
                ", school_Name='" + school_Name + '\'' +
                ", numOfSatTestTakers='" + numOfSatTestTakers + '\'' +
                ", satCriticalReadingAvgScore='" + satCriticalReadingAvgScore + '\'' +
                ", satMathAvgScore='" + satMathAvgScore + '\'' +
                ", satWritingAvgScore='" + satWritingAvgScore + '\'' +
                ", schoolSAT=" + schoolSAT +
                '}';
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(dbn);
        dest.writeString(school_Name);
        dest.writeString(numOfSatTestTakers);
        dest.writeString(satCriticalReadingAvgScore);
        dest.writeString(satMathAvgScore);
        dest.writeString(satWritingAvgScore);
        dest.writeTypedList(schoolSAT);
    }
}

