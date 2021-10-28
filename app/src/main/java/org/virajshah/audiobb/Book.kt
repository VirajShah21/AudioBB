package org.virajshah.audiobb

class Book(_title: String, _author: String) {
    var title = _title
    var author = _author
}

class BookList : ArrayList<Book>() {
    val titles: ArrayList<String>
        get() {
            var res = ArrayList<String>()
            for (book in this)
                res.add(book.title)
            return res
        }

    val authors: ArrayList<String>
        get() {
            var res = ArrayList<String>()
            for (book in this)
                res.add(book.author)
            return res
        }
}