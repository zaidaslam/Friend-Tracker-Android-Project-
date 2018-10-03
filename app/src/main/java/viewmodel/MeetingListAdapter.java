package viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zaid.assignment1.R;

import javax.annotation.Nonnull;

import model.Meeting;
import model.MeetingModel;


/**
 * Created by Zaid on 9/2/2017.
 */

public class MeetingListAdapter extends ArrayAdapter<Meeting> {

    private Activity activity;

    public MeetingListAdapter(Activity activity){
        super(activity,android.R.layout.simple_list_item_1, MeetingModel.getInstance().getMeetings());
        this.activity = activity;
    }

    @Nonnull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View view = activity.getLayoutInflater().inflate(R.layout.meeting_list_row,parent,false);
        //TextView idView = (TextView)view.findViewById(R.id.meeting_id);
        //TextView titleView = (TextView)view.findViewById(R.id.meeting_title);
        TextView startTimeView = (TextView)view.findViewById(R.id.meeting_startTime);
        TextView endTimeView = (TextView)view.findViewById(R.id.meeting_endTime);
        TextView locationView = (TextView)view.findViewById(R.id.meeting_location);
        Meeting meeting = getItem(position);
        //idView.setText(meeting.getId());
        //titleView.setText(meeting.getTitle());
        startTimeView.setText(meeting.getStartTime());
        endTimeView.setText(meeting.getEndTime());
        locationView.setText(meeting.getLocation());
        return view;
    }

}
