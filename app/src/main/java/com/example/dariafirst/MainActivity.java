package com.example.dariafirst;

import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.Toast;
import java.util.Locale;
import java.time.LocalDateTime;
import java.time.Duration;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private ImageButton imageButton;
    private final ZonedDateTime NEXT_CLASS_TIME = ZonedDateTime.of(
            LocalDateTime.of(2025, 10, 24, 12, 0),
            ZoneId.of("Europe/Kiev")
    );

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
        imageButton = findViewById(R.id.flag_button);
        imageButton.setBackgroundColor(getResources().getColor(android.R.color.darker_gray));
        imageButton.setOnClickListener(v -> showRemainingTimeToast());
    }

    private void showRemainingTimeToast() {
        ZonedDateTime now = ZonedDateTime.now(ZoneId.of("Europe/Kiev"));
        Duration duration = Duration.between(now, NEXT_CLASS_TIME);
        if (duration.isNegative()) {
            Toast.makeText(this, "Заняття вже відбулося!", Toast.LENGTH_LONG).show();
            return;
        }
        long seconds = duration.getSeconds();
        long days = seconds / (24 * 3600);
        seconds %= (24 * 3600);
        long hours = seconds / 3600;
        seconds %= 3600;
        long minutes = seconds / 60;
        seconds %= 60;

        String timeLeft = String.format(Locale.getDefault(),
                "%d днів %d годин %d хвилин та %d секунд",
                days, hours, minutes, seconds);
        Toast.makeText(this, timeLeft, Toast.LENGTH_LONG).show();
    }
}