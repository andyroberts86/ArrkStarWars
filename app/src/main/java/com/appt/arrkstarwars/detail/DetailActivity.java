package com.appt.arrkstarwars.detail;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.appt.arrkstarwars.R;

import java.util.Date;

public class DetailActivity extends AppCompatActivity {
    public static final String NAME_KEY = "NAME";
    public static final String HEIGHT_KEY = "HEIGHT";
    public static final String MASS_KEY = "MASS";
    public static final String CREATED_KEY = "CREATED";

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);
        String name = getIntent().getStringExtra(NAME_KEY);
        int height = getIntent().getIntExtra(HEIGHT_KEY, 0);
        int mass = getIntent().getIntExtra(MASS_KEY, 0);
        Date date = (Date) getIntent().getSerializableExtra(CREATED_KEY);

        ((TextView) findViewById(R.id.name_value)).setText(name);
        ((TextView) findViewById(R.id.height_value)).setText(TextHelper.formatHeight(height));
        ((TextView) findViewById(R.id.mass_value)).setText(Integer.toString(mass));
        ((TextView) findViewById(R.id.created_value)).setText(TextHelper.formatDate(date));
    }
}
