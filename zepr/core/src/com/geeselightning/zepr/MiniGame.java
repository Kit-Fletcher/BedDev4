package com.geeselightning.zepr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Cursor.SystemCursor;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Queue;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import com.geeselightning.zepr.screens.TextScreen;

/**
 * @author grayh
 * #changed:   Added this class
 */
public class MiniGame implements Screen {
	
	private Zepr parent;
	
	private Stage stage;
	private Stage pause;
	private Table table;
	private boolean isPaused;
	private boolean pauseButton = false;
	private Skin skin = new Skin(Gdx.files.internal("skin/pixthulhu-ui.json"));
	private SpriteBatch spriteBatch;
	private String zombieTexture = "MiniZombie.png";
	private Sprite background;
	private Sprite crosshair;
	static long timer = 0;
	private long last = 0;
	private  Queue<MiniZombie> ZombieQueue = new Queue<>();
	private MiniZombie tempZombie;
	private BitmapFont font;
	private static String gunStatus = "Reloaded";
	static long trigger = 0;
	private boolean reloaded = false;
	private int kills = 0;
	private float rand = 0;
	private Pixmap pm = new Pixmap(Gdx.files.internal("blank.png"));
	
	private static boolean death = false;

	
	MiniGame(Zepr zepr) {
		
		Gdx.graphics.setWindowedMode(1280, 720);
		
		parent = zepr;
		this.isPaused = false;
		
		this.stage = new Stage(new ScreenViewport());
		this.pause = new Stage(new ScreenViewport());
		
		background = new Sprite(new Texture("MiniGameLevel.png"));
		crosshair = new Sprite(new Texture("Crosshair.png"));
		crosshair.setScale(2);
		
		// Create a table that fills the screen. Everything else will go inside this table.
        table = new Table();
        table.setFillParent(true);
        // table.setDebug(true); // Adds borders for the table.
        
        spriteBatch = new SpriteBatch();
        font = new BitmapFont();
       
		Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
        death = false;
	}
	
	/**
	 * adds a zombie to ZombieQueue 
	 * a random number (x) of zombies is "generated" at random increments of time (t)
	 * such that 0 < x < 10 and 0 < t < 5
	 */
    private void generateZombie() {
		
		timer = MiniZombie.timer();
		
		if(timer > last+rand) {
			rand = Math.round(Math.random());
			
			for(int z = 0; z <= rand; z++) {
				ZombieQueue.addFirst(new MiniZombie(zombieTexture));
			}
			rand = Math.round(Math.random()*0.5);
			last = timer;
		}
	}
	
	/**
	 * Manages the gun cooldown.
	 * When gun is "fired", trigger is set to value of timer at firing.
	 * Gun has cooled down when timer exceeds trigger+0.75
	 */
    private void gunStatus() {
		
		if(timer>trigger+0.75) {
			gunStatus = "Reloaded";
			reloaded = true;
		}
		else {
			gunStatus = "Reloading";
			reloaded = false;
		}
	}
	
	/**
	 * @param deathCond set by zombie when distance to player = 0
	 */
	static void playerDeath(boolean deathCond) {
		death = deathCond;
	}
	
	/**
	 * @param zombie1 zombie that is either visible or not
	 * @param minIndex
	 * 
	 * Determines whether the zombie is visible or occluded by other zombies.
	 * If partially occluded, method changes visible with of zombie so that occluded
	 * areas cannot be fired at.
	 */
    private void isVisible(MiniZombie zombie1, int minIndex) {
		
		Sprite zombie1_s = zombie1.zombie;
		float zombie1X = zombie1_s.getX();
		float zombie1Width = zombie1_s.getWidth();
		
		for(int j = minIndex+1; j < ZombieQueue.size; j++) {
			
			MiniZombie zombie2 = ZombieQueue.get(j);
			
			Sprite zombie2_s = zombie2.zombie;
			float zombie2X = zombie2_s.getX();
			float zombie2Width = zombie2_s.getWidth();
		 
			if((zombie2X>zombie1X) && (zombie2X<(zombie1X+zombie1Width))) {			
				zombie1.setVisibleWidth(zombie2X-zombie1X);			
			}	
			else if((zombie1X>zombie2X) && (zombie1X<(zombie2X+zombie2Width))) {	
				zombie1.setVisibleX(zombie2X+zombie2Width);			
			}
		}
	}
	

	@Override
	public void show() {
		
	}

	@Override
	public void render(float delta) {
		
		if(death) {
			parent.setScreen(new TextScreen(parent, "Kills: "+kills+"\nMINIGAME OVER"));
			Gdx.graphics.setWindowedMode(1366, 768);
			Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
			ZombieQueue.clear();
		}
		
		// Pause Menu
		
		if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE) || isPaused) {
			
			Gdx.graphics.setSystemCursor(SystemCursor.Arrow);
				
			isPaused = true;
			
            // Clears the screen to black.
            Gdx.gl.glClearColor(0f, 0f, 0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
            TextButton exit = new TextButton("Exit", skin);
            TextButton resume = new TextButton("Resume", skin);
            
            if (!pauseButton) {
                      	 
                table.clear();
                
                stage.addActor(table);
                table.center();
                table.add(resume).pad(10);
                table.row();
                table.add(exit);
                pauseButton = true;
            }
           
            // Input processor has to be changed back once unpaused.
            Gdx.input.setInputProcessor(stage);
            
            // Defining actions for the resume button.
            resume.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                	Gdx.graphics.setCursor(Gdx.graphics.newCursor(pm, 0, 0));
                    isPaused = false;
                    // Change input processor back
                    Gdx.input.setInputProcessor(stage);
                    pauseButton = false;
                }
            });

            // Defining actions for the exit button.
            exit.addListener(new ChangeListener() {
                @Override
                public void changed(ChangeEvent event, Actor actor) {
                	Gdx.graphics.setWindowedMode(1366, 768);
                    parent.changeScreen(Zepr.Location.SELECT);
                }
            });
            
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
        } else {
            // Clears the screen to black.
            Gdx.gl.glClearColor(0f, 0f, 0f, 1);
            Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
            
            table.clear();
            
            stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
            stage.draw();
            
            // 1) Generate Zombies incrementally
            // 2) Get the Reload status of gun
            // 3) Draw the gun status 
            
            gunStatus();
                   
            spriteBatch.begin();
            background.draw(spriteBatch);
            font.draw(spriteBatch, "Kills: " + kills + "\n" + gunStatus, 50, 650);
          
            this.generateZombie();
                    
            // Draws zombies onto stage in order of depth
            
            for(int i = 0; i < ZombieQueue.size; i++) {          	  	        	
            	tempZombie = ZombieQueue.get(i);         	
            	isVisible(tempZombie, i);     	
            	if(tempZombie.getDamage() && reloaded) {   		
            		kills++;
            		trigger = timer;		
            		ZombieQueue.removeIndex(i);
            	}
            	else {  		
            		tempZombie.render(spriteBatch);
            	}      	
            }	
            
            crosshair.setPosition((float)(Gdx.input.getX()-(0.5*crosshair.getWidth())), -(float) ((Gdx.input.getY()+0.5*crosshair.getHeight())-720));
            crosshair.draw(spriteBatch);
            spriteBatch.end();
        }
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
