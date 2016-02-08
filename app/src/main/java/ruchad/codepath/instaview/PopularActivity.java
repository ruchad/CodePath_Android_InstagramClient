package ruchad.codepath.instaview;

import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class PopularActivity extends AppCompatActivity {

    private SwipeRefreshLayout swipeContainer;

    public static final String logTag = "PopularActivity";
    private ArrayList<PopularPhoto> photos;
    private PopularPhotoAdapter photoAdapter;

    public static final String client_id = "e05c462ebd86446ea48a5af73769b602";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setLogo(R.mipmap.ic_launcher);
        getSupportActionBar().setDisplayUseLogoEnabled(true);

        setContentView(R.layout.activity_popular);
        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                fetchRefreshedPhotos();
            }
        });

        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);

        photos = new ArrayList<>();
        photoAdapter = new PopularPhotoAdapter(this, photos);
        ListView lvPhotos = (ListView) findViewById(R.id.lvPhotos);
        lvPhotos.setAdapter(photoAdapter);

        fetchPopularPhotos();
    }

    public void fetchPopularPhotos(){
        String url = "https://api.instagram.com/v1/media/popular?client_id="+client_id;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, null, new JsonHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Log.i(logTag, response.toString());
                JSONArray photosJson;
                try {
                    photosJson = response.getJSONArray("data");

                    for (int i = 0; i < photosJson.length(); i++) {
                        JSONObject photoJson = photosJson.getJSONObject(i);
                        PopularPhoto photo = new PopularPhoto();
                        photo.setUsername(photoJson.getJSONObject("user").getString("username"));
                        if (photoJson.optJSONObject("caption") != null)
                            photo.setCaption(photoJson.getJSONObject("caption").getString("text"));
                        photo.setImageURL(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setImageHeight(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        photo.setLikesCount(photoJson.getJSONObject("likes").getInt("count"));
                        photo.setRelativeTime(Util.calcRelativeTime(photoJson.getLong("created_time")));
                        photo.setProfileImageURL(photoJson.getJSONObject("user").getString("profile_picture"));
                        ArrayList<PopularPhotoComment> comments = new ArrayList<>();
                        if(photoJson.optJSONObject("comments")!=null) {
                            int count = photoJson.getJSONObject("comments").getInt("count");
                            photo.setCommentsCount(count);
                            JSONObject commentsJson = photoJson.getJSONObject("comments");
                            JSONArray dataJson = commentsJson.getJSONArray("data");
                            for(int j=0; j<dataJson.length(); j++){
                                PopularPhotoComment comment = new PopularPhotoComment();
                                JSONObject commentJson = dataJson.getJSONObject(j);
                                comment.setUser(commentJson.getJSONObject("from").getString("username"));
                                comment.setUserComment(commentJson.getString("text"));
                                comment.setUserPic(commentJson.getJSONObject("from").getString("profile_picture"));
                                comment.setUserCommentTime(commentJson.getLong("created_time"));
                                comments.add(comment);
                            }
                        }
                        photo.setComments(comments);
                        photos.add(photo);
                    }
                    photoAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(logTag, "Failed to parse json data! " + e.toString());
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                super.onFailure(statusCode, headers, responseString, throwable);
            }
        });
    }

    public void fetchRefreshedPhotos(){
        String url = "https://api.instagram.com/v1/media/popular?client_id="+client_id;
        AsyncHttpClient client = new AsyncHttpClient();
        client.get(url, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                photoAdapter.clear();
                JSONArray photosJson;
                try {
                    photosJson = response.getJSONArray("data");

                    for (int i = 0; i < photosJson.length(); i++) {

                        JSONObject photoJson = photosJson.getJSONObject(i);

                        PopularPhoto photo = new PopularPhoto();
                        photo.setUsername(photoJson.getJSONObject("user").getString("username"));
                        if (photoJson.optJSONObject("caption") != null)
                            photo.setCaption(photoJson.getJSONObject("caption").getString("text"));
                        photo.setImageURL(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getString("url"));
                        photo.setImageHeight(photoJson.getJSONObject("images").getJSONObject("standard_resolution").getInt("height"));
                        photo.setLikesCount(photoJson.getJSONObject("likes").getInt("count"));
                        photo.setRelativeTime(Util.calcRelativeTime(photoJson.getLong("created_time")));
                        photo.setProfileImageURL(photoJson.getJSONObject("user").getString("profile_picture"));
                        ArrayList<PopularPhotoComment> comments = new ArrayList<>();
                        if(photoJson.optJSONObject("comments")!=null) {
                            int count = photoJson.getJSONObject("comments").getInt("count");
                            photo.setCommentsCount(count);
                            JSONObject commentsJson = photoJson.getJSONObject("comments");
                            JSONArray dataJson = commentsJson.getJSONArray("data");
                            for(int j=0; j<dataJson.length(); j++){
                                PopularPhotoComment comment = new PopularPhotoComment();
                                JSONObject commentJson = dataJson.getJSONObject(j);
                                comment.setUser(commentJson.getJSONObject("from").getString("username"));
                                comment.setUserComment(commentJson.getString("text"));
                                comment.setUserPic(commentJson.getJSONObject("from").getString("profile_picture"));
                                comment.setUserCommentTime(commentJson.getLong("created_time"));
                                comments.add(comment);
                            }
                        }
                        photo.setComments(comments);
                        photos.add(photo);
                    }
                    photoAdapter.notifyDataSetChanged();
                    swipeContainer.setRefreshing(false);
                } catch (JSONException e) {
                    e.printStackTrace();
                    Log.e(logTag, "Failed to parse json data! " + e.toString());
                }
            }
        });
    }
}
