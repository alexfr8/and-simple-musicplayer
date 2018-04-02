package com.musicplayer.musicplayer.ui.Fragments.Presenters;

import com.musicplayer.musicplayer.data.model.SearchItem;

import java.util.List;

/**
 * Created by alejandrofernandezruiz on 11/3/18.
 */

public interface DetailPresenter {

    void setupItem(SearchItem searchItem, List<SearchItem> searchItemList, int position);
    void setupPrevious(List<SearchItem> searchItemList, int position);
    void setupNext(List<SearchItem> searchItemList, int position);
    void setupButtonsPrevious(List<SearchItem> searchItemList, int position);
    void setupButtonNext(List<SearchItem> searchItemList, int position);
    void mediaPlayBtn(SearchItem item);
    void mediaPauseBtn(SearchItem item);
    void mediaPlayerNext(List<SearchItem> searchItemList, int position);
    void mediaPlayerPrev(List<SearchItem> searchItemList, int position);
}
