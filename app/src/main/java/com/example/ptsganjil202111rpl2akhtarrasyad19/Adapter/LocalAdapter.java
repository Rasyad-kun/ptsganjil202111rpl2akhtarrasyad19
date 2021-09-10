package com.example.ptsganjil202111rpl2akhtarrasyad19.Adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ptsganjil202111rpl2akhtarrasyad19.HomeActivity;
import com.example.ptsganjil202111rpl2akhtarrasyad19.Model.LocalModel;
import com.example.ptsganjil202111rpl2akhtarrasyad19.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class LocalAdapter extends RecyclerView.Adapter<LocalAdapter.LocalViewHolder>{
    private Context mContext;
    private ArrayList<LocalModel> mLocalList;

    //Detail Activity / Callback / OnItemClickListener
    private OnItemClickListener mListener;

    public interface OnItemClickListener {
        void onItemClick(int position);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    //RecyclerView
    public LocalAdapter(Context context, ArrayList<LocalModel> localList) {
        this.mContext = context;
        this.mLocalList = localList;
    }

    @NonNull
    @Override
    public LocalViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(mContext).inflate(R.layout.row_item, parent, false);
        return new LocalViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull LocalViewHolder holder, int position) {
        holder.mTextViewTitle.setText(mLocalList.get(position).getmTitle());
        holder.mTextviewGenre.setText("Genre : " + mLocalList.get(position).getmGenre());
        Glide.with(mContext)
                .load(mLocalList.get(position).getmImage())
                .fitCenter()
                .centerCrop()
                .placeholder(R.drawable.loader)
                .error(R.mipmap.ic_launcher_round)
                .into(holder.mImageView);
    }

    @Override
    public int getItemCount() {
        return mLocalList.size();
    }

    public class LocalViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        public TextView mTextViewTitle, mTextviewGenre;
        public ImageView mImageView;

        public LocalViewHolder(@NonNull View itemView) {
            super(itemView);
            view = itemView; //OnCreateContextMenuListener
            itemView.setOnCreateContextMenuListener(this); //OnCreateContextMenuListener
            mTextViewTitle = itemView.findViewById(R.id.text_title);
            mTextviewGenre = itemView.findViewById(R.id.text_genre);
            mImageView = itemView.findViewById(R.id.image_view);

            //Detail Activity / Callback / OnItemClickListener
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (mListener != null) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION) {
                            mListener.onItemClick(position);
                        }
                    }
                }
            });
        }

        //OnCreateContextMenuListener
        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            MenuItem Edit = menu.add(Menu.NONE, 1, 1, "Edit");
            MenuItem Delete = menu.add(Menu.NONE, 2, 2, "Delete");
            posisi = getAdapterPosition();
            Edit.setOnMenuItemClickListener(onClickContextMenu);
            Delete.setOnMenuItemClickListener(onClickContextMenu);
        }
    }

    //OnCreateContextMenuListener
    private View view;
    private int posisi;
    private final MenuItem.OnMenuItemClickListener onClickContextMenu = new MenuItem.OnMenuItemClickListener() {
        @Override
        public boolean onMenuItemClick(MenuItem item) {

            switch (item.getItemId()) {
                case 1:
                    //Do stuff
                    Toast.makeText(view.getContext(), mLocalList.get(posisi).getmTitle() + " is in the editing process now!", Toast.LENGTH_SHORT).show();
                    break;

                case 2:
                    //Delete data, konfirmasi dialog
                    AlertDialog.Builder builder = new AlertDialog.Builder(view.getContext());
                    builder.setMessage("want to delete this?")
                            .setCancelable(false)
                            .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int id) {
                                    Toast.makeText(view.getContext(), mLocalList.get(posisi).getmTitle() + " deleted successfully", Toast.LENGTH_SHORT).show();
                                    mLocalList.remove(posisi);
                                    notifyDataSetChanged();
                                }
                            })
                            .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    dialog.cancel();
                                }
                            })
                            //Set your icon here
                            .setTitle("Delete process")
                            .setIcon(android.R.drawable.ic_menu_delete);
                    AlertDialog alert = builder.create();
                    alert.show();//showing the dialog
                    break;
            }
            return true;
        }
    };
}
