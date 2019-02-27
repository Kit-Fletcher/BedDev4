package com.geeselightning.zepr.tests;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.geeselightning.zepr.Constant;
import com.geeselightning.zepr.Player;
import com.geeselightning.zepr.Zombie;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.junit.Assert.*;

@RunWith(GdxTestRunner.class)
public class ZombieTest {



    @Test
    // Test 3.1
    public void zombieDoesNoDamageToPlayerWhenAtMaxRange() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), Constant.ORIGIN, world);

        Zombie zombie = new Zombie(new Vector2(player.getCenter().x, player.getCenter().y - Constant.ZOMBIERANGE), world, Zombie.Type.ZOMBIE1);
        double originalHealth = player.getHealth();
        zombie.attack(player, 0);

        assertEquals("Player on the edge of range should not take damage when the zombie attacks.",
                player.getHealth(), originalHealth, 0.1);
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 3.2
    public void zombieDoesDamageToPlayerWhenInRange() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), Constant.ORIGIN, world);

        Zombie zombie = new Zombie(new Vector2(player.getCenter().x, player.getCenter().y - Constant.ZOMBIERANGE + 5), world, Zombie.Type.ZOMBIE1);
        double originalHealth = player.getHealth();
        zombie.attack(player, 0);

        assertNotEquals("Player within range should take damage when the zombie attacks.",
                player.getHealth(), originalHealth, 0.1);
        player.dispose();
        world.dispose();
    }


    @Test
    // Test 3.3
    public void zombieDoesNoDamageToPlayerOutOfRange() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), Constant.ORIGIN, world);

        Zombie zombie = new Zombie(new Vector2(player.getCenter().x, player.getCenter().y - 100), world, Zombie.Type.ZOMBIE1);
        double originalHealth = player.getHealth();
        zombie.attack(player, 0);

        assertEquals("Player outside of range should not take damage when the zombie attacks.",
                player.getHealth(), originalHealth, 0.1);
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 3.4
    public void zombieCannotAttackBeforeCooldownComplete() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), Constant.ORIGIN, world);

        Zombie zombie = new Zombie(new Vector2(player.getCenter().x, player.getCenter().y ), world, Zombie.Type.ZOMBIE1);
        double originalHealth = player.getHealth();
        zombie.attack(player, 0);
        zombie.attack(player, 0);

        assertEquals("Player should only have taken one hit if attacked again before cooldown complete.",
                originalHealth - Constant.ZOMBIEDMG, player.getHealth(), 0.1);
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 3.5
    public void zombieCanAttackAfterCooldownComplete() {
        World world = new World(new Vector2(0, 0), true);
        Player player = new Player(new Texture("player01.png"), Constant.ORIGIN, world);

        Zombie zombie = new Zombie(new Vector2(player.getCenter().x, player.getCenter().y ), world, Zombie.Type.ZOMBIE1);
        double originalHealth = player.getHealth();
        zombie.attack(player, 0);
        // zombie will not attack this go so has to be called a third time
        zombie.attack(player, Constant.ZOMBIEHITCOOLDOWN + 1);
        zombie.attack(player, 0);

        assertEquals("Player should have taken two hits if attacked again after cooldown complete.",
                originalHealth - (2 * Constant.ZOMBIEDMG), player.getHealth(), 0.1);
        player.dispose();
        world.dispose();
    }

    @Test
    // Test 3.6
    public void differentZombieTypesHaveDifferentHealthStats() {
        World world = new World(new Vector2(0, 0), true);

        Zombie zombie1 = new Zombie(Constant.ORIGIN, world, Zombie.Type.ZOMBIE1);
        Zombie zombie2 = new Zombie(Constant.ORIGIN, world, Zombie.Type.ZOMBIE2);
        Zombie zombie3 = new Zombie(Constant.ORIGIN, world, Zombie.Type.ZOMBIE3);

        assertNotEquals("Zombie1 and Zombie2 types should have different health stats",
                zombie1.getHealth(), zombie2.getHealth());
        assertNotEquals("Zombie2 and Zombie3 types should have different health stats",
                zombie2.getHealth(), zombie3.getHealth());

        zombie1.dispose();
        zombie2.dispose();
        zombie3.dispose();
        world.dispose();
    }

    @Test
    // Test 3.7
    public void differentZombieTypesHaveDifferentSpeedStats() {
        World world = new World(new Vector2(0, 0), true);

        Zombie zombie1 = new Zombie(Constant.ORIGIN, world, Zombie.Type.ZOMBIE1);
        Zombie zombie2 = new Zombie(Constant.ORIGIN, world, Zombie.Type.ZOMBIE2);
        Zombie zombie3 = new Zombie(Constant.ORIGIN, world, Zombie.Type.ZOMBIE3);

        assertNotEquals("Zombie1 and Zombie2 types should have different speed stats",
                zombie1.getSpeed(), zombie2.getSpeed());
        assertNotEquals("Zombie2 and Zombie3 types should have different speed stats",
                zombie2.getSpeed(), zombie3.getSpeed());
        assertNotEquals("Zombie3 and Zombie1 types should have different speed stats",
                zombie3.getSpeed(), zombie1.getSpeed());

        zombie1.dispose();
        zombie2.dispose();
        zombie3.dispose();
        world.dispose();
    }

    @Test
    // Test 3.8
    public void differentZombieTypesHaveDifferentAttackStats() {
        World world = new World(new Vector2(0, 0), true);

        Zombie zombie1 = new Zombie(Constant.ORIGIN, world, Zombie.Type.ZOMBIE1);
        Zombie zombie2 = new Zombie(Constant.ORIGIN, world, Zombie.Type.ZOMBIE2);
        Zombie zombie3 = new Zombie(Constant.ORIGIN, world, Zombie.Type.ZOMBIE3);

        assertNotEquals("Zombie1 and Zombie2 types should have different attack stats",
                zombie1.getAttackDamage(), zombie2.getAttackDamage());
        assertNotEquals("Zombie2 and Zombie3 types should have different attack stats",
                zombie2.getAttackDamage(), zombie3.getAttackDamage());
        assertNotEquals("Zombie3 and Zombie1 types should have different attack stats",
                zombie3.getAttackDamage(), zombie1.getAttackDamage());

        zombie1.dispose();
        zombie2.dispose();
        zombie3.dispose();
        world.dispose();
    }
}
