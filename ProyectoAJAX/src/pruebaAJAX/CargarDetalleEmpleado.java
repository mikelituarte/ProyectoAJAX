package pruebaAJAX;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import pool.Pool;


public class CargarDetalleEmpleado extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CargarDetalleEmpleado() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		String id = request.getParameter("id");
		System.out.println("AAAAAAAAAAAAAAAAAA "+id);

		Connection conexion = null;
		Statement st = null;
		ResultSet rs = null;
		String salario = null;
		String nombre = null;
		String apellido = null;
		MiEmpleado empleado = null;
		String mensajeJson = null;
		try 
			{
			conexion = Pool.getConnection();
			st = conexion.createStatement();
			rs = st.executeQuery("SELECT salary, first_name, last_name FROM EMPLOYEES where employeeId ="+ id);
			
			 
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			Gson gson = new Gson();
			
			

			while (rs.next())
			{
				salario = rs.getString(1);
				nombre = rs.getString(2);
				apellido = rs.getString(3);
				empleado = new MiEmpleado(nombre, apellido, salario);
				mensajeJson = gson.toJson(empleado);
			}
			response.getWriter().write(mensajeJson);
			
			
		} catch (Exception e) {
			// TODO: handle exception
			
		}
		finally {
			Pool.liberarRecursos(conexion, st, rs);
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}