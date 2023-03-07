/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package controller.auth;

import dal.AccountDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import model.Account;
import model.Role;

/**
 *
 * @author sonnt
 */
public class LoginController extends HttpServlet {

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
            throws ServletException, IOException {
        String errmsg = (String) request.getSession().getAttribute("errmsg");
        request.setAttribute("errmsg", errmsg);
        Cookie arr[] = request.getCookies();
        if (arr != null) {
            for (Cookie c : arr) {
                if (c.getName().equals("u")) {
                    request.setAttribute("user", c.getValue());
                }
                if (c.getName().equals("p")) {
                    request.setAttribute("pass", c.getValue());
                }
            }
        }
        request.getRequestDispatcher("view/auth/login.jsp").forward(request, response);
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
            throws ServletException, IOException {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String remember = request.getParameter("remember");
        AccountDBContext db = new AccountDBContext();
        Account account = db.get(username, password);
        if (account == null) {
            request.setAttribute("errmsg", "Username or Password is incorrect");
            request.getRequestDispatcher("view/auth/login.jsp").forward(request, response);
        } else {
            request.getSession().setAttribute("account", account);

            Cookie u = new Cookie("u", username);
            Cookie p = new Cookie("p", password);
            u.setMaxAge(60 * 30);

            if (remember == null || remember.length() == 0) {
                p.setMaxAge(0);
            } else {
                p.setMaxAge(60 * 30);
            }

            response.addCookie(u);
            response.addCookie(p);

            ArrayList<Role> roles = account.getRoles();
            if (roles.size() == 1) {
                for (Role role : roles) {
                    if (role.getRoleId() == 3) {
                        response.sendRedirect("student/timetable");
                    }
                    if (role.getRoleId() == 2) {
                        response.sendRedirect("lecturer/home");
                    }
                    if (role.getRoleId() == 1) {
                        response.sendRedirect("admin/editatts");
                    }
                }
            } else {
                request.getRequestDispatcher("view/auth/chooserole.jsp").forward(request, response);
            }
            

        }

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
