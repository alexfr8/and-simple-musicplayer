package com.musicplayer.musicplayer.ui.Fragments.Presenters;


import com.musicplayer.musicplayer.data.model.SearchItem;

/**
 * Created by alejandrofernandezruiz on 11/3/18.
 */

public interface SearchPresenter {

    public void performSearch(String value);
    public void navigateDetails(SearchItem selectedValue, int position);

}
