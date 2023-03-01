/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.cab.dao;

import com.cab.helper.Connect;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Badar muneer
 */
public class PriceDAO 
{
    public double getPrice()
    {
        PreparedStatement statement = Connect.getInstance().getPreparedStatement("SELECT * FROM PRICE;");
        try {
            ResultSet rs = statement.executeQuery();
            if(rs.next())
                return rs.getDouble("PRICE");
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage() + " in getting price.");
        }
        
        return 0.0;
    }
    
    public boolean updatePrice(double price)
    {
        PreparedStatement statement = Connect.getInstance().getPreparedStatement("UPDATE PRICE SET PRICE=? WHERE ID=?;");
        try {
            statement.setDouble(1, price);
            statement.setInt(2, 1);
            statement.executeUpdate();
            return true;
        } catch (SQLException ex) {
            System.out.println("ERROR: " + ex.getMessage() + " in updating price.");
        }
        
        return false;
    }
}
