package com.geeselightning.zepr.powerups;

import com.badlogic.gdx.graphics.Texture;
import com.geeselightning.zepr.Constant;
import com.geeselightning.zepr.Level;
import com.geeselightning.zepr.Player;

public class PowerUpHeal extends PowerUp {

    /**
     * Constructor for the healing power up
     * @param currentLevel level to spawn the power up in
     * @param player player to monitor for pick up event and to apply the effect to
     */
    public PowerUpHeal(Level currentLevel, Player player) {
        super(new Texture("heal.png"), currentLevel, player, 0, "Health PowerUp Collected");
    }

    /**
     * Increase the player health
     */
    @Override
    public void activate() {
        super.activate();

        //Health cannot be more than max health
        if(player.getHealth() + Constant.HEALUP < (int)(player.getHPMult() * Constant.PLAYERMAXHP))
        	player.setHealth(player.getHealth() + Constant.HEALUP);
        else
        	player.setHealth((int) (player.getHPMult() * Constant.PLAYERMAXHP));
    }

    //#changed:   Moved update method to inherited class
}
