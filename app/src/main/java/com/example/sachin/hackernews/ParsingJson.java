package com.example.sachin.hackernews;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class ParsingJson extends AsyncTask<String, Void, List<HackerFeed>> implements DownloadJson.OnDownlaodData {
    private List<HackerFeed> hackerFeedList;
    private String baseUrl = "https://hnrss.org/newest.jsonfeed";
    private DownloadStatus downloadStatus;
    private static final String TAG = "ParsingJson";
    private OnJsonDataAvailable mcallBack;


    private String buildUrl(String searchTag) {
        return Uri.parse(baseUrl).buildUpon().
                appendQueryParameter("q", searchTag).build().toString();

    }

    public ParsingJson(OnJsonDataAvailable available) {
      this.mcallBack=available;
    }

    @Override
    protected void onProgressUpdate(Void... values) {

        super.onProgressUpdate(values);
    }

    @Override
    protected void onPostExecute(List<HackerFeed> s) {
        super.onPostExecute(s);
        Log.d(TAG, "onPostExecute: onPostExecute Started");
        Log.d(TAG, "onPostExecute: List is "+s.toString());
        if(mcallBack!=null){
            Log.d(TAG, "onPostExecute: list is "+s);
            mcallBack.onJsonDataAvailable(s,downloadStatus);
        }

    }

    interface OnJsonDataAvailable {
        void onJsonDataAvailable(List<HackerFeed> feed,DownloadStatus status);
    }

    @Override
    protected List<HackerFeed> doInBackground(String... tag) {
        Log.d(TAG, "doInBackground: doinBackground started");
        String url = buildUrl(tag[0]);
        Log.d(TAG, "doInBackground: build url is " + url);
        DownloadJson downloadJson = new DownloadJson(this);
        downloadJson.downloading(url);
        Log.d(TAG, "doInBackground:list is "+hackerFeedList.toString());
        return hackerFeedList;


    }

    @Override
    public void onDownloadData(String downloadData, DownloadStatus status) {
        if (status == DownloadStatus.OK) {
            try {
                hackerFeedList = new ArrayList<>();
                Log.d(TAG, "parse: parsing start");
                JSONObject item = new JSONObject(downloadData);
                Log.d(TAG, "parse: checking array");
                JSONArray itemArray = item.getJSONArray("items");
                Log.d(TAG, "parse:size of array " + itemArray.length());
                for (int i = 0; i < itemArray.length(); i++) {
                    JSONObject items = itemArray.getJSONObject(i);

                    String title = items.getString("title");
                    String external_url = items.getString("external_url");
                    String date_pub = items.getString("date_published");
                    String author = items.getString("author");

                    HackerFeed hackerFeed = new HackerFeed(title, author, date_pub, external_url);
                    hackerFeedList.add(hackerFeed);

                }

                Log.d(TAG, "parse: parsing end");


                downloadStatus = DownloadStatus.OK;

            } catch (JSONException e) {
                Log.d(TAG, "parse: error on parsing " + e.getMessage());

            }
        }


    }

}
