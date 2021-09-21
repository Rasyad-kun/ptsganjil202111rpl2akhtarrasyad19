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
import com.example.ptsganjil202111rpl2akhtarrasyad19.Model.RealmModel;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Realm.RealmHelper;
import com.squareup.picasso.Picasso;

import io.realm.Realm;
import io.realm.RealmConfiguration;
import io.realm.RealmResults;

public class DetailRow extends AppCompatActivity implements View.OnClickListener {
    String title, desc, genre, image, release, actors, director, country, rating, imageLand, trailer;
    ImageView imageView, imageViewLand;
    TextView textViewTitle, textViewTitle2, textViewGenre, textViewDirector, textViewActors, textViewCountry, textViewRelease, textViewRating, textViewDesc;
    VideoView videoViewTrailer;
    ImageButton btnFavDetail;
    ProgressBar progressBar;
    Realm realm;
    RealmHelper realmHelper;
    RealmModel realmModel;
    Boolean key = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_row);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = findViewById(R.id.image_view_detail);
        imageViewLand = findViewById(R.id.imageLand_view_detail);
        textViewTitle = findViewById(R.id.text_title_detail);
        textViewTitle2 = findViewById(R.id.text_title_detail2);
        textViewGenre = findViewById(R.id.text_genre_detail);
        textViewDirector = findViewById(R.id.text_director_detail);
        textViewActors = findViewById(R.id.text_actors_detail);
        textViewCountry = findViewById(R.id.text_country_detail);
        textViewRelease = findViewById(R.id.text_release_detail);
        textViewRating = findViewById(R.id.text_rating_detail);
        textViewDesc = findViewById(R.id.text_desc_detail);
        videoViewTrailer = findViewById(R.id.video_view_detail);
        btnFavDetail = findViewById(R.id.btn_fav_detail);
        progressBar = findViewById(R.id.progress_bar_detail);

        AddData();

        Realm.init(DetailRow.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
//        realm = Realm.getDefaultInstance();

        btnFavDetail.setOnClickListener(this);
    }

    private void AddData() {
        Intent intent = getIntent();
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
            realmHelper = new RealmHelper(realm);
            if (!key) {
                realmModel = new RealmModel(title, desc, genre, image, release, actors, director, country, rating, imageLand);
                realmHelper.save(realmModel);
                btnFavDetail.setImageResource(R.drawable.ic_favorite);
                Toast.makeText(this, "Successfully added to favorites!", Toast.LENGTH_SHORT).show();
                key = true;
            } else {
//                id = realmModel.getId();
//                realmHelper.delete(id);
                Toast.makeText(this, "Already added to favorites!", Toast.LENGTH_SHORT).show();
            }
        }
    }
}