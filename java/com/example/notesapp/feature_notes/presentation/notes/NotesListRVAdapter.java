package com.example.notesapp.feature_notes.presentation.notes;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.notesapp.R;
import com.example.notesapp.core.OnColorChanged;
import com.example.notesapp.feature_notes.domain.model.Note;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.floatingactionbutton.ExtendedFloatingActionButton;

public class NotesListRVAdapter extends ListAdapter<Note , NotesListRVAdapter.NoteViewHolder> {

    private final OnItemClickListener listener;
    private final OnDeleteClickListener onDeleteClickListener;
    protected NotesListRVAdapter(OnItemClickListener listener, OnDeleteClickListener deleteClickListener) {
        super(NotesListRVAdapter.DIFF_UTIL);
        this.listener = listener;
        this.onDeleteClickListener = deleteClickListener;
    }

    @NonNull
    @Override
    public NoteViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.list_item_notes, parent, false);
        return new NoteViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NoteViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    class NoteViewHolder extends RecyclerView.ViewHolder{

        MaterialCardView cardView;
        TextView titleTextView;
        TextView contentTextView;
        ExtendedFloatingActionButton deleteBtn;

        public NoteViewHolder(@NonNull View view){
            super(view);

            cardView = view.findViewById(R.id.cv_listItemNotes);
            titleTextView = view.findViewById(R.id.titleTV_listItemNotes);
            contentTextView = view.findViewById(R.id.contentTv_listItemNotes);
            deleteBtn = view.findViewById(R.id.deleteBtn_listItemNotes);
        }

        public void bind(Note item){
            cardView.setOnClickListener(v -> listener.onItemClickListener(item.id));
            titleTextView.setText(item.Title);
            titleTextView.setGravity(Gravity.START);
            contentTextView.setText(item.Content);
            deleteBtn.setOnClickListener(v ->
                    onDeleteClickListener.onDeleteClickListener(item)
            );
            cardView.setCardBackgroundColor(
                    OnColorChanged.onColorChanged(cardView.getContext(), item.Color)
            );
        }
    }

    public static final DiffUtil.ItemCallback<Note> DIFF_UTIL = new DiffUtil.ItemCallback<Note>() {
        @Override
        public boolean areItemsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem == newItem;
        }

        @Override
        public boolean areContentsTheSame(@NonNull Note oldItem, @NonNull Note newItem) {
            return oldItem.id == newItem.id &&
                    oldItem.Title.equals(newItem.Title) &&
                    oldItem.Content.equals(newItem.Content) &&
                    oldItem.Color.equals(newItem.Color);
        }
    };

    interface OnItemClickListener{
        void onItemClickListener(int id);
    }
    interface OnDeleteClickListener{
        void onDeleteClickListener(Note note);
    }
}
