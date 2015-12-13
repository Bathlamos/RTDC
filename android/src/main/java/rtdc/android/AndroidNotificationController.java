/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015 Olivier Clermont, Jonathan Ermel, Mathieu Fortin-Boulay, Philippe Legault & Nicolas Ménard
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package rtdc.android;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import rtdc.android.AndroidBootstrapper;
import rtdc.android.R;
import rtdc.android.presenter.MainActivity;
import rtdc.android.presenter.fragments.FragmentType;
import rtdc.core.model.Message;

public class AndroidNotificationController {

    // We decrease this value each time there's a missed call so that each missed call as a unique notification ID
    private static int MISSED_CALL_NOTIFICATION_ID = Integer.MAX_VALUE;
    private static int TEXT_MESSAGE_NOTIFICATION_ID = 0;

    public static void addMissedCallNotification(String missedCaller){
        Context context = AndroidBootstrapper.getAppContext();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_phone_missed_white_24dp)
                        .setContentTitle("Missed call")
                        .setContentText("Missed call from " + missedCaller);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("fragment", FragmentType.MESSAGES);
        PendingIntent inCallPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(inCallPendingIntent);
        mBuilder.setVibrate(new long[]{0, 1000});
        mBuilder.setAutoCancel(true);
        ((NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE)).notify(MISSED_CALL_NOTIFICATION_ID--, mBuilder.build());
    }

    public static void addTextMessageNotification(Message message){
        String shortMessage = message.getContent().length() < 20 ? message.getContent() :
                message.getContent().substring(0, 20) + "...";
        Context context = AndroidBootstrapper.getAppContext();
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setSmallIcon(R.drawable.ic_chat_white_24dp)
                        .setContentTitle("Message from " + message.getReceiver().getFirstName() +  " " + message.getReceiver().getLastName())
                        .setContentText(shortMessage);
        Intent intent = new Intent(context, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra("fragment", FragmentType.MESSAGES);
        PendingIntent inCallPendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT);
        mBuilder.setContentIntent(inCallPendingIntent);
        mBuilder.setVibrate(new long[]{0, 1000});
        mBuilder.setPriority(Notification.PRIORITY_HIGH);
        mBuilder.setAutoCancel(true);
        ((NotificationManager) context.getSystemService(context.NOTIFICATION_SERVICE)).notify(TEXT_MESSAGE_NOTIFICATION_ID, mBuilder.build());
    }
}
