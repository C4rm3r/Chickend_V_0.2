package EMA.chickend.Logic.Classes;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;

import EMA.chickend.Logic.Interfaces.IGame;
import EMA.chickend.R;

/**
 * This class is A SINGLETON CLASS which represents a Game
 * (Source: https://stackoverflow.com/a/25236059/2196301)
 */
public class Game implements IGame {
    private static Game _instance;
    private List<Level> levels;

    private Game()
    {
        this.levels = new ArrayList<>();
        this.loadLevels();
    }

    /*
    public Game(List<Level> levels) {
        this.levels = levels;
    }
    */

    public static Game getInstance()
    {
        if (_instance == null)
        {
            _instance = new Game();
        }
        return _instance;
    }

    public List<Level> getLevels() {
        return levels;
    }

    public void setLevels(List<Level> levels) {
        this.levels = levels;
    }

    @Override
    public void playGame() {

    }

    @Override
    public void exitGame() {

    }

    public void loadLevels()
    {
        try
        {
            // Check if the file with the data of the game exists (Source: https://stackoverflow.com/a/16238330/2196301)
            File file = new File("Chickend.dat");
            if(file.exists())
            {
                // Deserialize the list of the levels (Source: https://howtodoinjava.com/java/collections/arraylist/serialize-deserialize-arraylist/)
                FileInputStream fis = new FileInputStream("Chickend.dat");
                ObjectInputStream ois = new ObjectInputStream(fis);

                this.levels = (List) ois.readObject();

                ois.close();
                fis.close();
            }
            // If the file doesn't exist - behave like it's the first time the game has been opened.
            else
            {
                Level level;
                // Create the list with the chickens of the current level
                // code...

                int currentTheme = 0;

                // Generate the levels of the game
                for (int i = 0; i < 20; i++)
                {
                    switch ((i / 5) + 1)
                    {
                        case 1:
                            currentTheme = R.drawable.background___ancient_egypt;
                            break;
                        case 2:
                            currentTheme = R.drawable.background___beach_of_mosh;
                            break;
                        case 3:
                            currentTheme = R.drawable.background___route_66;
                            break;
                        case 4:
                            currentTheme = R.drawable.background___life_of_xp;
                            break;
                        case 5:
                            currentTheme = R.drawable.background___brazil_tour;
                            break;
                    }

                    level = new Level(i + 1, true, 5, currentTheme);
                    this.levels.add(level);
                }

                // "Open" the first level in the game.
                this.levels.get(0).setIsLocked(false);
            }
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
            return;
        }
        catch (ClassNotFoundException c)
        {
            System.out.println("Class not found");
            c.printStackTrace();
            return;
        }
    }

    public void saveLevels()
    {
        // Serialize the list of the levels (Source: https://howtodoinjava.com/java/collections/arraylist/serialize-deserialize-arraylist/)
        try
        {
            FileOutputStream fos = new FileOutputStream("Chickend.dat");
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this.levels);
            oos.close();
            fos.close();
        }
        catch (IOException ioe)
        {
            ioe.printStackTrace();
        }
    }
}