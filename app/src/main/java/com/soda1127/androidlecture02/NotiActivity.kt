package com.soda1127.androidlecture02

import android.annotation.SuppressLint
import android.app.NotificationManager
import android.content.Context
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity


class NotiActivity : AppCompatActivity() {

    private lateinit var notiIdText: TextView
    private lateinit var notiInfoText: TextView

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_noti)

        notiIdText = findViewById(R.id.noti_id_text)
        notiInfoText = findViewById(R.id.noti_info_text)

        intent?.apply {
            // 전달 받은 "noti id" 출력
            val notiId = intent.getIntExtra("noti_id", 0)
            notiIdText.text = "Noti ID : $notiId"
            // 전달 받은 "noti text" 출력
            val notiInfoText = intent.getStringExtra("noti_text")
            this@NotiActivity.notiInfoText.text = "Noti INFO : $notiInfoText"

            // Notification 제거
            val notificationManager =
                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.cancel(notiId)
        }
    }
}
