package com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.adapter;

import android.content.Context;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.R;
import com.mindvalley.test.mindvalley_muhammadfaisalhyder_android_test.anim.RecyclerViewAnimation;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * RecyclerView Adapter class to bind image on RecyclerView
 */
public class ImageAdapter extends RecyclerView.Adapter<ImageAdapter.ItemsViewHolder> {

    private List<String> imagesList;
    private Context context;
    private static ClickListener clickListener;
    private int previousPosition = -1;

    public ImageAdapter(Context con, List<String> list) {
        this.imagesList = list;
        this.context = con;
    }

    public static class ItemsViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private ImageView imageView;

        ItemsViewHolder(View itemView) {
            super(itemView);

            imageView = (ImageView) itemView.findViewById(R.id.usersImageView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.itemClicked(view, getAdapterPosition());
            }
        }

        public void cancelRequests() {
            Picasso.with(imageView.getContext()).cancelRequest(imageView);
            imageView.setImageDrawable(ResourcesCompat.getDrawable(imageView.getContext().getResources(), R.drawable.image_not_found, null));
        }
    }

    @Override
    public ItemsViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item, parent, false);
        return new ItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ItemsViewHolder itemsViewHolder, final int position) {
        Picasso.with(context)
                .load(imagesList.get(position))
                .networkPolicy(NetworkPolicy.OFFLINE)
                .error(R.drawable.image_not_found)
                .into(itemsViewHolder.imageView, new Callback() {
                    @Override
                    public void onSuccess() {
                    }

                    @Override
                    public void onError() {
                        //Download online if cache has failed
                        Picasso.with(context)
                                .load(imagesList.get(position))
                                .error(R.drawable.image_not_found)
                                .into(itemsViewHolder.imageView);
                    }
                });

        //set Animation on Recycler View item
        if (position > previousPosition)
            RecyclerViewAnimation.setFadingAnimation(itemsViewHolder.imageView);
        else RecyclerViewAnimation.setFadingAnimation(itemsViewHolder.imageView);

        previousPosition = position;
    }

    @Override
    public void onViewRecycled(ItemsViewHolder viewHolder) {
        viewHolder.cancelRequests();
    }

    @Override
    public int getItemCount() {
        return imagesList.size();
    }

    public void setListener(ClickListener clicked) {
        ImageAdapter.clickListener = clicked;
    }
}