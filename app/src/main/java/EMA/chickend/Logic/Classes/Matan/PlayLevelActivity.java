package EMA.chickend.Logic.Classes.Matan;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.graphics.drawable.Drawable;
import android.media.Rating;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import EMA.chickend.GUI.AllLevelsOverviewForm;
import EMA.chickend.Logic.Classes.AppUtils;
import EMA.chickend.Logic.Classes.Chicken;
import EMA.chickend.Logic.Classes.Game;
import EMA.chickend.Logic.Classes.Level;
import EMA.chickend.R;


public class PlayLevelActivity extends AppCompatActivity implements ChickenListener {
    private static final int MIN_ANIMATION_DELAY = 500;
    private static final int MAX_ANIMATION_DELAY = 1500;
    private static final int NUMBER_OF_HEARTS = 5;
    private static int m_ChickenPerLevel = 20;
    private int m_ScreenWidth, m_ScreenHeight, m_ChickensKilled, m_HeartUsed;
    // need to get the level in the ctor to know how many chickens to Launch
    private Level m_Level;
    private boolean m_Playing;
    private List<ImageView> m_HeartImages = new ArrayList<>();
    // private List<MatanChicken> m_Chickens = new ArrayList<>();
    private List<Chicken> m_Chickens = new ArrayList<>();
    private Button m_GoButton;
    private ViewGroup m_ContentView;
    private int chickensLaunched = 0;
    private AlertDialog.Builder dialog = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.play_level_activity);

        m_ContentView = findViewById(R.id.PlayLevelActivity);
        setToFullScreen();

        ViewTreeObserver viewTreeObserver = m_ContentView.getViewTreeObserver();
        if (viewTreeObserver.isAlive()) {
            viewTreeObserver.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
                @Override
                public void onGlobalLayout() {
                    m_ContentView.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                    m_ScreenWidth = m_ContentView.getWidth();
                    m_ScreenHeight = m_ContentView.getHeight();
                }
            });
        }

        m_ContentView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setToFullScreen();
            }
        });

        // add 5 life's
        m_HeartImages.add((ImageView) findViewById(R.id.heart1));
        m_HeartImages.add((ImageView) findViewById(R.id.heart2));
        m_HeartImages.add((ImageView) findViewById(R.id.heart3));
        m_HeartImages.add((ImageView) findViewById(R.id.heart4));
        m_HeartImages.add((ImageView) findViewById(R.id.heart5));

        // Get the level from the bundle of activity's parameters
        this.m_Level = Game.getInstance().getLevels().get(getIntent().getIntExtra("Level", 0) - 1);
        this.m_Level.generateChickens(this);

        try {
            m_GoButton = findViewById(R.id.start_btn);
            Animation scale = AnimationUtils.loadAnimation(this, R.anim.scale);
            m_GoButton.startAnimation(scale);
            m_GoButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    // starting the level again
                    if (m_HeartUsed == 5) {
                        startLevelAgain();
                    } else {
                        // start the level
                        startLevel();
                    }

                }
            });
        }
        catch (Exception exception)
        {
            Toast.makeText(this, exception.toString(), Toast.LENGTH_LONG).show();
        }
    }

    private void resetHearts()
    {
        m_HeartUsed = 0;
        for (ImageView pin : m_HeartImages) {
            pin.setImageResource(R.drawable.heart_icon_fill);
        }
    }

    // Initialize the hearts for the game and start the level again.
    private void startLevelAgain()
    {
        this.resetHearts();
        startLevel();
    }

    private void setToFullScreen() {
        this.m_ContentView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }

    @Override
    protected void onResume() {
        super.onResume();
    }


    // set the background, animation and other variables and start launching balloons according to lvl
    private void startLevel()
    {
        RelativeLayout layout = (RelativeLayout)findViewById(R.id.PlayLevelActivity);
        layout.setBackgroundResource(this.m_Level.getTheme());
        layout.invalidate();

        this.resetHearts();
        chickensLaunched = 0;

        // launch the chicken async
        ChickenLauncher launcher = new ChickenLauncher();
        launcher.execute(this.m_Level.getLevelNumber());

        m_Playing = true;
        m_ChickensKilled = 0;
        m_GoButton.clearAnimation();
        m_GoButton.setVisibility(View.INVISIBLE);
    }

    // finish/start lvl pause
    private void finishLevel() {

        Toast.makeText(this,"Good job there !", Toast.LENGTH_SHORT).show();
        m_Playing = false;

        // go to screen of levels!. need to fix !!!
        // !!!!!!!!!
        /*
        TODO:
        1. set "isLocked" of the NEXT level to false
        2. show dialog message: would you like to proceed to the next level?
            Yes - go to the next level
            No  - go to the AllLevelsOverviewForm
        */

        this.m_Level.setNumberOfLives(this.NUMBER_OF_HEARTS - this.m_HeartUsed);

        // Unlock the next level - LOGICALLY && GRAPHICALLY
        Level nextLevel = Game.getInstance().getLevels().get(this.m_Level.getLevelNumber());
        nextLevel.setIsLocked(false);
        nextLevel.generateChickens(this);

        // TODO: check that this works and actually leads to the next level.
        dialog = new AlertDialog.Builder(this);
        dialog.setPositiveButton(R.string.messagebox_continue_playing_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                m_Level = nextLevel;
                dialog.cancel();
                startLevel();
            }
        });

        dialog.setNegativeButton(R.string.messagebox_stop_playing_text, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                finish();
            }
        });

        dialog.setCancelable(false);
        dialog.show();

        /*
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation scale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
                m_GoButton.startAnimation(scale);
                m_GoButton.setVisibility(View.VISIBLE);
                m_GoButton.setText(String.format("start level", m_Level.getLevelNumber()));
            }
        }, 500);
        */
    }



    /**
     * what will happen when a Chicken is pressed.
     * @param chicken specific Chicken.
     * @param userTouch if user touch the chicken or not.
     */
    @Override
    public void killChicken(Chicken chicken, boolean userTouch) {

        // need to add sound that the chicken is dead here !
        m_ChickensKilled++;
        m_ContentView.removeView(chicken);
        m_Chickens.remove(chicken);

        // if user did not touch the chicken.
        if (!userTouch) {
            // increase the heart that was used
            m_HeartUsed++;

            // check that used heart is greater than the total hearts.
            if(m_HeartUsed <= m_HeartImages.size()) {
                // changing the heart to not fill heart.
                m_HeartImages.get(m_HeartUsed - 1)
                        .setImageResource(R.drawable.heart_icon_not_fill);
            }

            // check if the game is over.
            if(m_HeartUsed == NUMBER_OF_HEARTS) {
                gameOver();
                return;
            }
            else {
                // let the user know that heart was down.
                // Toast.makeText(this, "heart down", Toast.LENGTH_SHORT).show();
            }
        }

        // if all the chicken was killed finish the level.
        if(m_ChickensKilled == m_ChickenPerLevel) {
            finishLevel();
        }
    }

    // If the user failed to pass the level.
    private void gameOver() {

        // TODO: @matan should change the MatanChicken class according to our class diagram
        // removing all the chicken from the screen.
        for (Chicken chicken : m_Chickens) {
            m_ContentView.removeView(chicken);
            chicken.setKilled(true);
        }

        // clear the array.
        m_Chickens.clear();
        m_Playing = false;

        // TODO: set the value of

        // the game is over !
        // move to dialog ? / press the button to start from the beginning.

        Toast.makeText(this,"Ohhh shit !", Toast.LENGTH_SHORT).show();
        m_Playing = false;
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Animation scale = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.scale);
                m_GoButton.startAnimation(scale);
                m_GoButton.setVisibility(View.VISIBLE);
                m_GoButton.setText(R.string.restart_game_button_text);
            }
        }, 500);
    }


    private class ChickenLauncher extends AsyncTask<Integer, Integer, Void> {

        @Override
        protected Void doInBackground(Integer... params)
        {
            if (params.length != 1) {
                throw new AssertionError(
                        "Expected 1 param for current level");
            }

            int level = params[0];

            m_ChickenPerLevel = m_Level.getChickens().length;

            // TODO: change name to something more informative
            int maxDelay = Math.max(MIN_ANIMATION_DELAY,
                    (MAX_ANIMATION_DELAY - ((level - 1) * 500)));
            int minDelay = maxDelay / 2;
            int xPosition;

            while (m_Playing && chickensLaunched < m_ChickenPerLevel) {

                //Get a random horizontal position for the next balloon
                Random random = new Random(new Date().getTime());
                // check if hebrew or English and set the x position according to your language
                SharedPreferences prefs = getSharedPreferences("Settings", Activity.MODE_PRIVATE);
                String language = prefs.getString("My_Lang", "");

                // when using hebrew the screen is is -1 from english.
                if(language.equals("iw"))
                    xPosition = -1*random.nextInt(m_ScreenWidth - 200);
                else
                    xPosition = random.nextInt(m_ScreenWidth - 200);

                // calls indirectly to onProgressUpdate
                publishProgress(xPosition);
                // counting the chickens for the level.
                // chickensLaunched++;

                // Wait a random number of milliseconds before add other chicken to the screen.
                int delay = random.nextInt(minDelay) + minDelay;
                try {
                    Thread.sleep(delay);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

            return null;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int xPosition = values[0];

            Chicken currentChickenToLaunch = PlayLevelActivity.this.m_Level.getChickens()[chickensLaunched];

            // counting the chickens for the level.
            chickensLaunched++;

            launchChickenAttack(xPosition, currentChickenToLaunch);
        }
    }

    // TODO: as being said above, this function should launch the new given chicken and not just new ordinary chicken...
    // add the chicken to the list and screen and then start attack.
    private void launchChickenAttack(int x, Chicken currentChickenToLaunch) {

        /*
        MatanChicken chicken = new MatanChicken(this, 150);
        // add the chicken to the array of chickens.
        m_Chickens.add(chicken);

        // Set chicken vertical position and dimensions and adding to the view.
        chicken.setX(x);
        chicken.setY(m_ScreenHeight + chicken.getHeight());
        // add the chicken to the screen
        m_ContentView.addView(chicken);
        // set the speed down of the chicken.
        chicken.chickenAppearances(m_ScreenHeight, chicken.getSpeed());
        */

        // add the chicken to the array of chickens.
        m_Chickens.add(currentChickenToLaunch);

        // Set chicken vertical position and dimensions and adding to the view.
        currentChickenToLaunch.setX(x);
        currentChickenToLaunch.setY(m_ScreenHeight + currentChickenToLaunch.getHeight());

        // add the chicken to the screen
        m_ContentView.addView(currentChickenToLaunch);

        // set the speed down of the chicken.
        currentChickenToLaunch.chickenAppearances(m_ScreenHeight, currentChickenToLaunch.getSpeed());
    }
}

