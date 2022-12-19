package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.utils.ScreenUtils;
import com.mygdx.Screens.GameScreen;
import com.mygdx.Screens.LoadingScreen;
import com.mygdx.Screens.MainMenu;

public class  Game  {
    private Music bgMusic;
    GameScreen currScreen;

    private GameScreen overlayScreen;
    boolean isPaused = false;


    Game(){
        bgMusic  = Gdx.audio.newMusic(Gdx.files.internal("Music/lofiINI.wav"));
        currScreen = new LoadingScreen(this);
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
}
