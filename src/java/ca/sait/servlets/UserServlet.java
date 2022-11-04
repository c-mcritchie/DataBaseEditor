/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package ca.sait.servlets;

import ca.sait.models.*;
import ca.sait.services.*;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author colem
 */
public class UserServlet extends HttpServlet {
    private UserService userServ;
    private RoleService roleServ;
    
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        userServ = new UserService();
        roleServ = new RoleService();
        
        request.setAttribute("roles", getRoles(request, response));

        request.setAttribute("Hidden", true);
        
        getUsers(request, response);    
        getRoles(request, response);
        
        this.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
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
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        String action = request.getParameter("action");

        request.setAttribute("Hidden", true);
    
        
        if (action.equals("delete")) {
            delete(request, response);
        } if (action.equals("add")) {
            insert(request, response);
        } if (action.equals("edit")) {
            edit(request, response);
        } 
        
        if (action.equals("update")) {
            String button = request.getParameter("updateButton");
            if (button.equals("cancel")) {
                request.setAttribute("Hidden", "true");
            } if (button.equals("submit")) {
                update(request, response);
            }
        }
        
        getUsers(request, response);
        getRoles(request, response);
        this.getServletContext().getRequestDispatcher("/WEB-INF/users.jsp").forward(request, response);
    }
    
    private List<User> getUsers(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<User> users = userServ.getAll();
            
            request.setAttribute("users", users);
            return users;
            
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private List<Role> getRoles(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<Role> roles = roleServ.getAll();
            
            request.setAttribute("roles", roles);
            return roles;
            
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    private void update(HttpServletRequest request, HttpServletResponse response) {
        
        String email = request.getParameter("updateEmail");
        String first = request.getParameter("updateFirst");
        String last = request.getParameter("updateLast");
        String password = request.getParameter("updatePassword");
        String roleName = request.getParameter("updateRole");
        
        Role role = new Role("regular user",2);
        
        for (Role i : getRoles(request, response)) {
            if (i.getName().equals(roleName)) {
                role = i;
                break;
            }
        } 
        
        try {
            userServ.update(email, true, first, last, password, role);
            

        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void delete(HttpServletRequest request, HttpServletResponse response) {
        
        try {
            String email = request.getParameter("delete");
            userServ.delete(email);
            

        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void insert(HttpServletRequest request, HttpServletResponse response) {
        try {
            String email = request.getParameter("addEmail");
            String first = request.getParameter("addFirst");
            String last = request.getParameter("addLast");
            String password = request.getParameter("addPassword");
            
            String roleName = request.getParameter("addRole");
            
            Role role = new Role("regular user",2);
            for (Role i : getRoles(request, response)) {
                if (i.getName().equals(roleName)) {
                    role = i;
                    break;
                }
            }       
            
            userServ.insert(email, true, first, last, password, role);
        } catch (Exception ex) {
            Logger.getLogger(UserServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void edit(HttpServletRequest request, HttpServletResponse response) {
        String email = request.getParameter("edit");
        
        
        List<User> users = getUsers(request, response);
        
        User toedit = new User();
        for (User i : users) {
            if (i.getEmail().equals(email)) {
                toedit = i;
                break;
            }
        }
        
        request.setAttribute("updateRoles", getRoles(request, response));
        request.setAttribute("updateUser", toedit);
        request.setAttribute("Hidden", false);
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
