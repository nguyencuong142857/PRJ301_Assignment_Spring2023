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
import model.Group;
import model.Lecturer;
import model.Subject;

/**
 *
 * @author admin
 */
public class GroupDBContext extends DBContext<Group> {

    public ArrayList<Group> listGroup(int lid) {
        ArrayList<Group> groups = new ArrayList<>();
        try {
            String sql = "SELECT	gid, gname, G.subid, S.subname, S.numberOfSession, G.lid, L.lname, sem, [year] \n"
                    + "	FROM [GROUP] G\n"
                    + "		INNER JOIN [Subject] S ON G.subid = S.subid\n"
                    + "		INNER JOIN Lecturer L ON L.lid = G.lid\n"
                    + "	WHERE G.lid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, lid);
            ResultSet rs = stm.executeQuery();
            while (rs.next()) {
                Group g = new Group();
                Lecturer l = new Lecturer();
                Subject sub = new Subject();

                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));

                l.setId(rs.getInt("lid"));
                l.setName(rs.getString("lname"));
                g.setSupervisor(l);

                sub.setId(rs.getInt("subid"));
                sub.setName(rs.getString("subname"));
                sub.setNumberOfSession(rs.getInt("numberOfSession"));
                
                g.setSubject(sub);

                groups.add(g);
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return groups;
    }

    @Override
    public void insert(Group model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void update(Group model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void delete(Group model) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public Group get(int id) {
        try {
            String sql = "SELECT	gid, gname, G.subid, S.subname, S.numberOfSession, G.lid, L.lname, sem, [year] \n"
                    + "	FROM [GROUP] G\n"
                    + "		INNER JOIN [Subject] S ON G.subid = S.subid\n"
                    + "		INNER JOIN Lecturer L ON L.lid = G.lid\n"
                    + "	WHERE gid = ?";
            PreparedStatement stm = connection.prepareStatement(sql);
            stm.setInt(1, id);
            ResultSet rs = stm.executeQuery();
            if (rs.next()) {
                Group g = new Group();
                Lecturer l = new Lecturer();
                Subject sub = new Subject();

                g.setId(rs.getInt("gid"));
                g.setName(rs.getString("gname"));

                l.setId(rs.getInt("lid"));
                l.setName(rs.getString("lname"));
                g.setSupervisor(l);

                sub.setId(rs.getInt("subid"));
                sub.setName(rs.getString("subname"));
                sub.setNumberOfSession(rs.getInt("numberOfSession"));
                
                g.setSubject(sub);

                return g;
            }
        } catch (SQLException ex) {
            Logger.getLogger(SessionDBContext.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    @Override
    public ArrayList<Group> list() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
