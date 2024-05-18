/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import DAO.UserDAOImpl;
import java.util.List;
import model.User;

/**
 *
 * @author VINHNQ
 */
public class UserServiceImpl implements UserService {

    UserDAOImpl userDAO = new UserDAOImpl();

    @Override
    public List<User> getAll() {
        return userDAO.getAll();
    }

    @Override
    public User findOne(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public User findOne(String username) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void insert(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(User user) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean register(String username, String password, String email, String fullName, String code) {
        if (userDAO.checkExistEmail(email)) {
            return false;
        }
        if (userDAO.checkExistUsername(username)) {
            return false;
        }
        userDAO.insertregister(new User(username, password, email, fullName, code, 0));
        return true;
    }

    @Override
    public boolean checkExistEmail(String email) {
        return userDAO.checkExistEmail(email);
    }
    
    @Override
    public User login(String username, String password) {
        User user = this.findOne(username); // Find the user by username
        if (user != null && password.equals(user.getPassword())) {
            return user; // Return the user if login is successful
        }
        return null; // Return null if login fails
    }

    @Override
    public boolean checkExistUsername(String username) {
        return userDAO.checkExistUsername(username);
    }

    @Override
    public void updatestatus(User user) {
        userDAO.updatestatus(user);
    }

}
