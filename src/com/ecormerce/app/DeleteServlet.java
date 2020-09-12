package com.ecormerce.app;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
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
 * Servlet implementation class DeleteServlet
 */
@WebServlet("/delete-product")
public class DeleteServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DeleteServlet() {
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		response.sendRedirect("delete-product.html");
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// fetch data
		Integer pid = Integer.parseInt(request.getParameter("pid"));

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
				String query = "delete from eproduct where p_id=?";

				// 3. Create a statement
				PreparedStatement pstm = conn.getConnection().prepareStatement(query);

				// 4. set Parameter
				pstm.setInt(1, pid);

				// 4. Execute Query
				int noOfRowsAffected = pstm.executeUpdate();
				out.print("<h3> No of Product deleted " + noOfRowsAffected + "</h3>");

				pstm.close();
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
