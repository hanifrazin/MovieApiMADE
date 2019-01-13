package com.example.hanif.movieapimade;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    private MovieLoader movieLoader;
    private RecyclerView mRecyclerView;
    private ResultMoviesAdapter movieRecyclerAdapter;
    private ArrayList<ResultMovies> movieList;

    EditText edtCari;
    ImageButton btnCari, btnRefresh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportActionBar().setTitle("Movie List");

        edtCari = (EditText)findViewById(R.id.edt_cari);
        btnCari = (ImageButton) findViewById(R.id.btn_cari);
        btnCari.setOnClickListener(this);
        btnRefresh = (ImageButton) findViewById(R.id.btn_refresh);
        btnRefresh.setOnClickListener(this);

        movieLoader = MovieLoader.getInstance(this);
        movieList = new ArrayList<>();

        setUpRecycler();
        getMovies(null);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_cari:
                getMovies(edtCari.getText().toString().trim());
                break;
            case R.id.btn_refresh:
                edtCari.setText("");
                getMovies(null);
                break;
        }
    }

    private void getMovies(String cari){
        movieLoader.getDownloadMovies(cari, new MovieLoader.MovieListener<ArrayList<ResultMovies>>() {
            @Override
            public void onMovieDownloaded(ArrayList<ResultMovies> result) {
                movieList = result;
                movieRecyclerAdapter.setResultMovies(result);
            }

            @Override
            public void onErrorDownloading(String errorMessage) {

            }
        });
    }

    private void setUpRecycler() {
        mRecyclerView = findViewById(R.id.recycler_main_movie);

        movieRecyclerAdapter = new ResultMoviesAdapter(this, movieList);

        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(movieRecyclerAdapter);

        LinearLayoutManager layout = new LinearLayoutManager(
                this, LinearLayoutManager.VERTICAL, false);
        mRecyclerView.setLayoutManager(layout);

        ItemClickSupport.addTo(mRecyclerView).setOnItemClickListener(new ItemClickSupport.OnItemClickListener() {
            @Override
            public void onItemClicked(RecyclerView recyclerView, int position, View v) {
                ResultMovies resultMovies = movieList.get(position);

                Intent detailIntent = new Intent(MainActivity.this,MoviesDetailActivity.class);
                detailIntent.putExtra("DetailMovies",resultMovies);
                startActivity(detailIntent);
            }
        });
    }
}
