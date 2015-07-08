
package forest.rice.field.k.preview.mediaplayer;

import java.io.Serializable;
import java.util.LinkedList;
import java.util.Queue;

import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.media.MediaPlayer.OnPreparedListener;
import android.os.Build;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.NotificationManagerCompat;
import android.support.v4.content.LocalBroadcastManager;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.SimpleTarget;

import forest.rice.field.k.R;
import forest.rice.field.k.preview.entity.Track;
import forest.rice.field.k.preview.manager.IntentManager;

public class MediaPlayerNotificationService extends Service implements
        OnPreparedListener, OnCompletionListener {

    private MediaPlayer player = null;
    private NotificationCompat.Builder notificationBuilder = null;
    private Queue<Track> playQueue = null;
    private Track playingTrack = null;
    private NotificationManagerCompat manager = null;

    public static Intent startIntent;

    public class ServiceStatics {
        public static final String ACTION_INIT = "INIT";
        public static final String ACTION_TRACK_ADD = "ADD";
        public static final String ACTION_TRACK_CLEAR = "CLEAR";
        public static final String ACTION_PLAY = "PLAY";
        public static final String ACTION_RESUME = "RESUME";
        public static final String ACTION_PAUSE = "PAUSE";
        public static final String ACTION_CLOSE = "CLOSE";
        public static final String ACTION_OPEN_PREVIEW = "OPEN_PREVIEW";
    }

    public class NotificationStatics {
        public static final int REQUEST_CODE_PLAY = 1001;
        public static final int REQUEST_CODE_PAUSE = 2001;
        public static final int REQUEST_CODE_RESUME = 3001;
        public static final int REQUEST_CODE_CLOSE = 4001;
        public static final int REQUEST_CODE_OPEN_PREVIEW = 5001;
        public static final int NOTIFY_ID = 3001;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        init();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        super.onStartCommand(intent, flags, startId);

        if (intent == null) {
            return START_STICKY;
        }

        startIntent = intent;

        String action = intent.getAction();

        if (action == null) {
        } else if (action.equals(ServiceStatics.ACTION_TRACK_ADD)) {
            Serializable s = intent.getSerializableExtra("TRACK");
            add(new Track(s));
        } else if (action.equals(ServiceStatics.ACTION_TRACK_CLEAR)) {
            clear();
        } else if (action.equals(ServiceStatics.ACTION_PLAY)) {
            play();
        } else if (action.equals(ServiceStatics.ACTION_PAUSE)) {
            pause();
        } else if (action.equals(ServiceStatics.ACTION_RESUME)) {
            resume();
        } else if (action.equals(ServiceStatics.ACTION_CLOSE)) {
            close();
        } else if (action.equals(ServiceStatics.ACTION_OPEN_PREVIEW)) {
            startActivity(IntentManager.createOpenPreviewIntent(getBaseContext()));
        }
        return START_STICKY;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        if (player.isPlaying()) {
            player.stop();
        }
        player.release();
        player = null;

        manager.cancelAll();
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public boolean stopService(Intent name) {
        return super.stopService(name);
    }

    private void init() {
        if (player == null) {
            player = new MediaPlayer();
            player.setAudioStreamType(AudioManager.STREAM_MUSIC);
        }

        if (notificationBuilder == null) {
            notificationBuilder = new NotificationCompat.Builder(
                    getApplicationContext());

            Intent playIntent = new Intent(getApplicationContext(),
                    MediaPlayerNotificationService.class);
            playIntent.setAction(ServiceStatics.ACTION_RESUME);
            PendingIntent playPIntent = PendingIntent.getService(
                    getApplicationContext(),
                    NotificationStatics.REQUEST_CODE_PLAY, playIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Intent pauseIntent = new Intent(getApplicationContext(),
                    MediaPlayerNotificationService.class);
            pauseIntent.setAction(ServiceStatics.ACTION_PAUSE);
            PendingIntent pausePIntent = PendingIntent.getService(
                    getApplicationContext(),
                    NotificationStatics.REQUEST_CODE_PAUSE, pauseIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Intent closeIntent = new Intent(getApplicationContext(),
                    MediaPlayerNotificationService.class);
            closeIntent.setAction(ServiceStatics.ACTION_CLOSE);
            PendingIntent closePIntent = PendingIntent.getService(
                    getApplicationContext(),
                    NotificationStatics.REQUEST_CODE_CLOSE, closeIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            Intent openPreviewIntent = new Intent(getApplicationContext(),
                    MediaPlayerNotificationService.class);
            openPreviewIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            openPreviewIntent.addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
            openPreviewIntent.setAction(ServiceStatics.ACTION_OPEN_PREVIEW);
            PendingIntent openPreviewPIntent = PendingIntent.getService(getApplicationContext(),
                    NotificationStatics.REQUEST_CODE_OPEN_PREVIEW, openPreviewIntent,
                    PendingIntent.FLAG_UPDATE_CURRENT);

            notificationBuilder.addAction(android.R.drawable.ic_media_play,
                    "Play", playPIntent);
            notificationBuilder.addAction(android.R.drawable.ic_media_pause,
                    "Pause", pausePIntent);
            notificationBuilder.setDeleteIntent(closePIntent);
            notificationBuilder.setContentIntent(openPreviewPIntent);

            notificationBuilder.setColor(Color.argb(255,63, 81, 181));
            notificationBuilder.setAutoCancel(false);
        }

        if (playQueue == null) {
            playQueue = new LinkedList<>();
        }

        manager = NotificationManagerCompat.from(getApplicationContext());
    }

    private void add(Track track) {
        if(track.get(Track.trackId).equals("DUMMY")) {
            return;
        }
        if (playQueue != null) {
            playQueue.add(track);
        }
    }

    private void clear() {
        playQueue.clear();
    }

    private void play() {
        playingTrack = playQueue.poll();
        if (playingTrack == null) {
            stopSelf();
            sendBroadcastForStop();
            return;
        }
        if (player != null) {
            if (player.isPlaying()) {
                player.stop();
            }
            try {
                String url = playingTrack.get(Track.previewUrl);
                player.reset();
                player.setDataSource(url);
                player.prepareAsync();
                player.setOnPreparedListener(this);
                player.setOnCompletionListener(this);
                sendBroadcastForPlay(playingTrack);
            } catch (Exception e) {
            }
        }

        if (notificationBuilder != null) {
            if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                notificationBuilder.setSmallIcon(android.R.drawable.ic_media_play);
            } else {
                notificationBuilder.setSmallIcon(R.drawable.ic_launcher);
            }

            notificationBuilder.setContentTitle(playingTrack
                    .get(Track.trackName)); // 1行目
            notificationBuilder.setContentText(playingTrack
                    .get(Track.artistName)); // 2行目
            notificationBuilder.setSubText(playingTrack
                    .get(Track.collectionName)); // 3行目
            notificationBuilder.setContentInfo("Preview"); // 右端

            notificationBuilder.setOngoing(true);

            manager.notify(NotificationStatics.NOTIFY_ID,
                    notificationBuilder.build());

            // Bitmap画像は非同期で取得
            Glide.with(getBaseContext()).load(playingTrack.getLargestArtwork()).asBitmap().into(new SimpleTarget<Bitmap>() {
                @Override
                public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
                    notificationBuilder.setLargeIcon(resource);
                    manager.notify(NotificationStatics.NOTIFY_ID,
                            notificationBuilder.build());
                }
            });
        }
    }

    private void pause() {
        try {
            player.pause();
        } catch (IllegalStateException e) {

        }

        if (notificationBuilder != null) {
            notificationBuilder.setOngoing(false);
            manager.notify(NotificationStatics.NOTIFY_ID,
                    notificationBuilder.build());
        }

        sendBroadcastForPause(playingTrack);
    }

    private void resume() {
        if (player != null) {
            player.start();
        }

        if (notificationBuilder != null) {
            notificationBuilder.setOngoing(true);

            manager.notify(NotificationStatics.NOTIFY_ID,
                    notificationBuilder.build());
        }

        sendBroadcastForPlay(playingTrack);
    }

    private void close() {
        stopSelf();
        sendBroadcastForStop();
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        mp.start();
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        playingTrack = null;
        play();
    }

    public void sendBroadcastForPlay(Track track) {
        Intent intent = new Intent();
        intent.putExtra("track", track);
        intent.setAction("PLAYING_TRACK");
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
    }

    public void sendBroadcastForPause(Track track) {
        Intent intent = new Intent();
        intent.putExtra("track", track);
        intent.setAction("PAUSE_TRACK");
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
    }

    public void sendBroadcastForStop() {
        Intent intent = new Intent();
        intent.setAction("STOP_TRACK");
        LocalBroadcastManager.getInstance(getBaseContext()).sendBroadcast(intent);
    }

}
