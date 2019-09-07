package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.view.View;
import android.text.TextUtils;
import android.widget.Toast;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;
import java.util.List;
public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;
    private ImageView imageIV;
    private TextView origin_labelIv;
    private TextView originIv;
    private TextView alsoKnown_labelIv;
    private TextView alsoKnownIv;
    private TextView ingredientsIv;
    private TextView descriptionIv;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        imageIV = findViewById(R.id.image_iv);
        origin_labelIv = findViewById(R.id.placeOfOrigin_label);
        originIv = findViewById(R.id.origin_tv);
        alsoKnown_labelIv = findViewById(R.id.alsoKnownAs_label);
        alsoKnownIv = findViewById(R.id.also_known_tv);
        ingredientsIv = findViewById(R.id.ingredients_tv);
        descriptionIv = findViewById(R.id.description_tv);
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
//        Picasso.with(this)
//                .load(sandwich.getImage())
//                .into(imageIV);

        setTitle(sandwich.getMainName());
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(imageIV);

        if (sandwich.getPlaceOfOrigin().isEmpty()) {
            // origin.setText(R.string.detail_error_message);
            // if we don't have origin, hide it.
            origin_labelIv.setVisibility(View.GONE);
            originIv.setVisibility(View.GONE);
        } else {
            originIv.setText(sandwich.getPlaceOfOrigin());
        }

        if (sandwich.getAlsoKnownAs().isEmpty()) {
            // alsoKnown.setText(R.string.detail_error_message);
            // if we don't have aka, hide it.
            alsoKnown_labelIv.setVisibility(View.GONE);
            alsoKnownIv.setVisibility(View.GONE);
        } else {
            List<String> aka = sandwich.getAlsoKnownAs();
            String aka_str = TextUtils.join(", ", aka);
            alsoKnownIv.setText(aka_str);
        }

        descriptionIv.setText(sandwich.getDescription());

        List<String> ing = sandwich.getIngredients();
        String ing_str = TextUtils.join(", ", ing);
        ingredientsIv.setText(ing_str);
    }
}
