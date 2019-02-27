package com.geeselightning.zepr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Sound;
import com.geeselightning.zepr.Zepr;

public class LoadingScreen implements Screen {

    private Zepr parent;

    /**
     * Constructor for the loading screen.
     * @param zepr an instance of the main class of the game
     */
    public LoadingScreen(Zepr zepr) {
        parent = zepr;

        loadSounds();
    }

    /**
     * Sounds for the game are loaded here.
     * #changed:   Added this method
     */
    public static void loadSounds() {
        Zepr.manager = new AssetManager();
        Zepr.manager.load("Quack.wav", Sound.class);
        Zepr.manager.load("zombie_take_dmg.wav", Sound.class);
        Zepr.manager.finishLoading();
    }

    @Override
    public void show() {
        // TODO Auto-generated method stub
    }

    /**
     * Draw the menu to the screen
     * @param delta the time between the start of the previous render() call and now
     */
    @Override
    public void render(float delta) {
        // Clears the screen to black.
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        // Changes to the menu screen.
        parent.changeScreen(Zepr.Location.MENU);
    }

    @Override
    public void resize(int width, int height) {
        // TODO Auto-generated method stub
    }

    @Override
    public void pause() {
        // TODO Auto-generated method stub
    }

    @Override
    public void resume() {
        // TODO Auto-generated method stub
    }

    @Override
    public void hide() {
        // TODO Auto-generated method stub
    }

    @Override
    public void dispose() {
        // TODO Auto-generated method stub
    }
}