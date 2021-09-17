package com.example.ptsganjil202111rpl2akhtarrasyad19.Model;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class RealmModel extends RealmObject {
    @PrimaryKey
    private Integer id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    private String mTitle, mDesc, mImage, mGenre, mRelease, mActors, mDirector, mCountry, mRating, mImageLand;

    public RealmModel() {
    }

    public RealmModel(String mTitle, String mDesc, String mGenre, String mImage, String mRelease, String mActors, String mDirector, String mCountry, String mRating, String mImageLand) {
        this.mTitle = mTitle;
        this.mDesc = mDesc;
        this.mGenre = mGenre;
        this.mImage = mImage;
        this.mRelease = mRelease;
        this.mActors = mActors;
        this.mDirector = mDirector;
        this.mCountry = mCountry;
        this.mRating = mRating;
        this.mImageLand = mImageLand;
    }

    public String getmTitle() {
        return mTitle;
    }

    public String getmDesc() {
        return mDesc;
    }

    public String getmGenre() {
        return mGenre;
    }

    public String getmImage() {
        return mImage;
    }

    public String getmRelease() {
        return mRelease;
    }

    public String getmActors() {
        return mActors;
    }

    public String getmDirector() {
        return mDirector;
    }

    public String getmCountry() {
        return mCountry;
    }

    public String getmRating() {
        return mRating;
    }

    public String getmImageLand() {
        return mImageLand;
    }
}
