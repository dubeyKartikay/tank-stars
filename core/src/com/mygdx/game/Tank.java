package com.mygdx.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Tank implements Serializable {
    private int fuel,health;

    private Body body;
    private PowerUps powerups;
    private Sprite sprite;
    private Body tankBody;
    public Tank(World world,int variety){
        this.health=100;
        powerups=new PowerUps();
        BodyDef tank1=new BodyDef();
        tank1.type= BodyDef.BodyType.DynamicBody;
        tank1.position.set(-55,8);
        CircleShape circleShape= new CircleShape();
        circleShape.setRadius(2f);
        FixtureDef fixturetank1=new FixtureDef();
        fixturetank1.density=100f;
        fixturetank1.friction=.1f; //0 to 1
        fixturetank1.restitution=0;  //bounce back after dropping to ground 0 to 1 if 1 then jumping infinte times same meter as dropped
        fixturetank1.shape=circleShape;
        setTankBody(world.createBody(tank1));
        getTankBody().createFixture(fixturetank1);

    }

    public void setSprite(int variety){
        if (variety == 0){
            sprite = new Sprite(new Texture(Gdx.files.internal("")));
        }
    }
    public void setHealth(int health) {
        this.health = health;
    }

    public int getHealth() {
        return health;
    }

    public void setFuel(int fuel) {
        this.fuel = fuel;
    }

    public int getFuel() {
        return fuel;
    }

    public void refuel(){
        this.fuel=100;
    }
    public void zerofuelOpponentPU(Tank tank){
        tank.setFuel(0);
    }

    public Body getTankBody() {
        return tankBody;
    }

    public void setTankBody(Body tankBody) {
        this.tankBody = tankBody;
    }
//    public void refill()
//    public void
}
