package com.musicplayer.musicplayer.ui.Fragments;


import android.app.FragmentTransaction;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.InputFilter;
import android.text.InputType;
import android.text.Spanned;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.VolleyError;
import com.musicplayer.musicplayer.R;
import com.musicplayer.musicplayer.adapters.SearchItemAdapter;
import com.musicplayer.musicplayer.data.model.SearchItem;
import com.musicplayer.musicplayer.ui.Activities.SearchActivity;
import com.musicplayer.musicplayer.ui.BaseFragment;
import com.musicplayer.musicplayer.ui.Fragments.Presenters.SearchPresenter;
import com.musicplayer.musicplayer.ui.Fragments.Presenters.SearchPresenterImp;
import com.musicplayer.musicplayer.ui.Fragments.Views.SearchView;


import java.util.ArrayList;
import java.util.List;

import io.github.yuweiguocn.lib.squareloading.SquareLoading;

/**
 * A simple {@link Fragment} subclass.
 */
public class SearchFragment extends BaseFragment implements SearchView,SearchItemAdapter.OnItemClickListener {


    public static final String POSITION = "POSITION";
    private static SearchActivity activity;
    private RecyclerView recyclerView;
    private EditText textSearchView;
    private SquareLoading progressBar;

    private SearchPresenter presenter;

    String query;

    public SearchFragment() {}
    public static SearchFragment newInstance(SearchActivity searchActivity) {
        Bundle args = new Bundle();
        SearchFragment fragment = new SearchFragment();
        activity = searchActivity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new SearchPresenterImp(this,activity);
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fra_search, container, false);

        textSearchView = root.findViewById(R.id.txt_search_view);
        progressBar = root.findViewById(R.id.pb_searching);
        addAnimation(root);
        prepareSearchView();

        recyclerView = root.findViewById(R.id.product_list_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);

        textSearchView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                query = charSequence.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }

        });

        textSearchView.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {
                    performSearch();
                    return true;
                } else if (actionId == KeyEvent.ACTION_DOWN) {
                    performSearch();
                    return true;
                }
                return false;
            }
        });




        return root;
    }


    private void addAnimation(View root) {

        TextView searchLabel = root.findViewById(R.id.search_label);
        TextView searchText1 = root.findViewById(R.id.search_subtitle1);
        TextView searchText2 = root.findViewById(R.id.search_subtitle2);

        Animation startAnimation = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in_animation);

        textSearchView.startAnimation(startAnimation);
        searchLabel.startAnimation(startAnimation);
        searchText1.startAnimation(startAnimation);
        searchText2.startAnimation(startAnimation);
    }



    private void performSearch() {
        if (query.length() != 0) {
            presenter.performSearch(query);
            dismissKeyboard();
        } else {
            List<SearchItem> filteredModelList = new ArrayList<SearchItem>();
            updateList(filteredModelList);
        }
    }



    public static InputFilter EMOJI_FILTER = new InputFilter() {

        @Override
        public CharSequence filter(CharSequence source, int start, int end, Spanned dest, int dstart, int dend) {
            for (int index = start; index < end; index++) {

                int type = Character.getType(source.charAt(index));

                if (type == Character.SURROGATE) {
                    return "";
                }
            }
            return null;
        }
    };
    private void prepareSearchView() {

        textSearchView.setInputType(InputType.TYPE_TEXT_FLAG_CAP_CHARACTERS);
        textSearchView.setHint(getResources().getString(R.string.search_hint));
        textSearchView.setFilters(new InputFilter[]{EMOJI_FILTER});

    }





    private void dismissKeyboard() {
        textSearchView.clearFocus();
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(textSearchView.getWindowToken(), 0);
    }

    private void updateList(List<SearchItem> itemList) {

        SearchItemAdapter searchAdapter = new SearchItemAdapter(itemList,R.layout.row_searchitem,this);
        if (itemList.size() >0) {
            recyclerView.setVisibility(View.VISIBLE);
          //  searchAdapter.setClickable(true);
        } else {
            recyclerView.setVisibility(View.VISIBLE);
            SearchItem emptyTrack = new SearchItem();
            emptyTrack.setTrackName(getString(R.string.no_results));
            itemList.add(emptyTrack);
            searchAdapter = new SearchItemAdapter(itemList, R.layout.row_searchitem, this);
           // searchAdapter.setClickable(false);
        }
        recyclerView.setAdapter(searchAdapter);
    }


    @Override
    public void onItemClick(SearchItem searchItemSelected, int position) {
        // this.showDetailActivity(itemSelected);
        this.presenter.navigateDetails(searchItemSelected, position);
    }



    //SEARCHVIEW IMPLEMENTATION.

    @Override
    public void receiveSearchResults(List<SearchItem> results) {
        Log.v("SEARCHFRAGMENTTAG", "Results: " + results.size());
        activity.setSearchList(results);
        this.updateList(results);
    }

    @Override
    public void receiveErrorResults(VolleyError error) {
        Toast.makeText(activity,getString(R.string.error_loading),Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showLoadinView() {
        progressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoadingView() {
        progressBar.setVisibility(View.GONE);
    }

    @Override
    public void goToDetails(SearchItem searchItem, int position) {

        DetailFragment detailFragment = DetailFragment.newInstance(activity);

        Bundle args = new Bundle();
        args.putInt(POSITION, position);
        detailFragment.setArguments(args);
        FragmentTransaction transaction = getFragmentManager().beginTransaction();


        transaction.replace(R.id.fragment_container, detailFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

}
