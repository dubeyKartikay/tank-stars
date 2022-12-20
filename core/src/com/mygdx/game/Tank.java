package com.mygdx.game;

import com.badlogic.gdx.scenes.scene2d.ui.Table;

import java.io.Serializable;

public class Tank implements Serializable {
    private int fuel,health;
    PowerUps powerups;
    public Tank(){
        this.health=100;
        powerups=new PowerUps();
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
//    public void refill()
//    public void
}
