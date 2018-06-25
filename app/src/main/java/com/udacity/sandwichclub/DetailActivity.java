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

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;


    TextView akaTextView;
    TextView ingredientsTextView;
    TextView originTextView;
    TextView descTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        //Get IDS
        ImageView ingredientsIv = findViewById(R.id.image_iv);
        akaTextView = findViewById(R.id.also_known_tv);
        ingredientsTextView = findViewById(R.id.ingredients_tv);
        originTextView = findViewById(R.id.origin_tv);
        descTextView = findViewById(R.id.description_tv);

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
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());


    }

    private String arrayToString(List<String> mList) {

        StringBuilder stringBuilder = new StringBuilder();
        for (String items : mList) {
            stringBuilder.append(items + "\n");
        }

        if (stringBuilder.length() >= 1) {
            return stringBuilder.toString();
        } else {
            return "None";
        }
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {

        //convert the lists to a single string with a newline and set in Textview
        akaTextView.setText(arrayToString(sandwich.getAlsoKnownAs()));
        ingredientsTextView.setText(arrayToString(sandwich.getIngredients()));

        //ingredientsTextView.setText(toString(sandwich.getIngredients()));
        originTextView.setText((sandwich.getPlaceOfOrigin()));
        descTextView.setText(sandwich.getDescription());

    }
}
