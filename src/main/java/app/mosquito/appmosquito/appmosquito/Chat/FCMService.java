package app.mosquito.appmosquito.appmosquito.Chat;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;

/**
 * Agosto, 02 2019
 *
 * @author suporte@moonjava.com.br (Tiago Aguiar).
 */
public class FCMService extends FirebaseMessagingService {

  @Override
  public void onMessageReceived(RemoteMessage remoteMessage) {
    final Map<String, String> data = remoteMessage.getData();

    if (data == null || data.get("sender") == null) return;

    final Intent ii = new Intent(this, ChatActivity.class);

    FirebaseFirestore.getInstance().collection("/users")
            .document(data.get("sender"))
            .get()
            .addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
              @Override
              public void onSuccess(DocumentSnapshot documentSnapshot) {
                User sender = documentSnapshot.toObject(User.class);

                ii.putExtra("user", sender);

                PendingIntent pIntent = PendingIntent.getActivity(
                        getApplicationContext(), 0, ii, 0);

                NotificationManager notificationManager = (NotificationManager)
                        getSystemService(Context.NOTIFICATION_SERVICE);

                String notificationChannelId = "my_channel_id_01";

                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                  NotificationChannel notificationChannel =
                          new NotificationChannel(notificationChannelId, "My Notifications",
                                  NotificationManager.IMPORTANCE_DEFAULT);

                  notificationChannel.setDescription("Channel description");
                  notificationChannel.enableLights(true);
                  notificationChannel.setLightColor(Color.RED);

                  notificationManager.createNotificationChannel(notificationChannel);
                }

                NotificationCompat.Builder builder =
                        new NotificationCompat.Builder(getApplicationContext(), notificationChannelId);

                builder.setAutoCancel(true)
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setContentTitle(data.get("title"))
                        .setContentText(data.get("body"))
                        .setContentIntent(pIntent);

                notificationManager.notify(1, builder.build());
              }
            });
  }
}


















