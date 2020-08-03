package com.example.simpletodo;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ItemsAdapter extends RecyclerView.Adapter<ItemsAdapter.ViewHolder>{


    List<String> Theitems;
    OnLongClickListener thelongClickListener;
    OnClickListener theclickListener;

    public interface OnLongClickListener {
        void onItemLongClicked(int position);
    }

    public interface OnClickListener {
        void onItemClicked(int position);
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(android.R.layout.simple_list_item_1, parent, false));
    }

    public ItemsAdapter(List<String> Theitems, OnLongClickListener thelongClickListener, OnClickListener theclickListener) {
        this.Theitems = Theitems;
        this.thelongClickListener = thelongClickListener;
        this.theclickListener = theclickListener;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(Theitems.get(position));
    }

    @Override
    public int getItemCount() {
        int itemSize = Theitems.size();
        return itemSize;
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView textViewitem;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewitem = itemView.findViewById(android.R.id.text1);
        }

        public void bind(String s) {
            textViewitem.setText(s);
            textViewitem.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int adapterposition = getAdapterPosition();
                    thelongClickListener.onItemLongClicked(adapterposition);
                    boolean thestatus = true;
                    return thestatus;
                }
            });
            textViewitem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int adapterposition = getAdapterPosition();
                    theclickListener.onItemClicked(adapterposition);
                }
            });
        }
    }

}
