package il.ac.shenkar.myfirstapp;

import android.annotation.TargetApi;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;

public class StatusBar extends BroadcastReceiver
{
    private NotificationManager nm;

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void onReceive(Context context, Intent intent)
    {

        Bitmap icon = BitmapFactory.decodeResource(context.getResources(),R.drawable.notification_large_icon);
        TaskListDataBase taskDataBase = TaskListDataBase.getInstance(context); // Getting an instance of the application SQLite database
        long id = 0;

        id = intent.getLongExtra("taskID", -1); // Getting the task id that supposed to be reminded
        String name= intent.getStringExtra("taskTitle");
        String description= intent.getStringExtra("taskDescription");


//        Task task = (Task) taskDataBase.getTask((int) id); // Getting this task from the database

        Intent newIntent = new Intent(context,MainActivity.class); // Creating an Intent to start the ToDoApplication
        PendingIntent pi = PendingIntent.getActivity(context, (int)id, newIntent, 0); // Wrapping the intent with PendingIntent to set in the notification

        nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE); // Getting the Notification Manager
        Notification.Builder builder = new Notification.Builder(context);

        builder.setContentTitle(name)
                .setContentText(description)
                .setDefaults(Notification.DEFAULT_ALL)
                .setContentIntent(pi)
                .setSmallIcon(R.drawable.notification_small_icon)
                .setLargeIcon(icon);

        Notification newNotification = builder.build(); // Building the new notification with the task details
        newNotification.flags |= Notification.FLAG_AUTO_CANCEL; // Dismiss the notification after been selected
        nm.notify((int)id, newNotification); // Adding the new notification to the Notification Manager with the task id
    }
}