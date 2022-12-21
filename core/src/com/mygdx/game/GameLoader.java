package com.mygdx.game;

import com.mygdx.Screens.MainMenu;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Calendar;

public class GameLoader {
//    public static Game Load(int slot){
//
//    }

    public static void Save(Game game,int slot){
        Game gamecurrent = game;
        try (ObjectOutputStream oos = new ObjectOutputStream(Files.newOutputStream(Paths.get("serializefile.txt")))) {
            gamecurrent.setSaveplace(slot);
            Calendar now = Calendar.getInstance();
            gamecurrent.setTime(new int[]{now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE)});
            game.addToSavedGames(slot,gamecurrent);
            oos.writeObject(gamecurrent);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
