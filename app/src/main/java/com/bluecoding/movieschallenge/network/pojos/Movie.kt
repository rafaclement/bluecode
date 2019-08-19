package com.bluecoding.movieschallenge.network.pojos

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

import java.io.Serializable

class Movie : Serializable {

    @SerializedName("vote_count")
    @Expose
    var vote_count: Integer? = null
    @SerializedName("id")
    @Expose
    var id: Int? = null
    @SerializedName("video")
    @Expose
    var video: Boolean? = null
    @SerializedName("vote_average")
    @Expose
    var vote_average: Double? = null
    @SerializedName("title")
    @Expose
    var title: String? = null
    @SerializedName("popularity")
    @Expose
    var popularity: String? = null
    @SerializedName("poster_path")
    @Expose
    var poster_path: String? = null
    @SerializedName("original_language")
    @Expose
    var original_language: String? = null
    @SerializedName("original_title")
    @Expose
    var original_title: String? = null
    @SerializedName("genre_ids")
    @Expose
    var genre_ids: List<Integer>? = null
    @SerializedName("starred_url")
    @Expose
    var starredUrl: String? = null
    @SerializedName("backdrop_path")
    @Expose
    var backdrop_path: String? = null
    @SerializedName("adult")
    @Expose
    var adult: Boolean? = null
    @SerializedName("overview")
    @Expose
    var overview: String? = null
    @SerializedName("release_date")
    @Expose
    var release_date: String? = null

}
