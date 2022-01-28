package EMA.chickend.Logic.Classes;

import android.content.Context;

import java.util.List;

import EMA.chickend.R;

public class MotherChicken extends Chicken
{
    private List<Chick> m_AllChicks = null;

    public MotherChicken(Context context)
    {
        super(context, 150, 6000, 3, R.drawable.mother_chicken_picture,R.raw.mother_chicken_sound);

    }

    public List<Chick> getAllChicks()
    {
        return this.m_AllChicks;
    }

    public void setAllChicks(List<Chick> allChicks)
    {
        this.m_AllChicks = allChicks;
    }

    /**
     * public void addChick()
     *     Purpose: Adds a single "Chick" to this "MotherChicken" object.
     * @param i_NewChick - The new "Chick" which should be inserted to this "MotherChicken" object.
     */
    public void addChick(Chick i_NewChick)
    {
        this.m_AllChicks.add(i_NewChick);
    }
}