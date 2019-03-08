package com.geeselightning.zepr;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;

public class NPC extends Character {

    private int hitRange;
   
    /**
     * Constructor for the Zombie class
     * @param NPCSpawn the coordinates to spawn the NPC at
     * @param world the Box2D world to add the zombie to
     * @param type the type of character to spawn
     *           
     */
    public NPC(Vector2 NPCSpawn, World world, Type type) {
        super(world);

        speed = Constant.NPCSPEED;
        attackDamage = Constant.NPCDMG;
        maxhealth = Constant.NPCMAXHP;

        health = maxhealth;

        body.setFixedRotation(true);
        body.setLinearDamping(50f);
        setCharacterPosition(NPCSpawn);

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
            // seek out player using gdx-ai seek functionality
            this.steeringBehavior = SteeringPresets.getSeek(this, Level.getPlayer());
            this.currentMode = SteeringState.SEEK;
            // update direction to face away from the player
            direction = - getDirectionTo(Level.getPlayer().getCenter());
        } else { //player cannot be seen, so wander randomly
            this.steeringBehavior = SteeringPresets.getWander(this);
            this.currentMode = SteeringState.WANDER;
            // update direction to face direction of travel
            direction = -(this.vectorToAngle(this.getLinearVelocity()));
        }
    }
}
