package com.wanli.webapp.dao;

import com.wanli.webapp.entities.Game;

import java.util.List;

public interface GameDAO {
    Game getGame(int id);
    List<Game> getAllGames();
    List<Game> getUserGames(int uid);
    List<Game> fuzzyQuery(String gname);
    boolean insertGame(Game game);
    boolean updateGame(Game game);
    boolean deleteGame(int id);
}
