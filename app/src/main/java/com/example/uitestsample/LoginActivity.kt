package com.example.uitestsample

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity

class LoginActivity : AppCompatActivity() {

    lateinit var handler: Handler

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        handler = Handler()

        findViewById<Button>(R.id.login_button).setOnClickListener {
            val noUserNameMessage = findViewById<TextView>(R.id.no_user_name_message)
            val editText = findViewById<EditText>(R.id.user_name_edit_text)
            val name = editText.text.toString()
            if (name.isEmpty()) {
                noUserNameMessage.visibility = View.VISIBLE
                return@setOnClickListener
            }

            noUserNameMessage.visibility = View.INVISIBLE
            val nowConnectingMessage = findViewById<LinearLayout>(R.id.now_connecting_message).apply {
                visibility = View.VISIBLE
            }
            val runnable = Runnable {
                startActivity(
                    Intent(this@LoginActivity, WelcomeActivity::class.java).apply {
                        putExtra("user_name", name)
                    }
                )
                nowConnectingMessage.visibility = View.INVISIBLE
                editText.clearFocus()
            }
            handler.postDelayed(runnable, 3000)
        }
    }
}