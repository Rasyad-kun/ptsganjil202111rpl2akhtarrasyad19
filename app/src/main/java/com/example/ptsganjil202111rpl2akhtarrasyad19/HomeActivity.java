package com.example.ptsganjil202111rpl2akhtarrasyad19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Adapter.LocalAdapter;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Model.LocalModel;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class HomeActivity extends AppCompatActivity implements LocalAdapter.OnItemClickListener {
    private RecyclerView recyclerView;
    private LocalAdapter localAdapter;
    private ArrayList<LocalModel> localList;
    private ProgressBar progressBar;
    private TextView tv_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        setTitle("Movie List");

        progressBar = findViewById(R.id.progress_bar);
        tv_empty = findViewById(R.id.tv_empty);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this)); //*

        AddData();
    }

    private void AddData() {
        String url = "https://api-filmapik.herokuapp.com/latest";
        AndroidNetworking.get(url)
                .build()
                .getAsJSONObject(new JSONObjectRequestListener() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            localList = new ArrayList<>();
                            JSONArray jsonArray = response.getJSONArray("result");

                            for (int i = 0; i < jsonArray.length(); i++) {
                                JSONObject result = jsonArray.getJSONObject(i);
                                String title = result.getString("title");
                                String image = result.getString("thumbnailPotrait");
                                String rating = result.getString("rating");
                                JSONObject detail = result.getJSONObject("detail");
                                String desc = detail.getString("description");
                                String genre = detail.getString("genre");
                                String release = detail.getString("release");
                                String actors = detail.getString("actors");
                                String director = detail.getString("director");
                                String country = detail.getString("country");
                                String imageLand = detail.getString("thumbnailLandscape");

                                localList.add(new LocalModel(title, desc, genre, image, release, actors, director, country, rating, imageLand));
                            }

                            ShowData();

                        } catch (JSONException e) {
                            Log.d("error", e.toString());
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.d("error", anError.toString());
                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progressBar.setVisibility(View.VISIBLE);
        localAdapter.notifyDataSetChanged();
        ShowData();
    }

    private void ShowData() {
        localAdapter = new LocalAdapter(this, localList); //*
        recyclerView.setAdapter(localAdapter); //*
        localAdapter.setOnItemClickListener(HomeActivity.this); //Detail Activity / Callback / OnItemClickListener

        if (localList.isEmpty()) {
            tv_empty.setVisibility(View.VISIBLE);
        } else {
            tv_empty.setVisibility(View.INVISIBLE);
        }

        progressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onItemClick(int position) {
        Intent detailIntent = new Intent(this, DetailRow.class);
        LocalModel clickedRow = localList.get(position);

        detailIntent.putExtra("title", clickedRow.getmTitle());
        detailIntent.putExtra("desc", clickedRow.getmDesc());
        detailIntent.putExtra("genre", clickedRow.getmGenre());
        detailIntent.putExtra("image", clickedRow.getmImage());
        detailIntent.putExtra("release", clickedRow.getmRelease());
        detailIntent.putExtra("actors", clickedRow.getmActors());
        detailIntent.putExtra("director", clickedRow.getmDirector());
        detailIntent.putExtra("country", clickedRow.getmCountry());
        detailIntent.putExtra("rating", clickedRow.getmRating());
        detailIntent.putExtra("imageLand", clickedRow.getmImageLand());

        startActivity(detailIntent);
    }

    @SuppressLint("NonConstantResourceId")
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_refresh:
                onRestart();
                Toast.makeText(this, "Refreshing now!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_home:
                Toast.makeText(this, "On the homepage now!!", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.menu_favorite:
                startActivity(new Intent(HomeActivity.this, FavActivity.class));
                Toast.makeText(this, "Moving to the favorite list now!", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.locale_menu, menu);
        return true;
    }
}