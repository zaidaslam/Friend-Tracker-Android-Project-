package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zaid on 9/2/2017.
 */

public class FriendModel

{


    private static FriendModel instance;

    List<Friend> friends;

    private FriendModel(){
        friends = new ArrayList<Friend>();
    }

    public static FriendModel getInstance()
    {   if(instance == null ){
        instance = new FriendModel();
     }
        return instance;
    }

    public Friend getFriendForPosition(int Position){
        return friends.get(Position);
    }

    public Friend getFriendById(String id){
        for (Friend friend : friends)
            if (friend.getId().equals(id))
                return friend;
        return null;
    }

    public List<Friend> getFriends(){
        return friends;
    }
}
