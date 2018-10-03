package Controller;

import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;

import com.example.zaid.assignment1.EditContactActivity;
import com.example.zaid.assignment1.EditMeetingActivity;
import com.example.zaid.assignment1.FriendListActivity;

import model.Friend;
import viewmodel.FriendListAdapter;

/**
 * Created by Zaid on 9/2/2017.
 */

public class FriendListController implements AdapterView.OnItemClickListener {

        private String LOG_TAG = this.getClass().getName();
        private Activity activity;
        private FriendListAdapter adapter;

    public FriendListController(Activity activity, FriendListAdapter adapter){
        this.activity = activity;
        this.adapter = adapter;
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        Friend friend = adapter.getItem(position);
        Log.d(LOG_TAG, String.format("Item %d clicked",position));
        Intent intent = new Intent(activity, EditContactActivity.class);
        intent.putExtra(FriendListActivity.ID_EXTRA,friend.getId());
        activity.startActivityForResult(intent,FriendListActivity.EDIT_FRIEND_REQUEST);

    }


}
