package com.gaurav.testapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.gaurav.testapp.model.Category

class CategoryAdapter(
    private val categories: List<Category>,
    private val onCategoryClick: (Category) -> Unit
) : RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder>() {


    private var selectedPosition: Int = RecyclerView.NO_POSITION

    inner class CategoryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {


        private val categoryName: TextView = itemView.findViewById(R.id.categoryText)

        init {
            itemView.setOnClickListener {
                val previousSelectedPosition = selectedPosition
                selectedPosition = adapterPosition
                notifyItemChanged(previousSelectedPosition)
                notifyItemChanged(selectedPosition)

                val selectedCategory = categories[adapterPosition]
                onCategoryClick(selectedCategory)
            }
        }

        fun bind(category: Category, isSelected: Boolean) {
            categoryName.text = category.name
            if (isSelected) {
                itemView.setBackgroundResource(R.drawable.selected_background)
                categoryName.setTextColor(itemView.context.getColor(R.color.selected_text_color))
            } else {
                itemView.setBackgroundResource(R.drawable.default_background)
                categoryName.setTextColor(itemView.context.getColor(R.color.default_text_color))
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_category, parent, false)
        return CategoryViewHolder(view)
    }

    override fun getItemCount(): Int = categories.size

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val category = categories[position]
        holder.bind(category, position == selectedPosition)
    }
}
