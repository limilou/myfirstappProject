package il.ac.shenkar.myfirstapp;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;



public class ItemListBaseAdapter extends BaseAdapter {

    private TaskListArray listArray;
    private Context taskListContext;
    private LayoutInflater l_Inflater;

    public ItemListBaseAdapter(Context context) {

         taskListContext = context;
        listArray= TaskListArray.getInstance(context);
        l_Inflater = LayoutInflater.from(context);
    }

    public int getCount() {
        return listArray.getSize();
    }

    public Object getItem(int position) {
        return listArray.getTask(position);
    }

    public long getItemId(int position) {
        return position;
    }


    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        final int index = position;

        if (convertView == null) {

            convertView = l_Inflater.inflate(R.layout.activity_listview,null);
            holder = new ViewHolder();
            holder.taskTitle = (TextView) convertView.findViewById(R.id.task_title);
            holder.taskDescription = (TextView) convertView.findViewById(R.id.task_description);
            holder.taskID = (TextView) convertView.findViewById(R.id.task_id);

            holder.done= (Button) convertView.findViewById(R.id.doneButton);

            convertView.setTag(holder);

        }
    else{
            holder=(ViewHolder) convertView.getTag();
        }
        holder.taskTitle.setText(listArray.getTask(position).getTitle());
        holder.taskDescription.setText(listArray.getTask(position).getDescription());
        holder.taskID.setText(String.valueOf(listArray.getTask(position).getId()));

        holder.done.setOnClickListener(new OnClickListener()
        {

                public void onClick(View v)
                {
                    DialogInterface.OnClickListener dialogClickListener =new DialogInterface.OnClickListener()
                    {
                        public void onClick(DialogInterface dialog, int which)
                        {
                            switch (which)
                            {
                                case DialogInterface.BUTTON_POSITIVE:
                                    listArray.removeTask(index);
                                    notifyDataSetChanged();
                                    break;
                                case DialogInterface.BUTTON_NEGATIVE:
                                    break;
                            }
                        }
                    };

                    AlertDialog.Builder builder = new AlertDialog.Builder(taskListContext);
                    builder.setMessage("Are you sure this task is done?").setPositiveButton("Yes",dialogClickListener).setNegativeButton("NO", dialogClickListener).show();
                }
            });

        return convertView;
        }


     static class ViewHolder {

        public TextView taskID;
        public TextView taskDescription;
        public TextView taskTitle;
        public Button done = null;
    }

}