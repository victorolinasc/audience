package br.com.concretesolutions.audience.sample.api.model;


import android.os.Parcel;
import android.os.Parcelable;

import com.squareup.moshi.Json;

public class RepositoryVO implements Parcelable {

    @Json(name = "id")
    private String id;
    @Json(name = "name")
    private String name;
    @Json(name = "full_name")
    private String fullName;
    @Json(name = "owner")
    private OwnerVO owner;
    @Json(name = "description")
    private String description;
    @Json(name = "created_at")
    private String createdAt;
    @Json(name = "updated_at")
    private String updatedAt;
    @Json(name = "pushed_at")
    private String pushedAt;
    @Json(name = "homepage")
    private String homepage;
    @Json(name = "size")
    private Integer size;
    @Json(name = "stargazers_count")
    private Integer stargazersCount;
    @Json(name = "watchers_count")
    private Integer watchersCount;
    @Json(name = "language")
    private String language;
    @Json(name = "forks_count")
    private int forksCount;
    @Json(name = "open_issues_count")
    private int openIssuesCount;
    @Json(name = "forks")
    private int forks;
    @Json(name = "open_issues")
    private int openIssues;
    @Json(name = "watchers")
    private int watchers;
    @Json(name = "default_branch")
    private String defaultBranch;

    protected RepositoryVO(Parcel in) {
        id = in.readString();
        name = in.readString();
        fullName = in.readString();
        owner = in.readParcelable(OwnerVO.class.getClassLoader());
        description = in.readString();
        createdAt = in.readString();
        updatedAt = in.readString();
        pushedAt = in.readString();
        homepage = in.readString();
        language = in.readString();
        forksCount = in.readInt();
        openIssuesCount = in.readInt();
        forks = in.readInt();
        openIssues = in.readInt();
        watchers = in.readInt();
        defaultBranch = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(fullName);
        dest.writeParcelable(owner, flags);
        dest.writeString(description);
        dest.writeString(createdAt);
        dest.writeString(updatedAt);
        dest.writeString(pushedAt);
        dest.writeString(homepage);
        dest.writeString(language);
        dest.writeInt(forksCount);
        dest.writeInt(openIssuesCount);
        dest.writeInt(forks);
        dest.writeInt(openIssues);
        dest.writeInt(watchers);
        dest.writeString(defaultBranch);
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

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
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

    public String getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    public String getPushedAt() {
        return pushedAt;
    }

    public void setPushedAt(String pushedAt) {
        this.pushedAt = pushedAt;
    }

    public String getHomepage() {
        return homepage;
    }

    public void setHomepage(String homepage) {
        this.homepage = homepage;
    }

    public Integer getSize() {
        return size;
    }

    public void setSize(Integer size) {
        this.size = size;
    }

    public Integer getStargazersCount() {
        return stargazersCount;
    }

    public void setStargazersCount(Integer stargazersCount) {
        this.stargazersCount = stargazersCount;
    }

    public Integer getWatchersCount() {
        return watchersCount;
    }

    public void setWatchersCount(Integer watchersCount) {
        this.watchersCount = watchersCount;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public int getForksCount() {
        return forksCount;
    }

    public void setForksCount(int forksCount) {
        this.forksCount = forksCount;
    }

    public int getOpenIssuesCount() {
        return openIssuesCount;
    }

    public void setOpenIssuesCount(int openIssuesCount) {
        this.openIssuesCount = openIssuesCount;
    }

    public int getForks() {
        return forks;
    }

    public void setForks(int forks) {
        this.forks = forks;
    }

    public int getOpenIssues() {
        return openIssues;
    }

    public void setOpenIssues(int openIssues) {
        this.openIssues = openIssues;
    }

    public int getWatchers() {
        return watchers;
    }

    public void setWatchers(int watchers) {
        this.watchers = watchers;
    }

    public String getDefaultBranch() {
        return defaultBranch;
    }

    public void setDefaultBranch(String defaultBranch) {
        this.defaultBranch = defaultBranch;
    }
}
