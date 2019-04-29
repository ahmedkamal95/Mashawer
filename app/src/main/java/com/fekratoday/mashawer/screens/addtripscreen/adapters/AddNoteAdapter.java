package com.fekratoday.mashawer.screens.addtripscreen.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.screens.addtripscreen.AddTripActivity;
import com.fekratoday.mashawer.screens.addtripscreen.AddTripContract;

import java.util.List;

public class AddNoteAdapter extends RecyclerView.Adapter<AddNoteAdapter.NotesViewHolder> {

    private List<Trip.Note> notes;
    private Context context;
    private AddTripContract addTripContract;
    private boolean isEditable;

    public AddNoteAdapter(Context context, List<Trip.Note> notes, AddTripContract addTripContract, boolean isEditable) {
        this.context = context;
        this.notes = notes;
        this.addTripContract = addTripContract;
        this.isEditable = isEditable;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_add_note_row, viewGroup, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int position) {
        final Trip.Note note = notes.get(position);

        notesViewHolder.txtNote.setText(note.getNoteBody());
        notesViewHolder.imgBtnDelete.setOnClickListener(v -> {
            notes.remove(position);
            notifyDataSetChanged();
            if (isEditable) {
                addTripContract.deleteNote(note.getId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        TextView txtNote;
        ImageButton imgBtnDelete;

        NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            txtNote = itemView.findViewById(R.id.txtNote);
            imgBtnDelete = itemView.findViewById(R.id.imgBtnDelete);
        }
    }
}
