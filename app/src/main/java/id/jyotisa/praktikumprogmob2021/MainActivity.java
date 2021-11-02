package id.jyotisa.praktikumprogmob2021;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ismaeldivita.chipnavigation.ChipNavigationBar;

import id.jyotisa.praktikumprogmob2021.fragment.ExploreFragment;
import id.jyotisa.praktikumprogmob2021.fragment.HomeFragment;
import id.jyotisa.praktikumprogmob2021.fragment.NewFragment;

public class MainActivity extends AppCompatActivity {

    private ChipNavigationBar chipNavigationBar;
    private Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTheme(R.style.Theme_PraktikumProgmob2021);
        setContentView(R.layout.activity_main);
        getSupportActionBar().hide();

        chipNavigationBar = findViewById(R.id.chipNav);

        chipNavigationBar.setItemSelected(R.id.home, true);
        getSupportFragmentManager().beginTransaction().replace(R.id.container, new HomeFragment()).commit();

        chipNavigationBar.setOnItemSelectedListener(new ChipNavigationBar.OnItemSelectedListener() {
            @Override
            public void onItemSelected(int i) {
                switch (i) {
                    case R.id.home:
                        fragment = new HomeFragment();
                        break;
                    case R.id.explore:
                        fragment = new ExploreFragment();
                        break;
                    case R.id.newJob:
                        fragment = new NewFragment();
                        break;

                }

                if (fragment != null) {
                    getSupportFragmentManager().beginTransaction().replace(R.id.container, fragment).commit();
                }

            }
        });
    }
}