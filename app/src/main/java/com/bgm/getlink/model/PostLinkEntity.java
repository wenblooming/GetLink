package com.bgm.getlink.model;

public class PostLinkEntity {
    private String id;
    private String authorName;
    private String title;
    private String publishAt;
    private String linkUrl;
    private String groupKey;

    public PostLinkEntity(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

    public String getGroupKey() {
        return groupKey;
    }

    public void setGroupKey(String groupKey) {
        this.groupKey = groupKey;
    }

    @Override
    public String toString() {
        return "PostLinkEntity{" +
                "id='" + id + '\'' +
                ", authorName='" + authorName + '\'' +
                ", title='" + title + '\'' +
                ", publishAt='" + publishAt + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                '}';
    }
}
