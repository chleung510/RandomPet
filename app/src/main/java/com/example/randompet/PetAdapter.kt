package com.example.randompet

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

// Extending the RecyclerView class with customized ViewHolder
class PetAdapter(private val petList: List<String>): RecyclerView.Adapter<PetAdapter.ViewHolder>() {
    // Nested class for extending RecyclerView Viewholder
    class ViewHolder(view: View): RecyclerView.ViewHolder(view) {
        val petImage: ImageView

        // Initialize whenever the class runs
        init {
            // Find our RecyclerView item's ImageView for future use
            petImage = view.findViewById(R.id.pet_image)
        }
    }

    // Create new views
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // Create a new view, which defines the UI of the list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.pet_item, parent, false)

        return ViewHolder(view)
    }

    // Returns the size of your dataset
    override fun getItemCount(): Int {
        return petList.size
    }

    //Replaces the contents of a view
    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        Glide.with(holder.itemView).load(petList[position]).centerCrop().into(holder.petImage)
        // `holder` can used to reference any View within the RecyclerView item's layout file
        holder.petImage.setOnClickListener {
            Toast.makeText(holder.itemView.context, "Doggo at position $position clicked", Toast.LENGTH_SHORT).show()
        }
    }
}