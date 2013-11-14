package il.ac.shenkar.myfirstapp;

import android.content.Context;

import java.util.ArrayList;

/**
 * Created by limor on 11/11/13.
 */
public class Singeltone {

    private static Singeltone instance = null;
    private static ArrayList<ItemDetails> arr = new ArrayList<ItemDetails>();
    private static Context context;

    private Singeltone(Context context){
        this.context = context;
    }

    public static synchronized  Singeltone getInstance()
    {
        if (instance == null)
        {
            instance = new Singeltone(context);
        }
         return instance;
    }

    public ArrayList<ItemDetails> getArr()
    {
        return arr;
    }
}
