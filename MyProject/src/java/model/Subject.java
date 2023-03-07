/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

import java.util.ArrayList;

/**
 *
 * @author Ngo Tung Son
 */
public class Subject {
    private int id;
    private String name;
    private int numberOfSession;
    private ArrayList<Group> groups = new ArrayList<>();
    private String detailname;

    public String getDetailname() {
        return detailname;
    }

    public void setDetailname(String detailname) {
        this.detailname = detailname;
    }
    
    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Group> getGroups() {
        return groups;
    }

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
    }

    public int getNumberOfSession() {
        return numberOfSession;
    }

    public void setNumberOfSession(int numberOfSession) {
        this.numberOfSession = numberOfSession;
    }
    
    
}
