package com.anthonycr.mezzanine;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        FileReader fileReader = new Mezzanine.Generator_FileReader();

        String file = fileReader.readInTestJsonFile();


    }
}
