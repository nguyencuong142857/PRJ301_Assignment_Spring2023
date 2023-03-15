/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Account;
import model.Attandance;
import model.Group;
import model.Lecturer;
import model.Room;
import model.Session;
import model.Student;
import model.Subject;
import model.TimeSlot;

/**
 *
 * @author admin
 */
public class AttendanceDBContext extends DBContext<Attandance> {

    public Attandance get(int sesid, int stdid) {
        try {
            String sql = "SELECT A.sesid, A.stdid,ISNULL(A.present, 0) present, \n"
                    + "			ISNULL(A.taker, '') taker, ISNULL(A.[description], '') [description]\n"
                    + "			, ISNULL(A.record_time, '')record_time\n"
                    + "		,S.[date], S.gid, S.attanded, S.lid, S.tid\n"
                    + "		,G.gname ,G.subid\n"
                    + "		,SUB.subname, SUB.numberOfSession, SUB.[name]\n"
                    + "		,L.lname, LA.username\n"
                    + "	FROM Attandance A \n"
                    + "		INNER JOIN [Session] S ON S.sesid = A.sesid\n"
                    + "		INNER JOIN [Group] G ON S.gid = G.gid\n"
                    + "		INNER JOIN [Subject] SUB ON SUB.subid = G.subid\n"
                    + "		INNER JOIN Lecturer L ON L.lid = S.lid\n"
                    + "		INNER JOIN Lecture_Account LA ON L.lid = LA.lid\n"
                    + "		WHERE A.sesid = ? AND A.stdid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, sesid);
            stm.setInt(2, stdid);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Attandance a = new Attandance();
                a.setDescription(rs.getString("description"));
                a.setPresent(rs.getBoolean("present"));
                a.setRecord_time(rs.getTimestamp("record_time"));
                a.setTaker(rs.getString("taker"));
                
                Session s = new Session();
                s.setAttandated(rs.getBoolean("attanded"));
                s.setDate(rs.getDate("date"));
                a.setSession(s);
                
                Group g = new Group();
                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));
                s.setGroup(g);
                
                Subject sub = new Subject();
                sub.setId(rs.getInt("subid"));
                sub.setName(rs.getString("subname"));
                sub.setDetailname(rs.getString("name"));
                sub.setNumberOfSession(rs.getInt("numberOfSession"));
                g.setSubject(sub);
                
                Lecturer l = new Lecturer();
                l.setId(rs.getInt("lid"));
                l.setName(rs.getString("lname"));
                Account acc = new Account();
                acc.setUsername(rs.getString("username"));
                l.setAccount(acc);
                s.setLecturer(l);
                
                TimeSlot t = new TimeSlot();
                t.setId(rs.getInt("tid"));
                s.setTimeslot(t);
                
                return a;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Attandance> filterStudent(int stdid, Date from, Date to) {
        ArrayList<Attandance> atts = new ArrayList<>();
        try {
            String sql = "SELECT \n"
                    + "                    	ses.sesid,ses.[date],ses.[index],ses.attanded\n"
                    + "						,S.stdid, S.stdname\n"
                    + "						,L.lid, L.lname\n"
                    + "                    	,g.gid,g.gname\n"
                    + "                    	,sub.subid,sub.subname,numberOfSession\n"
                    + "                    	,r.rid,r.rname\n"
                    + "                    	,t.tid,t.[description]\n"
                    + "						,ISNULL(A.present,0) present\n"
                    + "                    FROM [Session] ses\n"
                    + "							INNER JOIN Lecturer L ON L.lid = SES.lid\n"
                    + "                    		INNER JOIN [Group] g ON g.gid = ses.gid\n"
                    + "							INNER JOIN Student_Group SG ON SG.gid = G.gid\n"
                    + "							INNER JOIN Student S ON S.stdid = SG.stdid\n"
                    + "                    			INNER JOIN [Subject] sub ON sub.subid = g.subid\n"
                    + "                    			INNER JOIN Room r ON r.rid = ses.rid\n"
                    + "                    			INNER JOIN TimeSlot t ON t.tid = ses.tid\n"
                    + "						LEFT JOIN Attandance A ON A.sesid = SES.sesid AND A.stdid = S.stdid\n"
                    + "                    WHERE\n"
                    + "                    S.stdid = ?\n"
                    + "                    AND ses.[date] >= ?\n"
                    + "                   AND ses.[date] <= ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, stdid);
            stm.setDate(2, from);
            stm.setDate(3, to);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Attandance a = new Attandance();
                Session session = new Session();
                Lecturer l = new Lecturer();
                Room r = new Room();
                Group g = new Group();
                TimeSlot t = new TimeSlot();
                Subject sub = new Subject();

                session.setId(rs.getInt("sesid"));
                session.setDate(rs.getDate("date"));
                session.setIndex(rs.getInt("index"));
                session.setAttandated(rs.getBoolean("attanded"));

                l.setId(rs.getInt("lid"));
                l.setName(rs.getString("lname"));
                session.setLecturer(l);

                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));
                session.setGroup(g);

                sub.setId(rs.getInt("subid"));
                sub.setName(rs.getString("subname"));
                sub.setNumberOfSession(rs.getInt("numberOfSession"));
                g.setSubject(sub);

                r.setId(rs.getInt("rid"));
                r.setName(rs.getString("rname"));
                session.setRoom(r);

                t.setId(rs.getInt("tid"));
                t.setDescription(rs.getString("description"));
                session.setTimeslot(t);

                a.setSession(session);
                a.setPresent(rs.getBoolean("present"));
                atts.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return atts;
    }

    public ArrayList<Attandance> listAtt(int gid) {
        ArrayList<Attandance> atts = new ArrayList<>();
        try {
            String sql = "SELECT S.stdid, S.stdname\n"
                    + "		,SS.sesid, attanded\n"
                    + "		,ISNULL(A.present, 0) present\n"
                    + "	FROM Student S\n"
                    + "		INNER JOIN Student_Group SG ON S.stdid = SG.stdid\n"
                    + "		INNER JOIN [Group] G ON SG.gid = G.gid\n"
                    + "		INNER JOIN [Session] SS ON SS.gid = G.gid\n"
                    + "		LEFT JOIN Attandance A ON A.stdid = S.stdid AND SS.sesid = A.sesid\n"
                    + "		WHERE G.gid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, gid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Attandance a = new Attandance();
                Student s = new Student();
                Session ses = new Session();

                s.setId(rs.getInt("stdid"));
                s.setName(rs.getString("stdname"));
                a.setStudent(s);

                ses.setId(rs.getInt("sesid"));
                ses.setAttandated(rs.getBoolean("attanded"));
                a.setSession(ses);

                a.setPresent(rs.getBoolean("present"));

                atts.add(a);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LecturerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return atts;
    }

    public boolean isAttend(Student student, Session session) {
        try {
            String sql = "SELECT \n"
                    + "		sesid, stdid, present\n"
                    + "	FROM Attandance\n"
                    + "	WHERE sesid = ? AND stdid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, session.getId());
            stm.setInt(2, student.getId());
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                return rs.getBoolean("present");
            }
        } catch (SQLException ex) {
            Logger.getLogger(AttendanceDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    @Override
    public void insert(Attandance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Attandance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Attandance model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Attandance get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Attandance> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
