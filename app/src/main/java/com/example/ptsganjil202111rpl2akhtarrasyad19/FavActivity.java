package com.example.ptsganjil202111rpl2akhtarrasyad19;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ptsganjil202111rpl2akhtarrasyad19.Adapter.RealmAdapter;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Model.RealmModel;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Realm.RealmHelper;

import java.util.ArrayList;
import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class FavActivity extends AppCompatActivity {
    Realm realm;
    RealmHelper realmHelper;
    RecyclerView recyclerView;
    RealmAdapter realmAdapter;
    List<RealmModel> realmModelList;
    ProgressBar progressBar;
    TextView tv_empty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fav);
        setTitle("Favorite List");

        progressBar = findViewById(R.id.progress_bar_fav);
        tv_empty = findViewById(R.id.tv_empty_fav);
        recyclerView = findViewById(R.id.recycler_view_fav);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Setup Realm
        RealmConfiguration configuration = new RealmConfiguration.Builder().allowWritesOnUiThread(true).build();
        realm = Realm.getInstance(configuration);
//        realm = Realm.getDefaultInstance();

        realmHelper = new RealmHelper(realm);
        realmModelList = new ArrayList<>();

        realmModelList = realmHelper.getAllRealm();

        show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        progressBar.setVisibility(View.VISIBLE);
        realmAdapter.notifyDataSetChanged();
        show();
    }

    public void show() {
        realmAdapter = new RealmAdapter(this, realmModelList);
        recyclerView.setAdapter(realmAdapter);

        if (realmModelList.isEmpty()) {
            tv_empty.setVisibility(View.VISIBLE);
        } else {
            tv_empty.setVisibility(View.INVISIBLE);
        }

        progressBar.setVisibility(View.INVISIBLE);
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
                startActivity(new Intent(FavActivity.this, HomeActivity.class));
                Toast.makeText(this, "Back to homepage!", Toast.LENGTH_SHORT).show();
                finish();
                return true;
            case R.id.menu_favorite:
                Toast.makeText(this, "On favorite list now!", Toast.LENGTH_SHORT).show();
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