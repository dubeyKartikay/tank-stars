package com.mygdx.game;

import com.badlogic.gdx.physics.box2d.World;

import java.io.Serializable;

public class Player implements Serializable {
    private Tank tank;
    private PowerUps[] powerUps;
    private int health;

    private World world;
    private int tankIndex;

    public void setTankIndex(int tankIndex){
        this.tankIndex = tankIndex;
    }

    public void initTank(World world){
        this.setTank(new Tank(world,tankIndex));
    }


    public Tank getTank() {
        return tank;
    }

    public void setTank(Tank tank) {
        this.tank = tank;
    }
}
