package com.example.ptsganjil202111rpl2akhtarrasyad19.Adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ptsganjil202111rpl2akhtarrasyad19.DetailFavActivity;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Model.RealmModel;
import com.example.ptsganjil202111rpl2akhtarrasyad19.R;

import java.util.List;

public class RealmAdapter extends RecyclerView.Adapter<RealmAdapter.RealmViewHolder> {
    private List<RealmModel> mRealmModels;
    Context mContext;

    public RealmAdapter(Context context, List<RealmModel> realmModels){
        this.mContext = context;
        this.mRealmModels = realmModels;
    }

    @Override
    public RealmAdapter.RealmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_fav_item, parent, false);
        return new RealmViewHolder(v);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(RealmAdapter.RealmViewHolder holder, int position) {
        final RealmModel model = mRealmModels.get(position);
        holder.mTextViewTitle.setText(model.getmTitle());
        holder.mTextviewGenre.setText("Genre : " + model.getmGenre());
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
        return mRealmModels.size();
    }

    public class RealmViewHolder extends RecyclerView.ViewHolder{
        public TextView mTextViewTitle, mTextviewGenre;
        public ImageView mImageView;

        public RealmViewHolder(View itemView){
            super(itemView);
            mTextViewTitle = itemView.findViewById(R.id.text_title_fav_item);
            mTextviewGenre = itemView.findViewById(R.id.text_genre_fav_item);
            mImageView = itemView.findViewById(R.id.image_view_fav_item);
        }
    }
}
