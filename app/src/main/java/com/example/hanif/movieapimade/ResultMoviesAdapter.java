package com.example.hanif.movieapimade;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

public class ResultMoviesAdapter extends RecyclerView.Adapter<ResultMoviesAdapter.ResultMoviesViewHolder> {
    private Context context;
    private ArrayList<ResultMovies> resultMovies;

    public ResultMoviesAdapter(Context context, ArrayList<ResultMovies> resultMovies) {
        this.context = context;
        this.resultMovies = resultMovies;
    }

    public void setResultMovies(ArrayList<ResultMovies> resultMovies) {
        this.resultMovies = resultMovies;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ResultMoviesAdapter.ResultMoviesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_list_movies,viewGroup,false);
        return new ResultMoviesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ResultMoviesAdapter.ResultMoviesViewHolder holder, int position) {
        final ResultMovies result = resultMovies.get(position);
        Glide.with(context).load(result.getPosterPath()).into(holder.imgPoster);
        holder.tvJudul.setText(result.getTitle());
        holder.tvRating.setText(result.getRating());
        holder.tvDate.setText(result.getReleaseDate());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    @Override
    public int getItemCount() {
        return resultMovies.size();
    }

    public class ResultMoviesViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster;
        TextView tvJudul, tvRating, tvDate;

        public ResultMoviesViewHolder(@NonNull View itemView) {
            super(itemView);

            imgPoster = (ImageView)itemView.findViewById(R.id.poster_movie);
            tvJudul = (TextView)itemView.findViewById(R.id.movie_title);
            tvRating = (TextView)itemView.findViewById(R.id.rating);
            tvDate = (TextView)itemView.findViewById(R.id.tanggal);
        }
    }
}
