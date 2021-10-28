package org.virajshah.audiobb

class Book(title: String, author: String) {
    init {
        var title = title
        var author = author
    }
}

class BookList: ArrayList<Book>()