package com.example.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.myapplication.model.BadAss;

import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BadAssRecyclerViewAdapter extends RecyclerView.Adapter<BadAssRecyclerViewAdapter.BadAssViewHolder> {

    private final List<BadAss> listOfBadAss;
    private BadAssRecyclerViewAdapter.onClickBadAssItemListener onClickBadAssItemListener;


    public void setOnClickBadAssItemListener(BadAssRecyclerViewAdapter
                                                     .onClickBadAssItemListener onClickBadAssItemListener) {
        this.onClickBadAssItemListener = onClickBadAssItemListener;
    }


    public BadAssRecyclerViewAdapter(List<BadAss> listOfBadAss) {
        this.listOfBadAss = listOfBadAss;
    }


    @NonNull
    @Override
    public BadAssViewHolder onCreateViewHolder(@NonNull ViewGroup ItemsViewHolder, int viewType) {
        View view = LayoutInflater.from(ItemsViewHolder.getContext())
                .inflate(R.layout.layout_badass_details, ItemsViewHolder, false);
        return new BadAssViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull BadAssViewHolder holder, int position) {
        BadAss badAss = listOfBadAss.get(position);
        holder.badassImageView.setImageResource(listOfBadAss.get(position).getImage());
        holder.badAssTextView.setText(badAss.getName());
        holder.itemView.setOnClickListener(view -> {
            onClickBadAssItemListener
                    .onBadAssItemClick(holder.itemView, holder.getAdapterPosition());
        });
    }

    @Override
    public int getItemCount() {
        return listOfBadAss.size();
    }

    public static class BadAssViewHolder extends RecyclerView.ViewHolder{

        ImageView badassImageView;
        TextView badAssTextView;

        public BadAssViewHolder(@NonNull View itemView) {
            super(itemView);
            badassImageView = itemView.findViewById(R.id.badass_imageView);
            badAssTextView = itemView.findViewById(R.id.badass_textView);
        }
    }

    public interface onClickBadAssItemListener {
        void onBadAssItemClick(View badassItemView, int position);
    }
}
