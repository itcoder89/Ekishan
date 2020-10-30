package com.android.ekishan.model;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

/**
 * Created by ${} on 5/21/2020.
 */
public class GetReviewData {
    @SerializedName("tobereviewed")
    private ArrayList<ReviewedList> tobereviewed;

    @SerializedName("reviewed")
    private ArrayList<ReviewedList> reviewed;

    public ArrayList<ReviewedList> getTobereviewed() {
        return tobereviewed;
    }

    public void setTobereviewed(ArrayList<ReviewedList> tobereviewed) {
        this.tobereviewed = tobereviewed;
    }

    public ArrayList<ReviewedList> getReviewed() {
        return reviewed;
    }

    public void setReviewed(ArrayList<ReviewedList> reviewed) {
        this.reviewed = reviewed;
    }
}
