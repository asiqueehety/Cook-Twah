package com.example.cooktwah;

import android.animation.ArgbEvaluator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.graphics.Color;
import android.os.Handler;

public class MainActivity extends AppCompatActivity {

    private final Handler handler = new Handler();
    private final int[] colors = {Color.rgb(30,62,98), Color.BLUE, Color.rgb(31,69,41), Color.rgb(22,29,111), Color.BLACK};
    private int colorIndex = 0;

    Button login;
    Button signup;
    TextView tv1;
    TextView tv2;
    TextView tv3;

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

        login = findViewById(R.id.button);
        signup = findViewById(R.id.button4);
        tv1 = findViewById(R.id.tv1);
        tv2 = findViewById(R.id.tv2);
        tv3 = findViewById(R.id.tv3);

        tv1.setTextColor(getResources().getColor(R.color.black));
        tv2.setTextColor(getResources().getColor(R.color.black));
        tv3.setTextColor(getResources().getColor(R.color.black));

        // Initially hide tv2
        tv1.setVisibility(TextView.INVISIBLE);
        tv2.setVisibility(TextView.INVISIBLE);
        tv3.setVisibility(TextView.INVISIBLE);

        // Delay the appearance of tv2 by 4 seconds
        new Handler().postDelayed(()->fadeInTextView(tv1,4000),3000);
        new Handler().postDelayed(()->fadeInTextView(tv2,10000),9000);
        new Handler().postDelayed(()->fadeInTextView(tv3,30000),25000);

        login.setOnClickListener(view -> {
            Intent intent = new Intent(this, Login.class);
            startActivity(intent);
        });

        signup.setOnClickListener(view -> {
            Intent intent = new Intent(this, Signup.class);
            startActivity(intent);
        });
        startSmoothColorTransition();
        // Reference the smoke ImageView // Create a translation animation to move the smoke
        ImageView smokeViewlt = findViewById(R.id.smokelt);
        smokeViewlt.setTranslationY(150f);
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(smokeViewlt, "translationX", -500f, 500f);
        ImageView smokeViewrt1 = findViewById(R.id.smokert1);
        ObjectAnimator animator2 = ObjectAnimator.ofFloat(smokeViewrt1, "translationX", 500f, -500f);
        ImageView smokeViewrt2 = findViewById(R.id.smokert2);
        smokeViewrt2.setTranslationY(150f);
        ObjectAnimator animator3 = ObjectAnimator.ofFloat(smokeViewrt2, "translationX", 500f, -500f);
        ImageView smokeViewrb = findViewById(R.id.smokerb);
        smokeViewrb.setTranslationY(1700f);
        ObjectAnimator animator4 = ObjectAnimator.ofFloat(smokeViewrb, "translationX", 500f, -500f);
        ImageView smokeViewlb = findViewById(R.id.smokelb);
        smokeViewlb.setTranslationY(1500f);
        ObjectAnimator animator5 = ObjectAnimator.ofFloat(smokeViewlb, "translationX", -500f, 500f);
        ImageView smokeViewlb2 = findViewById(R.id.smokelb2);
        smokeViewlb2.setTranslationY(1300f);
        ObjectAnimator animator6 = ObjectAnimator.ofFloat(smokeViewlb2, "translationX", -500f, 500f);
        ImageView smokeViewrb2 = findViewById(R.id.smokerb2);
        smokeViewrb2.setTranslationY(1400f);
        ObjectAnimator animator7 = ObjectAnimator.ofFloat(smokeViewrb2, "translationX", 500f, -500f);

        smokeAnimation(animator1);
        smokeAnimation(animator2);
        smokeAnimation(animator3);
        smokeAnimation(animator4);
        smokeAnimation(animator5);
        smokeAnimation(animator6);
        smokeAnimation(animator7);
    }

    private void smokeAnimation(ObjectAnimator animator) {
        animator.setDuration(20000); // Duration of 10 seconds
        animator.setRepeatCount(ObjectAnimator.INFINITE); // Infinite loop
        animator.setRepeatMode(ObjectAnimator.REVERSE); // Reverse back and forth
        animator.start();
    }

    private void fadeInTextView(TextView tv, int time) {
        tv.setVisibility(View.VISIBLE); // Make it visible
        AlphaAnimation fadeIn = new AlphaAnimation(0f, 1f); // Animation from invisible to fully visible
        fadeIn.setDuration(time); // Set duration for the fade-in effect
        fadeIn.setFillAfter(true); // Maintain the final state after the animation
        tv.startAnimation(fadeIn);
    }
    private void startSmoothColorTransition() {
        // Define a ValueAnimator for the color transition
        ValueAnimator colorAnimator = ValueAnimator.ofObject(
                new ArgbEvaluator(),
                Color.rgb(31,69,41),
                Color.rgb(30,62,98), // Start color// Intermediate color
                Color.rgb(22,29,111) // End color
        );

        // Duration for a full color cycle (e.g., 5 seconds)
        colorAnimator.setDuration(5000);

        // Repeat the animation indefinitely
        colorAnimator.setRepeatCount(ValueAnimator.INFINITE);
        colorAnimator.setRepeatMode(ValueAnimator.REVERSE);

        // Apply the animated color to the TextViews
        colorAnimator.addUpdateListener(animation -> {
            int animatedColor = (int) animation.getAnimatedValue();
            tv1.setTextColor(animatedColor);
            tv2.setTextColor(animatedColor);
            tv3.setTextColor(animatedColor);
        });

        // Start the animation
        colorAnimator.start();
    }
}