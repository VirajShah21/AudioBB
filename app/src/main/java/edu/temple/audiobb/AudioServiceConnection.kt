package edu.temple.audiobb

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.widget.Toast
import edu.temple.audlibplayer.PlayerService


class AudioServiceConnection(_activity: MainActivity) : ServiceConnection {
    lateinit var binder: PlayerService.MediaControlBinder
    var audioServiceBound = false
    lateinit var activity: MainActivity

    fun setMainActivity(_activity: MainActivity) {
        activity = _activity
    }

    override fun onServiceConnected(name: ComponentName, service: IBinder) {
        binder = service as PlayerService.MediaControlBinder
        audioServiceBound = true

        Toast.makeText(activity, "Audio Service Bound", Toast.LENGTH_SHORT).show();
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        audioServiceBound = false
    }

    fun play(id: Int) {
        if (!audioServiceBound) {
            val playerIntent = Intent(activity, PlayerService::class.java)
//            playerIntent.putExtra("media", media)
            activity.startService(playerIntent)
            activity.bindService(playerIntent, this, Context.BIND_AUTO_CREATE)

            binder.play(id)
        } else {
            //Service is active
            //Send media with BroadcastReceiver
        }
    }

    fun play() {
        binder.pause()
    }
}