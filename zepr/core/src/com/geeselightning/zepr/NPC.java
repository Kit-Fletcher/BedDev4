package com.geeselightning.zepr;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.geeselightning.zepr.Zombie.Type;

public class NPC extends Character {
	private World world;
    private int hitRange;
    //public enum Type { ZOMBIE1, ZOMBIE2, ZOMBIE3, BOSS1, BOSS2 }
    /**
     * Constructor for the Zombie class
     * @param NPCSpawn the coordinates to spawn the NPC at
     * @param world the Box2D world to add the zombie to
     * @param type the type of character to spawn
     *           
     */
    public NPC(Vector2 NPCSpawn, World world,Type type) {
        super(world);
        this.world = world;
        speed = Constant.NPCSPEED;
        attackDamage = Constant.NPCDMG;
        maxhealth = Constant.NPCMAXHP;

        health = maxhealth;
        set(new Sprite(new Texture("NPC1.png")));
        body.setFixedRotation(true);
        body.setLinearDamping(50f);
        setCharacterPosition(NPCSpawn);
        body.setUserData("NPC");
        hitRange = (int) (Constant.NPCRANGE*getWidth()/30 - getWidth()*getHealth()/1200);
    }
  

    /**
     * Method to run away from player. 
     * @param delta the time between the start of the previous call and now
     *           Added LibGDX AI steering behaviour and wandering when player undetected.
     */
    @Override
    public void update(float delta) {
        //move according to velocity
        super.update(delta);
        
        if (Level.getPlayer().isVisible()) {
            // flee from player using gdx-ai flee functionality
            this.steeringBehavior = SteeringPresets.getFlee(this, Level.getPlayer());
            this.currentMode = SteeringState.FLEE; 
            // update direction to face away from the player
            //direction = - getDirectionTo(Level.getPlayer().getCenter());
            direction = -(this.vectorToAngle(this.getLinearVelocity()));
        } else { //player cannot be seen, so wander randomly
            this.steeringBehavior = SteeringPresets.getWander(this);
            this.currentMode = SteeringState.WANDER;
            // update direction to face direction of travel
            direction = -(this.vectorToAngle(this.getLinearVelocity()));
        }
        if(checkCollision()) {
        	this.setHealth(0);
        }
    }

    
    
    private Boolean	checkCollision() {
    	if(body.getUserData() == "escape") {
    		return true;
    	}else{
    		return false;
    	}
    }
}
