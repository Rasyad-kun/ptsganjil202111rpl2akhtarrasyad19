package com.example.ptsganjil202111rpl2akhtarrasyad19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Model.RealmModel;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Realm.RealmHelper;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class DetailFavActivity extends AppCompatActivity implements View.OnClickListener {
    String title, desc, genre, image, release, actors, director, country, rating, imageLand;
    Integer id;
    ImageView imageView;
    TextView textViewTitle, textViewInfo, textViewDesc;
    ImageButton btnFavDetail;
    ProgressBar progressBar;
    Realm realm;
    RealmHelper realmHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_fav);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Realm.init(DetailFavActivity.this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
//        realm = Realm.getDefaultInstance();
        realmHelper = new RealmHelper(realm);

        imageView = findViewById(R.id.image_view_detail);
        textViewTitle = findViewById(R.id.text_title_detail);
        textViewInfo = findViewById(R.id.text_info_detail);
        textViewDesc = findViewById(R.id.text_desc_detail);
        btnFavDetail = findViewById(R.id.btn_fav_detail);
        progressBar = findViewById(R.id.progress_bar);

        AddData();

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

        HolderData();
    }

    @SuppressLint("SetTextI18n")
    private void HolderData() {
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
            realmHelper.delete(id);
            Toast.makeText(this, "Delete Success", Toast.LENGTH_SHORT).show();
            finish();
        }
    }
}