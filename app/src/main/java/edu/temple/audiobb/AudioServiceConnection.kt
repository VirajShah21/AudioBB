package edu.temple.audiobb

import android.content.ComponentName
import android.content.ServiceConnection
import android.os.IBinder
import android.widget.Toast
import edu.temple.audlibplayer.PlayerService

class AudioServiceConnection(_activity: MainActivity) : ServiceConnection {
    lateinit var binder: PlayerService.MediaControlBinder
    var audioServiceBound = false
    var activity = _activity

    public override fun onServiceConnected(name: ComponentName, service: IBinder) {
        binder = service as PlayerService.MediaControlBinder
        audioServiceBound = true

        Toast.makeText(activity, "Audio Service Bound", Toast.LENGTH_SHORT).show();
    }

    override fun onServiceDisconnected(name: ComponentName?) {
        audioServiceBound = false
    }
}