package com.musicplayer.musicplayer.ui.Fragments.Presenters;



import com.musicplayer.musicplayer.data.model.SearchItem;
import com.musicplayer.musicplayer.ui.Activities.SearchActivity;
import com.musicplayer.musicplayer.ui.Fragments.Views.DetailView;

import java.util.List;

/**
 * Created by alejandrofernandezruiz on 11/3/18.
 */

public class DetailPresenterImp implements DetailPresenter {


    private DetailView detailView;
    private SearchActivity searchActivity;

    public  DetailPresenterImp(DetailView detailView, SearchActivity searchActivity) {
        this.detailView = detailView;
        this.searchActivity = searchActivity;
    }

    @Override
    public void setupItem(SearchItem searchItem, List<SearchItem> searchItemList, int position) {
        if (searchItem != null) {
            detailView.setupView(searchItem);
        } else if ((searchItem == null) && (searchItemList!= null)) {
            detailView.setupView(searchItemList.get(position));
        }
    }

    @Override
    public void setupPrevious(List<SearchItem> searchItemList, int position) {
        if (position > 0) {
            this.setupItem(null, searchItemList,position-1);
        } else if (position ==0) {
            this.setupItem(null,searchItemList,position-1);
            this.setupButtonsPrevious(searchItemList, position);
        }
    }

    @Override
    public void setupNext(List<SearchItem> searchItemList, int position) {
        if (position< searchItemList.size()-3){
            this.setupItem(null, searchItemList, position+1);
        } else if (position == searchItemList.size()-2) {
            this.setupItem(null, searchItemList, position+1);
        }
    }

    @Override
    public void setupButtonsPrevious(List<SearchItem> searchItemList, int position) {

    }

    @Override
    public void setupButtonNext(List<SearchItem> searchItemList, int position) {

    }

    @Override
    public void mediaPlayBtn(SearchItem item) {
        if (item.getPreviewUrl()!=null) {
            detailView.playItem(item);
        }
    }

    @Override
    public void mediaPauseBtn(SearchItem item) {
        if (item.getPreviewUrl()!=null) {
            detailView.pauseItem(item);
        }
    }

    @Override
    public void mediaPlayerNext(List<SearchItem> searchItemList, int position){
        if (position< searchItemList.size()-3){
            detailView.pauseItem(searchItemList.get(position));
            this.setupItem(null, searchItemList, position+1);
            detailView.nextItem(searchItemList.get(position+1));
        } else if (position == searchItemList.size()-2) {
            detailView.pauseItem(searchItemList.get(position));
            this.setupItem(null, searchItemList, position+1);
            detailView.nextItem(searchItemList.get(position+1));
        }
    }

    @Override
    public void mediaPlayerPrev(List<SearchItem> searchItemList, int position) {
        if (position > 0) {
            detailView.pauseItem(searchItemList.get(position));
            this.setupItem(null, searchItemList,position-1);
            detailView.prevItem(searchItemList.get(position-1));
        } else if (position ==0) {
            this.setupButtonsPrevious(searchItemList, position);
        }
    }
}
