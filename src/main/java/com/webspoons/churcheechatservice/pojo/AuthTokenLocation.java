
package com.webspoons.churcheechatservice.pojo;

import java.util.List;
import javax.annotation.Generated;
import com.google.gson.annotations.SerializedName;

@Generated("net.hexar.json2pojo")
@SuppressWarnings("unused")
public class AuthTokenLocation {

    @SerializedName("coordinates")
    private List<Long> mCoordinates;
    @SerializedName("type")
    private String mType;

    public List<Long> getCoordinates() {
        return mCoordinates;
    }

    public void setCoordinates(List<Long> coordinates) {
        mCoordinates = coordinates;
    }

    public String getType() {
        return mType;
    }

    public void setType(String type) {
        mType = type;
    }

}
