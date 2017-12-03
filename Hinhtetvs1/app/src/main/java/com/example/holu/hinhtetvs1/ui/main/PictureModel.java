package com.example.holu.hinhtetvs1.ui.main;

/**
 * Project Name Hinhtetvs1
 * Created by HoLu on 11/21/2017.
 * All rights reserved
 */

public class PictureModel {
    private String id;
    private String url;
    private int download;
    private int share;

    public PictureModel() {
    }

    public PictureModel(String id, String url, int download, int share) {
        this.id = id;
        this.url = url;
        this.download = download;
        this.share = share;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getDownload() {
        return download;
    }

    public void setDownload(int download) {
        this.download = download;
    }

    public int getShare() {
        return share;
    }

    public void setShare(int share) {
        this.share = share;
    }
}
