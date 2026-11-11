package com.example.cinema76.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.cinema76.R;
import com.example.cinema67.models.Movie;
import java.util.List;

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.MovieViewHolder> {

    private Context context;
    private List<Movie> movieList;
    private OnMovieClickListener listener;

    public interface OnMovieClickListener {
        void onUpdateClick(Movie movie);
        void onDeleteClick(Movie movie);
    }

    public MovieAdapter(Context context, List<Movie> movieList, OnMovieClickListener listener) {
        this.context = context;
        this.movieList = movieList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_movie, parent, false);
        return new MovieViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        Movie movie = movieList.get(position);
        holder.tvTitle.setText(movie.getTitle());
        holder.tvCinema.setText("السينما: " + movie.getCinemaName());
        holder.tvDesc.setText(movie.getDescription());

        Glide.with(context).load(movie.getPosterUrl()).into(holder.imgPoster);

        holder.btnUpdate.setOnClickListener(v -> listener.onUpdateClick(movie));
        holder.btnDelete.setOnClickListener(v -> listener.onDeleteClick(movie));
    }

    @Override
    public int getItemCount() {
        return movieList.size();
    }

    public static class MovieViewHolder extends RecyclerView.ViewHolder {
        ImageView imgPoster, btnUpdate, btnDelete;
        TextView tvTitle, tvCinema, tvDesc;

        public MovieViewHolder(@NonNull View itemView) {
            super(itemView);
            imgPoster = itemView.findViewById(R.id.imgMoviePoster);
            tvTitle = itemView.findViewById(R.id.tvMovieTitle);
            tvCinema = itemView.findViewById(R.id.tvCinemaName);
            tvDesc = itemView.findViewById(R.id.tvMovieDesc);
            btnUpdate = itemView.findViewById(R.id.btnUpdateMovie);
            btnDelete = itemView.findViewById(R.id.btnDeleteMovie);
        }
    }
}