package com.geeselightning.zepr;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.World;
import com.badlogic.gdx.utils.Array;
import com.geeselightning.zepr.Zombie.Type;

public class NPC extends Character {

    /**
     * Constructor for the NPC class
     * @param NPCSpawn the coordinates to spawn the NPC at
     * @param world the Box2D world to add the zombie to
     * @param type the type of character to spawn
     *           
     */
    public NPC(Vector2 NPCSpawn, World world,Type type) {
        super(world);
        speed = Constant.NPCSPEED;
        attackDamage = Constant.NPCDMG;
        maxhealth = Constant.NPCMAXHP;

        health = maxhealth;
        set(new Sprite(new Texture("NPC1.png")));
        body.setFixedRotation(true);
        body.setLinearDamping(50f);
        setCharacterPosition(NPCSpawn);
        body.setUserData("NPC");
    }

    /**
     * Update method, involves steering behaviour.
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
            direction = -(this.vectorToAngle(this.getLinearVelocity()));
        } else { //player cannot be seen, so wander randomly
            this.steeringBehavior = SteeringPresets.getWander(this);
            this.currentMode = SteeringState.WANDER;
            // update direction to face direction of travel
            direction = -(this.vectorToAngle(this.getLinearVelocity()));
        }
        //NPC removed upon reaching an exit wall
        if(checkCollision()) {
        	this.setHealth(0);
        }
    }

    
    //Checks if the NPC has hit a wall and should therefore be removed
    private Boolean	checkCollision() {
    	if(body.getUserData() == "escape") {
    		return true;
    	}else{
    		return false;
    	}
    }
}
