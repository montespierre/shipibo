/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weimont.model;

import com.weimont.config.Conexion;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 *
 * @author montespierreg
 */
public class VentaDao {
    
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    
    public String GenerarSerie(){
        String numeroserie = "";
        String sql = "select max(NumeroSerie) from ventas";
        
        try {
            con = cn.conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                numeroserie = rs.getString(1);
            }
        } catch (Exception e) {
        }
        return numeroserie;
    }
}
