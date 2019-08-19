package com.bluecoding.movieschallenge.network.pojos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Page : Serializable {

    @SerializedName("page")
    @Expose
    var page: Int? = null
    @SerializedName("total_results")
    @Expose
    var total_results: Int? = null
    @SerializedName("total_pages")
    @Expose
    var total_pages: Int? = null
    @SerializedName("results")
    @Expose
    var results: List<Movie>? = null

}
