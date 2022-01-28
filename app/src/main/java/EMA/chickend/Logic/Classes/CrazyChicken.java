package EMA.chickend.Logic.Classes;

import android.content.Context;

import java.util.LinkedList;
import java.util.List;

import EMA.chickend.R;

/**
 * This class is a class which represents a CrazyChicken.
 * This chicken is crazier than regular chicken, which holds an eggs and can lay them.
 */
public class CrazyChicken extends Chicken
{
    private List<Egg> eggs;

    public CrazyChicken(Context context)
    {
        super(context, 150, 2000, 1, R.drawable.crazy_chicken_picture,R.raw.crazy_chicken_sound);
        eggs = new LinkedList<Egg>();
        eggs.add(new Egg(10,20));
        eggs.add(new Egg(20,10));
    }

    public List<Egg> getEggs() {
        return eggs;
    }

    public void setEggs(List<Egg> eggs) {
        this.eggs = eggs;
    }


}
