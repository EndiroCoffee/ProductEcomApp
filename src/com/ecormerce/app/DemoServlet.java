package com.ecormerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecomerce.connection.DBConnection;

/**
 * Servlet implementation class DemoServlet
 */
@WebServlet("/init")
public class DemoServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DemoServlet() { }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		
		// Get Config
		InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/config.properties");	
		Properties props = new Properties();
		props.load(ins);
		
		// Create a connection
		try {
			DBConnection conn= new DBConnection(props.getProperty("url"),props.getProperty("username"),props.getProperty("password"));
			if(conn != null)
				out.print("<h3>DB Connection is intialized ! </h3>");
			else
				out.print("<h3>DB Connection is not intialized! </h3>");
			
			conn.closeConnection();
			out.print("<h3>DB Connection is closed !</h3>");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
