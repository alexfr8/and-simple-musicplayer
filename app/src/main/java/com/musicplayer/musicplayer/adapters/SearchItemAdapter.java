package com.musicplayer.musicplayer.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.musicplayer.musicplayer.data.model.SearchItem;

import java.util.ArrayList;
import java.util.List;


/**
 *
 * This class is an adapter for the simple recyclerview included in the search
 * view
 * @param: mList : A list of searched items
 * @param: itemLayout: the layout of the row to show.
 *
 *
 * */
public class SearchItemAdapter extends RecyclerView.Adapter<SearchItemViewHolder> {

    //DATA
    private List<SearchItem> mList = new ArrayList<>();
    //LAYOUT
    private int itemLayout;

    //listener
    private final OnItemClickListener listener;
    public interface OnItemClickListener {

        void onItemClick(SearchItem searchItemSelected, int position);
    }

    /**
     * Constructor of the SearchItemAdapter
     * @param items: A list of Items
     * @param itemLayout: the row layout
     * @param listener: the listener to ear the user item click
     */
    public SearchItemAdapter(List<SearchItem> items, int itemLayout, OnItemClickListener listener) {

        this.itemLayout = itemLayout;
        this.listener = listener;
        this.mList = items;
    }


    @Override
    public SearchItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new SearchItemViewHolder(v);
    }

    @Override public void onBindViewHolder(final SearchItemViewHolder viewHolder, final int position) {

        final Integer pos = position;
        final SearchItem searchItem = mList.get(position);
        viewHolder.bindSearchItem(searchItem);


        viewHolder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override public void onClick(View v) {

                listener.onItemClick(searchItem, position);

            }
        });
    }



    @Override
    public int getItemCount() {

        return mList.size();
    }

}