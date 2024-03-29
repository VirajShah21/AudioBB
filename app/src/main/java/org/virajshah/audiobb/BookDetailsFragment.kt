package org.virajshah.audiobb

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 * Use the [BookDetailsFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class BookDetailsFragment : Fragment() {
    var model = BookViewModel()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val rootView = inflater.inflate(R.layout.fragment_book_details, container, false)

        val titleView = rootView.findViewById<TextView>(R.id.details_title)
        val authorView = rootView.findViewById<TextView>(R.id.details_author)

        val titleObserver = Observer<String> { newTitle ->
            titleView.text = newTitle
        }

        val authorObserver = Observer<String> { newAuthor ->
            authorView.text = newAuthor
        }

        model.title.observe(viewLifecycleOwner, titleObserver)
        model.author.observe(viewLifecycleOwner, authorObserver)


        // Inflate the layout for this fragment
        return rootView
    }

    fun update(title: String, author: String) {

    }

    companion object {
        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment BookDetailsFragment.
         */
        // TODO: Rename and change types and number of parameters
        @JvmStatic
        fun newInstance() =
            BookDetailsFragment().apply {
                arguments = Bundle().apply {
                }
            }
    }
}