package com.example.ptsganjil202111rpl2akhtarrasyad19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

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

public class DetailRow extends AppCompatActivity implements View.OnClickListener {
    ImageButton btn_fav_detail;
    String title, desc, genre, image, release, actors, director, country, rating, imageLand;
    Realm realm;
    RealmHelper realmHelper;
    RealmModel realmModel;
    Integer id;
    Boolean key;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_row);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        setTitle(title);

        getIntentData();

        ProgressBar progressBar = findViewById(R.id.progress_bar);
        ImageView imageView = findViewById(R.id.image_view_detail);
        TextView textViewTitle = findViewById(R.id.text_title_detail);
        TextView textViewInfo = findViewById(R.id.text_info_detail);
        TextView textViewDesc = findViewById(R.id.text_desc_detail);
        btn_fav_detail = findViewById(R.id.btn_fav_detail);

        //holder
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

        Realm.init(this);
        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
        realm = Realm.getInstance(configuration);

//        Realm.init(DetailRow.this);
//        RealmConfiguration configuration = new RealmConfiguration.Builder().build();
//        realm = Realm.getInstance(configuration);

//        if (realm != null){
//            Number currentIdNum = realm.where(RealmModel.class).max("id");
//            if (currentIdNum == null){
//                key = true;
//                btn_fav_detail.setImageResource(R.drawable.ic_favorite);
//            } else {
//                key = false;
//            }
//        }

        btn_fav_detail.setOnClickListener(this);
    }

    private void getIntentData() {
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
        if (v == btn_fav_detail) {
            if (key == false) {
                realmModel = new RealmModel(title, desc, genre, image, release, actors, director, country, rating, imageLand);
                realmHelper = new RealmHelper(realm);
                realmHelper.save(realmModel);
                btn_fav_detail.setImageResource(R.drawable.ic_favorite);
                key = true;
                Toast.makeText(DetailRow.this, "Successfully added to favorites!", Toast.LENGTH_SHORT).show();
            } else {
                id = realmModel.getId();
                realmHelper.delete(id);
                btn_fav_detail.setImageResource(R.drawable.ic_favorite_shadow);
                key = false;
                Toast.makeText(DetailRow.this, "Successfully deleted from favorite!", Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
}