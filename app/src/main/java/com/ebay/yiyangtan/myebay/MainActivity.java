package com.ebay.yiyangtan.myebay;

import android.content.Intent;
import android.graphics.Color;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.os.Environment;
import android.util.Log;
import android.app.Activity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.Window;
import android.util.Log;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.view.View;
import android.widget.Spinner;
//import android.widget.TextView;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.EditText;
import java.io.Serializable;
import android.os.AsyncTask;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpGet;
//import org.apache.http.entity.mime.HttpMultipartMode;
//import org.apache.http.entity.mime.MultipartEntity;
//import org.apache.http.entity.mime.content.FileBody;
import org.apache.http.client.utils.URLEncodedUtils;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONObject;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

    public class MainActivity extends ActionBarActivity implements AdapterView.OnItemSelectedListener{
    private static final String TAG = "buckysMessage";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);




        //This is called by Fragment




        setContentView(R.layout.activity_main);
        //Log.i(TAG,"onCreate");


        //Spinner
        Spinner spinner = (Spinner) findViewById(R.id.sortBySpinner);
        ArrayAdapter adapter = ArrayAdapter.createFromResource(this, R.array.sort_by_array, android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);


        Button clearButton = (Button)findViewById(R.id.clearButton);
        clearButton.setOnClickListener(new View.OnClickListener(){
                             @Override
                             /*
                               public void onClick(View view){
                               keyword = (EditText) findViewById(R.id.keywordText);
                               keyword.setText(" ");
                               priceTo = (EditText)findViewById(R.id.priceToText);
                               priceTo.setText("");
                               priceFrom = (EditText)findViewById(R.id.priceFromText);
                               priceFrom.setText("");
                               Spinner spinner = (Spinner) findViewById(R.id.sortBySpinner);
                               spinner.setSelection(0);}
                                */

                                public void onClick(View view){
                                Intent intent = getIntent();
                                finish();
                                startActivity(intent);
                             }




                                        }
        );
        /*clearButton.setOnClickListener(
                new Button.OnClickListener(){
                    public void onClick(View v){
                        TextView keyword = (TextView)findViewById(R.id.keyword);
                        keyword.setText("KEYWORD");

                    }
                }
        );

        clearButton.setOnLongClickListener(
                new Button.OnLongClickListener(){
                    public boolean onLongClick(View v){
                        TextView keyword = (TextView)findViewById(R.id.keyword);
                        keyword.setText("K E Y W O R D");
                        return true;
                    }
                }
        );*/

        //Register views for Form Validation
        registerViews();


    }

    private void registerViews() {
        //keyword
        keyword = (EditText) findViewById(R.id.keywordText);

        keyword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                    Validation.hasText(keyword);
            }

        });


        //priceTo
        priceTo = (EditText)findViewById(R.id.priceToText);

        /*
        priceTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.isValidPrice(priceTo,false);
            }
        });
        */
        //priceFrom
        //priceTo
        priceFrom = (EditText)findViewById(R.id.priceFromText);
        /*
        priceFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                Validation.isValidPrice(priceFrom,false);
            }
        });
        */

        priceTo.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(priceFrom.getText().toString().trim()!=""&&priceTo.getText().toString().trim()!="") {
                    Validation.isValidPriceRange(priceFrom, priceTo, "Maximum price cannot be below minimum price");
                }
            }
        });

        priceFrom.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(priceFrom.getText().toString().trim()!=""&&priceTo.getText().toString().trim()!="") {
                    Validation.isValidPriceRange(priceFrom, priceTo, "Maximum price cannot be below minimum price");
                }
            }
        });

        searchButton = (Button) findViewById(R.id.searchButton);
        searchButton.setOnClickListener(new View.OnClickListener(){
                      @Override
                      public void onClick(View view){
                          if(checkValidation()) {
                              new PostDataAsyncTask().execute();
                              Log.i(TAG, "postData after validated!");

                          }
                          else
                              Toast.makeText(MainActivity.this, "Please input valid value", Toast.LENGTH_LONG).show();

                      }


         }
        );

        spinner = (Spinner) findViewById(R.id.sortBySpinner);
    }

    //private void submitForm(){
        //Submit your form here.
        //Toast.makeText(MainActivity.this, "Submitting form", Toast.LENGTH_LONG).show();
        //Toast.makeText(MainActivity.this, spinner.getSelectedItem().toString() + priceTo.getText().toString(), Toast.LENGTH_LONG).show();
    //}

    private boolean checkValidation(){
        boolean ret = true;

        if(!Validation.hasText(keyword)) ret=false;
        if(!Validation.isValidPrice(priceTo, false)) ret=false;
        if(!Validation.isValidPrice(priceFrom, false)) ret=false;
        if(!Validation.isValidPriceRange(priceFrom, priceTo,"Maximum price cannot be below minimum price")) ret=false;

        return ret;

    }

    private EditText keyword;
    private EditText priceTo;
    private EditText priceFrom;
    private Button searchButton;
    private Spinner spinner;
    private String responseText;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        //Log.i(TAG, "onSaveInstanceState");
    }


    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        //Log.i(TAG, "onRestoreInstanceState");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {

    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public class PostDataAsyncTask extends AsyncTask<String, String, String> {

        //Context context;

        //private PostDataAsyncTask(Context context){
            //this.context = context.getApplicationContext();
        //}

        protected void onPreExecute() {
            super.onPreExecute();
            // do stuff before posting data
        }

        @Override
        protected String doInBackground(String... strings) {
            try {
                postText();
                Log.i(TAG, "call postText in PostDataAsyncTask Class");


            } catch (NullPointerException e) {
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(String string) {
            // do stuff after posting data
            try{
            JSONObject json = new JSONObject(responseText);
            String ack = json.getString("ack");
            Log.i(TAG,"JSON parse here and ack: "+ ack);
            if(ack.equals("Success")) {
                Intent intent = new Intent(MainActivity.this, ResultActivity.class);
                Bundle b = new Bundle();

                b.putString("keyword",keyword.getText().toString());
                b.putString("ack", json.getString("ack"));
                b.putString("resultCount", json.getString("resultCount"));
                b.putString("pageNumber", json.getString("pageNumber"));
                b.putString("itemCount",json.getString("itemCount"));

                //item0
                int i;
                for (i=0;i<Integer.parseInt(b.getString("itemCount"));i++) {
                    b.putString("title"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("title"));
                    b.putString("url"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("viewItemURL"));
                    b.putString("image"+String.valueOf(i), (json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("galleryURL") !=
                        "") ? json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("galleryURL") : "N.A.");

                    //b.putString("image"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("galleryURL"));


                    b.putString("bigImage"+String.valueOf(i), (json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("pictureURLSuperSize") !=
                            "") ? json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("pictureURLSuperSize") :
                            b.getString("image"+String.valueOf(i)));
                    b.putString("price"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("convertedCurrentPrice"));
                    b.putString("shipping"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("shippingServiceCost"));
                    b.putString("condition"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("conditionDisplayName"));
                    b.putString("listingType"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("listingType"));
                    b.putString("location"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("location"));
                    b.putString("categoryName"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("categoryName"));
                    b.putString("topRated"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("basicInfo").getString("topRatedListing"));


                    b.putString("seller"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("sellerInfo").getString("sellerUserName"));
                    b.putString("feedback"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("sellerInfo").getString("feedbackScore"));
                    b.putString("positiveFeedback"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("sellerInfo").getString("positiveFeedbackPercent"));
                    b.putString("feedbackRating"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("sellerInfo").getString("feedbackRatingStar"));
                    b.putString("topRatedSeller"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("sellerInfo").getString("topRatedSeller"));
                    b.putString("storeName"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("sellerInfo").getString("sellerStoreName"));
                    b.putString("storeURL"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("sellerInfo").getString("sellerStoreURL"));

                    b.putString("shippingType"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("shippingInfo").getString("shippingType"));
                    b.putString("shipToLocations"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("shippingInfo").getString("shipToLocations"));
                    b.putString("expeditedShipping"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("shippingInfo").getString("expeditedShipping"));
                    b.putString("oneDayShippingAvailable"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("shippingInfo").getString("oneDayShippingAvailable"));
                    b.putString("returnAccepted"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("shippingInfo").getString("returnsAccepted"));
                    b.putString("handlingTime"+String.valueOf(i), json.getJSONObject("item"+String.valueOf(i)).getJSONObject("shippingInfo").getString("handlingTime"));

                }






                intent.putExtras(b);
                //int i;
                //for(i = 0 ;i < Integer.parseInt(json.getString("itemCount")); i++){


                //}

                startActivity(intent);
                TextView noResults = (TextView) findViewById(R.id.noResults);
                noResults.setText("");

            }
            else {
                //Toast.makeText(MainActivity.this, "No results found", Toast.LENGTH_LONG).show();
                TextView noResults = (TextView) findViewById(R.id.noResults);
                noResults.setText("No results found");
            }

            } catch (NullPointerException e) {
                //Log.i(TAG,"JSON NOT parse here");
                e.printStackTrace();
            } catch (Exception e) {
                //Log.i(TAG,"JSON NOT parse here");
                e.printStackTrace();
            }

        }
    }
    //private static final String TAG = "MainActivity.java";
    // this will post our text data
    private void postText(){
        try{
            // url where the data will be posted
            String postReceiverUrl = "http://tan-env.elasticbeanstalk.com/server.php";
            Log.i(TAG, "postURL: " + postReceiverUrl);


            // add your data
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>();
            nameValuePairs.add(new BasicNameValuePair("keywords", keyword.getText().toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("rangelow", priceFrom.getText().toString().trim()));
            nameValuePairs.add(new BasicNameValuePair("rangehigh", priceTo.getText().toString().trim()));
            if(spinner.getSelectedItem().toString() == "Best Match"){
                nameValuePairs.add(new BasicNameValuePair("sortby", "bestmatch"));
            }
            else if(spinner.getSelectedItem().toString()=="Price: highest first"){
                nameValuePairs.add(new BasicNameValuePair("sortby", "pricehighestfirst"));
            }
            else if(spinner.getSelectedItem().toString()=="Price + Shipping: highest first"){
                nameValuePairs.add(new BasicNameValuePair("sortby", "priceshippinghighestfirst"));
            }
            else if(spinner.getSelectedItem().toString()=="Price + Shipping: lowest first"){
                nameValuePairs.add(new BasicNameValuePair("sortby", "priceshippinglowestfirst"));
            }
            nameValuePairs.add(new BasicNameValuePair("resultsperpage", "5"));
            nameValuePairs.add(new BasicNameValuePair("page", "1"));

            // HttpClient
            HttpClient httpClient = new DefaultHttpClient();

            // post header
            HttpGet httpGet = new HttpGet(postReceiverUrl+"?"+ URLEncodedUtils.format(nameValuePairs, "utf-8"));


            // execute HTTP post request
            HttpResponse response = httpClient.execute(httpGet);

            responseText =  EntityUtils.toString(response.getEntity());
            /*
            HttpEntity resEntity = response.getEntity();

            if (resEntity != null) {

                String responseStr = EntityUtils.toString(resEntity).trim();
                Log.i(TAG, "Response: " +  responseStr);

                // you can add an if statement here and do other actions based on the response
            }*/

        } catch (ClientProtocolException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
