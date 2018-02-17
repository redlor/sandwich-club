package com.udacity.sandwichclub.utils;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static Sandwich parseSandwichJson(String json) {

        try {
            // Find the main JSON object
            JSONObject mainObject = new JSONObject(json);

            // Get content
            JSONObject name = mainObject.getJSONObject("name");
            String mainName = name.getString("mainName");

            JSONArray alsoKnownAs = name.getJSONArray("alsoKnownAs");
            List<String> alsoKnownAsList = new ArrayList<>();
            for (int i = 0; i < alsoKnownAs.length(); i++) {
                alsoKnownAsList.add(alsoKnownAs.getString(i));
            }

            String placeOfOrigin = mainObject.getString("placeOfOrigin");
            String description = mainObject.getString("description");
            String image = mainObject.getString("image");

            JSONArray ingredients = mainObject.getJSONArray("ingredients");
            List<String> ingredientsList = new ArrayList<>();
            for (int i = 0; i < ingredients.length(); i++) {
                ingredientsList.add(ingredients.getString(i));
            }

            return new Sandwich(mainName, alsoKnownAsList, placeOfOrigin,
                    description, image, ingredientsList);

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return null;
    }
}
