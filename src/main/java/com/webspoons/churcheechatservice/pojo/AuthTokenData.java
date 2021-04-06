
package com.webspoons.churcheechatservice.pojo;

import java.util.List;
import javax.annotation.Generated;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.google.gson.annotations.SerializedName;

@JsonIgnoreProperties(ignoreUnknown = true)
@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AuthTokenData {

    @SerializedName("bio")
    private String mBio;
    @SerializedName("createdAt")
    private String mCreatedAt;
    @SerializedName("dailyVerseMode")
    private String mDailyVerseMode;
    @SerializedName("deletedAt")
    private Object mDeletedAt;
    @SerializedName("devotion")
    private List<Object> mDevotion;
    @SerializedName("email")
    private String mEmail;
    @SerializedName("emailVerified")
    private Boolean mEmailVerified;
    @SerializedName("event")
    private List<Object> mEvent;
    @SerializedName("hymn")
    private List<Object> mHymn;
    @SerializedName("img")
    private Object mImg;
    @SerializedName("location")
    private AuthTokenLocation mAuthTokenLocation;
    @SerializedName("media")
    private List<Object> mMedia;
    @SerializedName("name")
    private String mName;
    @SerializedName("phoneNumberVerified")
    private Boolean mPhoneNumberVerified;
    @SerializedName("pricing")
    private Object mPricing;
    @SerializedName("publicToken")
    private String mPublicToken;
    @SerializedName("source")
    private String mSource;
    @SerializedName("stripe")
    private String mStripe;
    @SerializedName("type")
    private String mType;
    @SerializedName("updatedAt")
    private String mUpdatedAt;
    @SerializedName("verified")
    private Boolean mVerified;
    @SerializedName("__v")
    private Long m_V;
    @SerializedName("_id")
    private String m_id;

    public String getBio() {
        return mBio;
    }

    public void setBio(String bio) {
        mBio = bio;
    }

    public String getCreatedAt() {
        return mCreatedAt;
    }

    public void setCreatedAt(String createdAt) {
        mCreatedAt = createdAt;
    }

    public String getDailyVerseMode() {
        return mDailyVerseMode;
    }

    public void setDailyVerseMode(String dailyVerseMode) {
        mDailyVerseMode = dailyVerseMode;
    }

    public Object getDeletedAt() {
        return mDeletedAt;
    }

    public void setDeletedAt(Object deletedAt) {
        mDeletedAt = deletedAt;
    }

    public List<Object> getDevotion() {
        return mDevotion;
    }

    public void setDevotion(List<Object> devotion) {
        mDevotion = devotion;
    }

    public String getEmail() {
        return mEmail;
    }

    public void setEmail(String email) {
        mEmail = email;
    }

    public Boolean getEmailVerified() {
        return mEmailVerified;
    }

    public void setEmailVerified(Boolean emailVerified) {
        mEmailVerified = emailVerified;
    }

    public List<Object> getEvent() {
        return mEvent;
    }

    public void setEvent(List<Object> event) {
        mEvent = event;
    }

    public List<Object> getHymn() {
        return mHymn;
    }

    public void setHymn(List<Object> hymn) {
        mHymn = hymn;
    }

    public Object getImg() {
        return mImg;
    }

    public void setImg(Object img) {
        mImg = img;
    }

    public AuthTokenLocation getLocation() {
        return mAuthTokenLocation;
    }

    public void setLocation(AuthTokenLocation authTokenLocation) {
        mAuthTokenLocation = authTokenLocation;
    }

    public List<Object> getMedia() {
        return mMedia;
    }

    public void setMedia(List<Object> media) {
        mMedia = media;
    }

    public String getName() {
        return mName;
    }

    public void setName(String name) {
        mName = name;
    }

    public Boolean getPhoneNumberVerified() {
        return mPhoneNumberVerified;
    }

    public void setPhoneNumberVerified(Boolean phoneNumberVerified) {
        mPhoneNumberVerified = phoneNumberVerified;
    }

    public Object getPricing() {
        return mPricing;
    }

    public void setPricing(Object pricing) {
        mPricing = pricing;
    }

    public String getPublicToken() {
        return mPublicToken;
    }

    public void setPublicToken(String publicToken) {
        mPublicToken = publicToken;
    }

    public String getSource() {
        return mSource;
    }

    public void setSource(String source) {
        mSource = source;
    }

    public String getStripe() {
        return mStripe;
    }

    public void setStripe(String stripe) {
        mStripe = stripe;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

    public String getUpdatedAt() {
        return mUpdatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        mUpdatedAt = updatedAt;
    }

    public Boolean getVerified() {
        return mVerified;
    }

    public void setVerified(Boolean verified) {
        mVerified = verified;
    }

    public Long get_V() {
        return m_V;
    }

    public void set_V(Long _V) {
        m_V = _V;
    }

    public String get_id() {
        return m_id;
    }

    public void set_id(String _id) {
        m_id = _id;
    }

}
