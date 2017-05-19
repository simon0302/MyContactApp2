package com.example.simon.mycontactapp2;

import android.content.Intent;
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
    EditText editNumber;

    EditText editSearch;
    String[] fields;

    //public static final String EXTRA_MESSAGE = "com.example.mycontactapp2.MESSAGE";

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myDb = new DatabaseHelper(this);

        //add layout here
        editName = (EditText) findViewById(R.id.editText_name);

        //tasks
        editAddress = (EditText) findViewById(R.id.editText_address);
        editEmail = (EditText) findViewById(R.id.editText_email);
        editNumber = (EditText) findViewById(R.id.editText_number);
        editSearch = (EditText) findViewById(R.id.editText_search);

        fields = new String[] {"Name: ", "Address: ", "Email: ", "Number: "};
    }

    public void addData(View v) {

        Log.d("MyContact", " addData() is adding contact");

        //this means that only the name is required, but not the address, age, or email
        boolean isInserted = myDb.insertData(editName.getText().toString(), editAddress.getText().toString(), editEmail.getText().toString(), editNumber.getText().toString());

        if (isInserted == true) {
            Log.d("MyContact", "Success in inserting data");
            //insert success toast message here
            Toast.makeText(MainActivity.this, "Contact Added", Toast.LENGTH_SHORT).show();

        } else {
            Log.d("MyContact", "Failure inserting data");
            //insert failure toast message here
            Toast.makeText(MainActivity.this, "Failure Inserting Contact", Toast.LENGTH_SHORT).show();
        }

        editName.setText("");
        editAddress.setText("");;
        editEmail.setText("");
        editNumber.setText("");
    }

    public void viewData(View v) {

        Log.d("My Contact", "viewData is used");
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0) {
            showMessage("Error", "Data not found");
        }

        StringBuffer buffer = new StringBuffer();
        res.moveToFirst();

        for (int i = 0; i < res.getCount(); i++) {
            for (int j = 1; j <=4; j++) {
                buffer.append(fields[j-1]);
                buffer.append(res.getString(j));
                buffer.append("\n");
            }
            buffer.append("\n");
            res.moveToNext();
        }
        //display the message using showMessage() call
        showMessage("Contacts", buffer.toString());
    }


    private void showMessage(String title, String message) {
        //alertdialog.builder call
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setCancelable(true); //to cancel using the back button
        builder.setTitle(title);
        builder.setMessage(message);
        builder.show();
    }

    public void searchEntry(View v) {
        Log.d("My Contact app", "Search method used");

        String search = editSearch.getText().toString();
        StringBuffer buffer = new StringBuffer();
        Cursor res = myDb.getAllData();
        res.moveToFirst();

        for (int i = 0; i < res.getCount(); i++) {
            if (res.getString(1).equals(search)) {
                for (int j = 1; j <=4; j++) {
                    buffer.append(fields[j-1]);
                    buffer.append(res.getString(j));
                    buffer.append("\n");
                }
            }
            buffer.append("\n");
            res.moveToNext();
        }

        if (buffer.toString().equals("")) {
            showMessage("No match found", "");
        } else {
            showMessage(" Search Results", buffer.toString());
        }
        editSearch.setText("");
    }
}

