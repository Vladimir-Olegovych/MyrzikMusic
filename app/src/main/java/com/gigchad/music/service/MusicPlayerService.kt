package com.gigchad.music.service

import android.Manifest.permission.POST_NOTIFICATIONS
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.app.Service
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.BitmapFactory
import android.media.MediaPlayer
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.core.app.NotificationCompat
import androidx.core.content.ContextCompat
import com.gigchad.domain.feature.home.models.MusicData
import com.gigchad.music.R
import com.gigchad.music.core.playMusicData
import com.gigchad.music.core.service.AdaptiveServiceBinder
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class MusicPlayerService: Service() {

    private var durationJob: Job? = null
    private val scope = CoroutineScope(Dispatchers.Main)
    private var mediaPlayer = MediaPlayer()
    private val isPlaying = MutableStateFlow(false)
    private val maxDuration = MutableStateFlow(0f)
    private val currentDuration = MutableStateFlow(0f)
    private val currentMusic = MutableStateFlow(MusicData())
    private var musicList = mutableListOf<MusicData>()
    val binder = MusicPlayerBinder()

    inner class MusicPlayerBinder: AdaptiveServiceBinder<MusicPlayerService>() {
        override fun getService() = this@MusicPlayerService
        fun isPlaying() =  this@MusicPlayerService.isPlaying
        fun maxDuration() =  this@MusicPlayerService.maxDuration
        fun currentDuration() =  this@MusicPlayerService.currentDuration
        fun currentMusic() = this@MusicPlayerService.currentMusic
        fun setMusicList(list: List<MusicData>){
            this@MusicPlayerService.musicList = list.toMutableList()
        }
    }

    override fun onBind(intent: Intent?): IBinder {
        return binder
    }

    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        intent?.let {
            when(intent.action) {
                PREV -> {
                    prev()
                }
                PLAY_PAUSE -> {
                    playPause()
                }
                NEXT -> {
                    next()
                }
                else -> {
                    currentMusic.update { MusicData() }
                    play(currentMusic.value)
                }
            }
        }
        return START_STICKY
    }

    fun updateDurations(){
        durationJob = scope.launch {
            if (mediaPlayer.isPlaying.not()) return@launch

            maxDuration.update { mediaPlayer.duration.toFloat() }
            while (true) {
                currentDuration.update { mediaPlayer.currentPosition.toFloat() }
                delay(1000)
            }
        }
    }

    fun prev(){
        durationJob?.cancel()
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer()

        val index = musicList.indexOf(currentMusic.value)
        val prevIndex = if (index < 0) musicList.size.minus(1) else index.minus(1)
        val prevMusicData = musicList[prevIndex]
        currentMusic.update { prevMusicData }
        mediaPlayer.playMusicData(
            musicData = prevMusicData,
            onPreparedListener = {
                sendNotification(prevMusicData)
                updateDurations()
            }
        )
    }

    fun playPause(){
        if (mediaPlayer.isPlaying) {
            mediaPlayer.pause()
        } else {
            mediaPlayer.release()
        }
        sendNotification(currentMusic.value)
    }

    fun next(){
        durationJob?.cancel()
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer()

        val index = musicList.indexOf(currentMusic.value)
        val nextIndex = index.plus(1).mod(musicList.size)
        val nextMusicData = musicList[nextIndex]
        currentMusic.update { nextMusicData }
        mediaPlayer.playMusicData(
            musicData = nextMusicData,
            onPreparedListener = {
                sendNotification(nextMusicData)
                updateDurations()
            }
        )
    }

    private fun play(musicData: MusicData){
        mediaPlayer.reset()
        mediaPlayer = MediaPlayer()
        mediaPlayer.playMusicData(
            musicData = musicData,
            onPreparedListener = {
                sendNotification(musicData)
                updateDurations()
            }
        )
    }

    private fun sendNotification(musicData: MusicData){
        isPlaying.update { mediaPlayer.isPlaying }
        val style = androidx.media.app.NotificationCompat.MediaStyle()
            .setShowActionsInCompactView(0, 1, 2)

        val notification = NotificationCompat.Builder(this, CHANNEL_ID)
            .setStyle(style)
            .setContentTitle(musicData.title)
            .setContentText(musicData.artist)
            .addAction(R.drawable.outline_skip_previous_24, PREV, createPendingIntent(PREV))
            .addAction(
                if(mediaPlayer.isPlaying) { R.drawable.outline_pause_circle_24 } else { R.drawable.outline_play_circle_24 },
                PLAY_PAUSE,
                createPendingIntent(PLAY_PAUSE)
            )
            .addAction(R.drawable.outline_skip_next_24, NEXT, createPendingIntent(NEXT))
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_foreground))
            .build()

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            if (ContextCompat.checkSelfPermission(this, POST_NOTIFICATIONS) == PackageManager.PERMISSION_GRANTED){
                startForeground(1, notification)
            }
        } else {
            startForeground(1, notification)
        }
    }
    private fun createPendingIntent(action: String): PendingIntent {
        val intent = Intent(this, MusicPlayerService::class.java)
        intent.action = action
        return PendingIntent.getService(
            this, 0, intent, PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
    }

    companion object {
        const val PREV = "prev"
        const val NEXT = "next"
        const val PLAY_PAUSE = "play_pause"

        const val CHANNEL_ID = "Music Service Channel ID (MyrzikMusic)"
        const val CHANNEL_NAME = "Music Service Channel NAME (MyrzikMusic)"
        fun bindNotification(context: Context){
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                val channel = NotificationChannel(CHANNEL_ID, CHANNEL_NAME, NotificationManager.IMPORTANCE_DEFAULT)
                val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
                notificationManager.createNotificationChannel(channel)
            }
        }
    }
}