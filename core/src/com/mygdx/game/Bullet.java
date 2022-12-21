package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.physics.box2d.*;

import java.io.Serializable;

public class Bullet implements Serializable {
    private static Texture texture;
    private int damage = 10,speed,angle,fire_power,x,y;

    private Tank tank;
    private boolean collidable = false;
    public boolean isCollidable(){
        return  collidable;
    }

    public void setCollidable(boolean val){
        collidable = val;
    }
    private Body body;
    public int getDamage() {
        return damage;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }


    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    public int getAngle() {
        return angle;
    }

    public void setAngle(int angle) {
        this.angle = angle;
    }

    public int getFire_power() {
        return fire_power;
    }

    public void setFire_power(int fire_power) {
        this.fire_power = fire_power;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    private World world;
    public void setY(int y) {
        this.y = y;
    }
    Bullet(World world, int x, int y,Tank tank){
        BodyDef bodyDef = new BodyDef();
        bodyDef.position.set(x,y);
        bodyDef.type = BodyDef.BodyType.DynamicBody;
        this.setWorld(world);
        body = world.createBody(bodyDef);
        CircleShape circleShape= new CircleShape();
        circleShape.setRadius(2f);
        circleShape.setPosition(new Vector2(0,5));
        FixtureDef fixtureDef =  new FixtureDef();
        fixtureDef.shape =circleShape;
        fixtureDef.density=1;
        body.createFixture(fixtureDef);
        body.setUserData(this);
        setTank(tank);
    }
//    public Bullet(int damage, int speed, int angle, int fire_power, int x, int y) {
//        this.damage = damage;
//        this.speed = speed;
//        this.angle = angle;
//        this.fire_power = fire_power;
//        this.x = x;
//        this.y = y;
//        if(texture==null){
//            texture=new Texture("img/bullet.png");
//        }
//    }
    public void update(float deltatime){

    }


    public Body getBody() {
        return body;
    }

    public void setBody(Body body) {
        this.body = body;
    }

    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }

    public World getWorld() {
        return world;
    }

    public void setWorld(World world) {
        this.world = world;
    }

    public void clear(){
        this.world.destroyBody(this.getBody());
    }
}
