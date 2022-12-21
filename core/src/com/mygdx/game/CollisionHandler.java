package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.Contact;
import com.badlogic.gdx.physics.box2d.ContactImpulse;
import com.badlogic.gdx.physics.box2d.ContactListener;
import com.badlogic.gdx.physics.box2d.Manifold;

public class CollisionHandler implements ContactListener {

    @Override
    public void beginContact(Contact contact) {
        System.out.println("Collision happened");
        int res = classifyCollision(contact);
        switch (res){
            case 0:
                handleBulletBulletCollision();
                break;
        }

    }

    private int classifyCollision(Contact contact){
        if (contact.getFixtureA().getBody().getUserData() == null || contact.getFixtureB().getBody().getUserData() == null){
            return -1;
        }
        System.out.println("A" + contact.getFixtureA().getBody().getUserData().getClass());
        System.out.println("B" + contact.getFixtureB().getBody().getUserData().getClass());
        if (contact.getFixtureA().getBody().getUserData().getClass() == Bullet.class && contact.getFixtureB().getBody().getUserData().getClass() == Bullet.class ){

            return 0;
        }
        return -1;
    }

    private void handleBulletBulletCollision(){

    }
    @Override
    public void endContact(Contact contact) {

    }

    @Override
    public void preSolve(Contact contact, Manifold oldManifold) {
        int res = classifyCollision(contact);
        switch (res){
            case 0:
                contact.setEnabled(false);
                break;
        }
    }

    @Override
    public void postSolve(Contact contact, ContactImpulse impulse) {

    }
}
