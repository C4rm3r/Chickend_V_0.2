package EMA.chickend.GUI;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.ContextThemeWrapper;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import EMA.chickend.Logic.Classes.AppUtils;
import EMA.chickend.Logic.Classes.Game;
import EMA.chickend.Logic.Classes.Level;
import EMA.chickend.Logic.Classes.Matan.PlayLevelActivity;
import EMA.chickend.R;

public class AllLevelsOverviewForm extends Activity
{
    private RelativeLayout              m_RelativeLayout_MainLayout = null;

    private List<Level>                 m_AllLevels                 = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_levels_overview_form);

        this.m_RelativeLayout_MainLayout = findViewById(R.id.RelativeLayout_MainLayout);
        this.m_AllLevels = Game.getInstance().getLevels();

        embedLevelsAsVisualComponents();
    }

    private void embedLevelsAsVisualComponents()
    {
        Level currentLevel;
        int relativeLayoutId,
            currentThemeImageViewId,
            currentLockImageViewId,
            currentTextViewId,
            currentRatingBarId;

        RelativeLayout visualComponentOfTheCurrentLevel;
        RelativeLayout.LayoutParams layoutParams;

        for (int currentIndex = 0; currentIndex < this.m_AllLevels.size(); currentIndex++)
        {
            /*
                2. for each level:
            */
            currentLevel = this.m_AllLevels.get(currentIndex);

            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 20, 20, 20);

            visualComponentOfTheCurrentLevel = currentLevel.getVisualComponentOfLevelObject(this);

            /*
            00  01  02
            03  04  05
            06  07  08
            09  10  11
            12  13  14
            15  16  17
            18  19  20
            */

            if (currentIndex >= 3)
            {
                layoutParams.addRule(RelativeLayout.BELOW, Game.getInstance().getLevels().get(currentIndex - 3).getVisualComponent().getId());
            }

            if (currentIndex % 3 != 0)
            {
                layoutParams.addRule(RelativeLayout.RIGHT_OF, Game.getInstance().getLevels().get(currentIndex - 1).getVisualComponent().getId());
            }

            /*
                2.2.1. if (currentIndex % 3 == 0) -> place below the (currentIndex - 3) Visual Component
            */

            /*
                2.3. Create new RatingBar which represents the number of lives which the user passed with it the current level
            */

            visualComponentOfTheCurrentLevel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    RelativeLayout currentRelativeLayout = (RelativeLayout) v;

                    TextView currentTextView = (TextView) currentRelativeLayout.getChildAt(3);

                    int levelNumber = Integer.valueOf(currentTextView.getText().toString());
                    int levelIndex = levelNumber - 1;
                    Level currentLevelWhichShouldBePlayed = Game.getInstance().getLevels().get(levelIndex);

                    // If the current level is still locked
                    if (currentLevelWhichShouldBePlayed.isLocked() == true)
                    {
                        Toast.makeText(AllLevelsOverviewForm.this, "You can't play an unlocked game !", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent intent = new Intent(AllLevelsOverviewForm.this, PlayLevelActivity.class);
                        intent.putExtra("Level", levelNumber);
                        startActivity(intent);
                    }
                }
            });

            this.m_RelativeLayout_MainLayout.addView(visualComponentOfTheCurrentLevel, layoutParams);
        }

        Game.getInstance().getLevels().get(0).setImageAlpha();
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    /*
    1. load data about all the levels --> isLocked, numberOfLives, theme
    2. for each level:
        2.1. Create new imagebutton where the image is the theme image of the current level
        2.2. Place the image in the correct place:
                2.2.1. if (currentIndex % 4 == 0) -> place below the (currentIndex-4) imagebutton
                2.2.2. else -> place right of the (currentIndex-1) imagebutton
        2.3. Create new RatingBar which represents the number of lives which the user passed with it the current level
        2.3. Define onClickListener and it's action (== Open the requested level).
    */

    public void updateLevelVisualComponent(int levelIndex)
    {
        // this.m_AllLevelsVisualComponents[levelIndex]
    }
}