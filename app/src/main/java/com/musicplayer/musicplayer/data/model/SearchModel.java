package com.musicplayer.musicplayer.data.model;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


/**
 * A search model element.
 */
public class SearchModel {

    @SerializedName("resultCount")
    @Expose
    private Integer resultCount;
    @SerializedName("results")
    @Expose
    private List<SearchItem> results = null;

    public Integer getResultCount() {
        return resultCount;
    }

    public void setResultCount(Integer resultCount) {
        this.resultCount = resultCount;
    }

    public List<SearchItem> getResults() {
        return results;
    }

    public void setResults(List<SearchItem> results) {
        this.results = results;
    }

}