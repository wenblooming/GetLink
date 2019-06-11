package com.bgm.getlink.model;

public class Result {
    /**
     *   "converterId": "string",
     "authorName": "string",
     "title": "string",
     "sourceLink": "string",
     "linkUrl": "string",
     "linkMD5": "string",
     "publishAt": "2019-05-24T09:26:59.926Z",
     "deepLinkStatus": 0,
     "groupKey": "string",
     "groupName": "string",
     "id": "string"
     */

    private String converterId;
    private String authorName;
    private String title;
    private String sourceLink;
    private String linkUrl;
    private String linkMd5;
    private String publishAt;
    private int deepLinkStatus;
    private String groupKey;
    private String groupName;
    private String id;

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

    public String getSourceLink() {
        return sourceLink;
    }

    public void setSourceLink(String sourceLink) {
        this.sourceLink = sourceLink;
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

    public String getGroupName() {
        return groupName;
    }

    public void setGroupName(String groupName) {
        this.groupName = groupName;
    }

    public String getLinkMd5() {
        return linkMd5;
    }

    public void setLinkMd5(String linkMd5) {
        this.linkMd5 = linkMd5;
    }

    public int getDeepLinkStatus() {
        return deepLinkStatus;
    }

    public void setDeepLinkStatus(int deepLinkStatus) {
        this.deepLinkStatus = deepLinkStatus;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getConverterId() {
        return converterId;
    }

    public void setConverterId(String converterId) {
        this.converterId = converterId;
    }

    public String getPublishAt() {
        return publishAt;
    }

    public void setPublishAt(String publishAt) {
        this.publishAt = publishAt;
    }

    @Override
    public String toString() {
        return "Result{" +
                "converterId='" + converterId + '\'' +
                ", authorName='" + authorName + '\'' +
                ", title='" + title + '\'' +
                ", sourceLink='" + sourceLink + '\'' +
                ", linkUrl='" + linkUrl + '\'' +
                ", linkMd5='" + linkMd5 + '\'' +
                ", publishAt='" + publishAt + '\'' +
                ", deepLinkStatus=" + deepLinkStatus +
                ", groupKey='" + groupKey + '\'' +
                ", groupName='" + groupName + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
