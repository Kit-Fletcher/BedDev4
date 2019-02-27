package com.geeselightning.zepr.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.geeselightning.zepr.Constant;
import com.geeselightning.zepr.Level;
import com.geeselightning.zepr.Player;

public class PowerUpInvisibility extends PowerUp {

    /**
     * Constructor for the invisibility power up
     * @param currentLevel level to spawn the power up in
     * @param player player to monitor for pick up event and to apply the effect to
     */
    public PowerUpInvisibility(Level currentLevel, Player player) {
        super(new Texture("invisibility.png"), currentLevel, player, Constant.INVISIBILITYTIME, "Invisibility PowerUp Collected");
    }

    /**
     * Enable player invisibility
     */
    @Override
    public void activate() {
        super.activate();
        player.setVisible(false); //player is undetectable for 5 seconds
    }

    /**
     * Disable player invisibility
     */
    @Override
    public void deactivate() {
        super.deactivate();
        player.setVisible(true);
    }

    //#changed:   Moved update method to inherited class
}
