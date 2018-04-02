package com.musicplayer.musicplayer.ui.Fragments.Presenters;

import com.android.volley.VolleyError;
import com.musicplayer.musicplayer.data.model.SearchItem;
import com.musicplayer.musicplayer.data.model.SearchModel;
import com.musicplayer.musicplayer.data.networking.OnRequestResultListener;
import com.musicplayer.musicplayer.data.networking.ServiceRestManager;
import com.musicplayer.musicplayer.ui.Activities.SearchActivity;
import com.musicplayer.musicplayer.ui.Fragments.Views.SearchView;

/**
 * Created by alejandrofernandezruiz on 11/3/18.
 */

public class SearchPresenterImp  implements  SearchPresenter{

    private SearchView searchView;
    private SearchActivity searchActivity;

    public  SearchPresenterImp(SearchView searchView, SearchActivity searchActivity) {
        this.searchView = searchView;
        this.searchActivity = searchActivity;
    }

    @Override
    public void performSearch(String value) {
        searchView.showLoadinView();
        ServiceRestManager.getInstance().getSearchItemList(value, searchActivity, new OnRequestResultListener() {
            @Override
            public void OnRequestComplete(Object result) {
                SearchModel search = (SearchModel) result;
                searchView.hideLoadingView();
                searchView.receiveSearchResults((search.getResults()));
            }

            @Override
            public void OnRequestError(VolleyError error) {
                searchView.hideLoadingView();
                searchView.receiveErrorResults(error);
            }
        });

    }

    @Override
    public void navigateDetails(SearchItem selectedValue, int position) {

        searchView.goToDetails(selectedValue, position);

    }




}
