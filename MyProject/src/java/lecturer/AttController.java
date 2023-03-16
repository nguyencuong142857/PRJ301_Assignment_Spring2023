/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/JSP_Servlet/Servlet.java to edit this template
 */

package lecturer;

import controller.auth.BaseRoleController;
import dao.LecturerDBContext;
import dao.SessionDBContext;
import java.io.IOException;
import java.io.PrintWriter;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.Date;
import model.Account;
import model.Attandance;
import model.Lecturer;
import model.Session;
import model.Student;
import util.DateTimeHelper;

/**
 *
 * @author sonnt
 */
public class AttController extends BaseRoleController {
   

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    @Override
    protected void processPost(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        Session ses = new Session();
        ses.setId(Integer.parseInt(req.getParameter("sesid")));
        String[] stdids = req.getParameterValues("stdid");
        for (String stdid : stdids) {
            Attandance a =new Attandance();
            Student s = new Student();
            a.setStudent(s);
            a.setDescription(req.getParameter("description"+stdid));
            a.setPresent(req.getParameter("present"+stdid).equals("present"));
            s.setId(Integer.parseInt(stdid));
            a.setTaker(account.getUsername());
            ses.getAttandances().add(a);
        }
        SessionDBContext db = new SessionDBContext();
        db.update(ses);
        resp.sendRedirect("takeatt?id="+ses.getId());
    }

    @Override
    protected void processGet(HttpServletRequest req, HttpServletResponse resp, Account account) throws ServletException, IOException {
        int sesid = Integer.parseInt(req.getParameter("id"));
        LecturerDBContext ldb = new LecturerDBContext();
        Lecturer l = ldb.getLecture(account.getUsername());
        int lid = l.getId();
        SessionDBContext sesDB = new SessionDBContext();
        boolean hasPermission = false;
        ArrayList<Session> sess = sesDB.getListSession(lid);
        for (Session ses : sess) {
            if (ses.getId() == sesid)
                hasPermission = true;
        }
        
        if (hasPermission){
        Session ses = sesDB.get(sesid);
        Date today = new Date();
        java.sql.Date today_sql = DateTimeHelper.toDateSql(today);
        
        req.setAttribute("username", account.getUsername());
        req.setAttribute("today", today_sql);
        req.setAttribute("ses", ses);
        req.getRequestDispatcher("../view/lecturer/checkattendance.jsp").forward(req, resp);
        } else {
            req.getSession().setAttribute("account", null);
            req.getSession().setAttribute("errmsg", "You don't have permission to access this page");
            resp.sendRedirect("../login");
        }
    }

}
