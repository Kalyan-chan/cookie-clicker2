package com.example.cookieclicker;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    private long count = 0;
    private TextView text;
    private static final int DONATE_REQUEST_CODE = 1;

    private int pointsPerClick = 1;
    boolean notifiedNovice = false;
    boolean notifiedAmateur = false;
    boolean notifiedProfessional = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        text = findViewById(R.id.textView);
    }

    @SuppressLint("DefaultLocale")
    public void onClick(View view) {
        count += pointsPerClick;
        String timesText = getTimesText(count);
        text.setText(String.format("У вас %d %s", count, timesText));

        if (count >= 80 && !notifiedProfessional) {
            Toast.makeText(this, "80: Уровень профессионала", Toast.LENGTH_SHORT).show();
            notifiedProfessional = true;
        } else if (count >= 50 && !notifiedAmateur) {
            Toast.makeText(this, "50: Уровень любителя", Toast.LENGTH_SHORT).show();
            notifiedAmateur = true;
        } else if (count >= 30 && !notifiedNovice) {
            Toast.makeText(this, "30: Уровень новичка", Toast.LENGTH_SHORT).show();
            notifiedNovice = true;
        }
    }

    private String getTimesText(long count) {
        if (count % 10 == 1 && count % 100 != 11) {
            return "печенька";
        } else if (count % 10 >= 2 && count % 10 <= 4 && !(count % 100 >= 12 && count % 100 <= 14)) {
            return "печеньки";
        } else {
            return "печенек";
        }
    }

    public void onClickDonateActivity(View view) {
        Intent intent = new Intent(this, DonateActivity.class);
        startActivityForResult(intent, DONATE_REQUEST_CODE);
    }

    @SuppressLint("DefaultLocale")
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == DONATE_REQUEST_CODE && resultCode == RESULT_OK) {
            Toast.makeText(this, "❤\uFE0F Спасибо за поддержку!", Toast.LENGTH_SHORT).show();
        }

        if (requestCode == 2 && resultCode == RESULT_OK) {
            count = data.getLongExtra("count", count);
            pointsPerClick = data.getIntExtra("pointsPerClick", pointsPerClick);
            text.setText(String.format("У вас %d %s", count, getTimesText(count)));
        }
    }

    public void onClickShopActivity(View view) {
        Intent intent = new Intent(this, ShopActivity.class);
        intent.putExtra("count", count);
        intent.putExtra("pointsPerClick", pointsPerClick);
        startActivityForResult(intent, 2);
    }

}
