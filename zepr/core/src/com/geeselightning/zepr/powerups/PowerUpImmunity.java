package com.geeselightning.zepr.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.geeselightning.zepr.Constant;
import com.geeselightning.zepr.Level;
import com.geeselightning.zepr.Player;

public class PowerUpImmunity extends PowerUp {

    /**
     * Constructor for the immunity power up
     * @param currentLevel level to spawn the power up in
     * @param player player to monitor for pick up event and to apply the effect to
     */
    public PowerUpImmunity(Level currentLevel, Player player) {
        super(new Texture("immunity.png"), currentLevel, player, Constant.IMMUNITYTIME, "Immunity PowerUp Collected");
    }

    /**
     * Enable player immunity
     */
    @Override
    public void activate() {
        super.activate();
        player.setImmune(true);
    }

    /**
     * Disable player immunity
     */
    @Override
    public void deactivate() {
        super.deactivate();
        player.setImmune(false);
    }

    //#changed:   Moved update method to inherited class
}
