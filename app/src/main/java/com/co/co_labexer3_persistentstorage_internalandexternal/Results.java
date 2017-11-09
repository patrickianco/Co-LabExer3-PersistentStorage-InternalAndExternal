package com.co.co_labexer3_persistentstorage_internalandexternal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

public class Results extends AppCompatActivity {

    EditText etFilename;
    TextView displayMessage;
    SharedPreferences preferences;
    String message;
    String filename;
    FileInputStream fis;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        etFilename = (EditText) findViewById(R.id.filenameresults);
        displayMessage = (TextView) findViewById(R.id.displayMessage);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        getSupportActionBar().setTitle("Lab Exer 3 - Reading Data");
    }

    public void loadShared(View view){
        message = preferences.getString("message", "");
        displayMessage.setText(message);
    }

    public void loadInternalStorage(View view){
        filename = etFilename.getText().toString() + ".txt";
        try{
            fis = openFileInput(filename);
            StringBuffer fileContent = new StringBuffer("");
            byte[] buffer = new byte[1024];
            int n;

            while((n = fis.read(buffer)) != -1){
                fileContent.append(new String(buffer, 0, n));
            }
            displayMessage.setText(fileContent);
            fis.close();

        }catch (FileNotFoundException e){
            e.printStackTrace();
            displayMessage.setText("File not Found!");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

    public void loadInternalCache(View view){
        filename = etFilename.getText().toString() + ".txt";
        File dir = getCacheDir();
        loadData(filename, dir);
    }

    public void loadExternalCache(View view){
        filename = etFilename.getText().toString() + ".txt";
        File dir = getExternalCacheDir();
        loadData(filename, dir);
    }

    public void loadExternalStorage(View view){
        filename = etFilename.getText().toString() + ".txt";
        File dir = getExternalFilesDir("temp");
        loadData(filename, dir);
    }

    public void loadExternalPublic(View view){
        filename = etFilename.getText().toString() + ".txt";
        File dir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        loadData(filename, dir);
    }

    public void loadData(String filename, File dir){
        StringBuffer buffer = new StringBuffer();
        int read = 0;
        try{
            FileInputStream fis = new FileInputStream(new File(dir, filename));
            while((read = fis.read()) != -1){
                buffer.append((char) read);
            }
            displayMessage.setText(buffer.toString());
            fis.close();
        }catch (FileNotFoundException e){
            e.printStackTrace();
            displayMessage.setText("File not Found!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goBack(View view){
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }
}
