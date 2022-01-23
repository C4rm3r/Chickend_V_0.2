package EMA.chickend;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import EMA.chickend.GUI.AllLevelsOverviewForm;

public class MainActivity extends AppCompatActivity {

    public static Context m_context;

    @RequiresApi(api = Build.VERSION_CODES.Q)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        m_context = getApplicationContext();

        setContentView(R.layout.activity_main);

       // RelativeLayout layout = findViewById(R.id.RelativeLayout_Layout);
        //layout.getBackground().setAlpha(160);

        ImageView chick = findViewById(R.id.chick_chicken_start);
        ImageView regularChicken = findViewById(R.id.regular_chicken_start);
        ImageView motherChicken = findViewById(R.id.mother_chicken_start);

        motherChicken.getBackground().setAlpha(230);
        chick.getBackground().setAlpha(230);
        regularChicken.getBackground().setAlpha(230);



        Button btn = findViewById(R.id.mybutton);

        btn.setAlpha(1);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, AllLevelsOverviewForm.class));
            }
        });
    }
}