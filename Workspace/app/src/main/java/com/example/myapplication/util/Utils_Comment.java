package com.example.myapplication.util;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.NonNull;

import com.example.myapplication.R;

public class Utils_Comment extends Dialog implements View.OnClickListener {
    EditText editText;
    Button ensureButton;
    Button cancelButton;
    PressEnsureListener pressEnsureListener;

    public void setPressEnsureListener(PressEnsureListener pressEnsureListener) {
        this.pressEnsureListener = pressEnsureListener;
    }

    public Utils_Comment(@NonNull Context context) {
        super(context);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.comment_input);
        editText = findViewById(R.id.comment_input_edittext);
        ensureButton = findViewById(R.id.comment_input_button_ensure);
        cancelButton = findViewById(R.id.comment_input_button_cancel);
        ensureButton.setOnClickListener(this);
        cancelButton.setOnClickListener(this);
    }

    public interface PressEnsureListener{
        public void PressEnsure();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.comment_input_button_ensure:
                if (pressEnsureListener != null){
                    pressEnsureListener.PressEnsure();
                }
                break;
            case R.id.comment_input_button_cancel:
                cancel();
                break;
        }
    }

    public String getComment(){
        return editText.getText().toString().trim();
    }

//    public void changeDialogSize(){
//        Window window = getWindow();
//        WindowManager.LayoutParams windowslayoutparameters = window.getAttributes();
//        Display display = window.getWindowManager().getDefaultDisplay();
//        windowslayoutparameters.width = (int)(display.getWidth());
//        windowslayoutparameters.gravity = Gravity.BOTTOM;
//        window.setBackgroundDrawableResource(android.R.color.transparent);
//        window.setAttributes(windowslayoutparameters);
//        handler.sendEmptyMessageDelayed(1,100);
//    }

//    Handler handler = new Handler(){
//        @Override
//        public void handleMessage(@NonNull Message msg) {
//            InputMethodManager inputMethodManager = (InputMethodManager) getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
//            inputMethodManager.toggleSoftInput(0,InputMethodManager.HIDE_NOT_ALWAYS);
//        }
//    };



}
