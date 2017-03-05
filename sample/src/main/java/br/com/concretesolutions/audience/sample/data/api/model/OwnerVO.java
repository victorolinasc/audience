package br.com.concretesolutions.audience.sample.data.api.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

public class OwnerVO implements Parcelable {

    @Json(name = "login")
    private String login;
    @Json(name = "id")
    private String id;
    @Json(name = "avatar_url")
    private String avatarUrl;

    public OwnerVO() {
    }

    protected OwnerVO(Parcel in) {
        login = in.readString();
        id = in.readString();
        avatarUrl = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(login);
        dest.writeString(id);
        dest.writeString(avatarUrl);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<OwnerVO> CREATOR = new Creator<OwnerVO>() {
        @Override
        public OwnerVO createFromParcel(Parcel in) {
            return new OwnerVO(in);
        }

        @Override
        public OwnerVO[] newArray(int size) {
            return new OwnerVO[size];
        }
    };

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAvatarUrl() {
        return avatarUrl;
    }

    public void setAvatarUrl(String avatarUrl) {
        this.avatarUrl = avatarUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        OwnerVO ownerVO = (OwnerVO) o;

        if (login != null ? !login.equals(ownerVO.login) : ownerVO.login != null) return false;
        if (id != null ? !id.equals(ownerVO.id) : ownerVO.id != null) return false;
        return avatarUrl != null ? avatarUrl.equals(ownerVO.avatarUrl) : ownerVO.avatarUrl == null;

    }

    @Override
    public int hashCode() {
        int result = login != null ? login.hashCode() : 0;
        result = 31 * result + (id != null ? id.hashCode() : 0);
        result = 31 * result + (avatarUrl != null ? avatarUrl.hashCode() : 0);
        return result;
    }
}