package com.musicplayer.musicplayer.ui.Activities;

import android.app.Activity;
import android.app.FragmentTransaction;
import android.os.Bundle;


import com.musicplayer.musicplayer.R;
import com.musicplayer.musicplayer.data.model.SearchItem;
import com.musicplayer.musicplayer.ui.Fragments.SearchFragment;

import java.util.ArrayList;
import java.util.List;

public class SearchActivity extends Activity {

    List<SearchItem> searchItemList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);

        SearchFragment searchFragment = SearchFragment.newInstance(this);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();


        transaction.replace(R.id.fragment_container, searchFragment);
        transaction.addToBackStack(null);

// Commit the transaction
        transaction.commit();
        searchItemList = new ArrayList<>();
    }


    public void setSearchList(List<SearchItem> searchList) {
        this.searchItemList = searchList;
    }

    public List<SearchItem> getSearchItemList() {
        return this.searchItemList;
    }
}
