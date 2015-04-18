package com.example.ruchika.corral_client.Additional_Features;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.example.corral_client.R;
import com.example.ruchika.corral_client.Database.DiscussionObject;
import com.example.ruchika.corral_client.Database.DiscussionOperations;

import java.util.List;

public class DiscussionZone extends ActionBarActivity {

    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_discussion_zone);
        listView = (ListView) findViewById(R.id.lstDiscusssions);

        updateUI();


    }

    public List listFromDB(){

        DiscussionOperations discDBoperation;
        discDBoperation = new DiscussionOperations(getApplicationContext());
        discDBoperation.open();
        List<DiscussionObject> list = discDBoperation.getAllDiscussions();

        return list;

    }


    private void updateUI() {

        List<DiscussionObject> list = listFromDB();
       DiscussionObject [] objs= new DiscussionObject[list.size()];
       for(int i=0;i<list.size();i++ ){

            objs[i] = list.get(i);

        }
        ArrayAdapter<DiscussionObject> adapter = new ArrayAdapter<DiscussionObject>(this, android.R.layout.simple_list_item_1, objs);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                Intent intent = new Intent(getApplicationContext(),DiscussionResponse.class);
                DiscussionObject obj = (DiscussionObject) parent.getItemAtPosition(position);
                obj.getTimername();
                intent.putExtra("tname",obj.getTimername());
                intent.putExtra("val",obj.toString());
                startActivity(intent);
                //System.out.println(obj.toString());

            }
        });

    }
    /*@Override
    protected void onListItemClick(ListView l, View v, int position, long id) {


        super.onListItemClick(l, v, position, id);
// ListView Clicked item index
        int itemPosition = position;
// ListView Clicked item value
        String itemValue = (String) l.getItemAtPosition(position);
        content.setText("User selected: " +itemValue);
        Intent i = new Intent(getApplicationContext(),
                ChatActivity.class);
        i.putExtra("TOUSER",itemValue);
        startActivity(i);
        finish();
    }*/

    @Override
    protected void onResume() {

          updateUI();
        super.onResume();


    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_discussion_zone, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
