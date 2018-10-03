package viewmodel;

import android.app.Activity;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.zaid.assignment1.R;

import javax.annotation.Nonnull;

import model.Friend;
import model.FriendModel;

/**
 * Created by Zaid on 9/2/2017.
 */

public class FriendListAdapter extends ArrayAdapter<Friend> {

    private Activity activity;

    public FriendListAdapter(Activity activity){
        super(activity,android.R.layout.simple_list_item_1, FriendModel.getInstance().getFriends());
        this.activity = activity;
    }

    @Nonnull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent){

        Log.d("dnfjhslkfdjlskjflksjdf",""+FriendModel.getInstance().getFriends().size());
        View view = activity.getLayoutInflater().inflate(R.layout.event_list_row,parent,false);

        TextView nameView = (TextView)view.findViewById(R.id.friend_name);
        TextView idView = (TextView)view.findViewById(R.id.friend_id);
        TextView DobView = (TextView)view.findViewById(R.id.friend_DOB);
        Friend friend = getItem(position);

        nameView.setText(friend.getName());
        idView.setText(friend.getId());
        DobView.setText(friend.getLatitude().toString());
        return view;
    }

}
