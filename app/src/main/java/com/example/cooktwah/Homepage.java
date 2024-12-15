package com.example.cooktwah;

import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import com.example.cooktwah.databinding.ActivityHomepageBinding;
public class Homepage extends AppCompatActivity {

    ActivityHomepageBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityHomepageBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        binding.bNav.setOnItemSelectedListener(item -> {
            int itemId = item.getItemId();
            if (itemId == R.id.cook) {
                replaceFragment(new CookFragment());
            } else if (itemId == R.id.home) {
                replaceFragment(new HomeFragment());
            } else if (itemId == R.id.profile) {
                replaceFragment(new ProfileFragment());
            }
            return true;
        });
        if (savedInstanceState == null) {
            replaceFragment(new HomeFragment()); // Set HomeFragment as the default fragment
            binding.bNav.setSelectedItemId(R.id.home); // Make sure Home is selected in BottomNavigationView
        }
    }

    private void replaceFragment(Fragment fragment)
    {
        getSupportFragmentManager().beginTransaction().replace(R.id.frameLayout1, fragment).commit();
    }
}