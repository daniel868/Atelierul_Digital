package com.example.atelierul_digital.week_5;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atelierul_digital.R;

public class ChatOneActivity extends AppCompatActivity {

    private EditText inputFiled;
    private Button buttonSend;
    private TextView receivedTextView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_one);

        inputFiled = findViewById(R.id.sentMessageEditTextOne);
        buttonSend = findViewById(R.id.sendButton1);
        receivedTextView = findViewById(R.id.receivedTextView2);

        buttonSend.setOnClickListener(view -> sendMessage());
    }

    private void sendMessage() {
        String message = inputFiled.getText().toString();
        Intent intent = new Intent(this, ChatTwoActivity.class);
        intent.putExtra(ChatTwoActivity.MESSAGE_EXTRA, message);

        startActivityForResult(intent, 101);
        inputFiled.setText("");
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 101 && resultCode == RESULT_OK && data != null) {
            String receivedMessage = data.getStringExtra(ChatTwoActivity.MESSAGE_EXTRA);
            if (receivedMessage != null) {
                receivedTextView.append("\n");
                receivedTextView.append(receivedMessage);
            }
        }
    }
}
