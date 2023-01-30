/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cab.dao;

import com.cab.helper.Connect;
import com.cab.model.User;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
/**
 *
 * @author Badar muneer
 */
public class UserDAO 
{
    public User getUserByUsernameAndPassword(String username, String password)
    {
        String query = "SELECT * FROM USERS WHERE USERNAME=? AND PASSWORD=?;";
        
        PreparedStatement statement = Connect.getInstance().getPreparedStatement(query);
        try {
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet rs = statement.executeQuery();
            if(rs.next())
            {
                User user = new User();
                user.setUsername(rs.getString("username"));
                user.setPassword(rs.getString("password"));
                user.setAccessLevel(rs.getInt("access_level"));
                return user;
            }
        } catch (SQLException ex) 
        {
            System.out.println("ERROR: " + ex.getMessage());
        }
        
        return null;
    }
}
