package com.example.ptsganjil202111rpl2akhtarrasyad19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.MediaController;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.VideoView;

import com.bumptech.glide.Glide;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Realm.RealmHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailFavActivity extends AppCompatActivity implements View.OnClickListener {
    String title, desc, genre, image, release, actors, director, country, rating, imageLand, trailer;
    Integer id;
    ImageView imageView, imageViewLand;
    TextView textViewTitle, textViewTitle2, textViewGenre, textViewDirector, textViewActors, textViewCountry, textViewRelease, textViewRating, textViewDesc;
    VideoView videoViewTrailer;
    ImageButton btnFavDetail;
    ProgressBar progressBar;
    Realm realm;
    RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fav);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.image_view_detail_fav);
        imageViewLand = findViewById(R.id.imageLand_view_detail_fav);
        textViewTitle = findViewById(R.id.text_title_detail_fav);
        textViewTitle2 = findViewById(R.id.text_title_detail2_fav);
        textViewGenre = findViewById(R.id.text_genre_detail_fav);
        textViewDirector = findViewById(R.id.text_director_detail_fav);
        textViewActors = findViewById(R.id.text_actors_detail_fav);
        textViewCountry = findViewById(R.id.text_country_detail_fav);
        textViewRelease = findViewById(R.id.text_release_detail_fav);
        textViewRating = findViewById(R.id.text_rating_detail_fav);
        textViewDesc = findViewById(R.id.text_desc_detail_fav);
        videoViewTrailer = findViewById(R.id.video_view_detail_fav);
        btnFavDetail = findViewById(R.id.btn_fav_detail_fav);
        progressBar = findViewById(R.id.progress_bar_detail_fav);

        AddData();

        Realm.init(DetailFavActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);
//        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        btnFavDetail.setOnClickListener(this);
    }

    private void AddData() {
        Intent intent = getIntent();
        id = Integer.parseInt(intent.getStringExtra("id"));
        title = intent.getStringExtra("title");
        desc = intent.getStringExtra("desc");
        genre = intent.getStringExtra("genre");
        image = intent.getStringExtra("image");
        release = intent.getStringExtra("release");
        actors = intent.getStringExtra("actors");
        director = intent.getStringExtra("director");
        country = intent.getStringExtra("country");
        rating = intent.getStringExtra("rating");
        imageLand = intent.getStringExtra("imageLand");
//        trailer = intent.getStringExtra("trailer");

        HolderData();
    }

    @SuppressLint("SetTextI18n")
    private void HolderData() {
        if (imageLand.isEmpty()) {
            Glide.with(this)
                    .load(image)
                    .fitCenter()
                    .centerCrop()
                    .placeholder(R.drawable.loader)
                    .error(image)
                    .into(imageViewLand);
        } else {
            Glide.with(this)
                    .load(imageLand)
                    .fitCenter()
                    .optionalCenterCrop()
                    .placeholder(R.drawable.loader)
                    .error(image)
                    .into(imageViewLand);
        }
        Glide.with(this)
                .load(image)
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.loader)
                .error(image)
                .into(imageView);
        textViewTitle.setText(title);
        textViewGenre.setText("Genre : " + genre);
        textViewDirector.setText("Director : " + director);
        textViewActors.setText("Actors : " + actors);
        textViewCountry.setText("Country : " + country);
        textViewRelease.setText("Release : " + release);
        textViewRating.setText("Rating : " + rating);
        textViewTitle2.setText(title);
        textViewDesc.setText("Synopsis : " + desc);

//        MediaController mediaController = new MediaController(this);
//        mediaController.setAnchorView(videoViewTrailer);
//        videoViewTrailer.setMediaController(mediaController);
//        videoViewTrailer.setVideoURI(Uri.parse(trailer));

        setTitle(title);
        progressBar.setVisibility(View.GONE);
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

    @Override
    public void onClick(View v) {
        if (v == btnFavDetail) {
            Toast.makeText(this, title + " Successfully deleted!", Toast.LENGTH_SHORT).show();
            realmHelper.delete(id);
            finish();
        }
    }
}