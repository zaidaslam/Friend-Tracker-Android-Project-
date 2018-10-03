package Controller;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.zaid.assignment1.EditMeetingActivity;
import com.example.zaid.assignment1.MeetingListActivity;
import model.Meeting;
import viewmodel.MeetingListAdapter;

/**
 * Created by Zaid on 9/2/2017.
 */

public class MeetingListController implements AdapterView.OnItemClickListener {

    private String LOG_TAG = this.getClass().getName();
    private Activity activity;
    private MeetingListAdapter adapter;

    public MeetingListController(Activity activity, MeetingListAdapter adapter){
        this.activity = activity;
        this.adapter = adapter;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Meeting meeting = adapter.getItem(position);
        Log.d(LOG_TAG, String.format("Item %d clicked",position));
        Intent intent = new Intent(activity, EditMeetingActivity.class);
        intent.putExtra(MeetingListActivity.ID_EXTRA,meeting.getId());
        activity.startActivityForResult(intent, MeetingListActivity.EDIT_MEETING_REQUEST);

    }


}
