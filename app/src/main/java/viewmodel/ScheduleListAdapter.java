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

import model.Friend;
import model.FriendModel;
import model.Schedule;
import model.ScheduleModel;

/**
 * Created by Zaid on 9/2/2017.
 */

public class ScheduleListAdapter extends ArrayAdapter<Schedule> {

    private Activity activity;

    public ScheduleListAdapter(Activity activity){
        super(activity,android.R.layout.simple_list_item_1, ScheduleModel.getInstance().getSchedule());
        this.activity = activity;
    }

    @Nonnull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        View view = activity.getLayoutInflater().inflate(R.layout.schedule_list_row,parent,false);
        TextView idView = (TextView)view.findViewById(R.id.friend_id);
        TextView nameView = (TextView)view.findViewById(R.id.friend_name);
        TextView DobView = (TextView)view.findViewById(R.id.friend_DOB);
        Schedule schedule = getItem(position);
        idView.setText(schedule.getId());
        nameView.setText(schedule.getName());
        DobView.setText(schedule.getDOB());
        return view;
    }

}
