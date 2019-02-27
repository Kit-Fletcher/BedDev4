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
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.geeselightning.zepr.Zepr;

public class MenuScreen implements Screen {

    private Zepr parent;
    private Stage stage;
    private Label titleLabel;

    /**
     * Constructor for the menu screen
     * @param zepr an instance of the main class of the game
     */
    public MenuScreen(Zepr zepr) {
        // Constructor builds the gui of the menu screen.
        // parent allows the MenuScreen to reference the MyGdxGame class.
        parent = zepr;

        // The stage is the controller which will react to inputs from the user.
        stage = new Stage(new ScreenViewport());

        // Create a table that fills the screen. Everything else will go inside this table.
        Table table = new Table();
        table.setFillParent(true);
        //table.setDebug(true); // Adds borders for the table.
        stage.addActor(table);

        // Importing the necessary assets for the button textures.
        Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

        // Creating a title.
        titleLabel = new Label( "Zombie Engineering Project", skin, "subtitle");

        // Creating buttons.
        TextButton start = new TextButton("Start", skin);
        TextButton exit = new TextButton("Exit", skin);

        // Adding content to the table (screen).
        table.add(titleLabel);
        table.row().pad(10, 40, 10, 40);
        table.add(start).fillX().uniformX();
        table.row().pad(10, 40, 10, 40);
        table.add(exit).fillX().uniformX();

        // Defining actions for the start button.
        start.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Zepr.Location.STORY);
            }
        });

        // Defining actions for the exit button.
        exit.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });
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

    /**
     * Show method which is run when the screen enters focus
     * #changed:   Moved most of the code from here to the constructor
     */
    @Override
    public void show() {
        // Send any input from the user to the stage.
        Gdx.input.setInputProcessor(stage);
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
     * Dispose the of the menu screen instance, clearing the memory
     */
    @Override
    public void dispose() {
        // Dispose of assets when they are no longer needed.
        stage.dispose();
    }
}

