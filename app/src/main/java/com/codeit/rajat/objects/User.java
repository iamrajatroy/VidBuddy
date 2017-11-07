package com.codeit.rajat.objects;

import android.net.Uri;

import java.util.List;

/**
 * Created by rajat on 7/11/17.
 */

public class User {
    private String uId;
    private String userName;
    private String emailId;
    private Uri photoUri;
    private List<String> followers;
    private List<String> followedBy;

    public User(String uId, String userName, String emailId, Uri photoUri) {
        this.uId = uId;
        this.userName = userName;
        this.emailId = emailId;
        this.photoUri = photoUri;
    }

    public String getuId() {
        return uId;
    }

    public void setuId(String uId) {
        this.uId = uId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public Uri getPhotoUri() {
        return photoUri;
    }

    public void setPhotoUri(Uri photoUri) {
        this.photoUri = photoUri;
    }

    public List<String> getFollowers() {
        return followers;
    }

    public void setFollowers(List<String> followers) {
        this.followers = followers;
    }

    public List<String> getFollowedBy() {
        return followedBy;
    }

    public void setFollowedBy(List<String> followedBy) {
        this.followedBy = followedBy;
    }
}
