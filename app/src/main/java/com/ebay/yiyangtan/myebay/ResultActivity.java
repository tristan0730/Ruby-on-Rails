package com.ebay.yiyangtan.myebay;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.app.ListActivity;
import android.widget.ListView;
import android.os.Bundle;
import android.widget.ArrayAdapter;

import java.util.ArrayList;


public class ResultActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);
        showResults();
    }

    public void showResults(){
        Bundle bundle = this.getIntent().getExtras();
        TextView keyword = (TextView) findViewById(R.id.keyword);
        keyword.append("Results for '"+bundle.getString("keyword")+"'");
        ArrayList <String> itemname = new ArrayList<>();
        ArrayList <String> image = new ArrayList<>();
        ArrayList <String> priceShipping = new ArrayList<>();
        ArrayList <String> url = new ArrayList<>();
        int i;
        for (i=0;i<Integer.parseInt(bundle.getString("itemCount"));i++){
            String prshp;
            itemname.add(bundle.getString("title"+String.valueOf(i)));
            image.add(bundle.getString("image"+String.valueOf(i)));
            if(bundle.getString("shipping" + String.valueOf(i)).equals("0.0") || bundle.getString("shipping" + String.valueOf(i)).equals("FREE") ||bundle.getString("shipping"+String.valueOf(i)).equals("")){
                prshp = "Price: $"+bundle.getString("price"+String.valueOf(i))+" (FREE Shipping)";
            }
            else{
                prshp = "Price: $"+bundle.getString("price"+String.valueOf(i))+" (+ $"+bundle.getString("shipping"+String.valueOf(i))+" Shipping)";
            }
            priceShipping.add(prshp);
            url.add(bundle.getString("url"+String.valueOf(i)));
            bundle.putString("prshp"+String.valueOf(i),prshp);
        }
        /*
        String[] itemname ={
                bundle.getString("title"),
                "Camera",
                "Global",.
                "FireFox",
                "UC Browser"
        };

        String [] image = {
                bundle.getString("image"),
                bundle.getString("image"),
                bundle.getString("image"),
                bundle.getString("image"),
                bundle.getString("image")
        };
        */

        CustomList adapter = new CustomList(ResultActivity.this, itemname, image, priceShipping, url, bundle);
        ListView list = (ListView) findViewById(android.R.id.list);
        list.setAdapter(adapter);

        //this.setListAdapter(new ArrayAdapter<String>(
                //this, R.layout.mylist,
                //R.id.Itemname,itemname));




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_result, menu);
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
