package com.geeselightning.zepr.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.geeselightning.zepr.Level;
import com.geeselightning.zepr.Player;

public class PowerUpCure3 extends PowerUp {

    /**
     * Constructor for the healing power up
     * @param currentLevel level to spawn the power up in
     * @param player player to monitor for pick up event and to apply the effect to
     */
    public PowerUpCure3(Level currentLevel, Player player) {
        super(new Texture("heal.png"), currentLevel, player, 0, "Cure3 PowerUp Collected");
    }

    /**
     * Increase the player health
     */
    @Override
    public void activate() {
        super.activate();


    }

}
