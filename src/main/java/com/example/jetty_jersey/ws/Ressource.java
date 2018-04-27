package com.example.jetty_jersey.ws;

import DAO.DAO;
import DAO.User;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

public class Ressource {
    class AuthException extends Exception {
        public AuthException(String msg) {
            super(msg);
        }
    }

    public User getUserBySession(HttpServletRequest httpRequest) throws AuthException {
        String username = (String) httpRequest.getSession().getAttribute("user");
        if (username == null)
            throw new AuthException("user not in session");

        try {
            return DAO.getActionUser().getOneUser(DAO.client, username);
        } catch (IOException e) {
            throw new AuthException("user doesnot exist");
        }
    }
}
