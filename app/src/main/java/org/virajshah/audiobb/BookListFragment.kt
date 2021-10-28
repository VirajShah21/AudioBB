package org.virajshah.audiobb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment


private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

class BookListFragment : Fragment() {
    private var titles: ArrayList<String>? = null
    private var authors: ArrayList<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
            titles = it.getStringArrayList("titles")
            authors = it.getStringArrayList("authors")
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_book_list, container, false)
    }

    companion object {
        @JvmStatic
        fun newInstance(books: BookList) =
            BookListFragment().apply {
                arguments = Bundle().apply {
                    putStringArrayList("titles", books.titles)
                    putStringArrayList("authors", books.authors)
                }
            }
    }
}