package com.example.manifestexplorer;

import android.app.Activity;
import android.app.ActivityOptions;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.transition.ChangeTransform;
import android.util.Pair;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

public class AppRVAdapter extends RecyclerView.Adapter<AppRVAdapter.appViewHolder>{
    private Context mContext;
    private ArrayList<AppliInfo> appList;

    public AppRVAdapter(Context context, ArrayList<AppliInfo> apps){
        mContext = context;
        appList = apps;
    }
    @NonNull
    @Override
    public appViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardlayout, parent, false);
        appViewHolder appViewHolder = new appViewHolder(itemView);
        return appViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull appViewHolder holder, int position) {
        final AppliInfo app = appList.get(position);
        holder.mAppName.setText(app.getTitle());
        holder.mImageView.setImageBitmap(app.getIcon());
        holder.mCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), ManifestInfoActivity.class);
                Bundle bundle = new Bundle();
                bundle.putParcelable("app", app);
                intent.putExtras(bundle);
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation((Activity)view.getContext(), Pair.create(view.findViewById(R.id.cardNoteTitle), "transition1"), Pair.create(view.findViewById(R.id.imageView4), "transition2"));
                    view.getContext().startActivity(intent, options.toBundle());
                    //overridePendingtransition()
                } else {
                    view.getContext().startActivity(intent);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return appList.size();
    }

    public class appViewHolder extends RecyclerView.ViewHolder{
        public TextView mAppName;
        public CardView mCardView;
        public ImageView mImageView;
        public appViewHolder(View itemView) {
            super(itemView);
            mAppName = itemView.findViewById(R.id.cardNoteTitle);
            mCardView = itemView.findViewById(R.id.cardViewNote);
            mImageView = itemView.findViewById(R.id.imageView4);
        }
    }

}
