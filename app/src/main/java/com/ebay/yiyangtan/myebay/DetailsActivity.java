package com.ebay.yiyangtan.myebay;

import android.app.AlertDialog;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;

import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareButton;
import com.facebook.share.widget.ShareDialog;
import com.facebook.*;
import com.facebook.appevents.AppEventsLogger;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.ProfilePictureView;
import com.facebook.share.ShareApi;
import com.facebook.share.Sharer;
import com.facebook.share.internal.ShareInternalUtility;
import com.facebook.share.model.SharePhoto;
import com.facebook.share.model.SharePhotoContent;
import com.facebook.share.model.ShareLinkContent;
import com.facebook.share.widget.ShareDialog;


public class DetailsActivity extends ActionBarActivity {

    private static final String TAG = "buckysMessage";
    private Bundle bundle;
    private FacebookCallback<Sharer.Result> shareCallback = new FacebookCallback<Sharer.Result>() {
        @Override
        public void onCancel() {
            Log.d("HelloFacebook", "Canceled");
            Toast.makeText(DetailsActivity.this, "Post Cancelled", Toast.LENGTH_LONG).show();
        }

        @Override
        public void onError(FacebookException error) {
            Log.d("HelloFacebook", String.format("Error: %s", error.toString()));
        }

        @Override
        public void onSuccess(Sharer.Result result) {
            Log.d("HelloFacebook", "Success!");
            /*
            if(result.getPostId()==null) {
                //Toast.makeText(DetailsActivity.this, "Post Story, ID: " +
                 //       result.getPostId(), Toast.LENGTH_LONG).show();
                Toast.makeText(DetailsActivity.this,"Not Posted", Toast.LENGTH_SHORT).show();
            }
            */
        }
    };
    //private String position;
    //private Boolean canPresentShareDialog;
    public DetailsActivity(){
        super();
        bundle=null;
        //bundle = this.getIntent().getExtras();
        //position=bundle.getString("position");
    }
    CallbackManager callbackManager;
    ShareDialog shareDialog;

    private void postFeed(){
        if (ShareDialog.canShow(ShareLinkContent.class)) {

            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle(bundle.getString("title"+bundle.getString("position")))
                    .setContentDescription(
                            bundle.getString("prshp" + bundle.getString("position")) + " ,Locations: " + bundle.getString("location" + bundle.getString("position")))
                    .setContentUrl(Uri.parse(bundle.getString("url" + bundle.getString("position"))))
                    .setImageUrl(Uri.parse(bundle.getString("image" + bundle.getString("position"))))
                    .build();

            Log.i(TAG,"Yes can show!");
            shareDialog.show(linkContent);
        }
    }



    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        callbackManager.onActivityResult(requestCode, resultCode, data);

        Bundle b=data.getExtras();
        for (String key : b.keySet()) {
            Object value = b.get(key);
            Log.d(TAG, String.format("%s %s (%s)", key,
                    value.toString(), value.getClass().getName()));
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //Init Facebook SDK
        FacebookSdk.sdkInitialize(getApplicationContext());
        callbackManager = CallbackManager.Factory.create();
        shareDialog = new ShareDialog(this);
        shareDialog.registerCallback(callbackManager, shareCallback);

        ImageView fb=(ImageView) findViewById(R.id.facebook);
        fb.setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                postFeed();
            }
        });
        //canPresentShareDialog = ShareDialog.canShow(ShareLinkContent.class);

        /*
        if (ShareDialog.canShow(ShareLinkContent.class)) {
            ShareLinkContent linkContent = new ShareLinkContent.Builder()
                    .setContentTitle("Hello Facebook")
                    .setContentDescription(
                            "The 'Hello Facebook' sample  showcases simple Facebook integration")
                    .setContentUrl(Uri.parse("http://developers.facebook.com/android"))
                    .build();

            shareDialog.show(linkContent);
        }
       */

        //ImageView fb=(ImageView) findViewById(R.id.facebook);


        bundle = this.getIntent().getExtras();
        final String position=bundle.getString("position");
        final String url = bundle.getString("url"+position);

        Button button = (Button) findViewById(R.id.BuyNow);

        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setAction(Intent.ACTION_VIEW);
                intent.addCategory(Intent.CATEGORY_BROWSABLE);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        TabHost tabHost = (TabHost) findViewById(R.id.tabHost);
        tabHost.setup();

        TabHost.TabSpec tabSpec = tabHost.newTabSpec("basic info");
        tabSpec.setContent(R.id.basicInfo);
        tabSpec.setIndicator("BASIC INFO");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("seller");
        tabSpec.setContent(R.id.seller);
        tabSpec.setIndicator("SELLER");
        tabHost.addTab(tabSpec);

        tabSpec = tabHost.newTabSpec("shipping");
        tabSpec.setContent(R.id.shipping);
        tabSpec.setIndicator("SHIPPING");
        tabHost.addTab(tabSpec);
        showTabs();

    }
    public void showTabs(){
        //final Bundle bundle = this.getIntent().getExtras();
        final String position=bundle.getString("position");

        ImageView img = (ImageView) findViewById(R.id.Image);
        new DownloadImageTask(img).execute(bundle.getString("bigImage"+position));




        TextView item = (TextView) findViewById(R.id.Itemname);
        item.setText(bundle.getString("title"+position));
        //item.setText(url);

        TextView priceShipping = (TextView) findViewById(R.id.PriceShipping);
        priceShipping.setText(bundle.getString("prshp"+position));

        TextView location = (TextView) findViewById(R.id.Location);
        location.setText(bundle.getString("location"+position));
        //location.setText(bundle.getString("topRated" + position));

        ImageView topRatedListing = (ImageView)findViewById(R.id.topRatedListing);
        if(bundle.getString("topRated" + position).equals("true")){
            topRatedListing.setBackgroundResource(R.drawable.top);
        }


        //SELLER
        TextView userName = (TextView) findViewById(R.id.userNameText);
        userName.setText(bundle.getString("seller"+position));
        TextView feedbackScore = (TextView) findViewById(R.id.feedbackScoreText);
        feedbackScore.setText(bundle.getString("feedback"+position));
        TextView positiveFeedback = (TextView) findViewById(R.id.positiveFeedbackText);
        positiveFeedback.setText(bundle.getString("positiveFeedback"+position));
        TextView feedbackRating = (TextView) findViewById(R.id.feedbackRatingText);
        feedbackRating.setText(bundle.getString("feedbackRating"+position));
        ImageView topRated = (ImageView) findViewById(R.id.topRatedImage);
        if(bundle.getString("topRatedSeller"+position).equals("true")){

            topRated.setBackgroundResource(R.drawable.yes);
        }
        else{
            topRated.setBackgroundResource(R.drawable.no);
        }
        TextView store = (TextView) findViewById(R.id.storeText);
        store.setText((bundle.getString("storeName" + position).equals("")==false)?bundle.getString("storeName" + position):"N.A.");
        if(store.getText().equals("N.A.")==false) {
            store.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    Intent intent = new Intent();
                    intent.setAction(Intent.ACTION_VIEW);
                    intent.addCategory(Intent.CATEGORY_BROWSABLE);
                    intent.setData(Uri.parse(bundle.getString("storeURL" + position)));
                    startActivity(intent);
                }
            });
        }

        //BASIC INFO
        TextView categoryName = (TextView) findViewById(R.id.categoryNameText);
        categoryName.setText(bundle.getString("categoryName"+position));

        TextView condition = (TextView) findViewById(R.id.conditionText);
        condition.setText(bundle.getString("condition"+position));

        TextView buyingFormat = (TextView) findViewById(R.id.buyingFormatText);
        buyingFormat.setText(bundle.getString("listingType"+position));

        //SHIPPING INFO
        TextView shippingType = (TextView) findViewById(R.id.shippingTypeText);
        shippingType.setText(bundle.getString("shippingType" + position));

        TextView handlingTime = (TextView) findViewById(R.id.handlingTimeText);
        handlingTime.setText(bundle.getString("handlingTime" + position) + " day(s)");

        TextView shippingLocations = (TextView) findViewById(R.id.shippingLocationsText);
        shippingLocations.setText(bundle.getString("shipToLocations" + position));

        ImageView expeditedShipping = (ImageView) findViewById(R.id.expeditedShippingImage);
        if(bundle.getString("expeditedShipping"+position).equals("true")){

            expeditedShipping.setBackgroundResource(R.drawable.yes);
        }
        else{
            expeditedShipping.setBackgroundResource(R.drawable.no);
        }

        ImageView oneDayShipping = (ImageView) findViewById(R.id.oneDayShippingImage);
        if(bundle.getString("oneDayShippingAvailable"+position).equals("true")){

            oneDayShipping.setBackgroundResource(R.drawable.yes);
        }
        else{
            oneDayShipping.setBackgroundResource(R.drawable.no);
        }

        ImageView returnsAccepted = (ImageView) findViewById(R.id.returnsAcceptedImage);
        if(bundle.getString("returnAccepted"+position).equals("true")){

            returnsAccepted.setBackgroundResource(R.drawable.yes);
        }
        else{
            returnsAccepted.setBackgroundResource(R.drawable.no);
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_details, menu);
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
