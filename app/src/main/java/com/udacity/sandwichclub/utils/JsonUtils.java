package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private static String SANDWICH_NAME = "name";
    private static String SANDWICH_MAIN = "mainName";
    private static String SANDWICH_AKA = "alsoKnownAs";
    private static String SANDWICH_ORIGIN = "placeOfOrigin";
    private static String SANDWICH_DESC = "description";
    private static String SANDWICH_IMAGE = "image";
    private static String SANDWICH_INGRED = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {

            //get json objects, not list or arrays, starts with a {
            JSONObject sandwichObj = new JSONObject(json);

            JSONObject jsonObjectName = sandwichObj.getJSONObject(SANDWICH_NAME);
            String sandMainName = jsonObjectName.getString(SANDWICH_MAIN);
            String sandPlaceOfOrigin = sandwichObj.getString(SANDWICH_ORIGIN);
            String sandDescription = sandwichObj.getString(SANDWICH_DESC);
            String sandImage = sandwichObj.getString(SANDWICH_IMAGE);


            //parse array, start with a [
            JSONArray akaArray = jsonObjectName.getJSONArray(SANDWICH_AKA);
            JSONArray ingredArray = sandwichObj.getJSONArray(SANDWICH_INGRED);

            //wrote method to take JSONarray and make it a list
            List<String> sandAka = arrayToList(akaArray);
            List<String> sandIngred = arrayToList(ingredArray);

            //String mainName, List<String> alsoKnownAs, String placeOfOrigin, String description, String image, List<String> ingredients
            Sandwich selectedSandwich = new Sandwich(sandMainName, sandAka, sandPlaceOfOrigin, sandDescription,sandImage,sandIngred);
            return selectedSandwich;

//            }
        } catch (JSONException e) {
            e.printStackTrace();
            return null;
        }


    }

    //loop through objects in array resource
    private static List<String> arrayToList(JSONArray inArray) throws JSONException {
        //initialize the list. Null did not work.  Needed an empty list in order to add.
        List<String> outList = new ArrayList<>();

        for (int i = 0; i < inArray.length(); i++) {
            outList.add(inArray.getString(i));
        }
        return outList;
    }
}