package beatmap.me.beatmap;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

public class RequestSender {

    private RequestQueue queue;
    private static final String USER_ID_URL = "https://api.spotify.com/v1/me";
    private static String id;

    public RequestSender(Context context)
    {
        queue = Volley.newRequestQueue(context);
    }

    public String getUserId()
    {
        StringRequest stringRequest = new StringRequest(Request.Method.GET, USER_ID_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.e("PlaylistsFragment", "Response is: "
                                + response.substring(0, 500));
                        id = response.substring(0, 10);
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("PlaylistsFragment", "Error: Getting user id from endpoint " +
                        USER_ID_URL + " failed.");
                        id = error.toString();
                    }
                }
        );
        queue.add(stringRequest);

        return id;
    }

    public void getPlaylists()
    {
        String url = "https://api.spotify.com/v1/users/" + getUserId() + "/playlists";

        StringRequest stringRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Display the first 500 characters of the response string.
                        Log.e("PlaylistsFragment", "Response is: "
                                + response.substring(0, 500));
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e("PlaylistsFragment", "That didn't work!");
                    }
                }
        );

        queue.add(stringRequest);
    }
}
