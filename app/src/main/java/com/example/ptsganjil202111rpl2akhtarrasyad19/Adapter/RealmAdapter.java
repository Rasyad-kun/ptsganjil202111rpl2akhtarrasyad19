package com.example.ptsganjil202111rpl2akhtarrasyad19.Adapter;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ptsganjil202111rpl2akhtarrasyad19.DetailFavActivity;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Model.RealmModel;
import com.example.ptsganjil202111rpl2akhtarrasyad19.R;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Realm.RealmHelper;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmConfiguration;

public class RealmAdapter extends RecyclerView.Adapter<RealmAdapter.RealmViewHolder> {
    private List<RealmModel> mRealmModelsList;
    Context mContext;

    public RealmAdapter(Context context, List<RealmModel> realmModels) {
        this.mContext = context;
        this.mRealmModelsList = realmModels;
    }

    @Override
    public RealmAdapter.RealmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fav_item, parent, false);
        return new RealmViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RealmAdapter.RealmViewHolder holder, int position) {
        final RealmModel model = mRealmModelsList.get(position);
        holder.mTextViewTitle.setText(model.getmTitle());
        holder.mTextviewGenre.setText("Genre: " + model.getmGenre());
        Glide.with(mContext)
                .load(model.getmImage())
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.loader)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.mImageView);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(v.getContext(), DetailFavActivity.class);
                intent.putExtra("id", model.getId().toString());
                intent.putExtra("title", model.getmTitle());
                intent.putExtra("desc", model.getmDesc());
                intent.putExtra("genre", model.getmGenre());
                intent.putExtra("image", model.getmImage());
                intent.putExtra("release", model.getmRelease());
                intent.putExtra("actors", model.getmActors());
                intent.putExtra("director", model.getmDirector());
                intent.putExtra("country", model.getmCountry());
                intent.putExtra("rating", model.getmRating());
                intent.putExtra("imageLand", model.getmImageLand());
                v.getContext().startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return mRealmModelsList.size();
    }

    public class RealmViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView mTextViewTitle, mTextviewGenre;
        public ImageView mImageView;

        public RealmViewHolder(View itemView) {
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.text_title_fav_item);
            mTextviewGenre = itemView.findViewById(R.id.text_genre_fav_item);
            mImageView = itemView.findViewById(R.id.image_view_fav_item);

            Realm.init(mContext);
            RealmConfiguration configuration = new RealmConfiguration.Builder().build();
            realm = Realm.getInstance(configuration);

            view = itemView; //OnCreateContextMenuListener
            itemView.setOnCreateContextMenuListener(this); //OnCreateContextMenuListener
        }

        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Remove = menu.add(Menu.NONE, 1, 1, "Remove");
            posisi = getAdapterPosition();
            Remove.setOnMenuItemClickListener(onClickContextMenu);
        }
    }

    //OnCreateContextMenuListener
    private View view;
    private int posisi;
    RealmHelper realmHelper;
    Realm realm;
    private final MenuItem.OnMenuItemClickListener onClickContextMenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {

                case 1:
                    //Delete data, konfirmasi dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("Want to remove this?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(view.getContext(), mRealmModelsList.get(posisi).getmTitle() + " successfully removed from favorite list!", Toast.LENGTH_SHORT).show();
                                    realmHelper = new RealmHelper(realm);
                                    realmHelper.delete(mRealmModelsList.get(posisi).getId());
//                                    mRealmModelsList.remove(posisi);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            //Set your icon here
                            .setTitle("Remove process")
                            .setIcon(android.R.drawable.ic_menu_delete);
                    AlertDialog alert = builder.create();
                    alert.show();//showing the dialog
                    break;
            }
            return true;
        }
    };
}
