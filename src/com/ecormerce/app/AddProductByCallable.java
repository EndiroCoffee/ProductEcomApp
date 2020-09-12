package com.ecormerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.ecomerce.connection.DBConnection;

/**
 * Servlet implementation class AddProductByCallable
 */
@WebServlet("/add-product-callable")
public class AddProductByCallable extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public AddProductByCallable() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("add-prduct-c.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// fetch data
		String name = request.getParameter("name");
		Integer price = Integer.parseInt(request.getParameter("price"));

		response.setContentType("text/html");
		PrintWriter out = response.getWriter();

		// Get Config
		InputStream ins = getServletContext().getResourceAsStream("/WEB-INF/config.properties");
		Properties props = new Properties();
		props.load(ins);

		// Create a connection
		try {
			DBConnection conn = new DBConnection(props.getProperty("url"), props.getProperty("username"),
					props.getProperty("password"));
			if (conn != null) {

				// 2. Create a query
				String query = "call add_eproduct(?,?)";

				// 3. Create a CallableStatement
				CallableStatement cstm = conn.getConnection().prepareCall(query);

				// 4. set Parameter
				cstm.setString(1, name);
				cstm.setInt(2, price);

				// 4. Execute Query
				int noOfRowsAffected = cstm.executeUpdate();
				out.print("<h3> No of Product added " + noOfRowsAffected + "</h3>");

				cstm.close();
			}

			conn.closeConnection();
			out.print("<h3>DB Connection is closed !</h3>");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (SQLException e) {
			e.printStackTrace();
		}

	}

}
