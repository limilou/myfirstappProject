package il.ac.shenkar.myfirstapp;


import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;

import java.util.Calendar;

public class SetReminder
{
    public SetReminder(Context context, Calendar cal, Task task)
    {
        Intent intent = new Intent(context, StatusBar.class); // Creating new intent to active with the reminder
        intent.putExtra("taskID", task.getId()); // Adding to the indent the task id to remind
        intent.putExtra("taskTitle", task.getTitle());
        intent.putExtra("taskDescription", task.getDescription());
        PendingIntent pendingIntent = PendingIntent.getBroadcast(context, (int)task.getId(), intent, 0); // Wrapping the intent in pending intent and adding it to the alarm manager
        // with the task id, so multiply reminders is available

        AlarmManager am = (AlarmManager) context.getSystemService(Context.ALARM_SERVICE); // Getting the alarm manager
        am.set(AlarmManager.RTC_WAKEUP, cal.getTimeInMillis(), pendingIntent); // Adding the alarm manager the pending intent
    }
}