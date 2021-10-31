package com.example.myapplication;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class EditProfileActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_eidt_profile);
    }

    public void btnSaveProfile(View v)
    {
        //when click "save" button, upload edited information to the server
        EditText etNewName = findViewById(R.id.edit_nickname);
        EditText etNewPass = findViewById(R.id.edit_password);
        EditText etNewEmail = findViewById(R.id.edit_email);

        String newName = etNewName.getText().toString();
        String newPass = etNewPass.getText().toString();
        String newEmail = etNewEmail.getText().toString();

        Log.v("new nickname",newName);
        Log.v("new password",newPass);
        Log.v("new email",newEmail);
    }
}