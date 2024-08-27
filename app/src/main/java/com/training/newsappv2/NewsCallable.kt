package com.training.newsappv2

import retrofit2.Call
import retrofit2.http.GET

interface NewsCallable {
    //@G(ET("/v2/top-headlines?country=us&apiKey=d65fb642634249529947df670aa69d90&pageSize=30")
    @GET("/v2/top-headlines?q=trump&apiKey=d65fb642634249529947df670aa69d90&pageSize=30")
    fun getNews(): Call<News>

}