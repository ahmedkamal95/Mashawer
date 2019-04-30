package com.fekratoday.mashawer.utilities;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.LinearLayout.LayoutParams;
import android.widget.ScrollView;

import com.fekratoday.mashawer.model.beans.Trip;

import java.util.List;


public class DynamicLayout extends AppCompatActivity {
    ScrollView scrollView;
    LinearLayout linearLayout;
    LayoutParams layoutParams;
    List<Trip.Note> notesList;

    public DynamicLayout() {

    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        scrollView = new ScrollView(this);
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.VERTICAL);
        scrollView.addView(linearLayout);
        for (Trip.Note note : notesList) {
            CheckBox checkBox = new CheckBox(this);
            checkBox.setId(note.getId());
            checkBox.setText(note.getNoteBody());
            linearLayout.addView(checkBox);
            checkBox.setOnCheckedChangeListener((CompoundButton, button) -> {
                if (button) {
                    note.setDoneState(true);
                }
            });

            setContentView(scrollView);
        }
    }
}
