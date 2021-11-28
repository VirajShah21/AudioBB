package edu.temple.audiobb

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.Handler
import android.os.IBinder
import android.os.Message
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.SeekBar
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import edu.temple.audlibplayer.PlayerService
import java.lang.StringBuilder
import java.util.*
import android.widget.SeekBar.OnSeekBarChangeListener





class MainActivity : AppCompatActivity(), BookListFragment.BookSelectedInterface {

    private lateinit var bookListFragment: BookListFragment
    private var serviceBound = false
    private lateinit var audioService: PlayerService.MediaControlBinder
    private var playerPosition: Int = 0
    private var isPlaying = false

    private val audioServiceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            val binder: PlayerService.MediaControlBinder =
                service as PlayerService.MediaControlBinder
//            player = binder.getService()
            audioService = binder
            serviceBound = true

            Toast.makeText(this@MainActivity, "Service Bound", Toast.LENGTH_SHORT).show()
        }

        override fun onServiceDisconnected(name: ComponentName) {
            serviceBound = false
        }
    }

    private val searchRequest =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            supportFragmentManager.popBackStack()
            it.data?.run {
                bookListViewModel.copyBooks(getSerializableExtra(BookList.BOOKLIST_KEY) as BookList)
                bookListFragment.bookListUpdated()
            }

        }

    private val isSingleContainer: Boolean by lazy {
        findViewById<View>(R.id.container2) == null
    }

    private val selectedBookViewModel: SelectedBookViewModel by lazy {
        ViewModelProvider(this).get(SelectedBookViewModel::class.java)
    }

    private val bookListViewModel: BookList by lazy {
        ViewModelProvider(this).get(BookList::class.java)
    }

    companion object {
        const val BOOKLISTFRAGMENT_KEY = "BookListFragment"
    }

    override fun onStart() {
        super.onStart()
        val audioServiceIntent = Intent(this, PlayerService::class.java)
        bindService(audioServiceIntent, audioServiceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Grab test data
        //getBookList()

        // If we're switching from one container to two containers
        // clear BookDetailsFragment from container1
        if (supportFragmentManager.findFragmentById(R.id.container1) is BookDetailsFragment
            && selectedBookViewModel.getSelectedBook().value != null
        ) {
            supportFragmentManager.popBackStack()
        }


        // If this is the first time the activity is loading, go ahead and add a BookListFragment
        if (savedInstanceState == null) {
            bookListFragment = BookListFragment()
            supportFragmentManager.beginTransaction()
                .add(R.id.container1, bookListFragment, BOOKLISTFRAGMENT_KEY)
                .hide(supportFragmentManager.findFragmentById(R.id.controllerContainer)!!)
                .commit()
        } else {
            bookListFragment =
                supportFragmentManager.findFragmentByTag(BOOKLISTFRAGMENT_KEY) as BookListFragment
            // If activity loaded previously, there's already a BookListFragment
            // If we have a single container and a selected book, place it on top
            if (isSingleContainer && selectedBookViewModel.getSelectedBook().value != null) {
                supportFragmentManager.beginTransaction()
                    .replace(R.id.container1, BookDetailsFragment())
                    .setReorderingAllowed(true)
                    .addToBackStack(null)
                    .commit()
            }
        }

        // If we have two containers but no BookDetailsFragment, add one to container2
        if (!isSingleContainer && supportFragmentManager.findFragmentById(R.id.container2) !is BookDetailsFragment)
            supportFragmentManager.beginTransaction()
                .add(R.id.container2, BookDetailsFragment())
                .commit()

        findViewById<ImageButton>(R.id.searchButton).setOnClickListener {
            searchRequest.launch(Intent(this, SearchActivity::class.java))
        }
    }

    override fun onBackPressed() {
        // Backpress clears the selected book
        selectedBookViewModel.setSelectedBook(null)
        super.onBackPressed()
    }

    override fun bookSelected() {
        // Perform a fragment replacement if we only have a single container
        // when a book is selected

        if (isSingleContainer) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.container1, BookDetailsFragment())
                .show(supportFragmentManager.findFragmentById(R.id.controllerContainer)!!)
                .setReorderingAllowed(true)
                .addToBackStack(null)
                .commit()
        }

        var seekbar = findViewById<SeekBar>(R.id.seekBar)

        seekbar.max = selectedBookViewModel.getSelectedBook().value!!.duration

        seekbar.setOnSeekBarChangeListener(object : OnSeekBarChangeListener {
            override fun onStopTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
                // TODO Auto-generated method stub
            }

            override fun onProgressChanged(seekBar: SeekBar, progress: Int, fromUser: Boolean) {
                // TODO Auto-generated method stub
                playerPosition = progress
                audioService.seekTo(progress)
            }
        })

        findViewById<Button>(R.id.playPause).setOnClickListener {
            if (isPlaying) {
                audioService.pause()
            } else {
                audioService.play(1)
            }
            isPlaying = true
        }

        updateDisplay()
    }

    private fun updateDisplay() {
        val timer = Timer()
        timer.schedule(object : TimerTask() {
            override fun run() {
               if (audioService.isPlaying) {
                   playerPosition++;
                   findViewById<SeekBar>(R.id.seekBar).progress = playerPosition
               }
            }
        }, 0, 1000) //Update text every second
    }
}