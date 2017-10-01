package com.londonappbrewery.bitcointicker;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    // Constants:
    // TODO: Create the base URL
    private final String BASE_URL = "https://apiv2.bitcoinaverage.com/indices/global/ticker/";
    private final String TAG = "BitCoin";

    // Member Variables:
    TextView mPriceTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mPriceTextView = (TextView) findViewById(R.id.priceLabel);
        Spinner spinner = (Spinner) findViewById(R.id.currency_spinner);

        // Create an ArrayAdapter using the String array and a spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.currency_array, R.layout.spinner_item);

        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(R.layout.spinner_dropdown_item);

        // Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        // TODO: Set an OnItemSelected listener on the spinner
        spinner.setOnItemSelectedListener ( new AdapterView.OnItemSelectedListener ( ) {

            @Override
            public void onItemSelected ( AdapterView < ? > parent, View view, int position, long id ) {
                Log.d ( TAG, "onItemSelected: " + parent.getItemAtPosition ( position  ) );
                letsDoSomeNetworking ( "BTC"  + parent.getItemAtPosition ( position ) );
            }

            @Override
            public void onNothingSelected ( AdapterView < ? > parent ) {
                Log.d ( TAG, "onNothingSelected: Nothing Selected" );
            }
        } );

    }

    // TODO: complete the letsDoSomeNetworking() method
    private void letsDoSomeNetworking(String url) {

        Log.d ( TAG, "Full URL : " + BASE_URL + url );

        AsyncHttpClient client = new AsyncHttpClient();
        client.get( BASE_URL + url , null , new JsonHttpResponseHandler () {

            @Override
            public void onSuccess ( int statusCode, Header[] headers, JSONObject response ) {
                Log.d ( TAG, "onSuccess: JSON  = "  + response.toString ());

                try {
                    BitCoinDataModel weatherDataModel = BitCoinDataModel.fromJson ( response );
                    updateUI ( weatherDataModel );
                } catch ( Exception e ) {
                    e.printStackTrace ( );
                }
            }

            @Override
            public void onFailure ( int statusCode, Header[] headers, String responseString, Throwable throwable ) {
                Log.d ( TAG, "onFailure: " + responseString );
            }

            @Override
            public void onFailure ( int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse ) {
                Log.d ( TAG, "onFailure: " + errorResponse.toString () );
            }
        });
    }

    private void updateUI ( BitCoinDataModel weatherDataModel ) {

        mPriceTextView.setText ( String.valueOf ( weatherDataModel.getAsk ()  ) );
    }
}
