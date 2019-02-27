package com.geeselightning.zepr.tests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.geeselightning.zepr.*;
import com.geeselightning.zepr.powerups.PowerUpHeal;
import com.geeselightning.zepr.powerups.PowerUpImmunity;
import com.geeselightning.zepr.powerups.PowerUpSpeed;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;


@RunWith(GdxTestRunner.class)
public class PowerUpTest {

    @Test
    // Test 4.1
    public void powerUpHealthAddsHPToPlayer() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), new Vector2(0, 0), world);
        PowerUpHeal heal = new PowerUpHeal(null, player);
        player.takeDamage(50);
        double originalHealth = player.getHealth();
        heal.activate();
        heal.update(1);
        assertEquals("Heal powerup should give the player more hit points.",
                originalHealth + Constant.HEALUP, player.getHealth(), 0.1);
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 4.1.1
    public void powerUpHealthCapsAtMaxHP() {
        World world = new World(new Vector2(0,0), true);
        Player player = new Player(new Texture("player01.png"), new Vector2(0, 0), world);
        PowerUpHeal heal = new PowerUpHeal(null, player);
        double originalHealth = player.getHealth();
        heal.activate();
        heal.update(1);
        assertEquals("Heal powerup should cap at max hit points.",
                originalHealth, player.getHealth(), 0.01);
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 4.2
    public void powerUpSpeedIncreasePlayersSpeed() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), new Vector2(0, 0), world);
        PowerUpSpeed speed = new PowerUpSpeed(null, player);
        float originalSpeed = player.getSpeed();
        speed.activate();
        assertEquals("Speed powerup should increase the Players speed.", originalSpeed + Constant.SPEEDUP,
                player.getSpeed(), 0.1);
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 4.3
    public void powerUpSpeedDeactivatesAfter10s() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), new Vector2(0, 0), world);
        PowerUpSpeed speed = new PowerUpSpeed(null, player);
        double originalSpeed = player.getSpeed();
        speed.activate();
        speed.update(11);
        assertEquals("Speed should go back to the original speed after 10s.", originalSpeed, player.getSpeed(), 0.1);
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 4.4
    public void powerUpSpeedDoesNotDeactiveBefore10s() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), new Vector2(0, 0), world);
        PowerUpSpeed speed = new PowerUpSpeed(null, player);
        double originalSpeed = player.getSpeed();
        speed.activate();
        speed.update(9);
        assertNotEquals("Speed powerup should increase the Players speed.", originalSpeed,
                player.getSpeed());
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 4.5
    public void powerUpSpeedDeactivateMethodResetsPlayerSpeed() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), new Vector2(0, 0), world);
        PowerUpSpeed speed = new PowerUpSpeed(null, player);
        double originalSpeed = player.getSpeed();
        speed.activate();
        speed.update(5);
        speed.deactivate();
        assertEquals("Player speed is reset if deactivate is used on the powerup.", originalSpeed,
                player.getSpeed(), 0.1);
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 4.6
    public void playerCannotPickUpFarAwayPowerUp() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), new Vector2(100, 100), world);
        PowerUpHeal powerUp = new PowerUpHeal(null, player);
        powerUp.setPosition(0,0);
        assertFalse("Player cannot pickup a power up if it is not touching it.", powerUp.overlapsPlayer());
        player.dispose();
        world.dispose();
    }

    @Test
    //Test 4.7
    public void playerCanPickUpClosePowerUp() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), new Vector2(31, 31), world);
        PowerUpHeal powerUp = new PowerUpHeal(null, player);
        powerUp.setPosition(0,0);
        assertTrue("Player can pickup a power up if it is touching it.", powerUp.overlapsPlayer());
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 4.8
    public void powerUpImmunityStopsThePlayerTakingDamage() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), new Vector2(0, 0), world);
        PowerUpImmunity immunity = new PowerUpImmunity(null, player);
        immunity.activate();
        double originalHealth = player.getHealth();
        player.takeDamage(30);
        assertEquals("Player health before and after taking damage should remain the same when immunity is activated.",
                originalHealth, player.getHealth(), 0.1);
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 4.9
    public void powerUpImmunityDeactivatesAfter5s() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), new Vector2(0, 0), world);
        PowerUpImmunity immunity = new PowerUpImmunity(null, player);
        double originalHealth = player.getHealth();
        immunity.activate();
        player.takeDamage(40);
        immunity.update(6);
        player.takeDamage(30);
        assertEquals("Player should take 30 damage after the immunity expires", originalHealth - 30,
                player.getHealth(), 0.1);
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 4.10
    public void powerUpImmunityDeactivateMethodCancelsImmunity() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), new Vector2(0, 0), world);
        PowerUpImmunity immunity = new PowerUpImmunity(null, player);
        double originalHealth = player.getHealth();
        immunity.activate();
        immunity.update(2);
        player.takeDamage(40);
        immunity.deactivate();
        player.takeDamage(30);
        assertEquals("Player should take 30 damage after immunity is deactivated.", originalHealth-30,
                player.getHealth(), 0.1);
        player.dispose();
        world.dispose();
    }
}
