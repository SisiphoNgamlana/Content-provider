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
    private BadAssRecyclerViewAdapter.OnLongClickBadAssItemListener onLongClickBadAssItemListener;


    public void setOnClickBadAssItemListener(BadAssRecyclerViewAdapter
                                                     .onClickBadAssItemListener onClickBadAssItemListener) {
        this.onClickBadAssItemListener = onClickBadAssItemListener;
    }

    public void setOnLongClickBadAssItemListener(OnLongClickBadAssItemListener onLongClickBadAssItemListener){
        this.onLongClickBadAssItemListener = onLongClickBadAssItemListener;
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
        BadAss badAss = listOfBadAss.get(holder.getAdapterPosition());
        holder.badassImageView.setImageResource(listOfBadAss.get(holder.getAdapterPosition()).getImage());
        holder.badAssTextView.setText(badAss.getName());
        holder.itemView.setOnClickListener(view -> {
            onClickBadAssItemListener
                    .onBadAssItemClick(holder.itemView, holder.getAdapterPosition());
        });
        holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View view) {
                onLongClickBadAssItemListener.onLongClickBadAssItemListener(view, holder.getAdapterPosition());
                notifyItemRemoved(holder.getAdapterPosition());
                return false;
            }
        });
    }

    public void clear(List<BadAss> listOfBadAss){
        this.listOfBadAss.clear();
        this.listOfBadAss.addAll(listOfBadAss);
        notifyDataSetChanged();
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

    public interface OnLongClickBadAssItemListener {
        void onLongClickBadAssItemListener(View badassItemView, int position);
    }
}
