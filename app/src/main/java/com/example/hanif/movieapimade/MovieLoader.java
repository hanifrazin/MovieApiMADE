package com.example.hanif.movieapimade;

import android.content.Context;
import android.net.Uri;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MovieLoader {
    public interface MovieListener<AnyType> {
        void onMovieDownloaded(AnyType a);

        void onErrorDownloading(String errorMessage);
    }

    private static MovieLoader movieLoader = null;
    private RequestQueue requestQueue;
    private Context context;
    private String api_key = "439b3999c7539025504db61e9d4e034a";
    private String url_tmdb = "https://api.themoviedb.org/3";
    private String language = "en-US";

    public MovieLoader(Context context) {
        this.context = context;
        requestQueue = Volley.newRequestQueue(context);
    }

    public static synchronized MovieLoader getInstance(Context context) {
        if (movieLoader == null)
            movieLoader = new MovieLoader(context);

        return movieLoader;
    }

    public void getDownloadMovies(String cari, final MovieListener<ArrayList<ResultMovies>> listener){
        Uri uri = null;
        String urlMovies = null;
        if((cari != null) && (!cari.equals(""))){
            urlMovies = url_tmdb+"/search/movie";
            uri = Uri.parse(urlMovies).buildUpon()
                    .appendQueryParameter("api_key", api_key)
                    .appendQueryParameter("language", language)
                    .appendQueryParameter("query", cari)
                    .build();
        }else{
            urlMovies = url_tmdb+"/movie/popular";
            uri = Uri.parse(urlMovies).buildUpon()
                    .appendQueryParameter("api_key", api_key)
                    .build();
        }
        Log.d("loader", uri.toString());

        final JsonObjectRequest request = new JsonObjectRequest(
                Request.Method.GET, uri.toString(), null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {

                        ArrayList<ResultMovies> resultMovies = parseMovies(response);

                        listener.onMovieDownloaded(resultMovies);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                listener.onErrorDownloading("Error connecting to the API");
            }
        }
        );

        requestQueue.add(request);

    }

    private ArrayList<ResultMovies> parseMovies(JSONObject jsonObject) {
        try {
            ArrayList<ResultMovies> resultMovies = new ArrayList<>();

            JSONArray array = jsonObject.getJSONArray("results");

            for (int i = 0; i < array.length(); i++) {

                JSONObject object = array.getJSONObject(i);

                int id = object.getInt("id");
                String title = object.getString("title");
                String poster = object.getString("poster_path");
                String ori_lang = object.getString("original_language");
                String ori_title = object.getString("original_title");
                String backdrop = object.getString("backdrop_path");
                String overview = object.getString("overview");
                String release_date = object.getString("release_date");
                String rating = String.valueOf(object.getDouble("vote_average"));


                ResultMovies result = new ResultMovies(id, title, poster, ori_lang, ori_title,
                                                    backdrop, overview, release_date, rating);

                resultMovies.add(result);
            }

            return resultMovies;
        } catch (JSONException err) {
            // Error occurred!
            return null;
        }
    }
}
