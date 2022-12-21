package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.Screens.GameScreen;
import com.mygdx.Screens.LoadingScreen;
import com.mygdx.Screens.MainMenu;

import java.io.Serializable;
import java.util.ArrayList;

public class Game implements Serializable {
    private Music bgMusic;
    private int saveplace;
    private ArrayList<Game>savedgames;
    private Player player1;
    private Player player2;
    GameScreen currScreen;
    private int[] time =new int[]{0,0};
    public ArrayList<Game> getSavedgames() {
        return savedgames;
    }

    public void setSavedgames(ArrayList<Game> savedgames) {
        this.savedgames = savedgames;
    }

    public void setSaveplace(int saveplace) {
        this.saveplace = saveplace;
    }

    public int getSaveplace() {
        return saveplace;
    }

    private GameScreen overlayScreen;
    boolean isPaused = false;

    Game(){
        savedgames=new ArrayList<Game>(2);
        bgMusic  = Gdx.audio.newMusic(Gdx.files.internal("Music/lofiINI.wav"));
        currScreen = new LoadingScreen(this);
        player1 = new Player();
        player2 = new Player();
    }
    public void refreshgame(){

    }
    public void setTime(int[] time) {
        this.time = time;
    }

    public int[] getTime() {
        return time;
    }

    public void playMusic(){
        if (!bgMusic.isPlaying()){
            bgMusic.play();
        }
    }
    public void pauseMusic(){
        if (bgMusic.isPlaying()){
            bgMusic.pause();
        }
    }

    public boolean isMusicPlaying(){
        return bgMusic.isPlaying();
    }
    public void render(){

        float delta  = Gdx.graphics.getDeltaTime();
        ScreenUtils.clear(1, 0, 0, 1);
        if (isPaused()){
            overlayScreen.setThisAsInputStream();
            overlayScreen.render(Gdx.graphics.getDeltaTime());
        }else {
            currScreen.setThisAsInputStream();
            currScreen.render(delta);
        }
    }
    public GameScreen getCurrScreen() {
        return currScreen;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setCurrScreen(GameScreen currScreen) {
        this.currScreen = currScreen;
    }

    public GameScreen getOverlayScreen() {
        return overlayScreen;
    }

    public void setOverlayScreen(GameScreen overlayScreen) {
        this.overlayScreen = overlayScreen;
    }

    public void destroyOverlay(){
        this.overlayScreen = null;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }
}
