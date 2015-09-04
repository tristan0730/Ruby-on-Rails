package com.ebay.yiyangtan.myebay;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.webkit.WebView;
import android.widget.TextView;
import android.content.Intent;
import android.net.Uri;
import android.app.Activity;
import android.content.Context;
import android.support.v7.app.ActionBarActivity;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.HttpClient;
import org.apache.http.impl.client.DefaultHttpClient;

import java.lang.reflect.Array;
import java.net.URL;
import org.apache.http.HttpResponse;
import org.apache.http.entity.BufferedHttpEntity;
import java.io.InputStream;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;



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
/**
 * Created by yiyangtan on 4/27/15.
 */
public class CustomList extends ArrayAdapter<String> {
    private static final String TAG = "buckysMessage";

    private final Activity context;
    //private final String[] itemname;
    //private final String [] image;
    private final ArrayList<String> itemname;
    private final ArrayList<String> image;
    private final ArrayList<String> priceShipping;
    private final ArrayList<String> url;
    private final Bundle bundle;
    public CustomList(Activity context,
                      ArrayList<String> itemname, ArrayList<String> image, ArrayList<String> priceShipping, ArrayList<String> url, Bundle bundle) {
        super(context, R.layout.mylist, itemname);
        this.context = context;
        this.itemname = itemname;
        this.image = image;
        this.priceShipping = priceShipping;
        this.url = url;
        this.bundle = bundle;

    }
    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        LayoutInflater inflater = context.getLayoutInflater();
        View rowView= inflater.inflate(R.layout.mylist, null, true);
        TextView txtTitle = (TextView) rowView.findViewById(R.id.Itemname);

        //ImageView img = (ImageView) rowView.findViewById(R.id.Image);
        //txtTitle.setText(itemname[position]);
        txtTitle.setText(itemname.get(position));
        txtTitle.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent(context,DetailsActivity.class);
                Bundle bundle1 = bundle;
                bundle1.putString("position",String.valueOf(position));
                intent.putExtras(bundle1);
                context.startActivity(intent);
            }

        });
        TextView priceShippingText = (TextView) rowView.findViewById(R.id.PriceShipping);
        priceShippingText.setText(priceShipping.get(position));
        Log.i(TAG, "Customize GetView!");
        ImageView img=(ImageView) rowView.findViewById(R.id.Image);
        //new DownloadImageTask(img).execute(image.get(position));
        new DownloadImageTask(img).execute(image.get(position));
        //"http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
        img.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent= new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url.get(position)));
                context.startActivity(intent);
            }

        });

        /*
        try{
        URL newurl = new URL("http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg");
        Bitmap mIcon_val = BitmapFactory.decodeStream(newurl.openConnection().getInputStream());
        img.setImageBitmap(mIcon_val);
        Log.i(TAG, "Picture downloaded!");
        } catch (Exception ex) {

        }
        */

        //img.loadUrl("http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg");

        //imageView.setImageResource(image[position]);
        /*
        try {
            URL url = new URL("http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg");
            //URL url = new URL("http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg");
            //try this url = "http://0.tqn.com/d/webclipart/1/0/5/l/4/floral-icon-5.jpg"
            HttpGet httpRequest;

            httpRequest = new HttpGet(url.toURI());

            HttpClient httpclient = new DefaultHttpClient();
            HttpResponse response = httpclient.execute(httpRequest);

            HttpEntity entity = response.getEntity();
            BufferedHttpEntity b_entity = new BufferedHttpEntity(entity);
            InputStream input = b_entity.getContent();

            Bitmap bitmap = BitmapFactory.decodeStream(input);

            img.setImageBitmap(bitmap);
            Log.i(TAG, "Picture downloaded!");


        } catch (Exception ex) {

        }
        */


        return rowView;
    }
}
