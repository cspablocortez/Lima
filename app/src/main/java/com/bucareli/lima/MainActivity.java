package com.bucareli.lima;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.net.HttpCookie;

public class MainActivity extends AppCompatActivity {

    EditText bodyText;
    TextView wordCount;
    TextView fileTitle;
    Button shareBtn;
    float textSize;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        bodyText = (EditText) findViewById(R.id.editTextBody);
        wordCount = (TextView) findViewById(R.id.textViewWordCount);
        fileTitle = (TextView) findViewById(R.id.textViewFileTitle);
        shareBtn = (Button) findViewById(R.id.btnShare);

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, bodyText.getText());

                // send intent
                try {
                    startActivity(shareIntent);
                } catch (ActivityNotFoundException e) {
                    Toast.makeText(getApplicationContext(), R.string.shareError, Toast.LENGTH_LONG).show();
                }
            }
        });

        bodyText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {}
            @Override
            public void afterTextChanged(Editable editable) {}

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().length() > 1) {
                    wordCount.setText("Word Count: " + charSequence.toString().split("\\s++").length);
                    String title = charSequence.toString().split("\\R")[0];
                    if (title.length() > 20) {
                        title = title.substring(0, 20) + "...";
                    }
                    fileTitle.setText(title);
                } else {
                    wordCount.setText(R.string.wordcount);
                    fileTitle.setText(R.string.fileTitle);
                }
            }
        });
    }

    // Handle increase and decrease of font size with Ctrl + and Ctrl -
    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        switch (keyCode) {
            case KeyEvent.KEYCODE_EQUALS:
                if (event.isCtrlPressed()) {
                    textSize = (float) bodyText.getTextSize() / 2;
                    textSize += 0.5;
                    bodyText.setTextSize(textSize);
//                    Toast.makeText(this, "Text size: " + textSize, Toast.LENGTH_SHORT).show();
                }
                return true;
            case KeyEvent.KEYCODE_MINUS:
                if (event.isCtrlPressed()) {
                    textSize = (float) bodyText.getTextSize() / 2;
                    textSize -= 2;
                    bodyText.setTextSize(textSize);
//                    Toast.makeText(this, "Text size: " + textSize, Toast.LENGTH_SHORT).show();
                }
                return true;
            default:
                return super.onKeyUp(keyCode, event);
        }
    }
}