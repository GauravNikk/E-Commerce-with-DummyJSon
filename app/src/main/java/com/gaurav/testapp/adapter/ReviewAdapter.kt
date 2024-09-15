package com.gaurav.testapp.adapter

import com.gaurav.testapp.R

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.testapp.model.Review
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class ReviewAdapter(private val reviews: List<Review>) : RecyclerView.Adapter<ReviewAdapter.ReviewViewHolder>() {

    inner class ReviewViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val reviewerName: TextView = itemView.findViewById(R.id.reviewerName)
        val reviewDate: TextView = itemView.findViewById(R.id.reviewDate)
        val reviewRating: TextView = itemView.findViewById(R.id.reviewRating)
        val reviewComment: TextView = itemView.findViewById(R.id.reviewComment)

        fun bind(review: Review) {
            reviewerName.text = review.reviewerName
            reviewDate.text = formatDate(review.date)
            reviewRating.text = "Rating: ${review.rating} / 5"
            reviewComment.text = "${review.comment} ${itemView.context.getString(R.string.desc)}"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ReviewViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_review, parent, false)
        return ReviewViewHolder(view)
    }

    override fun getItemCount(): Int = reviews.size

    override fun onBindViewHolder(holder: ReviewViewHolder, position: Int) {
        val review = reviews[position]
        holder.bind(review)
    }

    fun formatDate(dateString: String): String {
        val inputFormat = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS'Z'", Locale.getDefault())
        inputFormat.timeZone = TimeZone.getTimeZone("UTC")
        val outputFormat = SimpleDateFormat("dd-MM-yy", Locale.getDefault())

        val date: Date = inputFormat.parse(dateString) ?: return ""
        return outputFormat.format(date)
    }
}
