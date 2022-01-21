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
    private RelativeLayout[]            m_AllLevelsVisualComponents = null;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.all_levels_overview_form);

        this.m_RelativeLayout_MainLayout = findViewById(R.id.RelativeLayout_MainLayout);
        this.m_AllLevels = Game.getInstance().getLevels();
        this.m_AllLevelsVisualComponents = new RelativeLayout[this.m_AllLevels.size()];

        embedLevelsAsVisualComponents();
    }

    private void retrieveAllLevelsData()
    {
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

            // Dynamically load the suitable id of the current RelativeLayout
            relativeLayoutId = AppUtils.getIdValueByIdStringName(String.format("id_of_relativelayout_of_level_%d", currentIndex + 1), this);

            // Dynamically load the suitable id of the current ImageView of the theme iamge
            currentThemeImageViewId = AppUtils.getIdValueByIdStringName(String.format("id_of_imageview_of_level_%d", currentIndex + 1), this);

            // Dynamically load the suitable id of the current ImageView of the lock image
            currentLockImageViewId = AppUtils.getIdValueByIdStringName(String.format("id_of_lockimageview_of_level_%d", currentIndex + 1), this);

            // Dynamically load the suitable id of the current TextView
            currentTextViewId = AppUtils.getIdValueByIdStringName(String.format("id_of_textview_of_level_%d", currentIndex + 1), this);

            // Dynamically load the suitable id of the current RatingBar
            currentRatingBarId = AppUtils.getIdValueByIdStringName(String.format("id_of_ratingbar_of_level_%d", currentIndex + 1), this);

            layoutParams = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(20, 20, 20, 20);

            visualComponentOfTheCurrentLevel = getVisualComponentOfLevelObject(
                    currentLevel.getLevelNumber(),
                    currentLevel.getTheme(),
                    currentThemeImageViewId,
                    currentRatingBarId,
                    relativeLayoutId,
                    currentLockImageViewId,
                    currentLevel.getLevelLockImage(),
                    currentTextViewId);

            this.m_AllLevelsVisualComponents[currentIndex] = visualComponentOfTheCurrentLevel;

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
                layoutParams.addRule(RelativeLayout.BELOW, this.m_AllLevelsVisualComponents[currentIndex - 3].getId());
            }

            if (currentIndex % 3 != 0)
            {
                layoutParams.addRule(RelativeLayout.RIGHT_OF, this.m_AllLevelsVisualComponents[currentIndex - 1].getId());
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
                    Level currentLevelWhichShouldBePlayed = AllLevelsOverviewForm.this.m_AllLevels.get(levelIndex);

                    // If the current level is still locked
                    if (currentLevelWhichShouldBePlayed.isLocked() == true)
                    {
                        Toast.makeText(AllLevelsOverviewForm.this, "You can't play an unlocked game !", Toast.LENGTH_LONG).show();
                    }
                    else
                    {
                        Intent intent = new Intent(AllLevelsOverviewForm.this, PlayLevelActivity.class);
                        intent.putExtra("Level", currentLevelWhichShouldBePlayed);
                        startActivity(intent);
                    }
                }
            });

            this.m_RelativeLayout_MainLayout.addView(visualComponentOfTheCurrentLevel, layoutParams);
        }
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

    public RelativeLayout getVisualComponentOfLevelObject(int levelNumber, int backgorundImageId,
                                                          int currentThemeImageViewId,
                                                          int currentRatingBarId, int currentRelativeLayoutId,
                                                          int currentLockerImageViewId,
                                                          int lockerImageId,
                                                          int currentLevelNumberTextViewId){
        /**
         * @param: level
         *
         * */
        /*relative layout*/
        RelativeLayout levelContainer = new RelativeLayout(AllLevelsOverviewForm.this);
        levelContainer.setId(currentRelativeLayoutId);

        /* image background */
        ImageView imageBackground = new ImageView(AllLevelsOverviewForm.this);
        imageBackground.setId(currentThemeImageViewId);
        int dp300 = AppUtils.ConvertPixelsToDPs(this.getResources(),300);
        RelativeLayout.LayoutParams imageBackgroundParams = new RelativeLayout.LayoutParams(dp300,dp300);
        imageBackground.setLayoutParams(imageBackgroundParams);
        imageBackground.setImageResource(backgorundImageId);

        /* rating bar */
        RatingBar ratingBar = new RatingBar(new ContextThemeWrapper(this,R.style.ratingBar),null ,R.style.ratingBar);
        ratingBar.setId(currentRatingBarId);
        RelativeLayout.LayoutParams ratingBarParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        ratingBarParams.addRule(RelativeLayout.BELOW,currentThemeImageViewId);
        ratingBarParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        int dp8 = AppUtils.ConvertPixelsToDPs(this.getResources(), 8);
        ratingBarParams.setMargins(0,dp8,0,0);
        ratingBar.setLayoutParams(ratingBarParams);
        ratingBar.setNumStars(5);

        // TODO: change this to the real number of stars
        ratingBar.setRating(5);

        /*text view level number*/
        TextView textView = new TextView(AllLevelsOverviewForm.this);
        RelativeLayout.LayoutParams textViewParams = new RelativeLayout.LayoutParams(
                RelativeLayout.LayoutParams.WRAP_CONTENT,
                RelativeLayout.LayoutParams.WRAP_CONTENT);
        textViewParams.addRule(RelativeLayout.CENTER_HORIZONTAL);
        textViewParams.addRule(RelativeLayout.CENTER_VERTICAL);
        textView.setLayoutParams(textViewParams);
        textView.setId(currentLevelNumberTextViewId);
        textView.setText(levelNumber+"");
        int dp10 = AppUtils.ConvertDPsToPixels(this.getResources(),10);
        textView.setTextSize(dp10);

        /*image lock*/
        ImageView imageLock = new ImageView(AllLevelsOverviewForm.this);
        imageLock.setId(currentLockerImageViewId);
        imageLock.setImageResource(lockerImageId);
        int dp30 = AppUtils.ConvertPixelsToDPs(this.getResources(),30);
        RelativeLayout.LayoutParams imageLockParams = new RelativeLayout.LayoutParams(dp30,dp30);
        imageLockParams.addRule(RelativeLayout.ALIGN_TOP);
        imageLockParams.addRule(RelativeLayout.ALIGN_RIGHT,currentThemeImageViewId);
        imageLock.setLayoutParams(imageLockParams);
        imageLock.setRotation(340);

        levelContainer.addView(imageBackground);
        levelContainer.addView(imageLock);
        levelContainer.addView(ratingBar);
        levelContainer.addView(textView);

        return levelContainer;
    }
}