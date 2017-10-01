package com.londonappbrewery.bitcointicker;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by mtaghipour on 10/1/17.
 */

class BitCoinDataModel {

    private int ask;

    public static BitCoinDataModel fromJson( JSONObject jsonObject) throws JSONException {

        BitCoinDataModel bitCoinDataModel = new BitCoinDataModel ();

        bitCoinDataModel.ask = jsonObject.getInt ( "ask" );

        return bitCoinDataModel;
    }

    public int getAsk ( ) {
        return ask;
    }
}
