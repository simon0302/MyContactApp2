package com.example.simon.mycontactapp2;


import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;




public class MainActivity extends AppCompatActivity {

    DatabaseHelper myDb;
    EditText editName;
    Button btnAddData;

    //tasks
    EditText editAddress;
    EditText editEmail;


    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        //add layout here
        editName = (EditText) findViewById(R.id.editText_name);

        //not sure if correct
        // editAddress = (EditText) findViewById(R.id.editText_name);
        // editEmail = (EditText) findViewById(R.id.editText_name);
    }

    public void addData(View v) {

        Log.d("MyContact", " addData() is used");

        //this means that only the name is required, but not the address, age, or email
        boolean isInserted = myDb.insertData(editName.getText().toString());

        if (isInserted == true) {
            Log.d("MyContact", "Success in inserting data");
            //insert success toast message here
            Toast.makeText(MainActivity.this, "Contact Added", Toast.LENGTH_SHORT).show();

        } else {
            Log.d("MyContact", "Failure inserting data");
            //insert failure toast message here
            Toast.makeText(MainActivity.this, "Failure Inserting Contact", Toast.LENGTH_SHORT).show();
        }

    }

    public void viewData(View v) {

        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "No data is found in the database");
            //put a log d message and toast message
            Log.d("MyContact", "Data not found in database");
            Toast.makeText(MainActivity.this, "Data not found in database", Toast.LENGTH_SHORT).show();
            return;
        }

        StringBuffer buffer = new StringBuffer();
        //set up  a loop with cursor (res) and using the method moveToNext()

        while (res.moveToNext()) {
            //inside loop, append each column to the buffer
            buffer.append(editName)
            buffer.append(editAddress);
            buffer.append(editEmail);
        }

        //display the message using showMessage() call
        showMessage("Data", buffer.toString());
    }

    private void showMessage(String title, String message) {
        //alertdialog.builder call
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true); //to cancel using the back button
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }
}

