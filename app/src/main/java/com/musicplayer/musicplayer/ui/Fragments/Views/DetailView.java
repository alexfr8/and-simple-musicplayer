package com.musicplayer.musicplayer.ui.Fragments.Views;


import com.musicplayer.musicplayer.data.model.SearchItem;

/**
 * Created by alejandrofernandezruiz on 11/3/18.
 */

public interface DetailView {

    void setupView(SearchItem searchItem);
    void setupButtonsPreviousDisabled();
    void setupButtonsNextDisabled();
    void playItem(SearchItem searchItem);
    void pauseItem(SearchItem searchItem);
    void prevItem(SearchItem prevSearchItem);
    void nextItem(SearchItem nextSearchItem);

}
