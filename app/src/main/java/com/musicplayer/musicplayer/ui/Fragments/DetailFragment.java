package com.musicplayer.musicplayer.ui.Fragments;


import android.media.MediaPlayer;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;


import com.musicplayer.musicplayer.R;
import com.musicplayer.musicplayer.data.model.SearchItem;
import com.musicplayer.musicplayer.data.networking.ServiceRestManager;
import com.musicplayer.musicplayer.ui.Activities.SearchActivity;
import com.musicplayer.musicplayer.ui.BaseFragment;
import com.musicplayer.musicplayer.ui.Fragments.Presenters.DetailPresenter;
import com.musicplayer.musicplayer.ui.Fragments.Presenters.DetailPresenterImp;
import com.musicplayer.musicplayer.ui.Fragments.Views.DetailView;

import java.io.IOException;

/**
 * A simple {@link Fragment} subclass.
 */
public class DetailFragment extends BaseFragment implements DetailView {

    private static SearchActivity activity;
    private DetailPresenter presenter;
    TextView lblArtist,lblTitle,lblAlbum;
    ImageButton btnPrev,btnPause,btnPlay,btnNext;
    ImageView imgArtWork;

    private MediaPlayer mp;

    int position;
    SearchItem selectedItem;

    public DetailFragment() {
        // Required empty public constructor
    }
    public static DetailFragment newInstance(SearchActivity searchActivity) {
        Bundle args = new Bundle();
        DetailFragment fragment = new DetailFragment();
        activity = searchActivity;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        presenter = new DetailPresenterImp(this, activity);
    }

    @Override
    public void onResume(){
        super.onResume();
        mp = new MediaPlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        mp.stop();
        mp.release();
        mp = null;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fra_detail, container, false);

        lblArtist = root.findViewById(R.id.lbl_detail_artist);
        lblTitle =root.findViewById(R.id.lbl_detail_title);
        lblAlbum= root.findViewById(R.id.lbl_detail_album);
        imgArtWork = root.findViewById(R.id.img_artwork);
        btnNext = root.findViewById(R.id.btn_detail_next);
        btnPause = root.findViewById(R.id.btn_detail_pause);
        btnPlay = root.findViewById(R.id.btn_detail_play);
        btnPrev = root.findViewById(R.id.btn_detail_previous);
        Bundle bundle=getArguments();

        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.mediaPlayerNext(activity.getSearchItemList(),position);

            }
        });

        btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.mediaPlayerPrev(activity.getSearchItemList(),position);
            }
        });

        btnPlay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.mediaPlayBtn(selectedItem);
            }
        });

        btnPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                presenter.mediaPauseBtn(selectedItem);
            }
        });

        //here is your list array
        position = bundle.getInt(SearchFragment.POSITION);
        selectedItem = activity.getSearchItemList().get(position);
        presenter.setupItem(selectedItem,activity.getSearchItemList(),position);
        return root;
    }

    @Override
    public void setupView(SearchItem searchItem) {
        lblTitle.setText(searchItem.getTrackName());
        lblArtist.setText(searchItem.getArtistName());
        lblAlbum.setText(searchItem.getCollectionName());
        ServiceRestManager.getInstance().loadImage(searchItem.getArtworkUrl100(), imgArtWork,activity);
    }

    @Override
    public void setupButtonsPreviousDisabled() {
        this.btnPrev.setEnabled(false);
        this.btnNext.setEnabled(true);
    }

    @Override
    public void setupButtonsNextDisabled() {
        this.btnPrev.setEnabled(false);
        this.btnNext.setEnabled(true);
    }

    @Override
    public void playItem(SearchItem searchItem) {
        if (mp!=null) {
            mp.reset();
            mp.setOnErrorListener(new MediaPlayer.OnErrorListener() {
                public boolean onError(MediaPlayer mp, int what, int extra) {
                    mp.reset();
                    return false;
                }
            });

            mp.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                public void onPrepared(MediaPlayer mp) {
                    mp.start();
                }
            });

            try {
                mp.setDataSource(searchItem.getPreviewUrl());
                mp.prepareAsync();
            } catch (IllegalArgumentException e) {
            } catch (IllegalStateException e) {
            } catch (IOException e) {
            }
        }
    }

    @Override
    public void pauseItem(SearchItem searchItem) {
        if (mp!=null) {
            if (mp.isPlaying()) {
                mp.pause();
            } else {
                mp.start();
            }
        }
    }

    @Override
    public void prevItem(SearchItem prevSearchItem) {
        position--;
        selectedItem = prevSearchItem;
        this.playItem(selectedItem);
    }

    @Override
    public void nextItem(SearchItem nextSearchItem) {
        position++;
        selectedItem=nextSearchItem;
        this.playItem(selectedItem);
    }
}
