package com.example.cinema76.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.cinema76.R;
import com.example.cinema76.adapters.MovieAdapter;
import com.example.cinema76.models.Movie;
import com.example.cinema76.services.FirebaseServices;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import java.util.ArrayList;
import java.util.List;

public class ShowMoviesFragment extends Fragment {

    private RecyclerView recyclerView;
    private MovieAdapter adapter;
    private List<Movie> movieList;
    private FirebaseServices fbs;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_show_movies, container, false);

        fbs = FirebaseServices.getInstance();
        recyclerView = view.findViewById(R.id.rvMovies);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        movieList = new ArrayList<>();

        adapter = new MovieAdapter(getContext(), movieList, new MovieAdapter.OnMovieClickListener() {
            @Override
            public void onUpdateClick(Movie movie) {
                Toast.makeText(getContext(), "تعديل: " + movie.getTitle(), Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onDeleteClick(Movie movie) {
                showDeleteConfirmationDialog(movie);
            }
        });

        recyclerView.setAdapter(adapter);
        loadMoviesFromFirestore();

        return view;
    }

    private void loadMoviesFromFirestore() {
        fbs.getDb().collection("movies").get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                movieList.clear();
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Movie movie = document.toObject(Movie.class);
                    movieList.add(movie);
                }
                adapter.notifyDataSetChanged();
            } else {
                Toast.makeText(getContext(), "فشل في تحميل الأفلام", Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void showDeleteConfirmationDialog(Movie movie) {
        new AlertDialog.Builder(getContext())
                .setTitle("حذف الفيلم")
                .setMessage("هل أنت متأكد من رغبتك في حذف فيلم " + movie.getTitle() + "؟")
                .setPositiveButton("نعم", (dialog, which) -> {
                    fbs.getDb().collection("movies").document(movie.getId()).delete()
                            .addOnSuccessListener(aVoid -> {
                                Toast.makeText(getContext(), "تم حذف الفيلم بنجاح", Toast.LENGTH_SHORT).show();
                                loadMoviesFromFirestore();
                            });
                })
                .setNegativeButton("لا", null)
                .show();
    }
}