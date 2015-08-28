package org.androidtutorials.provider;

import android.app.Activity;

import android.net.Uri;
import android.os.Bundle;
import android.content.ContentValues;
import android.database.Cursor;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    public void deleteAllEmployees(View view) {
        Uri friends = EmployeeProvider.CONTENT_URI;
        int count = getContentResolver().delete(friends, null, null);
        String countNum = "" + count + " records are deleted.";
        Toast.makeText(this, countNum, Toast.LENGTH_LONG).show();
    }

    public void addEmployee(View view) {
        ContentValues values = new ContentValues();

        values.put(EmployeeProvider.FIRSTNAME, ((EditText) findViewById(R.id.firstname)).getText()
                .toString());
        values.put(EmployeeProvider.LASTNAME, ((EditText) findViewById(R.id.lastname)).getText()
                .toString());
        values.put(EmployeeProvider.AGE,
                Integer.parseInt(((EditText) findViewById(R.id.age)).getText().toString()));

        Uri uri = getContentResolver().insert(EmployeeProvider.CONTENT_URI, values);

        Toast.makeText(getBaseContext(), "Added: " + uri.toString(), Toast.LENGTH_LONG).show();
    }

    public void showAllEmployees(View view) {
        Uri friends = EmployeeProvider.CONTENT_URI;
        Cursor c = getContentResolver().query(friends, null, null, null, EmployeeProvider.LASTNAME);
        String result = "";

        if (!c.moveToFirst()) {
            Toast.makeText(this, result + " no content yet!", Toast.LENGTH_LONG).show();
        } else {
            do {
                result = result + "\n" + c.getString(c.getColumnIndex(EmployeeProvider.FIRSTNAME))
                        + " with id " + c.getString(c.getColumnIndex(EmployeeProvider.ID));
            } while (c.moveToNext());
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
        }
        c.close();
    }
}