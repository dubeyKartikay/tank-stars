package com.mygdx.game;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

import java.nio.BufferOverflowException;

public class Bullet {
    private static Texture texture;
    private int damage,speed,angle,fire_power,x,y;

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

    public void setY(int y) {
        this.y = y;
    }

    public Bullet(int damage, int speed, int angle, int fire_power, int x, int y) {
        this.damage = damage;
        this.speed = speed;
        this.angle = angle;
        this.fire_power = fire_power;
        this.x = x;
        this.y = y;
        if(texture==null){
            texture=new Texture("img/bullet.png");
        }
    }
    public void update(float deltatime){

    }


}
