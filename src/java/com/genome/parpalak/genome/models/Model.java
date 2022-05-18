package com.genome.parpalak.genome.models;

import com.genome.parpalak.controllers.ProjectController;
import com.genome.parpalak.controllers.TaskController;
import com.genome.parpalak.db.Database;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Model {
    
    protected void updateData(String sql) {
        Statement stmt = null;
        Connection conn = null;

        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(Model.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    protected ArrayList<ArrayList> selectData(String sql, String[] fields) {
        Statement stmt = null;
        ResultSet rs = null;
        Connection conn = null;

        ArrayList<ArrayList> listOfList = new ArrayList<>();

        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();
            rs = stmt.executeQuery(sql);
            while (rs.next()) {
                ArrayList ar = new ArrayList();
                for (int i = 0; i < fields.length; i++) {
                    Object object = rs.getObject(fields[i]);
                    if(object == null)
                        ar.add("");
                    else
                        ar.add(object);
                }
                listOfList.add(ar);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (rs != null) {
                    rs.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return listOfList;

    }
    
    protected void deleteData(String sql) {
        Statement stmt = null;
        Connection conn = null;
        try {
            conn = Database.getConnection();
            stmt = conn.createStatement();
            stmt.executeUpdate(sql);
        } catch (SQLException ex) {
            Logger.getLogger(TaskController.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                if (stmt != null) {
                    stmt.close();
                }
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
                Logger.getLogger(ProjectController.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        
    }

}
