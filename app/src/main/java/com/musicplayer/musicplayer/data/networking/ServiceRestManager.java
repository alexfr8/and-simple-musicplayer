package com.musicplayer.musicplayer.data.networking;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.LruCache;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.DataSource;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.load.engine.GlideException;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.RequestOptions;
import com.bumptech.glide.request.target.Target;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.musicplayer.musicplayer.data.model.SearchModel;


import org.json.JSONArray;
import org.json.JSONObject;


/*+
* @singleton: Service rest for manage network request.
* Volley is the http nwk library used. https://developer.android.com/training/volley/index.html
*
* */
public class ServiceRestManager {


    private static final ServiceRestManager ourInstance = new ServiceRestManager();
    private RequestQueue mRequestQueue;
    private static Context ctx;

    public static ServiceRestManager getInstance() {
        return ourInstance;
    }

    private static final String protocol = "https://";
    private static final String base_url = "itunes.apple.com/";



    private ServiceRestManager() {



    }

    /**
     *
     * @return the queue of network calls
     */
    public RequestQueue getRequestQueue() {
        if (mRequestQueue == null) {
            // getApplicationContext() is key, it keeps you from leaking the
            // Activity or BroadcastReceiver if someone passes one in.
            mRequestQueue = Volley.newRequestQueue(ctx.getApplicationContext());
        }
        return mRequestQueue;
    }

    /**
     * A method to add a request to the queue
     * @param req: The request to add
     * @param <T>: kind of request (generic)
     */
    public <T> void addToRequestQueue(Request<T> req) {
        getRequestQueue().add(req);
    }


    /**
     * A simple method to using singleton instance mack async call for the newtork data
     * the url is based on the protocol + base_url provided in the class
     * @param item: String with the name of song, artist or album to search
     * @param ctx: Context of the activity to search
     * @param listener: listener to know when Volley get the response (or error).
     */
    public void getSearchItemList(String item, Context ctx, final OnRequestResultListener listener) {
        //TODO: Update to use the request api params to setup the term + item
        String tail = "search?term=";
        String url = protocol + base_url + tail+item;

        this.ctx = ctx;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                        Gson gson = new Gson();
                        SearchModel search = gson.fromJson(response.toString(),SearchModel.class);
                        listener.OnRequestComplete(search);
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        listener.OnRequestError(error);
                    }
                });

        // Access the RequestQueue through your singleton class.
        try {
            addToRequestQueue(jsonObjectRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * Method to load asyncronusly a image into a view. With cache activated to optimize
     * networking and the flow of the list scrolling.
     * @param url: url of image
     * @param img: the view where to set the view
     * @param ctx: ctx of activity.
     */
    public void loadImage(String url, ImageView img, Context ctx) {

        //TODO: SETUP A PLACE HOLDER WHILE ASYNC LOAD ENDS.
        RequestOptions requestOptions = new RequestOptions();
        requestOptions.diskCacheStrategy(DiskCacheStrategy.ALL);
        requestOptions.centerCrop();

        Glide.with(ctx).load(url)
        .apply(requestOptions).into(img);
    }

}
