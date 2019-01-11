package com.example.sachin.hackernews;

import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


enum DownloadStatus {IDLE, OK, PROCESSING, ERROR}

class DownloadJson {
    private static final String TAG = "downloadJson";
    boolean status = false;
    private String url;
    private DownloadStatus downloadStatus;
    private OnDownlaodData mcallBack;

    interface OnDownlaodData {
        void onDownloadData(String downloadData, DownloadStatus status);
    }

    public DownloadJson(OnDownlaodData mcallback) {
        this.mcallBack = mcallback;
    }

     void downloading(String urlpath) {

        String downloadData = downloadUrl(urlpath);
         Log.d(TAG, "downloading: download data is "+downloadData);
        if (mcallBack != null) {
            mcallBack.onDownloadData(downloadData, downloadStatus);
        }
    }

    private String downloadUrl(String urlPath) {
        downloadStatus = DownloadStatus.IDLE;
        Log.d(TAG, "DownloadUrl: download url start");
        try {
            URL url = new URL(urlPath);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            int responseCode = connection.getResponseCode();
            if (responseCode == 200) {
                downloadStatus = DownloadStatus.PROCESSING;
                Log.d(TAG, "downloadUrl: processing");
            }
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            StringBuilder data = new StringBuilder();

            for (String line = reader.readLine(); line != null; line = reader.readLine()) {
                data.append(line).append("\n");
            }
            reader.close();
            connection.disconnect();
            Log.d(TAG, "DownloadUrl: Response Code is : " + responseCode);
            Log.d(TAG, "DownloadUrl: DownloadStatus is " + DownloadStatus.OK);
            downloadStatus = DownloadStatus.OK;
//            Log.d(TAG, "downloadUrl: "+data.toString());
            return data.toString();

        } catch (MalformedURLException e) {
            Log.d(TAG, "DownloadUrl: URL is invalid");
        } catch (IOException E) {
            Log.d(TAG, "DownloadUrl: error in connection");
        }
        downloadStatus = DownloadStatus.ERROR;
        return null;
    }


}
