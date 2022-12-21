package com.mygdx.game;

import com.mygdx.Screens.MainMenu;

public class GameLoader {
    public static void resetGame(Game game){
        game.setPlayer1(new Player());
        game.setPlayer2(new Player());
        game.setCurrScreen(new MainMenu(game));
    }
}
