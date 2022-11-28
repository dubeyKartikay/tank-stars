package com.mygdx.game;
import java.util.HashMap;

public class PowerUps {
    private int sheildlvl;
    private int fueladdlvl;
    private int fuelsublvl;
    private int damagelvl;
    private HashMap<Integer,Integer>sheildMap=new HashMap<Integer,Integer>();
    private HashMap<Integer,Integer>fueladdMap=new HashMap<Integer,Integer>();
    private HashMap<Integer,Integer>fuelsubMap=new HashMap<Integer,Integer>();
    private HashMap<Integer,Integer>damageMap=new HashMap<Integer,Integer>();
    PowerUps(){
        this.sheildMap.put(1,1000);
        this.sheildMap.put(0,0);
        this.sheildMap.put(2,1250);
        this.sheildMap.put(3,1500);
        this.fueladdMap.put(0,0);
        this.fueladdMap.put(1,50);
        this.fueladdMap.put(2,100);
        this.fuelsubMap.put(0,0);
        this.fuelsubMap.put(1,50);
        this.fuelsubMap.put(2,100);
        this.damageMap.put(0,0);
        this.damageMap.put(1,1000);
        this.damageMap.put(2,2000);
    }
    public void updatelvl(String str,int lvl){
        if(str.equals("SHIELD")){
            this.setSheildlvl(lvl);
        }
        else if(str.equals("FUELADD")){
            this.setFueladdlvl(lvl);
        }else if(str.equals("FUELSUB")){
            this.setFuelsublvl(lvl);
        }else if(str.equals("DAMAGE")){
            this.setDamagelvl(lvl);
        }
    }


    public int getSheildlvl() {
        return sheildlvl;
    }

    public void setSheildlvl(int sheildlvl) {
        this.sheildlvl = sheildlvl;
    }

    public int getFueladdlvl() {
        return fueladdlvl;
    }

    public void setFueladdlvl(int fueladdlvl) {
        this.fueladdlvl = fueladdlvl;
    }

    public int getFuelsublvl() {
        return fuelsublvl;
    }

    public void setFuelsublvl(int fuelsublvl) {
        this.fuelsublvl = fuelsublvl;
    }

    public int getDamagelvl() {
        return damagelvl;
    }

    public void setDamagelvl(int damagelvl) {
        this.damagelvl = damagelvl;
    }
    public int getLvlValue(String str){
        if(str.equals("SHIELD")){
            return sheildMap.get(this.getSheildlvl());
        }
        else if(str.equals("FUELADD")){
            return fueladdMap.get(this.getFueladdlvl());
        }else if(str.equals("FUELSUB")){
            return fuelsubMap.get(this.getFuelsublvl());
        }else if(str.equals("DAMAGE")){
            return damageMap.get(this.getDamagelvl());
        }
        return 0;
    }
}
