package com.example.sipcalculator;

import android.os.Bundle;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.example.sipcalculator.database.AppDatabase;
import com.example.sipcalculator.database.DatabaseInitializer;
import com.example.sipcalculator.database.MutualFundDao;
import com.google.android.material.tabs.TabLayout;

public class MainActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MutualFundDao mutualFundDao = AppDatabase.getDatabase(this).mutualFundDao();
        DatabaseInitializer.populateDatabase(mutualFundDao);
        TabLayout tabLayout = findViewById(R.id.tab_layout);
        tabLayout.addTab(tabLayout.newTab().setText("SIP Calculator"));
        tabLayout.addTab(tabLayout.newTab().setText("View Report"));

// Set a listener to handle tab selection
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                // Handle tab selection and load the respective fragment
                int position = tab.getPosition();
                if (position == 0) {
                    openFragment(new SipCalculatorFragment());
                } else if (position == 1) {
                    openFragment(new LumpsumCalculatorFragment());
                }
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {}

            @Override
            public void onTabReselected(TabLayout.Tab tab) {}
        });
        // Optionally, load the SIP Calculator fragment by default
        if (savedInstanceState == null) {
            openFragment(new SipCalculatorFragment());
        }
    }

    private void openFragment(Fragment fragment) {
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.replace(R.id.fragment_container, fragment);
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }
}