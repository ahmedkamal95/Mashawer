package com.fekratoday.mashawer.utilities;

import android.app.Service;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;

import com.fekratoday.mashawer.R;
import com.fekratoday.mashawer.model.beans.Trip;
import com.fekratoday.mashawer.model.database.TripDaoSQL;

import java.util.ArrayList;
import java.util.List;

public class WidgetService extends Service {

    private WindowManager mWindowManager;
    private View mChatHeadView;
    private View collapsedView, expandedView;
    private int tripId;
    private TripDaoSQL tripDaoSQL;
    private List<Trip.Note> noteList;
    private NotesWidgetAdapter notesWidgetAdapter;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView recyclerNoteList;

    public WidgetService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        tripDaoSQL = new TripDaoSQL(this);
        tripId = intent.getIntExtra("tripId", -1);
        if (tripId > -1) {
            noteList.addAll(tripDaoSQL.getTripById(tripId).getNotesList());
            notesWidgetAdapter.notifyDataSetChanged();
        }
        return START_NOT_STICKY;
    }

    @Override
    public void onCreate() {
        super.onCreate();

        noteList = new ArrayList<>();
        mChatHeadView = LayoutInflater.from(this).inflate(R.layout.layout_chat_head, null);
        expandedView = mChatHeadView.findViewById(R.id.expanded_container);
        collapsedView = mChatHeadView.findViewById(R.id.collapse_view);

        recyclerNoteList = mChatHeadView.findViewById(R.id.recyclerNoteList);
        recyclerNoteList.setLayoutManager(new LinearLayoutManager(WidgetService.this));
        notesWidgetAdapter = new NotesWidgetAdapter(WidgetService.this, noteList);
        recyclerNoteList.setAdapter(notesWidgetAdapter);

        final WindowManager.LayoutParams params = new WindowManager.LayoutParams(
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.WRAP_CONTENT,
                WindowManager.LayoutParams.TYPE_PHONE,
                WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
                PixelFormat.TRANSLUCENT);

        params.gravity = Gravity.TOP | Gravity.START;
        params.x = 0;
        params.y = 100;

        mWindowManager = (WindowManager) getSystemService(WINDOW_SERVICE);
        if (mWindowManager != null) {
            mWindowManager.addView(mChatHeadView, params);
        }

        ImageView closeButtonCollapsed = mChatHeadView.findViewById(R.id.close_btn);
        closeButtonCollapsed.setOnClickListener(view -> stopSelf());

        ImageView closeButton = mChatHeadView.findViewById(R.id.close_button);
        closeButton.setOnClickListener(view -> {
            collapsedView.setVisibility(View.VISIBLE);
            expandedView.setVisibility(View.GONE);
        });

        mChatHeadView.findViewById(R.id.root_container).setOnTouchListener(new View.OnTouchListener() {
            private int initialX;
            private int initialY;
            private float initialTouchX;
            private float initialTouchY;

            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        initialX = params.x;
                        initialY = params.y;
                        initialTouchX = event.getRawX();
                        initialTouchY = event.getRawY();
                        return true;
                    case MotionEvent.ACTION_UP:
                        int Xdiff = (int) (event.getRawX() - initialTouchX);
                        int Ydiff = (int) (event.getRawY() - initialTouchY);

                        if (Xdiff < 10 && Ydiff < 10) {
                            if (isViewCollapsed()) {
                                collapsedView.setVisibility(View.GONE);
                                expandedView.setVisibility(View.VISIBLE);
                            }
                        }
                        return true;
                    case MotionEvent.ACTION_MOVE:
                        params.x = initialX + (int) (event.getRawX() - initialTouchX);
                        params.y = initialY + (int) (event.getRawY() - initialTouchY);
                        mWindowManager.updateViewLayout(mChatHeadView, params);
                        return true;
                }
                return false;
            }
        });
    }

    private boolean isViewCollapsed() {
        return mChatHeadView == null || mChatHeadView.findViewById(R.id.collapse_view).getVisibility() == View.VISIBLE;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mChatHeadView != null) mWindowManager.removeView(mChatHeadView);
    }
}
