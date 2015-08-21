package com.example.android.system.runtimepermissions.calendar;


import android.database.Cursor;
import android.os.Bundle;
import android.provider.CalendarContract;
import android.provider.ContactsContract;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.text.TextUtils;
import android.text.format.DateUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.android.common.logger.Log;
import com.example.android.system.runtimepermissions.R;

import java.text.DateFormat;
import java.text.Format;
import android.text.Html;
import java.util.Calendar;


/**
 * Created by frank on 8/20/15.
 */
public class CalendarFragment extends Fragment implements LoaderManager.LoaderCallbacks<Cursor> {
    private static final String TAG = "Calendars";
    private TextView mMessageText = null;

    /**
     * Projection for the content provider query includes the id, start date, and title of calendar event
     */
    private static final String[] PROJECTION = {
            CalendarContract.Events._ID,
            CalendarContract.Events.DTSTART,
            CalendarContract.Events.TITLE};

    /**
     * Selection for query all events which started from today.
     */
    private static final String SELECTION = CalendarContract.Events.DTSTART + " >= " + Calendar.getInstance().getTimeInMillis();

    /**
     * Sort order for the query. Sorted by start time in ascending order.
     */
    private static final String ORDER = CalendarContract.Events.DTSTART + " ASC";


    /**
     * Creates a new instance of a CalendarsFragment.
     */
    public static CalendarFragment newInstance() {
        return new CalendarFragment();
    }


    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_calendar, container, false);

        mMessageText = (TextView) rootView.findViewById(R.id.calendar_message);
        loadCalendar();
        return rootView;
    }

    /**
     * Restart the Loader to query the Contacts content provider to display the first contact.
     */
    private void loadCalendar() {
        getLoaderManager().restartLoader(0, null, this);
    }

    /**
     * Initialises a new {@link CursorLoader} that queries the {@link ContactsContract}.
     */
    @Override
    public Loader<Cursor> onCreateLoader(int i, Bundle bundle) {
        return new CursorLoader(getActivity(), CalendarContract.Events.CONTENT_URI, PROJECTION,
                SELECTION, null, ORDER);
    }


    /**
     * Dislays either the name of the first calendar.
     */
    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
        if (cursor != null) {
            final int totalCount = cursor.getCount();
            if (totalCount > 0) {
                String name = null;
                Long start = 0L;
                cursor.moveToFirst();
                do {
                    name = cursor.getString(cursor.getColumnIndex(CalendarContract.Events.TITLE));
                    start = cursor.getLong(cursor.getColumnIndex(CalendarContract.Events.DTSTART));
                    cursor.moveToNext();
                } while (name == null && cursor != null);
                Format df = DateFormat.getDateInstance();
                Format tf = DateFormat.getTimeInstance();

                mMessageText.setText(Html.fromHtml(
                        getResources().getString(R.string.calendar_string,
                                totalCount,
                                name,
                                df.format(start),
                                tf.format(start))));
                Log.d(TAG, "Next calendar loaded: " + name);
                Log.d(TAG, "Total number of calendars: " + totalCount);
            } else {
                Log.d(TAG, "List of calendar is empty.");
                mMessageText.setText(R.string.calendar_empty);
            }
        }
    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
        mMessageText.setText(R.string.calendar_empty);
    }
}
