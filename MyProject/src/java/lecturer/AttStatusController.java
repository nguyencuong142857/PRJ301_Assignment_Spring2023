/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */
package lecturer;

import controller.auth.BaseRoleController;
import dao.AttendanceDBContext;
import dao.GroupDBContext;
import dao.LecturerDBContext;
import dao.SessionDBContext;
import dao.StudentDBContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import model.Account;
import model.Attandance;
import model.Group;
import model.Session;
import model.Student;

/**
 *
 * @author admin
 */
public class AttStatusController extends BaseRoleController {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response, Account account)
            throws ServletException, IOException {
        LecturerDBContext ldb = new LecturerDBContext();
        int lid = ldb.getLecture(account.getUsername()).getId();
        GroupDBContext gdb = new GroupDBContext();
        ArrayList<Group> groups = gdb.listGroup(lid);
        request.setAttribute("groups", groups);
        
        if (request.getParameter("gid")== null) {
        } else {
            
            int gid = Integer.parseInt(request.getParameter("gid"));
            Group group = gdb.get(gid);

            SessionDBContext sesdb = new SessionDBContext();
            ArrayList<Session> sessions = sesdb.getSessionFromGroup(gid);

            StudentDBContext sdb = new StudentDBContext();
            ArrayList<Student> students = sdb.getListStudent(gid);

            AttendanceDBContext attdb = new AttendanceDBContext();
            ArrayList<Attandance> atts = attdb.listAtt(gid);
            
            request.setAttribute("sessions", sessions);
            request.setAttribute("group", group);
            request.setAttribute("students", students);
            request.setAttribute("atts", atts);
        }
        request.setAttribute("username", account.getUsername());
        request.getRequestDispatcher("../view/lecturer/attstatus.jsp").forward(request, response);

    }

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        processRequest(req, resp, account);
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        processRequest(req, resp, account);
    }

    

}
