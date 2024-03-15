package com.wanli.webapp.dao.Impl;

import com.wanli.webapp.dao.BaseDAO;
import com.wanli.webapp.dao.GameDAO;
import com.wanli.webapp.entities.Game;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GameDAOImpl extends BaseDAO implements GameDAO {
    @Override
    public Game getGame(int id) {
        return executeQueryAndGetFirstResult("SELECT gid, gname, imageUrl, publisher, uname, rdate, description FROM games, users WHERE games.publisher = users.uid AND gid = ?", new Object[]{id});
    }

    @Override
    public List<Game> getAllGames() {
        return executeQueryAndGetList("SELECT gid, gname, imageUrl, publisher, uname, rdate, description FROM games, users WHERE games.publisher = users.uid");
    }

    @Override
    public List<Game> getUserGames(int uid) {
        return executeQueryAndGetList("SELECT games.gid, gname, imageUrl, publisher, uname, rdate, description FROM games, users, user_game WHERE games.publisher = users.uid AND user_game.uid = ? AND games.gid = user_game.gid", new Object[]{uid});
    }

    @Override
    public List<Game> fuzzyQuery(String gname) {
        return executeQueryAndGetList("SELECT gid, gname, imageUrl, publisher, uname, rdate, description FROM games, users WHERE games.publisher = users.uid AND gname LIKE ?", new Object[]{"%" + gname + "%"});
    }

    private Game createGame(ResultSet rs) throws SQLException {
        Game game = new Game();
        game.setGid(rs.getInt("gid"));
        game.setGname(rs.getString("gname"));
        game.setImage(rs.getString("imageUrl"));
        game.setPublisher(rs.getInt("publisher"));
        game.setPubName(rs.getString("uname"));
        game.setRdate(rs.getDate("rdate"));
        game.setDescription(rs.getString("description"));
        return game;
    }

    private List<Game> executeQueryAndGetList(String query, Object... params) {
        List<Game> games = new ArrayList<>();
        try (Connection connection = getConnection();
             PreparedStatement ps = prepareStatement(connection, query, params);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                games.add(createGame(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return games;
    }

    private Game executeQueryAndGetFirstResult(String query, Object... params) {
        Game game = null;
        try (Connection connection = getConnection();
             PreparedStatement ps = prepareStatement(connection, query, params);
             ResultSet rs = ps.executeQuery()) {
            if (rs.next()) {
                game = createGame(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return game;
    }

    private PreparedStatement prepareStatement(Connection connection, String query, Object... params) throws SQLException {
        PreparedStatement ps = connection.prepareStatement(query);
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i + 1, params[i]);
        }
        return ps;
    }
    private int executeUpdate(String query, Object... params) {
        try (Connection connection = getConnection();
             PreparedStatement ps = prepareStatement(connection, query, params)) {
            return ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return 0;
    }

    @Override
    public boolean insertGame(Game game) {
        return executeUpdate("INSERT INTO games (gname, imageUrl, publisher, rdate, description) VALUES (?, ?, ?, ?, ?)", game.getGname(), game.getImage(), game.getPublisher(), game.getRdate(), game.getDescription()) > 0;
    }

    @Override
    public boolean updateGame(Game game) {
        return executeUpdate("UPDATE games SET gname = ?, imageUrl = ?, publisher = ?, rdate = ?, description = ? WHERE gid = ?", game.getGname(), game.getImage(), game.getPublisher(), game.getRdate(), game.getDescription(), game.getGid()) > 0;
    }

    @Override
    public boolean deleteGame(int id) {
        return executeUpdate("DELETE FROM games WHERE gid = ?", id) > 0;
    }
}

