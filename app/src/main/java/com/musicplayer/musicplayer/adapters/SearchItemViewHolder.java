package com.musicplayer.musicplayer.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.musicplayer.musicplayer.R;
import com.musicplayer.musicplayer.data.model.SearchItem;
import com.musicplayer.musicplayer.data.networking.ServiceRestManager;


public class SearchItemViewHolder extends RecyclerView.ViewHolder {

    //Context
    TextView searchItemName;
    TextView searchItemArtist;
    ImageView searchItemImage;

    //Context
    private Context mContext;

    /**
     * Constructor for the Item Holder to setup the row layout
     * @param itemView: a row View
     */
    public SearchItemViewHolder(View itemView) {
        super(itemView);
        searchItemName = itemView.findViewById(R.id.lbl_row_title);
        searchItemImage = itemView.findViewById(R.id.img_row_artist);
        searchItemArtist = itemView.findViewById(R.id.lbl_row_artist);
        mContext = itemView.getContext();
    }

    /**
     * bind method to populate fields in the row with the content of item
     * @param item: the item to populate
     */
    public void bindSearchItem(SearchItem item) {
        searchItemArtist.setText(item.getArtistName());
        searchItemName.setText(item.getTrackName());
        ServiceRestManager.getInstance().loadImage(item.getArtworkUrl100(),searchItemImage,mContext);
    }


}