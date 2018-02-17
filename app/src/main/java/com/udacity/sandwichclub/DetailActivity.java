package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @BindView(R.id.image_iv)
    ImageView mImageView;
    @BindView(R.id.origin_tv)
    TextView mPlaceOfOrigin;
    @BindView(R.id.also_known_tv)
    TextView mAlsoKnownAs;
    @BindView(R.id.description_tv)
    TextView mDescription;
    @BindView(R.id.ingredients_tv)
    TextView mIngredients;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ButterKnife.bind(this);


        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(mImageView);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        // Set the text for the TextViews
        String description = sandwich.getDescription();
        mDescription.setText(" " + description);

        String placeOfOrigin = sandwich.getPlaceOfOrigin();
        if (!placeOfOrigin.equals("")) {
            mPlaceOfOrigin.setText(" " + placeOfOrigin);
        } else {
            mPlaceOfOrigin.setText(" " + getResources().getString(R.string.unknown_place));
        }

        List<String> alsoKnowAsList = sandwich.getAlsoKnownAs();
        StringBuilder knownAsBuilder = new StringBuilder(" ");
        formatList(alsoKnowAsList, knownAsBuilder);
        if (!alsoKnowAsList.isEmpty()) {
            mAlsoKnownAs.setText(knownAsBuilder);
        } else {
            mAlsoKnownAs.setText(" " + getResources().getString(R.string.itself));
        }

        StringBuilder ingredientsBuilder = new StringBuilder(" ");
        List<String> ingredientsList = sandwich.getIngredients();
        formatList(ingredientsList, ingredientsBuilder);

        if (!ingredientsList.isEmpty()) {
            mIngredients.setText(ingredientsBuilder);
        } else {
            mIngredients.setText(" " + getResources().getString(R.string.no_ingredients));
        }


    }

    /**
     * This method formats the list in order to remove parenthesis and to add commas and dot at the end.
     *
     * @param list
     * @param builder
     * @return the builder
     */
    private StringBuilder formatList(List<String> list, StringBuilder builder) {
        int i = 0;
        for (String s : list) {
            builder.append(s);
            i++;
            if (i < list.size()) {
                builder.append(", ");
            } else {
                builder.append(".");
            }
        }
        return builder;
    }
}
