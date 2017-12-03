package com.example.holu.hinhtetvs1.helper;

import android.app.Activity;
import android.app.DownloadManager;
import android.content.Context;
import android.net.Uri;
import android.os.Environment;

import com.example.holu.hinhtetvs1.ui.main.PictureModel;

import java.io.File;
import java.util.UUID;

/**
 * Project Name Hinhtetvs1
 * Created by HoLu on 11/21/2017.
 * All rights reserved
 */

public class DownloadFileFromURL {
    public static void downloadFile(Activity activity, PictureModel pictureModel) {
        File direct = new File(Environment.getExternalStorageDirectory()
                + "/AnhsirkDasarp");

        if (!direct.exists()) {
            direct.mkdirs();
        }

        DownloadManager mgr = (DownloadManager) activity.getSystemService(Context.DOWNLOAD_SERVICE);

        Uri downloadUri = Uri.parse(pictureModel.getUrl());
        DownloadManager.Request request = new DownloadManager.Request(
                downloadUri);

        request.setAllowedNetworkTypes(
                DownloadManager.Request.NETWORK_WIFI
                        | DownloadManager.Request.NETWORK_MOBILE)
                .setAllowedOverRoaming(false).setTitle(pictureModel.getId()+ UUID.randomUUID().toString())
                .setDescription("Something useful. No, really.")
                .setDestinationInExternalPublicDir("/Hinhtet", pictureModel.getId()+".jpg");

        mgr.enqueue(request);
    }
}