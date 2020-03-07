package com.soda1127.androidlecture02

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {

    private lateinit var notificationButton: Button
    private lateinit var dialButton: Button
    private lateinit var browserButton: Button
    private lateinit var sendButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        notificationButton = findViewById(R.id.notification_button)
        dialButton = findViewById(R.id.dial_button)
        browserButton = findViewById(R.id.browser_button)
        sendButton = findViewById(R.id.send_button)

        notificationButton.setOnClickListener {
            generateNotification()
        }

        // 클릭 시 다이얼 입력하는 화면을 암시적 인텐트로 띄운 이후
        // URI를 파싱하여 입력창에 전화번호 넣기
        dialButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_DIAL, Uri.parse("tel:010-1234-5678"))
            startActivity(intent)
        }


        // 클릭 시 브라우징을 하는 화면을 암시적 인텐트로 띄운 이후
        // URI를 파싱하여 프로토콜에 맞는 주소 검색하기
        browserButton.setOnClickListener {
            val intent = Intent(Intent.ACTION_VIEW, Uri.parse("https://www.google.com"))
            startActivity(intent)
        }

        // 클릭 시 텍스트를 보낼 화면을 암시적 인텐트로 띄운 이후
        // 데이터 타입에 맞게 Extra Data를 보내기
        sendButton.setOnClickListener {
            val sendIntent = Intent()
            sendIntent.action = Intent.ACTION_SEND
            sendIntent.putExtra(Intent.EXTRA_TEXT, "암시적 인텐트로 데이터와 함께 넣어줘봤다")
            sendIntent.type = "text/plain"
            startActivity(sendIntent)
        }
    }

    private fun generateNotification() {
        val notiId = 101
        val strChannelId = "channel_id"

        val notificationIntent = Intent(this, NotiActivity::class.java)
        notificationIntent.putExtra("noti_id", notiId)
        notificationIntent.putExtra("noti_text", "PendingIntent test")

        // Pending Intent 생성
        val contentIntent = PendingIntent.getActivity(
            this,
            0,
            notificationIntent,
            PendingIntent.FLAG_UPDATE_CURRENT
        )

        // Notification 생성
        val builder = NotificationCompat.Builder(this, strChannelId)
        builder.setContentTitle(getString(R.string.app_name))
            .setContentText("Noti Acitivity로 이동")
            .setSmallIcon(R.drawable.ic_launcher_background)
            .setLargeIcon(BitmapFactory.decodeResource(resources, R.drawable.ic_launcher_background))
            .setContentIntent(contentIntent)
            .setAutoCancel(true)
            .setWhen(System.currentTimeMillis())
            .setDefaults(Notification.DEFAULT_ALL)

        // Android O (API 26) 이상 부터는 channel id 등록 필요
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val strTitle = getString(R.string.app_name)
            var channel =
                notificationManager.getNotificationChannel(strChannelId)
            if (channel == null) {
                channel = NotificationChannel(strChannelId, strTitle, NotificationManager.IMPORTANCE_HIGH)
                notificationManager.createNotificationChannel(channel)
            }
            builder.setChannelId(strChannelId)
        }

        notificationManager.notify(notiId, builder.build())
    }
}
