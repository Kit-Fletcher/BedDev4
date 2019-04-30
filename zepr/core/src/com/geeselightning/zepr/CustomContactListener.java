package com.geeselightning.zepr;


import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;
import com.geeselightning.zepr.Level;


/**
 * Class for handling Box2D collisions and collision events
 */
public class CustomContactListener implements ContactListener {

	@Override
	public void beginContact(Contact contact) {
		
		//Get the Box2D bodies that collided
		Body bodyA = contact.getFixtureA().getBody();
		Body bodyB = contact.getFixtureB().getBody();
		if((bodyA.getUserData() == "wall" && bodyB.getUserData() == "NPC") || (bodyA.getUserData() == "NPC" && bodyB.getUserData() == "wall")){
			//Sets the body user data to escape so the NPC can be removed
			if(bodyA.getUserData() == "NPC") {
				bodyA.setUserData("escape");
			}else {
				bodyB.setUserData("escape");
			}
		}
	}

	@Override
	public void endContact(Contact contact) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void preSolve(Contact contact, Manifold oldManifold) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void postSolve(Contact contact, ContactImpulse impulse) {
		// TODO Auto-generated method stub
		
	}
	


}
