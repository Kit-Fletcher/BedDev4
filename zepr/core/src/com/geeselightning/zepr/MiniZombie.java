package com.geeselightning.zepr;

import java.util.concurrent.ThreadLocalRandom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

//#changed:   Added this class
public class MiniZombie {
	
	private static long timer;
	private long last;
	private long collisionTimer;
	Sprite zombie;
	private int y = Gdx.graphics.getHeight()/2;
	private int width = Gdx.graphics.getWidth();
	private double direction = 10;
	private int inc = 0;
	private boolean collision = false;
	private float zombieWidth;
	private float zombieHeight;
	private float zombieX;
	private float zombieY;
	private int spawnX;
	private int mouseX;
	private int mouseY;
	private float initialHeight;
	private float initialWidth;
	private BitmapFont font;
	private int distance = 30;
	private double rand;
	
	MiniZombie(String texture) {
		
	
		zombie = new Sprite(new Texture(texture));
		font = new BitmapFont();
		initialHeight = zombie.getHeight();
		initialWidth = zombie.getWidth();
		this.spawn();		
	}
		
	/**
	 * @return
	 * 
	 * At 1 second increments, zombie direction is randomised.
	 * If collision is detected, zombie moves in opposite direction for 1.5 seconds
	 * Zombie size is constantly increased as distance decreases
	 */
	private Sprite move() {
		
		zombieX = zombie.getX();
		zombieY = zombie.getY();
		zombieWidth = zombie.getWidth();
		zombieHeight = zombie.getHeight();
		
		if(timer > collisionTimer + 1.5) {
			collision = false;
		}
		
		if (timer > last + 1) {	
			last = timer;
			distance -= 2;
			if(!collision) {
				direction = Math.random();
			}
			last = timer;	
			
		}
		
		if(direction > 0.5 && direction < 1) {
			if(this.collision()) {
				inc--;
			} else {
				inc++;
			}		
		} 
		else if(direction < 0.5) {
			if(this.collision()) {
				inc++;
			} else {
				inc--;
			}
		}
	
		if(distance <= 0) {
			MiniGame.playerDeath(true);
		}
		
		zombie.setSize(zombieWidth += 0.3, zombieHeight += 0.3);		
		zombie.setPosition(spawnX+inc, y-zombieWidth/2);	
		return zombie;
	}
	
	
	/**
	 * @return
	 * 
	 * Detects collisions between zombies and the edge of the window
	 */
	private boolean collision() {
	
		if(zombieX <= 150 || zombieX+(zombieWidth) >= width-150) {	
			collisionTimer = timer;
			this.collision = true;
		}
		return this.collision;
	}
	
	
	/**
	 * @param width
	 * 
	 * Sets the width of the zombie sprite: called in MiniGame
	 */
	void setVisibleWidth(float width) {
		this.zombieWidth = width;
	}
	
	/**
	 * @param x
	 * 
	 * Sets the x value of the zombie sprite: called in MiniGame
	 */
	void setVisibleX(float x) {
		this.zombieX = x;
	}
	
	/**
	 * @return
	 * 
	 * Returns true if zombie has been successfully hit.
	 */
	boolean getDamage() {
		
		mouseX = Gdx.input.getX();
		mouseY = -(Gdx.input.getY()-720);
	
		if(Gdx.input.isButtonPressed(Input.Buttons.LEFT)) {
			MiniGame.trigger = MiniGame.timer;
			return aimingAt();
		} else {
			return false;
		}		
	}
	
	/**
	 * Randomises the spawn location of the zombie to one of three gates in the stage
	 */
	public void spawn() {
		
		rand = Math.random();
		
		if(rand < 0.3){
			spawnX = 230;
		}
		else if(rand >= 0.3 && rand <= 0.6){
			spawnX = 610;
		}
		else if(rand > 0.6) {
			spawnX = 1000;
		}
		
		last = timer+1;
		zombie.setPosition(spawnX, y);	
		zombie.setSize(initialWidth, initialHeight);
	}
	
	/**
	 * @param spriteBatch spriteBatch to draw
	 * 
	 * Calls move method and draws zombie on stage alongside distance to player
	 */
	public void render(SpriteBatch spriteBatch) {
		
        this.move().draw(spriteBatch);
        font.draw(spriteBatch, Integer.toString(distance) + " meters", zombieX, zombieY+zombieHeight);
       
	}
	
	/**
	 * @return
	 * 
	 * Determines whether the player is aiming at zombie sprite with mouse
	 */
	private boolean aimingAt() {
		return (mouseX >= zombieX && mouseX <= zombieX+(zombieWidth) &&
                mouseY <= y+(zombieHeight) && mouseY >= zombieY);
	}
	
	
	/**
	 * @return
	 * 
	 * returns timer value from start of game
	 */
	static long timer() {
		timer = System.nanoTime()/1000000000;		
		return timer;
	}
	
}

