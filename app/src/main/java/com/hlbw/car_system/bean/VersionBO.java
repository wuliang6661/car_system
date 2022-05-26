package com.hlbw.car_system.bean;

public class VersionBO {


    /**
     * latestVersion : 2
     * downloadUrl : https://shjz.yingjin.pro/api/upload/app/app-debug.apk
     * isForceUpdate : 0
     * content : 新功能上线
     */

    private String latestVersion;
    private String downloadUrl;
    private int isForceUpdate;
    private String content;

    public String getLatestVersion() {
        return latestVersion;
    }

    public void setLatestVersion(String latestVersion) {
        this.latestVersion = latestVersion;
    }

    public String getDownloadUrl() {
        return downloadUrl;
    }

    public void setDownloadUrl(String downloadUrl) {
        this.downloadUrl = downloadUrl;
    }

    public int getIsForceUpdate() {
        return isForceUpdate;
    }

    public void setIsForceUpdate(int isForceUpdate) {
        this.isForceUpdate = isForceUpdate;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
