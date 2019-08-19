package com.bluecoding.movieschallenge.network.pojos

class Response<T>(var `object`: T, var message: String, var error: Int)
