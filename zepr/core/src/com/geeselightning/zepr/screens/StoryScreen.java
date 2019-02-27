package com.geeselightning.zepr.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.geeselightning.zepr.Zepr;

public class StoryScreen implements Screen {

    private Zepr parent;
    private Stage stage;

    /**
     * Constructor for the story screen
     * @param zepr an instance of the main class of the game
     * #changed:   Added this class to the game
     */
    public StoryScreen(Zepr zepr) {
        // Constructor builds the gui of the menu screen.
        // parent allows the StoryScreen to reference the MyGdxGame class.
        parent = zepr;

        // The stage is the controller which will react to inputs from the user.
        this.stage = new Stage(new ScreenViewport());

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true); // Adds borders for the table.
        stage.addActor(table);

        // Importing the necessary assets for the button textures.
        Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

        // Writing the story.
        Label line1 = new Label("After a hard night of partying following the dreaded POPL exam, you wake up\n" +
                "to find yourself in the middle of town, your friends nowhere to be found.", skin);
        Label line2 = new Label("As you try and recall how you ended up here, you hear a \n" +
                "low rumbling sound coming from the alleyway near you.", skin);
        Label line3 = new Label("A horde of decaying zombies suddenly appear from the alleyways,\n" +
                "their clothes tattered, blood and bone sticking out of their bodies.", skin);
        Label line4 = new Label("Not soon after, the zombies notice you and charge towards you, \n" +
                "trampling over each other, their rumbles turning into screams and cries.\n" +
                "But in the spur of the movement, you suddenly realize...\n", skin);
        Label line5 = new Label("You forgot to hand in your SEPR Assessment!", skin, "subtitle");

        line1.setAlignment(Align.center);
        line2.setAlignment(Align.center);
        line3.setAlignment(Align.center);
        line4.setAlignment(Align.center);
        line5.setAlignment(Align.center);

        // Creating continue button.
        TextButton cont = new TextButton("Continue", skin);

        // Adding content to the table (screen).
        table.add(line1);
        table.row().pad(10, 40, 10, 40);
        table.add(line2);
        table.row().pad(10, 40, 10, 40);
        table.add(line3);
        table.row().pad(10, 40, 10, 40);
        table.add(line4);
        table.row().pad(10, 40, 10, 40);
        table.add(line5);
        table.row().pad(10,40,10,40);
        table.add(cont);

        // Defining actions for the continue button.
        cont.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Zepr.Location.SELECT);
            }
        });
    }

    /**
     * Show method which is run when the screen enters focus
     */
    @Override
    public void show() {
        // Send any input from the user to the stage.
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Draw the menu to the screen
     * @param delta the time between the start of the previous render() call and now
     */
    @Override
    public void render(float delta) {
        // Clears the screen to black.
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draws the stage.
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }

    /**
     * Resize method, called when the game window is resized
     * @param width the new window width
     * @param height the new window height
     */
    @Override
    public void resize(int width, int height) {
        // Update the screen when the window resolution is changed.
        stage.getViewport().update(width, height, true);
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

    /**
     * Dispose of the screen, clearing the memory
     */
    @Override
    public void dispose() {
        // Dispose of assets when they are no longer needed.
        stage.dispose();
    }
}
