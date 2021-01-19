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
public class ProductoDao {
    Conexion cn = new Conexion();
    Connection con;
    PreparedStatement ps;
    ResultSet rs;
    int r;
    
    public Producto buscar(int id){
        Producto p = new Producto();
        String sql = "select * from producto where IdProducto="+id;
        
        try {
            con = cn.conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
               p.setId(rs.getInt(1));
               p.setNom(rs.getString(2));
               p.setPre(rs.getDouble(3));
               p.setStock(rs.getInt(4));
               p.setEstado(rs.getString(5));
            }
        } catch (Exception e) {
        }
        return p;
    }
    
    public void actualizarstock(int id, int stock){
        String sql = "update producto set Stock=? where IdProducto=?";
        
        try {
            con = cn.conexion();
            ps = con.prepareStatement(sql);
            ps.setInt(1, stock);
            ps.setInt(2, id);
            ps.executeUpdate();
        } catch (Exception e) {
        }
        
    }
    
    public Producto listarId(int id){
        Producto pr = new Producto();
        String sql = "select* from producto where IdProducto="+id;
        
         try {
            con = cn.conexion();
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while(rs.next()){
                pr.setId(rs.getInt(1));
                pr.setNom(rs.getString(2));
                pr.setPre(rs.getDouble(3));
                pr.setStock(rs.getInt(4));
                pr.setEstado(rs.getString(5));
            }
        } catch (Exception e) {
        }
        return pr;
    }
    
    
}
