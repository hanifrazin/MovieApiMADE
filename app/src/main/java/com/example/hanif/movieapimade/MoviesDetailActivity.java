package com.example.hanif.movieapimade;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

public class MoviesDetailActivity extends AppCompatActivity {
    ImageView PosterDetail, BackDrop;
    TextView tvTitle, tvRating, tvRelease, tvSinopsis;
    ResultMovies resultMovies;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result_movies_detail);
        PosterDetail = (ImageView)findViewById(R.id.detail_poster);
        BackDrop = (ImageView)findViewById(R.id.backdrop);
        tvTitle = (TextView)findViewById(R.id.tv_title_detail);
        tvRating = (TextView)findViewById(R.id.tv_rating_detail);
        tvRelease = (TextView)findViewById(R.id.tv_tanggal_rilis);
        tvSinopsis = (TextView)findViewById(R.id.isi_sinopsis);

        resultMovies = (ResultMovies)getIntent().getSerializableExtra("DetailMovies");
        getSupportActionBar().setTitle("Movie Detail");
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Glide.with(MoviesDetailActivity.this).load(resultMovies.getBackdropPath()).into(BackDrop);
        Glide.with(MoviesDetailActivity.this).load(resultMovies.getPosterPath()).into(PosterDetail);
        tvTitle.setText(resultMovies.getTitle());
        tvRating.setText(resultMovies.getRating());
        tvRelease.setText(resultMovies.getReleaseDate());
        tvSinopsis.setText(resultMovies.getOverview());
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
