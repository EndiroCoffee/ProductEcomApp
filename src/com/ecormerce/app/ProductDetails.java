package com.ecormerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecomerce.connection.DBConnection;

/**
 * Servlet implementation class ProductDetails
 */
@WebServlet("/product-details")
public class ProductDetails extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProductDetails() {
        super();
        // TODO Auto-generated constructor stub
    }

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
		
		// 1. Create a connection
		try {
			DBConnection conn= new DBConnection(props.getProperty("url"),props.getProperty("username"),props.getProperty("password"));
			if(conn != null) {
				// 2. Create a query 
				String query  = "select * from eproduct";
				// 3. Create a statement
				Statement stm = conn.getConnection().createStatement();
				// 4. Execute Query 
				ResultSet rstm = stm.executeQuery(query);
				
				while(rstm.next()) {
					out.print("<br> ----------------------------- <br>");
					out.print(rstm.getInt(1) +"    " +rstm.getString(2) + "  " +rstm.getDouble(3));
				}
				rstm.close();
				stm.close();
				
			}
			conn.closeConnection();
			out.print("<h3>DB Connection is closed !</h3>");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}
		//create a db connection
		
		// execute query 
       // print product details
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
