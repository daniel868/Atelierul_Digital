package com.example.atelierul_digital.week_5;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.atelierul_digital.R;

public class ChatTwoActivity extends AppCompatActivity {
    protected final static String MESSAGE_EXTRA = "message_extra";

    private TextView receivedTextView;
    private EditText inputField;
    private Button sendButton;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat_two);

        receivedTextView = findViewById(R.id.receivedTextView);
        inputField = findViewById(R.id.sentMessageEditTextTwo);
        sendButton = findViewById(R.id.sendButton2);

        sendButton.setOnClickListener(v->sendMessage());
        handleReceivedMessage(getIntent());
    }

    private void handleReceivedMessage(Intent intent) {
        if (intent!=null){
            String message = intent.getStringExtra(MESSAGE_EXTRA);
            if (message!=null){
                receivedTextView.setText(message);
            }
        }
    }
    private void sendMessage(){
        String message = inputField.getText().toString();
        Intent intent = new Intent();
        intent.putExtra(MESSAGE_EXTRA,message);
        setResult(Activity.RESULT_OK,intent);
        finish();
    }

}
