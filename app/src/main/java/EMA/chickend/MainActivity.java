package EMA.chickend;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import EMA.chickend.GUI.AllLevelsOverviewForm;
import EMA.chickend.Logic.Classes.AppUtils;
import EMA.chickend.Logic.Classes.Game;
import EMA.chickend.Logic.Classes.Level;

public class MainActivity extends AppCompatActivity {

    public static Context m_context;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        m_context = getApplicationContext();

        setContentView(R.layout.activity_main);

        Button btn = findViewById(R.id.mybutton);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllLevelsOverviewForm.class));
            }
        });
    }
}