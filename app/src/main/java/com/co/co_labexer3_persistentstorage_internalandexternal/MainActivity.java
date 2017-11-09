package com.co.co_labexer3_persistentstorage_internalandexternal;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    EditText etData;
    EditText etFilename;
    SharedPreferences preferences;
    FileOutputStream fos = null;
    String filename;
    String message;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        etData = (EditText) findViewById(R.id.data);
        etFilename = (EditText) findViewById(R.id.filename);
        preferences = PreferenceManager.getDefaultSharedPreferences(this);
        getSupportActionBar().setTitle("Lab Exer 3 - Writing Data");
    }

    public void saveShared(View view){
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("message", etData.getText().toString());
        editor.commit();
        Toast.makeText(this, "Saved in Shared Preferences!", Toast.LENGTH_LONG).show();
    }

    public void saveInternalStorage(View view){
            filename = etFilename.getText().toString() + ".txt";
            message = etData.getText().toString();
            try{
                fos = openFileOutput(filename, Context.MODE_PRIVATE);
                fos.write(message.getBytes());
            }catch (FileNotFoundException e){
                e.printStackTrace();
            }catch (IOException e){
                e.printStackTrace();
            }finally {
                try{
                    fos.close();
                }catch (IOException e){
                    e.printStackTrace();
                }
            }
            Toast.makeText(this, "Saved in Internal Storage!", Toast.LENGTH_LONG).show();
        }


    public void saveInternalCache(View view){
        filename = etFilename.getText().toString() + ".txt";
        message = etData.getText().toString();
        File folder = getCacheDir();
        File file = new File(folder, filename);
        writeData(file, message);
        Toast.makeText(this, "Saved in Internal Cache!", Toast.LENGTH_LONG).show();
    }

    public void saveExternalCache(View view){
        filename = etFilename.getText().toString() + ".txt";
        message = etData.getText().toString();
        File folder = getExternalCacheDir();
        File file = new File(folder, filename);
        writeData(file, message);
        Toast.makeText(this, "Saved in External Cache!", Toast.LENGTH_LONG).show();
    }

    public void saveExternalStorage(View view){
        filename = etFilename.getText().toString() + ".txt";
        message = etData.getText().toString();
        File folder = getExternalFilesDir("temp");
        File file = new File(folder, filename);
        writeData(file, message);
        Toast.makeText(this, "Saved in External Storage!", Toast.LENGTH_LONG).show();
    }

    public void saveExternalPublic(View view){
        filename = etFilename.getText().toString() + ".txt";
        message = etData.getText().toString();
        File folder = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
        File file = new File(folder, filename);
        writeData(file, message);
        Toast.makeText(this, "Saved in External Public Storage!", Toast.LENGTH_LONG).show();
    }

    public void nextActivity(View view){
        Intent intent = new Intent(this, Results.class);
        startActivity(intent);
    }

    public void writeData(File file, String message){
        try{
            fos = new FileOutputStream(file);
            fos.write(message.getBytes());

        }catch(FileNotFoundException e){
            e.printStackTrace();
        }catch (IOException e){
            e.printStackTrace();
        }finally {
            try {
                fos.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
