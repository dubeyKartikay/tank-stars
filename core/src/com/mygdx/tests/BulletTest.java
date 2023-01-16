package com.mygdx.tests;

import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.World;
import com.mygdx.game.Bullet;
import com.mygdx.game.Tank;
import org.junit.Test;

import static junit.framework.Assert.assertFalse;
import static junit.framework.TestCase.assertEquals;
import static junit.framework.TestCase.assertTrue;


class BulletTest {

    @Test
    void testisCollidable() {
        Bullet b = new Bullet(new World(new Vector2(0,0),false),0,0,new Tank(new World(new Vector2(0,0),false),5,"d",5,5));
        b.setCollidable(true);
        assertTrue(b.isCollidable());
    }

    @Test
    void testsetCollidable() {
        Bullet b = new Bullet(new World(new Vector2(0,0),false),0,0,new Tank(new World(new Vector2(0,0),false),5,"d",5,5));
        b.setCollidable(false);
        assertFalse(b.isCollidable());
    }

    @Test
    void testgetSpeed() {
        Bullet b = new Bullet(new World(new Vector2(0,0),false),0,0,new Tank(new World(new Vector2(0,0),false),5,"d",5,5));
        b.setSpeed(100);
        assertEquals(b.getSpeed(),100);
    }

    @Test
    void testsetSpeed() {
        Bullet b = new Bullet(new World(new Vector2(0,0),false),0,0,new Tank(new World(new Vector2(0,0),false),5,"d",5,5));
        b.setSpeed(12);
        assertEquals(b.getSpeed(),12);
    }
}