    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.weimont.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;



/**
 *
 * @author montespierreg
 */
public class Conexion {
    
    public Conexion(){
        
    }

    public static Connection conexion() throws SQLException, InstantiationException, IllegalAccessException {
		Connection cn = null;

		// Parametros
		// String direccion =
		// "jdbc:mariadb://localhost:3306/agenda?user=moche232&password=2322715";
		String driver = "org.mariadb.jdbc.Driver";
		String urlDB = "jdbc:mariadb://localhost:3306/shipibo";
		String user = "ventas";
		String pass = "2322715";

		try {
			//Class.forName("org.mariadb.jdbc.Driver").newInstance();
			Class.forName(driver).newInstance();

			// cn = DriverManager.getConnection(direccion);
			cn = DriverManager.getConnection(urlDB, user, pass);

		} catch (SQLException e) {
			throw e;
		} catch (ClassNotFoundException e) {
			throw new SQLException("No se encontro el driver de la BD");
		} catch (Exception e) {
			throw new SQLException("No se puede establecer conexion de la BD");
		}
		return cn;
	}
}
