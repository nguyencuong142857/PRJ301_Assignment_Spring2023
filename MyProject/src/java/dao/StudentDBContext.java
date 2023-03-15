/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.Student;

/**
 *
 * @author admin
 */
public class StudentDBContext extends DBContext<Student> {

    public Student getStudent(String username) {
        try {
            String sql = "SELECT A.username, SA.stdid, S.stdname\n"
                    + "                    	FROM Account A \n"
                    + "                    	INNER JOIN Student_Account SA ON A.username = SA.username\n"
                    + "                    	INNER JOIN Student S ON SA.stdid = S.stdid\n"
                    + "                    	WHERE A.username = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setString(1, username);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Student s = new Student();
                s.setId(rs.getInt("stdid"));
                s.setName(rs.getString("stdname"));
                return s;
            }
        } catch (SQLException ex) {
            Logger.getLogger(LecturerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public ArrayList<Student> getListStudent(int gid) {
        ArrayList<Student> stds = new ArrayList<>();
        try {
            String sql = "SELECT S.stdid, stdname\n"
                    + "		,G.gid\n"
                    + "	FROM Student S\n"
                    + "		INNER JOIN Student_Group SG ON S.stdid = SG.stdid\n"
                    + "		INNER JOIN [Group] G ON SG.gid = G.gid\n"
                    + "		WHERE G.gid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, gid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Student s = new Student();

                s.setId(rs.getInt("stdid"));
                s.setName(rs.getString("stdname"));

                stds.add(s);
            }
        } catch (SQLException ex) {
            Logger.getLogger(LecturerDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return stds;
    }

    @Override
    public void insert(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Student model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Student get(int id) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public ArrayList<Student> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
