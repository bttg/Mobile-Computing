package com.example.myapplication.util;

import android.inputmethodservice.Keyboard;
import android.inputmethodservice.KeyboardView;
import android.text.Editable;
import android.text.InputType;
import android.view.View;
import android.widget.EditText;

import com.example.myapplication.R;

public class Utils_KeyboardView {
    private final Keyboard keyboard1;//Custom soft keyboard
    private KeyboardView keyboardView;
    private EditText editText;

    public interface PressOKListener{
        public void PressOK();
    }
    PressOKListener pressOKListener;
    public void setPressOKListener(PressOKListener pressOKListener) {
        this.pressOKListener = pressOKListener;
    }

    public Utils_KeyboardView(KeyboardView keyboardView, EditText editText) {
        this.keyboardView = keyboardView;
        this.editText = editText;
        this.editText.setInputType(InputType.TYPE_NULL);  //Cancel the system pop-up built-in keyboard
        keyboard1 = new Keyboard(this.editText.getContext(), R.xml.keyborad_key);

        this.keyboardView.setKeyboard(keyboard1);//Set the style of the keyboard
        this.keyboardView.setEnabled(true);//Set the soft keyboard to be used
        this.keyboardView.setPreviewEnabled(false);
        this.keyboardView.setOnKeyboardActionListener(listener);//Set up monitoring for keyboard buttons being clicked
    }

    KeyboardView.OnKeyboardActionListener listener = new KeyboardView.OnKeyboardActionListener() {
        @Override
        public void onPress(int i) {
        }
        @Override
        public void onRelease(int i) {
        }
        @Override
        public void onKey(int keyboard_primarycode, int[] ints) {
            Editable editable = editText.getText();
            int startpart = editText.getSelectionStart();
            if (keyboard_primarycode == -5){
                if (editable != null && editable.length()>0 ){
                    if (startpart>0){
                        editable.delete(startpart-1,startpart);
                    }
                }
            }else if (keyboard_primarycode == -3){
                editable.clear();
            }else if (keyboard_primarycode == -4){
                pressOKListener.PressOK();
            }else{
                editable.insert(startpart,Character.toString((char)keyboard_primarycode));
            }
        }
        @Override
        public void onText(CharSequence charSequence) {
        }
        @Override
        public void swipeLeft() {
        }
        @Override
        public void swipeRight() {
        }
        @Override
        public void swipeDown() {
        }
        @Override
        public void swipeUp() {
        }
    };

//    How to display the keyboard
    public void make_keyboard_visible(){
        int condition = keyboardView.getVisibility();
        if (condition == View.INVISIBLE || condition == View.GONE){
            keyboardView.setVisibility(View.VISIBLE);
        }
    }

    public void make_keyboard_invisible(){
        int condition  = keyboardView.getVisibility();
        if (condition == View.VISIBLE || condition == View.INVISIBLE){
            keyboardView.setVisibility(View.GONE);
        }
    }


}
