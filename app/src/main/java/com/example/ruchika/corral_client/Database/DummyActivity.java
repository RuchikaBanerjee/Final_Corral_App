package com.example.ruchika.corral_client.Database;

import android.app.ListActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;

import com.example.corral_client.R;

import java.util.List;

public class DummyActivity extends ListActivity {

            	    private UserOperations UserDBoperation;

           	    @Override
    	    public void onCreate(Bundle savedInstanceState) {
        	        super.onCreate(savedInstanceState);
        	        setContentView(R.layout.activity_dummy);

        	        UserDBoperation = new UserOperations(this);
        	        UserDBoperation.open();

        	        List values = UserDBoperation.getAllUsers();

        	        // Use the SimpleCursorAdapter to show the
        	        // elements in a ListView
        	        ArrayAdapter adapter = new ArrayAdapter(this,
                	                android.R.layout.simple_list_item_1, values);
        	        setListAdapter(adapter);
        	    }

            	    public void addUser(View view) {

        	        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();

        	        EditText text = (EditText) findViewById(R.id.editText1);
        	        UserDetails stud = UserDBoperation.addUser(text.getText().toString(),null);

        	        adapter.add(stud);

        	    }

            	    public void deleteFirstUser(View view) {

        	        ArrayAdapter adapter = (ArrayAdapter) getListAdapter();
        	        UserDetails stud = null;

        	        if (getListAdapter().getCount() > 0) {
            	            stud = (UserDetails) getListAdapter().getItem(0);
            	            UserDBoperation.deleteStudent(stud);
            	            adapter.remove(stud);
            	        }

        	    }

            	    @Override
    	    protected void onResume() {
        	        UserDBoperation.open();
        	        super.onResume();
        	    }

            	    @Override
    	    protected void onPause() {
        	        UserDBoperation.close();
        	        super.onPause();
        	    }


            	}