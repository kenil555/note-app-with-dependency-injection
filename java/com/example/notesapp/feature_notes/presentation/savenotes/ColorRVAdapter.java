package com.example.notesapp.feature_notes.presentation.savenotes;

import android.annotation.SuppressLint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.core.OnColorChanged;
import com.google.android.material.card.MaterialCardView;

public class ColorRVAdapter extends ListAdapter<String, ColorRVAdapter.ColorRVViewHolder> {

    private final OnItemClickListener clickListener;
    public ColorRVAdapter(OnItemClickListener clickListener) {
        super(ColorRVAdapter.DIFF_UTIL);
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ColorRVViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_color_states, parent, false);

        return new ColorRVViewHolder(view);
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public void onBindViewHolder(@NonNull ColorRVViewHolder holder, int position) {
        String  colorName = getItem(position);

        holder.cardView.setCardBackgroundColor(
                OnColorChanged.onColorChanged(holder.cardView.getContext(), colorName)
        );

        holder.cardView.setOnClickListener(v -> clickListener.onItemClickListener(colorName));
    }

    public class ColorRVViewHolder extends RecyclerView.ViewHolder{
        private MaterialCardView cardView;
        public ColorRVViewHolder(@NonNull View itemView) {
            super(itemView);

            cardView = itemView.findViewById(R.id.colorCV_ListItemColorState);
        }
    }

    public static final DiffUtil.ItemCallback<String> DIFF_UTIL = new DiffUtil.ItemCallback<String>() {
        @Override
        public boolean areItemsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }

        @Override
        public boolean areContentsTheSame(@NonNull String oldItem, @NonNull String newItem) {
            return oldItem.equals(newItem);
        }
    };

    public interface OnItemClickListener{
        void onItemClickListener(String colorName);
    }

}
