package org.coursera.symptomsmanagement.tools;

import org.coursera.androidcapstone.symptomsmanagement.R;
import org.coursera.symptomsmanagement.database.schema.Patient;
import org.coursera.symptomsmanagement.ui.patient.CheckInActivity;
import org.coursera.symptomsmanagement.ui.patient.SettingsActivity;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;

public class AlarmNotificationReceiver extends BroadcastReceiver {
	
	protected final CharSequence tickerText = "It's time for a new check-in.";
	protected final CharSequence contentTitle = "Please insert a new check-in";
	
	protected Intent mNotificationIntent;
	protected PendingIntent mContentIntent;
	protected Uri soundURI = Uri.parse("android.resource://org.coursera.symptomsmanagement.tools/"
			+ R.raw.burglar_alarm);
	protected long[] mVibratePattern = { 0, 200, 200, 300 };

	@Override
	public void onReceive(Context context, Intent intent) {
		mNotificationIntent = new Intent(context, CheckInActivity.class);
		mNotificationIntent.putExtra(Patient.ID, intent.getExtras().getInt(Patient.ID));
		mContentIntent = PendingIntent.getActivity(context, 0,
				mNotificationIntent, Intent.FLAG_ACTIVITY_NEW_TASK);
		
		Notification.Builder notificationBuilder = new Notification.Builder(
				context).setTicker(tickerText)
				.setSmallIcon(R.drawable.ic_stat_name)
//				.setSmallIcon(android.R.drawable.stat_sys_warning)
				.setAutoCancel(true).setContentTitle(contentTitle)
				.setContentIntent(mContentIntent)
				.setSound(RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION))
				.setVibrate(mVibratePattern);
		
		NotificationManager mNotificationManager = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		mNotificationManager.notify(1, notificationBuilder.build());
		

	}

}
