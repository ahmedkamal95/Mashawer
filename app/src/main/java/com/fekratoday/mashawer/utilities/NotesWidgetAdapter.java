package com.fekratoday.mashawer.utilities;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.NoteDaoSQL;
import com.fekratoday.mashawer.model.database.TripDaoFirebase;

import java.util.List;

public class NotesWidgetAdapter extends RecyclerView.Adapter<NotesWidgetAdapter.NotesViewHolder> {

    private List<Trip.Note> notes;
    private Context context;
    private NoteDaoSQL noteDaoSQL;
    private TripDaoFirebase tripDaoFirebase;

    public NotesWidgetAdapter(Context context, List<Trip.Note> notes) {
        this.context = context;
        this.notes = notes;
        noteDaoSQL = new NoteDaoSQL(context);
        tripDaoFirebase = new TripDaoFirebase();
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

        notesViewHolder.checkNote.setText(note.getNoteBody());

        notesViewHolder.checkNote.setOnCheckedChangeListener((buttonView, isChecked) -> {
            if (isChecked) {
                note.setDoneState(true);
                noteDaoSQL.updateNote(note);
                if (CheckInternetConnection.getInstance(context).checkInternet()) {
                    tripDaoFirebase.updateNote(note,position);
                }
            } else {
                note.setDoneState(false);
                noteDaoSQL.updateNote(note);
                if (CheckInternetConnection.getInstance(context).checkInternet()) {
                    tripDaoFirebase.updateNote(note,position);
                }
            }
        });
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
