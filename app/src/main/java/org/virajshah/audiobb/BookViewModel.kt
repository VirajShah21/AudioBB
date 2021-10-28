package org.virajshah.audiobb

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class BookViewModel : ViewModel() {
    val title: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }

    val author: MutableLiveData<String> by lazy {
        MutableLiveData<String>()
    }
}