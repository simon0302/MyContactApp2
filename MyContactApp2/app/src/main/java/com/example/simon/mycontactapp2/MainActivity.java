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
    String[] results;

    public static final String EXTRA_MESSAGE = "com.example.mycontactapp2.MESSAGE";

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

    }

    public void viewData(View v) {

        Intent intent = new Intent(this, DisplayMessageActivity.class);
        Button editText = (Button) findViewById(R.id.view_contacts_button);
        String message = editText.getText().toString();
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

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
            buffer.append(editName);
            buffer.append(editAddress);
            buffer.append(editEmail);
            buffer.append(editNumber);
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

    public void searchEntry(View v){
        Cursor res = myDb.getAllData();
        if (res.getCount() == 0){
            showMessage("Error", "No match found");
            return;
        }
        StringBuffer buffer = new StringBuffer();
        res.moveToFirst();

        int count = 0;

        while(res.moveToNext()) {
            if(res.getString(1).equals(editSearch.getText().toString())){

                count++;
                buffer.append("ID: " + count);

                for(int i = 1; i<=4; i++){
                    buffer.append(results[i-1]);
                    buffer.append(res.getString(i));
                    buffer.append("\n");
                }
                break;
            }
        }

        if (buffer.toString().isEmpty()){
            showMessage("Error", "Entry not found");
            return;
        }

        showMessage("Data Found", buffer.toString());
    }

}

