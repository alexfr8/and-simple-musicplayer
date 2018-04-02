package com.musicplayer.musicplayer.data.networking;

import com.android.volley.VolleyError;

/**
 * This is a simple request result listener for network request
 *
 * TODO: UPDATE FOR A EVENT BUS TO BE ABLE TO NOTIFY MULTIPLE ELEMENTS
 */

public interface OnRequestResultListener {

    public void OnRequestComplete(Object result) ;
    public void OnRequestError(VolleyError error);
}
