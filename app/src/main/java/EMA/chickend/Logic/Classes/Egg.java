package EMA.chickend.Logic.Classes;

import EMA.chickend.Logic.Interfaces.IBlowable;

/**
 * This class is a class which represents an Egg
 */
public class Egg implements IBlowable {
    private final int life        = 1;
    private int speed     = 0;
    private float size      = 0.0f;

    /**
     * public level()
     *      @param i_Size represent the size of the egg
     *      @param i_Speed represnet the speed the egg will go down */

    public Egg(int i_Speed, float i_Size) {
        this.speed = speed;
        this.size = size;
    }

    /**
     * implementation of the blow method from IBlow interface:
     *       purpose - make the egg to blow up*/
    @Override
    public void blow() {

    }

    public void onClick() {

    }
}
