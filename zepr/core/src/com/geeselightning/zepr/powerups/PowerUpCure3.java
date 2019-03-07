package com.geeselightning.zepr.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.geeselightning.zepr.Level;
import com.geeselightning.zepr.Player;
import com.geeselightning.zepr.Zepr;

public class PowerUpCure3 extends PowerUp {

    /**
     * Constructor for the healing power up
     * @param currentLevel level to spawn the power up in
     * @param player player to monitor for pick up event and to apply the effect to
     */
    public PowerUpCure3(Level currentLevel, Player player) {
        super(new Texture("cure3.png"), currentLevel, player, 0, "Cure3 PowerUp Collected");
    }

    @Override
    public void activate(Zepr zepr) {
        super.activate();
        zepr.setCure3(true);
    }

}
