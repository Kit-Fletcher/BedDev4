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
import com.badlogic.gdx.graphics.Color;
import com.geeselightning.zepr.Player;
import com.geeselightning.zepr.Zepr;
import com.geeselightning.zepr.Zepr.Location;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;

public class SelectLevelScreen implements Screen {

    private Zepr parent;
    private Stage stage;
    private Label stageDescription;
    private Label characterDescription;
    private Location stageLink;
    private boolean playerSet = false;

    /**
     * Constructor for the select level screen
     * @param zepr an instance of the main class of the game
     * #changed:   Moved most of the code to here from show(). Also added code to allow selection
     *             of new levels and playable characters
     */
    public SelectLevelScreen(Zepr zepr) {

        parent = zepr;

        // The stage is the controller which will react to inputs from the user.
        this.stage = new Stage(new ScreenViewport());

        // Send any input from the user to the stage.
        Gdx.input.setInputProcessor(stage);
        
        // Importing the necessary assets for the button textures.
        Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));

        // Creating stage buttons.
        TextButton town      = new TextButton("Town", skin);
        TextButton halifax   = new TextButton("Halifax", skin);
        TextButton courtyard = new TextButton("Courtyard", skin);
        TextButton centralhall = new TextButton("Central Hall", skin);
        TextButton glasshouse = new TextButton ("Glasshouse", skin);
        TextButton constantine = new TextButton("Constantine", skin);
        
        // Creating character buttons.
        TextButton nerdy  = new TextButton("Nerdy",skin);
        TextButton sporty = new TextButton("Sporty",skin);
        TextButton Artsy  = new TextButton("Artsy",skin);

        // Creating other buttons.
        TextButton play = new TextButton("Play", skin);
        TextButton save = new TextButton("Save", skin);
        TextButton load = new TextButton("Load", skin);
        TextButton back = new TextButton("Back", skin);
        TextButton minigame = new TextButton("Mini Game", skin);

        // Creating stage descriptions.
        Label title = new Label("Choose a stage and character.", skin, "subtitle");
        final String townDescription      = "You wake up hungover in town to discover there is a zombie apocalypse.";
        final String halifaxDescription   = "You need to get your laptop with the work on it from your accomodation.";
        final String courtyardDescription = "You should go to Courtyard and get some breakfast.";
        final String centralhallDescription = "You have no choice but to pass through Central Hall on your way home";
        final String glasshouseDescription = "Perhaps a small tipple in Glasshouse before the final leg?";
        final String constantineDescription = "You've made it home, but are you safe yet...?";
        final String defaultDescription   = "Select a stage from the buttons above.";
        stageDescription = new Label(defaultDescription, skin);
        stageDescription.setWrap(true);
        stageDescription.setWidth(100);
        stageDescription.setAlignment(Align.center);

        // Creating character descriptions.
        final String nerdyDescription  = "Construct a mech suit for yourself so you can take more hits.";
        final String sportyDescription = "Work out so you run faster.";
        final String ArtsyDescription  = "\"Creative Juices\" for extra Damage.";
        final String defaultCharacterDescription = "Select a type of student from the buttons above.";
        characterDescription = new Label(defaultCharacterDescription,skin);
        characterDescription.setWrap(true);
        characterDescription.setWidth(100);
        characterDescription.setAlignment(Align.center);

        // Adding menu bar.
        Table menuBar = new Table();
        menuBar.setFillParent(true);
        //menuBar.setDebug(true); // Adds borders for the table.
        stage.addActor(menuBar);

        menuBar.top().left();
        menuBar.row();
        menuBar.add(back).pad(10);
        menuBar.add(save).pad(10);
        menuBar.add(load).pad(10);

        // Adding stage selector buttons.
        Table stageSelect = new Table();
        stageSelect.setFillParent(true);
        //stageSelect.setDebug(true); // Adds borders for the table.
        stage.addActor(stageSelect);

        stageSelect.center();

        stageSelect.row();
        stageSelect.add(title).colspan(3);

        stageSelect.row().pad(50,0,100,0);
        stageSelect.add(town).pad(10);
        stageSelect.add(halifax).pad(10);    
        stageSelect.add(centralhall).pad(10);
        stageSelect.row();
        stageSelect.add(courtyard).pad(10);  
        stageSelect.add(glasshouse).pad(10);
        stageSelect.add(constantine).pad(10);
   
        stageSelect.row();
        stageSelect.add(stageDescription).width(1000f).colspan(3);

        // Adding select character Buttons
        stageSelect.row().center();
        stageSelect.add(nerdy).pad(10);
        stageSelect.add(sporty).pad(10);
        stageSelect.add(Artsy).pad(10);

        stageSelect.row().center();
        stageSelect.add(characterDescription).width(1000f).colspan(3);

        // Adding play button at the bottom.
        Table bottomTable = new Table();
        bottomTable.setFillParent(true);
        //bottomTable.setDebug(true); // Adds borders for the table.
        stage.addActor(bottomTable);

        bottomTable.bottom();
        bottomTable.add(minigame).pad(10);
        bottomTable.add(play).pad(10).center();

        // Adding button logic.

        // Defining actions for the save button
        save.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                File f = new File("saveData.txt");
                FileOutputStream edit;
                try {
                    edit = new FileOutputStream(f);
                    byte[] lvl = (Integer.toString(Zepr.progress.ordinal())).getBytes();
                    edit.write(lvl);
                    edit.close();
                    Gdx.app.log("Save status", "Saved!");
                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                parent.changeScreen(Zepr.Location.SELECT);
            }
        });

        // Defining actions for the load button
        load.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                File f = new File("saveData.txt");
                BufferedReader br;
                try {
                    br = new BufferedReader(new FileReader(f));
                    String stage;
                    while ((stage = br.readLine()) != null) {
                        Gdx.app.log("Player stage progress", "Player is on stage " + stage);
                        Zepr.progress = Zepr.Location.values()[Integer.parseInt(stage)];
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                parent.changeScreen(Location.SELECT);
            }
        });

        // Defining actions for the back button.
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                parent.changeScreen(Location.MENU);
            }
        });

        // Defining actions for the town button.
        town.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                stageDescription.setText(townDescription);
                stageLink = Location.TOWN;
            }
        });

        if (Zepr.progress.ordinal() <= Location.TOWN.ordinal()) {
            halifax.setColor(Color.DARK_GRAY);
            halifax.getLabel().setColor(Color.DARK_GRAY);
        } else {
            // Defining actions for the halifax button.
            halifax.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    stageDescription.setText(halifaxDescription);
                    stageLink = Location.HALIFAX;
                }
            });
        }
        
        if (Zepr.progress.ordinal() <= Location.HALIFAX.ordinal()) {
            centralhall.setColor(Color.DARK_GRAY);
            centralhall.getLabel().setColor(Color.DARK_GRAY);
        } else {
            // Defining actions for the centralhall button.
        	centralhall.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    stageDescription.setText(centralhallDescription);
                    stageLink = Location.CENTRALHALL;
                }
            });
        }

        if (Zepr.progress.ordinal() <= Location.CENTRALHALL.ordinal()) {
            courtyard.setColor(Color.DARK_GRAY);
            courtyard.getLabel().setColor(Color.DARK_GRAY);
        } else {
            // Defining actions for the courtyard button.
            courtyard.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    stageDescription.setText(courtyardDescription);
                    stageLink = Location.COURTYARD;
                }
            });
        }
       
        if (Zepr.progress.ordinal() <= Location.COURTYARD.ordinal()) {
            glasshouse.setColor(Color.DARK_GRAY);
            glasshouse.getLabel().setColor(Color.DARK_GRAY);
        } else {
            // Defining actions for the glasshouse button.
        	glasshouse.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    stageDescription.setText(glasshouseDescription);
                    stageLink = Location.GLASSHOUSE;
                }
            });
        }
        
        if (Zepr.progress.ordinal() <= Location.GLASSHOUSE.ordinal()) {
            constantine.setColor(Color.DARK_GRAY);
            constantine.getLabel().setColor(Color.DARK_GRAY);
        } else {
            // Defining actions for the constantine button.
        	constantine.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                    stageDescription.setText(constantineDescription);
                    stageLink = Location.CONSTANTINE;
                }
            });
        }

        //Defining actions for the nerdy button.
        nerdy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                characterDescription.setText(nerdyDescription);
                Player.setType(Player.PlayerType.NERDY);
                playerSet = true;
            }
        });
        //Defining actions for the sporty button.
        sporty.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                characterDescription.setText(sportyDescription);
                Player.setType(Player.PlayerType.SPORTY);
                playerSet = true;
            }
        });
        //Defining actions for the artsy button.
        Artsy.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                characterDescription.setText(ArtsyDescription);
                Player.setType(Player.PlayerType.ARTSY);
                playerSet = true;
            }
        });

        // Defining actions for the play button.
        play.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                if (stageLink != null && playerSet) {
                    parent.changeScreen(stageLink);
                }
            }
        });
        
        // Defining actions for the MiniGame button
        
        minigame.addListener(new ChangeListener() {
        	@Override
        	public void changed(ChangeEvent event, Actor actor) {
        		parent.changeScreen(Location.MINIGAME);
        	}
        	
        });
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
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // Draws the stage.
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 60f));
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
