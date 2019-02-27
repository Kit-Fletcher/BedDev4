package com.geeselightning.zepr;

public class Wave {

    public int numberToSpawn;
    public Zombie.Type zombieType;

    /**
     * Constructor for a wave of zombies
     * @param numberToSpawn the number of zombies to spawn in the wave
     * @param zombieType the type of zombie to spawn in the wave
     * #changed:   Added this class to the game
     */
    public Wave(int numberToSpawn, Zombie.Type zombieType) {
        this.numberToSpawn = numberToSpawn;
        this.zombieType = zombieType;
    }
}
