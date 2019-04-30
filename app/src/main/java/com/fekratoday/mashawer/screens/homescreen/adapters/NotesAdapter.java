package com.fekratoday.mashawer.screens.homescreen.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;

import java.util.List;

public class NotesAdapter extends RecyclerView.Adapter<NotesAdapter.NotesViewHolder> {

    private List<Trip.Note> notes;
    private Context context;

    public NotesAdapter(Context context, List<Trip.Note> notes) {
        this.context = context;
        this.notes = notes;
    }

    @NonNull
    @Override
    public NotesViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int position) {
        LayoutInflater inflater = (LayoutInflater) viewGroup.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.layout_trip_note_row, viewGroup, false);
        return new NotesViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull NotesViewHolder notesViewHolder, int position) {
        final Trip.Note note = notes.get(position);

        notesViewHolder.checkNote.setEnabled(false);
        notesViewHolder.checkNote.setText(note.getNoteBody());
        if (note.getDoneState()==1) {
            notesViewHolder.checkNote.setChecked(true);
        } else {
            notesViewHolder.checkNote.setChecked(false);
        }
    }

    @Override
    public int getItemCount() {
        return notes.size();
    }

    class NotesViewHolder extends RecyclerView.ViewHolder {

        CheckBox checkNote;

        NotesViewHolder(@NonNull View itemView) {
            super(itemView);

            checkNote = itemView.findViewById(R.id.checkNote);
        }
    }
}
