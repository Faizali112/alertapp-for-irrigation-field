package com.example.irrigationalert;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.ScrollView;
import android.widget.LinearLayout;
import android.widget.Button;
import android.media.MediaPlayer;
import androidx.cardview.widget.CardView;
import com.google.firebase.messaging.FirebaseMessaging;
import android.app.AlertDialog;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = "MainActivity";
    private static MainActivity instance;
    private TextView statusTextView;
    private ScrollView scrollView;
    private LinearLayout notificationContainer;
    private Button connectButton;
    private boolean isConnected = false;
    private HashMap<View, MediaPlayer> alertSoundMap = new HashMap<>();

    public static MainActivity getInstance() {
        return instance;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        instance = this;

        // Initialize views
        statusTextView = findViewById(R.id.statusTextView);
        scrollView = findViewById(R.id.scrollView);
        notificationContainer = findViewById(R.id.notificationContainer);
        connectButton = findViewById(R.id.connectButton);

        // Set up connect button
        connectButton.setOnClickListener(v -> connectToRover());
    }

    private void connectToRover() {
        connectButton.setEnabled(false);
        connectButton.setText("Connecting...");

        FirebaseMessaging.getInstance().getToken()
                .addOnCompleteListener(task -> {
                    if (!task.isSuccessful()) {
                        Log.w(TAG, "Fetching FCM registration token failed", task.getException());
                        connectButton.setEnabled(true);
                        connectButton.setText("Connect to Rover");
                        return;
                    }

                    String token = task.getResult();

                    // Show token in a dialog
                    new AlertDialog.Builder(this)
                            .setTitle("Device Token")
                            .setMessage(token)
                            .setPositiveButton("Copy", (dialog, which) -> {
                                android.content.ClipboardManager clipboard =
                                        (android.content.ClipboardManager) getSystemService(CLIPBOARD_SERVICE);
                                android.content.ClipData clip =
                                        android.content.ClipData.newPlainText("Device Token", token);
                                clipboard.setPrimaryClip(clip);
                                showToast("Token copied to clipboard");
                            })
                            .setNegativeButton("Close", null)
                            .show();

                    // Update UI
                    isConnected = true;
                    connectButton.setVisibility(View.GONE);
                    statusTextView.setVisibility(View.VISIBLE);
                    updateConnectionStatus(true);
                });
    }

    private void showToast(String message) {
        android.widget.Toast.makeText(this, message, android.widget.Toast.LENGTH_SHORT).show();
    }

    private void updateConnectionStatus(boolean connected) {
        runOnUiThread(() -> {
            statusTextView.setText("Status: Active");
            statusTextView.setTextColor(getResources().getColor(android.R.color.holo_green_dark));
        });
    }

    public void addNotificationToView(String roverId, String title, String message) {
        runOnUiThread(() -> {
            // Create new MediaPlayer instance for this notification
            MediaPlayer notificationSound = MediaPlayer.create(this, R.raw.alert);

            // Play alert sound
            if (notificationSound != null) {
                notificationSound.setLooping(true);
                notificationSound.start();
            }

            // Create CardView container
            CardView cardView = new CardView(this);
            LinearLayout.LayoutParams cardParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            );
            cardParams.setMargins(0, 0, 0, 16);
            cardView.setLayoutParams(cardParams);
            cardView.setCardElevation(4);
            cardView.setRadius(8);
            cardView.setContentPadding(16, 16, 16, 16);

            // Set card background color based on rover
            if (roverId.equals("1")) {
                cardView.setCardBackgroundColor(getResources().getColor(android.R.color.holo_blue_light));
            } else if (roverId.equals("2")) {
                cardView.setCardBackgroundColor(getResources().getColor(android.R.color.holo_green_light));
            }

            // Store the MediaPlayer instance with this card view
            alertSoundMap.put(cardView, notificationSound);

            // Create notification content layout
            LinearLayout contentLayout = new LinearLayout(this);
            contentLayout.setOrientation(LinearLayout.VERTICAL);
            contentLayout.setLayoutParams(new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
            ));

            // Add rover ID with icon
            TextView roverView = new TextView(this);
            String roverLabel = roverId.equals("1") ? "🤖 Rice Field Rover 1" : "🚜 Rice Field Rover 2";
            roverView.setText(roverLabel);
            roverView.setTextSize(14);
            roverView.setTypeface(null, android.graphics.Typeface.BOLD);
            roverView.setTextColor(getResources().getColor(android.R.color.white));
            contentLayout.addView(roverView);

            // Add title
            TextView titleView = new TextView(this);
            titleView.setText(title);
            titleView.setTextSize(18);
            titleView.setTypeface(null, android.graphics.Typeface.BOLD);
            titleView.setPadding(0, 8, 0, 0);
            titleView.setTextColor(getResources().getColor(android.R.color.white));
            contentLayout.addView(titleView);

            // Add message
            TextView messageView = new TextView(this);
            messageView.setText(message);
            messageView.setTextSize(16);
            messageView.setPadding(0, 8, 0, 16);
            messageView.setTextColor(getResources().getColor(android.R.color.white));
            contentLayout.addView(messageView);

            // Add timestamp
            TextView timeView = new TextView(this);
            timeView.setText(new java.text.SimpleDateFormat("HH:mm:ss").format(new java.util.Date()));
            timeView.setTextSize(12);
            timeView.setTextColor(getResources().getColor(android.R.color.white));
            contentLayout.addView(timeView);

            // Add dismiss button
            Button dismissButton = new Button(this);
            dismissButton.setText("Dismiss");
            dismissButton.setBackgroundColor(getResources().getColor(android.R.color.white));
            dismissButton.setTextColor(getResources().getColor(android.R.color.black));
            dismissButton.setOnClickListener(v -> {
                // Stop and release the sound
                MediaPlayer sound = alertSoundMap.get(cardView);
                if (sound != null) {
                    sound.stop();
                    sound.release();
                }
                alertSoundMap.remove(cardView);

                // Remove the notification
                notificationContainer.removeView(cardView);
            });
            contentLayout.addView(dismissButton);

            // Add content to card
            cardView.addView(contentLayout);

            // Add to container
            notificationContainer.addView(cardView, 0);

            // Scroll to top
            scrollView.post(() -> scrollView.fullScroll(ScrollView.FOCUS_UP));
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        instance = null;

        // Clean up all playing sounds
        for (MediaPlayer sound : alertSoundMap.values()) {
            if (sound != null) {
                sound.stop();
                sound.release();
            }
        }
        alertSoundMap.clear();
    }
}