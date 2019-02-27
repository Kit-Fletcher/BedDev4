package com.geeselightning.zepr.powerups;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.geeselightning.zepr.Level;
import com.geeselightning.zepr.Player;

public class PowerUp extends Sprite {

    private Level currentLevel;
    public static boolean active;
    private float timeRemaining;
    private float effectDuration;
    protected Player player;
    public String powerUpString;
    public static String activePowerUp;

    /**
     * Constructor for the generic power up class
     * @param texture the texture to display for the pick up
     * @param currentLevel the instance of Level to spawn the power up in
     * @param player player instance to pick up and apply the power up to
     * #changed:   Added power up text string and effectDuration system
     */
    PowerUp(Texture texture, Level currentLevel, Player player, float effectDuration, String powerUpString) {
        super(new Sprite(texture));
        this.currentLevel = currentLevel;
        this.effectDuration = effectDuration;
        // Tests pass a null currentLevel
        if (currentLevel != null)
            setPosition(currentLevel.getConfig().powerSpawn.x, currentLevel.getConfig().powerSpawn.y);
        this.player = player;
        activePowerUp = "No PowerUp Collected";
        
        this.powerUpString = powerUpString;
        
    }

    /**
     * Apply the power up effect to the player, removing the power up texture
     * #changed:   Moved timer code here from child classes
     */
    public void activate(){
        timeRemaining = effectDuration;
        active = true;
        this.getTexture().dispose();
        activePowerUp = powerUpString;
    }

    /**
     * Remove the power up effect from the player
     * #changed:   Added default text string
     */
    public void deactivate(){
    	activePowerUp = "No PowerUp Collected";
        active = false;
        if (currentLevel != null)
            // Tests pass a null currentLevel
            currentLevel.setCurrentPowerUp(null);
    }

    /**
     * Check whether the player instance is overlapping the power up
     * @return true if the player is overlapping the power up
     */
    public boolean overlapsPlayer(){
        Rectangle rectanglePlayer = player.getBoundingRectangle();
        Rectangle rectanglePower = this.getBoundingRectangle();
        return rectanglePlayer.overlaps(rectanglePower);
    }

    /**
     * Update method to advance the effect duration timer and deactivate if expired
     * @param delta the time between the start of the previous render() call and now
     * #changed:   Method now has timer code
     */
    public void update(float delta) {
        if (active) {
            timeRemaining -= delta;

            if (timeRemaining < 0)
                deactivate();
        }
    }

    public boolean isActive() {
        return active;
    }
}
