package rest;


import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import com.google.gson.Gson;

import pool.Pool;
import pruebaAJAX.MiEmpleado;


@Path("/serviciosEmpleados")
public class BorrarEmpleadoRest {

	@DELETE
	@Path("/borrarEmpleado/{id}")
	@Produces(MediaType.TEXT_PLAIN)
	public String deleteEmpleado(@PathParam("id") String idEmpleado) {
		
		//int idEmpleado = Integer.parseInt(inicial);
		Connection conexion = null;
		Statement st = null;
		ResultSet rs = null;
		MiEmpleado empleado = new MiEmpleado(idEmpleado, "nada", "0");
		//Gson gson = new Gson();
		//String empleado_json = gson.toJson(empleado);
		
		try{
			conexion = Pool.getConnection();
			st = conexion.createStatement();
			rs = st.executeQuery("DELETE FROM employees WHERE employee_id=" + idEmpleado);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			Pool.liberarRecursos(conexion, st, rs);
		}
		
		return "Ha sido eliminado el empleado con ID: " + idEmpleado;
	}
	
	@GET
	@Path("/obtenerEmpleado/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public String getEmpleado(@PathParam("id") String idEmpleado) throws SQLException {
		Connection conexion = null;
		Statement st = null;
		ResultSet rs = null;
		String nombre = null;
		String apellido = null;
		String salario = null;
		MiEmpleado empleado = null;
		Gson gson = new Gson();
		String empleado_json = null;
		
		try{
			conexion = Pool.getConnection();
			st = conexion.createStatement();
			rs = st.executeQuery("select first_name, last_name, salary from employees where employee_id = " + idEmpleado);
			while (rs.next())
			{
				nombre = rs.getString(1);
				apellido = rs.getString(2);
				salario = rs.getString(3);
			}
			empleado = new MiEmpleado(nombre, apellido, salario);
			empleado_json = gson.toJson(empleado);
		}
		catch(Exception e){
			e.printStackTrace();
		}
		finally{
			Pool.liberarRecursos(conexion, st, rs);
		}
		return empleado_json;
	}
	
	
}
