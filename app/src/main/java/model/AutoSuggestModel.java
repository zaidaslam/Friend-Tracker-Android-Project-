package model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Zaid on 9/2/2017.
 */

public class AutoSuggestModel

{


    private static AutoSuggestModel instance;

    List<AutoSuggest> autoSuggest;

    private AutoSuggestModel(){
            autoSuggest = new ArrayList<AutoSuggest>();
    }

    public static AutoSuggestModel getInstance()
    {   if(instance == null ){
        instance = new AutoSuggestModel();
    }
        return instance;
    }

    public AutoSuggest getAutoSuggestForPosition(int Position){
        return autoSuggest.get(Position);
    }

    public AutoSuggest getAutoSuggestById(String id){
        for (AutoSuggest autoSuggests : autoSuggest)
            if (autoSuggests.getId().equals(id))
                return autoSuggests;
        return null;
    }

    public List<AutoSuggest> getAutoSuggest(){
        return autoSuggest;
    }
}
