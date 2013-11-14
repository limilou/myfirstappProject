package il.ac.shenkar.myfirstapp;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.View;
import android.content.*;
import android.widget.EditText;
import android.widget.ListView;


import java.lang.String;
import java.util.ArrayList;

public class MainActivity extends Activity {

    private Singeltone itemDetailsrrayList=null;
    private ItemListBaseAdapter MyList ;

    public final static String EXTRA_MESSAGE = "ac.il.shenkar.myfirstapp.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MyList= new ItemListBaseAdapter(this,itemDetailsrrayList.getInstance().getArr());
        updatelist();

    }

    public void updatelist ()
    {
        ListView lv = (ListView) findViewById(R.id.listView);
        ItemListBaseAdapter MyList = new ItemListBaseAdapter(this,itemDetailsrrayList.getInstance().getArr());
        lv.setAdapter(MyList);

    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public void onResume() {
        super.onResume();  // Always call the superclass method first

        updatelist();

    }
    /** Called when the user clicks the Send button */
    public void sendMessage(View view) {

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        startActivity(intent);

    }



    public void done (View view)
    {
        ListView lv = (ListView) findViewById(R.id.listView);
        int position =lv.getPositionForView(view);
        ItemDetails toDel = (ItemDetails) lv.getItemAtPosition(position);
        itemDetailsrrayList.getInstance().getArr().remove(toDel);
        updatelist();
        MyList.notifyDataSetChanged();

    }
}
