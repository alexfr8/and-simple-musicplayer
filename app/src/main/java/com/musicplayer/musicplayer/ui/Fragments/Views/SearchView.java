package com.musicplayer.musicplayer.ui.Fragments.Views;

import com.android.volley.VolleyError;
import com.musicplayer.musicplayer.data.model.SearchItem;

import java.util.List;

/**
 * Created by alejandrofernandezruiz on 11/3/18.
 */

public interface SearchView {

    void receiveSearchResults(List<SearchItem> results);

    void receiveErrorResults(VolleyError error);

    void showLoadinView();

    void hideLoadingView();

    void goToDetails(SearchItem searchItem, int position);
}
