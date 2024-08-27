package com.training.newsappv2

import android.os.Bundle
import android.util.Log
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.isVisible
import com.training.newsappv2.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    //https://newsapi.org
    //https://newsapi.org
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        binding= ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        loadNews()
        binding.swipeRefresh.setOnRefreshListener {
            loadNews()

        }

    }
    private fun loadNews(){
        val retrofit=Retrofit
            .Builder()
            .baseUrl("https://newsapi.org")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
        val call=retrofit.create(NewsCallable::class.java)

        call.getNews().enqueue(object : Callback<News>{
            override fun onResponse(p0: Call<News>, p1: Response<News>) {
                val news=p1.body()
                val articles=news?.articles!!
                articles.removeAll {
                    it.title == "[Removed]"
                }
                showNews(articles)
                binding.progressBar.isVisible=false
                binding.swipeRefresh.isRefreshing=false


            }

            override fun onFailure(p0: Call<News>, p1: Throwable) {
                binding.progressBar.isVisible=false
                binding.swipeRefresh.isRefreshing=false


            }
        })
    }
    private fun showNews(articles:ArrayList<Article>){
        val adapter=NewsAdapter(this,articles)
        binding.newsList.adapter=adapter

    }
}