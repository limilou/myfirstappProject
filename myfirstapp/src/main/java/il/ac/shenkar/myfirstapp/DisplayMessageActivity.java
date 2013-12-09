package il.ac.shenkar.myfirstapp;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog.OnDateSetListener;
import android.app.TimePickerDialog.OnTimeSetListener;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;
import java.util.Calendar;

@SuppressLint("HandlerLeak")
public class DisplayMessageActivity extends FragmentActivity implements OnDateSetListener, OnTimeSetListener
{
    // Variables for the DatePicker
    private DatePickerFragment newDateFragment = null;
    private int year=-1, month=-1, day=-1;

    // Variables for the TimePicker
    private TimePickerFragment newTimeFragment = null;
    private int hour=-1, minute=-1;

    private TextView dateSeted;
    private TextView timeSeted;
    private boolean checked;
    private TaskListDataBase taskDataBase;
    private Button setDate;
    private Button setTime;
    static final int DATE_DIALOG_ID = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_displaymessageactivity); // Setting the UI view with create_task_list

        taskDataBase = TaskListDataBase.getInstance(getApplicationContext());
        dateSeted = (TextView) findViewById(R.id.date_set_view);
        timeSeted = (TextView) findViewById(R.id.time_set_view);

        Button createActivityButton = (Button) findViewById(R.id.create_button); //Creating the 'create' button
        createActivityButton.setOnClickListener(new View.OnClickListener() // Setting click listener for addTask button
        {
            public void onClick(View v)
            {
                long rowID;
                TaskListArray array = TaskListArray.getInstance(getApplicationContext());

                EditText titleField = (EditText) findViewById(R.id.enter_task_title); // Getting the task title
                Task newTask= new Task (titleField.getText().toString()); // Creating new task with that title

                EditText descriptionField = (EditText) findViewById(R.id.enter_task_description); // Getting the task description
                newTask.setDescription(descriptionField.getText().toString()); // Setting the task description

                if (checked)
                {
                    if (!dateAndTimeIsSet())
                        Toast.makeText(getApplicationContext(), "Please Set Date And Time For The Reminder", Toast.LENGTH_SHORT).show();
                    else
                    {
                        Calendar cal = Calendar.getInstance();
                        cal.set(year, month, day, hour, minute, 0); // Setting a new Calendar instance with the user reminder date

                        rowID = taskDataBase.addTask(newTask); // Adding to DataBase
                        newTask.setId(rowID);
                        array.addTask(newTask);

                        new SetReminder(getApplicationContext(), cal, newTask); // Creating a new reminder to be added to the alarm manager

                        finish();
                    }
                }
                else
                {
                    rowID = taskDataBase.addTask(newTask); // Adding to DataBase
                    newTask.setId(rowID);
                    array.addTask(newTask);

                    finish();
                }
            }
        });

        // Set Date Button
        setDate = (Button) findViewById(R.id.set_date); //Creating the 'Set Date' button
        setDate.setOnClickListener(new View.OnClickListener() // Setting click listener for setDate button
        {
            public void onClick(View v)
            {
                newDateFragment = new DatePickerFragment();
                newDateFragment.show(getFragmentManager(), "datePicker"); // Show the user dialog to pick a date
            }
        });

        // Set Time button
        setTime = (Button) findViewById(R.id.set_time); //Creating the 'Set Time' button
        setTime.setOnClickListener(new View.OnClickListener() // Setting click listener for setTime button
        {
            public void onClick(View v)
            {
                newTimeFragment = new TimePickerFragment();
                newTimeFragment.show(getFragmentManager(), "timePicker"); // Show the user dialog to pick a time
            }
        });

        final CheckBox checkBox = (CheckBox) findViewById(R.id.set_reminder); //Creating the CheckBox
        checkBox.setOnClickListener(new View.OnClickListener() // Setting click listener for the check-box for controlling the set's buttons clickability
        {
            public void onClick(View v)
            {
                checked = ((CheckBox) v).isChecked();
                switch(v.getId())
                {
                    case R.id.set_reminder:
                        if (checked) // If the CheckBox is checked allows the user to set a reminder, else don't
                        {
                            setDate.setVisibility(View.VISIBLE);
                            setTime.setVisibility(View.VISIBLE);
                            dateSeted.setVisibility(View.VISIBLE);
                            timeSeted.setVisibility(View.VISIBLE);
                        }
                        else
                        {
                            setDate.setVisibility(View.INVISIBLE);
                            setTime.setVisibility(View.INVISIBLE);
                            dateSeted.setVisibility(View.INVISIBLE);
                            timeSeted.setVisibility(View.INVISIBLE);
                        }
                        break;
                }
            }
        });
    }

    public boolean dateAndTimeIsSet() // Checking if the user set both date and time for the reminder
    {
        if (newDateFragment == null || newTimeFragment == null)
            return false;
        return true;
    }

    public void onDateSet(DatePicker view, int years, int months, int days)
    {
        this.year = years;
        this.month = months;
        this.day = days;

        dateSeted.setText(String.valueOf(day) + "/" + String.valueOf(month+1) + "/" + String.valueOf(year));
    }

    public void onTimeSet(TimePicker view, int hourOfDay, int minute)
    {
        this.hour = hourOfDay;
        this.minute = minute;

        timeSeted.setText(String.valueOf(hourOfDay) + ":" + String.valueOf(minute));
    }
}