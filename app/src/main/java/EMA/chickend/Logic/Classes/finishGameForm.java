package EMA.chickend.Logic.Classes;

import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import EMA.chickend.R;


public class finishGameForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button exitGameButton = findViewById(R.id.exit_game);
        Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
        exitGameButton.startAnimation(scale);

        exitGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                System.exit(0);
            }
        });
    }
}
