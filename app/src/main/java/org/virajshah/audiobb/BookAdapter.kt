package org.virajshah.audiobb

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import org.jetbrains.annotations.NotNull

class BookAdapter: RecyclerView.Adapter<BookAdapter.ViewHolder>() {

    var books: BookList? = null

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ViewHolder {
        val itemLayoutView = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_list_item, null)

        val viewHolder = ViewHolder(itemLayoutView)
        return viewHolder
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, @NotNull position: Int) {
        val book = books!![position]
        viewHolder.imageView.setOnClickListener {
            MainActivity.activity.detailsView.update(books!![position].title, books!![position].author)
        }
    }

    class ViewHolder(itemLayoutView: View) :
        RecyclerView.ViewHolder(itemLayoutView) {
        val imageView = itemLayoutView.findViewById<ImageView>(R.id.image)
    }

    override fun getItemCount(): Int {
        return images.size
    }
}
