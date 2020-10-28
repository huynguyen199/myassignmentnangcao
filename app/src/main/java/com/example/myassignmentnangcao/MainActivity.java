package com.example.myassignmentnangcao;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.myassignmentnangcao.Fragment.FragmentCourse;
import com.example.myassignmentnangcao.Fragment.FragmentMaps;
import com.example.myassignmentnangcao.Fragment.FragmentNews;
import com.example.myassignmentnangcao.Fragment.FragmentSocial;
import com.example.myassignmentnangcao.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {
    DrawerLayout drawer;

    GoogleSignInClient mGoogleSignInClient;
    ImageView imageView;
    TextView name,email;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this,gso);



        final Toolbar toolbar = findViewById(R.id.mytoolbar);
        setSupportActionBar(toolbar);
        final FrameLayout frame = findViewById(R.id.frameContainer);
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        final TextView yourname = navigationView.getHeaderView(0).findViewById(R.id.yourname);
        final ImageView imagegoogle = navigationView.getHeaderView(0).findViewById(R.id.image_google);
        final TextView youremail = navigationView.getHeaderView(0).findViewById(R.id.youremail);

        GoogleSignInAccount acct = GoogleSignIn.getLastSignedInAccount(this);
        if (acct != null) {
            String personName = acct.getDisplayName();
            String personEmail = acct.getEmail();
            Uri personPhoto = acct.getPhotoUrl();
            yourname.setText(personName);
            youremail.setText(personEmail);
            if(personPhoto == null){

            }else {
                Glide.with(this).load(personPhoto).into(imagegoogle);
            }
        }

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,drawer,toolbar,R.string.navigation_drawer_open,R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        if(savedInstanceState == null){
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.frameContainer,new FragmentCourse())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_school);
        }
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment = null;
                switch (menuItem.getItemId()){
                    case R.id.nav_school:
                        fragment = new FragmentCourse();
                        Toast.makeText(MainActivity.this,"the loai",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_map:
                        fragment = new FragmentMaps();

                        Toast.makeText(MainActivity.this,"Book",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_news:
                        fragment = new FragmentNews();
                        Toast.makeText(MainActivity.this,"Bill",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_social:
                        fragment = new FragmentSocial();
                        Toast.makeText(MainActivity.this,"Bill",Toast.LENGTH_SHORT).show();
                        break;
                    case R.id.nav_signout:
                        signOut();
                        Toast.makeText(MainActivity.this,"Thoát",Toast.LENGTH_SHORT).show();
                        break;

                }
                try {
                    getSupportFragmentManager().beginTransaction()
                            .replace(R.id.frameContainer,fragment).commit();
                    drawer.setSelected(true);
                    drawer.closeDrawer(GravityCompat.START);
                }catch (Exception e){
                    e.printStackTrace();
                }

                return true;
            }
        });
    }
    private void signOut() {
        mGoogleSignInClient.signOut()
                .addOnCompleteListener(this, new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if(task.isSuccessful()){
                            Log.d("tag","da thoat");
                            finish();
                        }

                    }
                });
    }
}
