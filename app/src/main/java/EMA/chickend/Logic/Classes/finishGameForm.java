package EMA.chickend.Logic.Classes;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import androidx.appcompat.app.AppCompatActivity;
import EMA.chickend.R;


// TODO : MERGE IT TO THE CODE WHEN THE LEVELS ARE FINISHED
public class finishGameForm extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Button exitGameButton = findViewById(R.id.exit_game);

        exitGameButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO : check if works..
                System.exit(0);
            }
        });
    }
}
