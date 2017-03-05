package br.com.concretesolutions.audience.sample.data.api.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

public class RepositoryVO implements Parcelable {

    @Json(name = "id")
    private String id;
    @Json(name = "name")
    private String name;
    @Json(name = "owner")
    private OwnerVO owner;
    @Json(name = "description")
    private String description;
    @Json(name = "created_at")
    private String createdAt;
    @Json(name = "stargazers_count")
    private Integer stargazersCount;
    @Json(name = "forks_count")
    private Integer forksCount;

    public RepositoryVO() {
    }

    protected RepositoryVO(Parcel in) {
        id = in.readString();
        name = in.readString();
        owner = in.readParcelable(OwnerVO.class.getClassLoader());
        description = in.readString();
        createdAt = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeParcelable(owner, flags);
        dest.writeString(description);
        dest.writeString(createdAt);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RepositoryVO> CREATOR = new Creator<RepositoryVO>() {
        @Override
        public RepositoryVO createFromParcel(Parcel in) {
            return new RepositoryVO(in);
        }

        @Override
        public RepositoryVO[] newArray(int size) {
            return new RepositoryVO[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public OwnerVO getOwner() {
        return owner;
    }

    public void setOwner(OwnerVO owner) {
        this.owner = owner;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public Integer getForksCount() {
        return forksCount;
    }

    public void setForksCount(Integer forksCount) {
        this.forksCount = forksCount;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        RepositoryVO that = (RepositoryVO) o;

        if (id != null ? !id.equals(that.id) : that.id != null) return false;
        if (name != null ? !name.equals(that.name) : that.name != null) return false;
        if (owner != null ? !owner.equals(that.owner) : that.owner != null) return false;
        if (description != null ? !description.equals(that.description) : that.description != null)
            return false;
        if (createdAt != null ? !createdAt.equals(that.createdAt) : that.createdAt != null)
            return false;
        if (stargazersCount != null ? !stargazersCount.equals(that.stargazersCount) : that.stargazersCount != null)
            return false;
        return forksCount != null ? forksCount.equals(that.forksCount) : that.forksCount == null;

    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + (owner != null ? owner.hashCode() : 0);
        result = 31 * result + (description != null ? description.hashCode() : 0);
        result = 31 * result + (createdAt != null ? createdAt.hashCode() : 0);
        result = 31 * result + (stargazersCount != null ? stargazersCount.hashCode() : 0);
        result = 31 * result + (forksCount != null ? forksCount.hashCode() : 0);
        return result;
    }
}
