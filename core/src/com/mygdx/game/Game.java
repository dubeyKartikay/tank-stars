package com.mygdx.game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.mygdx.Screens.GameScreen;
import com.mygdx.Screens.MainMenu;

public class Game  {
    private Music bgMusic;
    GameScreen currScreen;
    Game(){
        bgMusic  = Gdx.audio.newMusic(Gdx.files.internal("Music/lofiINI.wav"));
        currScreen = new MainMenu(this);

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
    public void render(){
        currScreen.render(Gdx.graphics.getDeltaTime());
    }
    public GameScreen getCurrScreen() {
        return currScreen;
    }

    public void setCurrScreen(GameScreen currScreen) {
        this.currScreen = currScreen;
    }
}
