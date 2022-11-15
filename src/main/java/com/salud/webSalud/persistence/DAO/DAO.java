/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.salud.webSalud.persistence.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 *
 * @author ematr
 */
public abstract class DAO {
    
    protected Connection conexion = null;
    protected ResultSet resultado = null;   
    protected Statement sentencia = null; 
    
    private final String USER = "root";
    private final String PASSWORD = "root";
    private final String DATABASE = "perros";
    private final String DRIVER ="com.mysql.jdbc.Driver";
    
    
    protected void conectarBase() throws SQLException, ClassNotFoundException{
         
        try{
            Class.forName(DRIVER);
            String urlBaseDeDatos = "jdbc:mysql://localgost:3306/" + DATABASE + "?UseSSL=False";
            conexion = DriverManager.getConnection(urlBaseDeDatos,USER,PASSWORD);
            
        }catch(ClassNotFoundException | SQLException ex) {
            throw ex;
        }
    }
    protected void desconectarBase() throws Exception {
        try {
            if (resultado != null) {
                resultado.close();
            }
            if (sentencia != null) {
                sentencia.close();
            }
            if (conexion != null) {
                conexion.close();
            }
        } catch (Exception ex) {
            throw ex;
        }
    }
    protected void consultarBase(String sql) throws Exception {
        try {
            conectarBase();
            sentencia = conexion.createStatement();
            resultado = sentencia.executeQuery(sql);
        } catch (Exception ex) {
            throw ex;
        }
    }
}
