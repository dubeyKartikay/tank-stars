package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;

public class Player implements Serializable {
    private Tank tank;
    private PowerUps[] powerUps;
    private int health;
    private World world;
    private int tankIndex;
    private String side;


    public void setTankIndex(int tankIndex){
        this.tankIndex = tankIndex;
    }

    public void setSide(String side) {
        this.side = side;
    }

    public String getSide() {
        return side;
    }

    public void initTank(World world,int x, int y){
        this.setTank(new Tank(world,tankIndex,side, x, y));
    }


    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }
}
