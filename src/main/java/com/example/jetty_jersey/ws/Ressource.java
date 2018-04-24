package com.example.jetty_jersey.ws;

import DAO.DAO;
import DAO.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class Ressource {

    public User getUserBySession(HttpServletRequest httpRequest) throws IOException {
        String username = (String) httpRequest.getSession().getAttribute("username");
        if (username == null)
            throw new IOException("theres no user in session");

        return DAO.getActionUser().getOneUser(DAO.client, username);
    }
}
