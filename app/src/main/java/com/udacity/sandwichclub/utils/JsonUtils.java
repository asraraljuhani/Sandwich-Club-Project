package com.udacity.sandwichclub.utils;

import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    private final static String NAME = "name";
    private final static String MAIN_NAME = "mainName";
    private final static String ALSO_KNOWN_AS = "alsoKnownAs";
    private final static String PLACE_OF_ORIGIN = "placeOfOrigin";
    private final static String DESCRIPTION = "description";
    private final static String IMAGE = "image";
    private final static String INGREDIENTS = "ingredients";

    public static Sandwich parseSandwichJson(String json) {

        try {
            JSONObject JsonObject = new JSONObject(json);

            JSONObject name = JsonObject.getJSONObject(NAME);
            String mainName = name.getString(MAIN_NAME);

            JSONArray JSONArrayAlsoKnownAs = name.getJSONArray(ALSO_KNOWN_AS);
            List<String> alsoKnownAs = convertToListFromJsonArray(JSONArrayAlsoKnownAs);

            String placeOfOrigin = JsonObject.optString(PLACE_OF_ORIGIN);

            String description = JsonObject.getString(DESCRIPTION);

            String image = JsonObject.getString(IMAGE);

            JSONArray Ingredients_in_JSON = JsonObject.getJSONArray(INGREDIENTS);
            List<String> ingredients = convertToListFromJsonArray(Ingredients_in_JSON);

            return new Sandwich(mainName, alsoKnownAs, placeOfOrigin, description, image, ingredients);

        } catch (JSONException e) {
            Log.e(JsonUtils.class.getSimpleName(), e.getMessage());
            e.printStackTrace();
            return null;
        }
    }
    private static List<String> convertToListFromJsonArray(JSONArray array) throws JSONException {
        List<String> ingredients = new ArrayList<>(array.length());
        for (int i = 0; i < array.length(); i++) {
            ingredients.add(array.getString(i));
        }
        return ingredients;
    }
}
