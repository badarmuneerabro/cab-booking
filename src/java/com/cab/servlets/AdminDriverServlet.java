/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package com.cab.servlets;

import com.cab.dao.BookingDAO;
import com.cab.dao.DriverDAO;
import com.cab.model.Booking;
import com.cab.model.Driver;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Badar muneer
 */
@WebServlet(name = "AdminServlet", urlPatterns = {"/admin/driver"})
public class AdminDriverServlet extends HttpServlet 
{

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        String action = request.getParameter("action");
        
        switch(action)
        {
            case "register":
                forwardRequest("driver/register", request, response);
                break;
            case "update":
                String username = request.getParameter("username");
                Driver driver = new DriverDAO().getDriverWithUsername(username);
                request.setAttribute("driver", driver);
                forwardRequest("driver/update", request, response);
                break;
            case "list":
                forwardToListPage(request, response);
                break;
            case "delete":
                String driverUsername = request.getParameter("username");
                new DriverDAO().deleteDriver(driverUsername);
                response.sendRedirect(request.getContextPath() + "/admin/driver?action=list");
                break;
            case "jobs":
                List<Booking> list = new BookingDAO().getAllBookingsAssignedToDriver(request.getParameter("username"));
                request.setAttribute("list", list);
                forwardRequest("driver/driverJobs", request, response);
                break;
        }
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException 
    {
        String username = request.getParameter("username");
        String name = request.getParameter("driverName");
        String password = request.getParameter("password");
        String licenseNumber = request.getParameter("licenseNumber");
        String carType = request.getParameter("carType");
        String carModel = request.getParameter("carModel");
        
        int accessLevel = 2;
        
        Driver driver = new Driver(name, licenseNumber, carType, carModel, username, password, accessLevel);
        DriverDAO dao = new DriverDAO();
        if(dao.insertDriver(driver) != null)
        {
            System.out.println("Driver inserted...");
        }
        
        response.sendRedirect("driver?action=list");
        
    }
    
    private void forwardRequest(String page, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        request.getRequestDispatcher("/WEB-INF/jsp/view/" + page + ".jsp").forward(request, response);
    }
    
    private void forwardToListPage(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        List<Driver> list = new DriverDAO().getAllDrivers();
        request.setAttribute("list", list);
        System.out.println("List Size in driver=" + list.size());
        forwardRequest("driver/list", request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
