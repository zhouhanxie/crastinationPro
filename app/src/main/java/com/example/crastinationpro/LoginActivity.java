package com.example.crastinationpro;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    private SqliteDTO sqliteDTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        TextView welcomeText = findViewById(R.id.login_welcome_text);
        TextView userNameText = findViewById(R.id.login_username_text);
        TextView guestText = findViewById(R.id.login_guest_text);
        //last one is for logging in as guest, use default event table

        welcomeText.setClickable(true);
        welcomeText.setVisibility(View.INVISIBLE);
        userNameText.setClickable(true);
        guestText.setClickable(true);




        welcomeText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast t = Toast.makeText(getApplicationContext(),"no u",Toast.LENGTH_SHORT);
                t.show();
            }
        });

        userNameText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //TODO: user SQliteDTO to create user table, might need additional method in DTO
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });

        guestText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);

            }
        });
    }
}
