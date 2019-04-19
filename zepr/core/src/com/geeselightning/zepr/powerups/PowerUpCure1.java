package com.geeselightning.zepr.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.geeselightning.zepr.Level;
import com.geeselightning.zepr.Player;
import com.geeselightning.zepr.Zepr;

public class PowerUpCure1 extends PowerUp {

    /**
     * Constructor for the first part of the cure
     * @param currentLevel level to spawn the power up in
     * @param player player to monitor for pick up event and to apply the effect to
     */
    public PowerUpCure1(Level currentLevel, Player player) {
        super(new Texture("cure1.png"), currentLevel, player, 0, "Cure1 PowerUp Collected");
        cure = true;
    }

    @Override
    public void activate(Zepr zepr) {
        super.activate();
        System.out.println("working cure 1");
        zepr.setCure1(true);
    }

}
