package org.virajshah.audiobb

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity

class MainActivity : AppCompatActivity() {
    companion object {
        private fun populateBookList(bl: BookList) {
            bl.add(Book("1984", "George Orwell"))
            bl.add(Book("Animal Farm", "George Orwell"))
            bl.add(Book("Excuse Me Professor", "Lawrence W Reed"))
            bl.add(Book("Fahrenheit 451", "Ray Bradbury"))
            bl.add(Book("Ronald Reagan", "Libby Highes"))
            bl.add(Book("The Federalist Papers", "Hamilton"))
            bl.add(Book("The Law", "Frederic Bastiat"))
            bl.add(Book("The Moon is Down", "John Steinbeck"))
            bl.add(Book("The Myth of the Robber Barons", "Burton W. Folsom"))
            bl.add(Book("The Road to Serfdom", "F.A. Hayek"))
        }
    }

    init {
        var bl = BookList()
        populateBookList(bl)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

}