package com.example.ptsganjil202111rpl2akhtarrasyad19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.squareup.picasso.Picasso;

public class DetailRow extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_row);
        ProgressBar progressBar = findViewById(R.id.progress_bar);

        Intent intent = getIntent();
        String title = intent.getStringExtra("title");
        String desc = intent.getStringExtra("desc");
        String genre = intent.getStringExtra("genre");
        String image = intent.getStringExtra("image");
        String release = intent.getStringExtra("release");
        String actors = intent.getStringExtra("actors");
        String director = intent.getStringExtra("director");
        String country = intent.getStringExtra("country");
        String rating = intent.getStringExtra("rating");
        String imageLand = intent.getStringExtra("imageLand");

        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewTitle = findViewById(R.id.text_title_detail);
        TextView textViewInfo = findViewById(R.id.text_info_detail);
        TextView textViewDesc = findViewById(R.id.text_desc_detail);

        if (imageLand.isEmpty()) {
            Glide.with(this)
                    .load(image)
                    .fitCenter()
                    .centerInside()
                    .placeholder(R.drawable.loader)
                    .error(R.mipmap.ic_launcher_round)
                    .into(imageView);
        } else {
            Glide.with(this)
                    .load(imageLand)
                    .fitCenter()
                    .centerInside()
                    .placeholder(R.drawable.loader)
                    .error(R.mipmap.ic_launcher_round)
                    .into(imageView);
        }
        textViewTitle.setText(title);
        textViewInfo.setText("Genre : " + genre +
                "\nDirector : " + director +
                "\nActors : " + actors +
                "\nCountry : " + country +
                "\nRelease : " + release +
                "\nRating : " + rating);
        textViewDesc.setText("Synopsis : " + desc);

        progressBar.setVisibility(View.GONE);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(title);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                this.finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}