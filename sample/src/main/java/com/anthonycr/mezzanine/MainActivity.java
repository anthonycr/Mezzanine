package com.anthonycr.mezzanine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileReader fileReader = new MezzanineGenerator.FileReader();

        String file = fileReader.readInTestJsonFile();

        TextView textView = (TextView) findViewById(R.id.file_text_view);
        textView.setText(file);

    }
}
