package beatmap.me.beatmap;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;

import com.spotify.sdk.android.Spotify;
import com.spotify.sdk.android.authentication.AuthenticationResponse;
import com.spotify.sdk.android.authentication.SpotifyAuthentication;
import com.spotify.sdk.android.playback.ConnectionStateCallback;
import com.spotify.sdk.android.playback.Player;
import com.spotify.sdk.android.playback.PlayerNotificationCallback;

public class LoginActivity extends Activity implements
        PlayerNotificationCallback, ConnectionStateCallback {

    private static final String CLIENT_ID = "49e382646ec34dcc931db6db5ceb1f6d";
    private static final String REDIRECT_URI = "beatmap-live://callback";
    private Player mPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        SpotifyAuthentication.openAuthWindow(CLIENT_ID, "token", REDIRECT_URI,
                new String[]{"user-read-private", "streaming", "playlist-read-private"}, null,
                this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Uri uri = intent.getData();
        if (uri != null) {
            AuthenticationResponse response = SpotifyAuthentication.parseOauthResponse(uri);
            Spotify spotify = new Spotify(response.getAccessToken());
            mPlayer = spotify.getPlayer(this, "BeatMap", this, new Player.InitializationObserver()
            {
                @Override
                public void onInitialized() {
                    mPlayer.addConnectionStateCallback(LoginActivity.this);
                    mPlayer.addPlayerNotificationCallback(LoginActivity.this);


                }

                @Override
                public void onError(Throwable throwable) {
                    Log.e("LoginActivity", "Could not initialize player: " + throwable.getMessage());
                }
            });
        }
    }

    @Override
    public void onLoggedIn() {
        Log.d("LoginActivity", "User logged in");
    }

    @Override
    public void onLoggedOut() {
        Log.d("LoginActivity", "User logged out");
    }

    @Override
    public void onTemporaryError() {
        Log.d("LoginActivity", "Temporary error occurred");
    }

    @Override
    public void onNewCredentials(String s) {
        Log.d("LoginActivity", "User credentials blob received");
    }

    @Override
    public void onConnectionMessage(String message) {
        Log.d("LoginActivity", "Received connection message: " + message);
    }

    @Override
    public void onPlaybackEvent(EventType eventType) {
        Log.d("LoginActivity", "Playback event received: " + eventType.name());
        switch (eventType) {
            // TODO: Handle event type as necessary
            default:
                break;
        }
    }

    @Override
    protected void onDestroy() {
        // VERY IMPORTANT! This must always be called or else you will leak resources
        Spotify.destroyPlayer(this);
        super.onDestroy();
    }
}