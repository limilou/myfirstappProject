package il.ac.shenkar.myfirstapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;


public class MainActivity extends Activity {
    private ListView LV;
    private TaskListArray taskLA;
    private Spinner spinner;
    private TextView sort;

    private ItemListBaseAdapter adapter ;
    private TaskListDataBase dataBase;



    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LV=(ListView) findViewById(R.id.listview);//create the list view
        taskLA= TaskListArray.getInstance(getApplicationContext());//create the task list
        //spinner= (Spinner) findViewById(R.id.sort_spinner);


        dataBase= TaskListDataBase.getInstance(getApplicationContext());
        taskLA.setTasks(dataBase.getAllTasks());

        adapter= new ItemListBaseAdapter(this);
        LV.setAdapter(adapter);

        Button addTaskButton = (Button) findViewById(R.id.add_button);
        addTaskButton.setOnClickListener(new View.OnClickListener() {

            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(),DisplayMessageActivity.class);
                startActivity(intent);

            }
        });
    /*spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

            switch(i)
            {
                case 0:
                    break;

                case 1: // Sort the list according to Task creation date
                    taskLA.sortFirstCreatedTaskFirst();
                    break;

                case 2: // Sort the list according to Task title
                    taskLA.sortByTitle();
                    break;
            }

            adapter.notifyDataSetChanged(); // Refresh the ListView when resuming this activity
        }

        public void onNothingSelected(AdapterView<?> adapterView) {

        }
     });*/
   }


    public void onResume() {
        super.onResume();  // Always call the superclass method first

       //super.onResume();
        adapter.notifyDataSetChanged();

    }
}
