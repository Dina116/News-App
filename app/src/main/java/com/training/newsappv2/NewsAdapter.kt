package com.training.newsappv2

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.app.ShareCompat
import androidx.core.net.toUri
import androidx.recyclerview.widget.RecyclerView.Adapter
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.training.newsappv2.databinding.ArticleListItemBinding

class NewsAdapter(val activity: Activity, val articles: ArrayList<Article>) :
    Adapter<NewsAdapter.NewsViewHolder>() {

    inner class NewsViewHolder(val binding: ArticleListItemBinding) : ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsAdapter.NewsViewHolder {
        val b = ArticleListItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NewsViewHolder(b)
    }

    //responsible of changing the data in the list item
    override fun onBindViewHolder(holder: NewsAdapter.NewsViewHolder, position: Int) {
        holder.binding.articleTitle.text = articles[position].title
        Glide
            .with(holder.binding.articleImage.context)
            .load(articles[position].urlToImage)
            .error(R.drawable.broken_image)
            .transition(DrawableTransitionOptions.withCrossFade(1000))
            .into(holder.binding.articleImage)

        val url = articles[position].url
        holder.binding.articleContainer.setOnClickListener {

            val intent = Intent(Intent.ACTION_VIEW, url.toUri())
            activity.startActivity(intent)

        }
        holder.binding.shareFab.setOnClickListener {
            ShareCompat
                .IntentBuilder(activity)
                .setType("text/plain")
                .setChooserTitle("Share Article with:")
                .setText(url)
                .startChooser()
        }


    }

    override fun getItemCount() = articles.size
}