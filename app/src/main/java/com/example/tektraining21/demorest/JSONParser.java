package com.example.tektraining21.demorest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by tektraining21 on 7/21/2014.
 */
public class JSONParser {

    public static List<Flower> parseFeed(String content) {
        JSONArray array = null;
        try {
            array = new JSONArray(content);
            List<Flower> flowerList = new ArrayList<Flower>();

            for (int i=0; i<array.length(); i++) {
                JSONObject object = array.getJSONObject(i);
                Flower flower = new Flower();

                flower.setCategory(object.getString("category"));
                flower.setInstructions(object.getString("instructions"));
                flower.setName(object.getString("name"));
                flower.setPhoto(object.getString("photo"));
                flower.setPrice(object.getDouble("price"));
                flower.setProductId(object.getInt("productId"));

                flowerList.add(flower);
            }

            return flowerList;
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }
}
