package EMA.chickend.Logic.Classes;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import EMA.chickend.GUI.AllLevelsOverviewForm;
import EMA.chickend.Logic.Classes.Matan.PlayLevelActivity;
import EMA.chickend.Logic.Interfaces.ILevel;
import EMA.chickend.MainActivity;
import EMA.chickend.R;

/**
 * The level class represents a stage in the game
 * Each stage has different difficulty levels.
 */
public class Level implements ILevel, Serializable {
    private int levelNumber;
    private boolean isLocked;
    private int numberOfLives;
    private Chicken[] chickens;
    private int theme;
    private int lockPhotoId;

    /**
     * public Level()
     *     Purpose: c'tor of the level class
     * @param levelNumber this object represents the level number.
     * @param isLocked
     * @param numberOfLives this object represents the number of lives the player have in the level.
     * @param theme
     */
    public Level(int levelNumber, boolean isLocked, int numberOfLives, int theme) {
        this.levelNumber = levelNumber;
        this.setIsLocked(isLocked);
        this.numberOfLives = numberOfLives;
        this.chickens = chickens;
        this.theme = theme;
    }

    public List<Chicken> generateChickens(Context i_Context)
    {
        String[] set = null;
        Random randomGenerator = new Random();
        int currentChickenTypeIndex;
        String currentChickenType;

        if (this.getLevelNumber() < 5)
        {
            set = new String[]{"Chick"};
        }
        else if (5 < this.getLevelNumber() && this.getLevelNumber() < 10)
        {
            set = new String[]{"Chick", "RegularChicken"};
        }
        else if (10 < this.getLevelNumber() && this.getLevelNumber() < 15)
        {
            set = new String[]{"Chick", "RegularChicken", "MotherChicken"};
        }
        else if (15 < this.getLevelNumber() && this.getLevelNumber() < 20)
        {
            set = new String[]{"Chick", "RegularChicken", "MotherChicken", "CrazyChicken"};
        }

        this.chickens = new Chicken[2]; // this.getLevelNumber()*10 + 20];

        for (int i = 0; i <  this.chickens.length; i++)
        {
            currentChickenTypeIndex = randomGenerator.nextInt(set.length);

            if (currentChickenTypeIndex == 0)
            {
                currentChickenTypeIndex = 1;
            }

            currentChickenType = set[currentChickenTypeIndex - 1];

            if (currentChickenType.equals("RegularChicken"))
            {
                this.chickens[i] = new RegularChicken(i_Context);
            }
            else if (currentChickenType.equals("Chick"))
            {
                this.chickens[i] = new Chick(i_Context);
            }
            else if (currentChickenType.equals("CrazyChicken"))
            {
                this.chickens[i] = new CrazyChicken(i_Context);
            }
            else if (currentChickenType.equals("MotherChicken"))
            {
                this.chickens[i] = new MotherChicken(i_Context);
            }
        }

        return null;
    }

    public int getNumberOfLives() {
        return numberOfLives;
    }

    public void setNumberOfLives(int numberOfLives) {
        this.numberOfLives = numberOfLives;
    }

    public int getLevelNumber() {
        return levelNumber;
    }

    public void setLevelNumber(int levelNumber) {
        this.levelNumber = levelNumber;
    }

    public Chicken[] getChickens() {
        return chickens;
    }

    public void setIsLocked(boolean newLevelLockState)
    {
        this.isLocked = newLevelLockState;

        if (this.isLocked == true)
        {
            this.lockPhotoId = R.drawable.ic_baseline_lock_24;
        }
        else
        {
            this.lockPhotoId = R.drawable.ic_baseline_unlock_24;
        }
    }

    public boolean isLocked()
    {
        return this.isLocked;
    }

    public int getLevelLockImage()
    {
        return this.lockPhotoId;
    }

    public int getTheme()
    {
        return this.theme;
    }

    public void setTheme(int newTheme)
    {
        this.theme = newTheme;
    }

    /**
     * This method start the level the user selected.
     */
    @Override
    public void PlayGame() {

    }

    /**
     * This method return to the main menu of the game.
     */
    @Override
    public void ExitGame() {

    }
}