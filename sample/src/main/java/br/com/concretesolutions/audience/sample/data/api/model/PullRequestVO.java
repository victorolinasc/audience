package br.com.concretesolutions.audience.sample.data.api.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

public class PullRequestVO implements Parcelable {

    @Json(name = "title")
    private String title;
    @Json(name = "body")
    private String body;
    @Json(name = "user")
    private OwnerVO user;
    @Json(name = "created_at")
    private String createdAt;

    protected PullRequestVO(Parcel in) {
        title = in.readString();
        body = in.readString();
        user = in.readParcelable(OwnerVO.class.getClassLoader());
        createdAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(body);
        dest.writeParcelable(user, flags);
        dest.writeString(createdAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PullRequestVO> CREATOR = new Creator<PullRequestVO>() {
        @Override
        public PullRequestVO createFromParcel(Parcel in) {
            return new PullRequestVO(in);
        }

        @Override
        public PullRequestVO[] newArray(int size) {
            return new PullRequestVO[size];
        }
    };

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public OwnerVO getUser() {
        return user;
    }

    public void setUser(OwnerVO user) {
        this.user = user;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }
}
