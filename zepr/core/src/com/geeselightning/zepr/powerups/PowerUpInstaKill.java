package com.geeselightning.zepr.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.geeselightning.zepr.Constant;
import com.geeselightning.zepr.Level;
import com.geeselightning.zepr.Player;

public class PowerUpInstaKill extends PowerUp {

    /**
     * Constructor for the instakill power up
     * @param currentLevel level to spawn the power up in
     * @param player player to monitor for pick up event and to apply the effect to
     */
    public PowerUpInstaKill(Level currentLevel, Player player) {
        super(new Texture("instakill.png"), currentLevel, player, Constant.INSTAKILLTIME, "InstaKill PowerUp Collected");
    }

    /**
     * Increase player attack damage attribute
     */
    @Override
    public void activate() {
        super.activate();
        player.setBoostDamage(10);
    }


    /**
     * Reset player attack damage
     */
    @Override
    public void deactivate() {
        super.deactivate();
        player.setBoostDamage(1);
    }

    //#changed:   Moved update method to inherited class
}
