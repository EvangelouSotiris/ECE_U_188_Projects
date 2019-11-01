import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {
	/*
	public void doGet (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {	
		response.setContentType("text/html");
        	PrintWriter out = response.getWriter();
       		out.println("<html>");
        	out.println("<head>");
        	out.println("<title>Hello World!</title>");
        	out.println("</head>");
        	out.println("<body>");
        	out.println("Dont GET this servlet");
		out.println("</body>");
        	out.println("</html>");	
	}
	*/
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String jdbcURL = "jdbc:mysql://localhost:3306/project3";
        	String dbUser = "";
        	String dbPassword = "";
		
		String name=request.getParameter("username");
		String pass=request.getParameter("password");
		try {
			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);	
			String sql = "SELECT lastname FROM userinfo WHERE username = ? and password = ?";
        		PreparedStatement statement = connection.prepareStatement(sql);
        		statement.setString(1, name);
        		statement.setString(2, pass);
 		     	ResultSet result = statement.executeQuery();
			response.setContentType("text/html");
        		PrintWriter out = response.getWriter();
       			out.println("<html>");
        		out.println("<head>");
        		out.println("<title>Hello World!</title>");
        		out.println("</head>");
        		out.println("<body>");
        		out.println("<h1>post request got, SELECT lastname FROM userinfo WHERE username = ? and password = ?</h1>");
			out.println("<p>yo</p>");
        		out.println(result);
			out.println("</body>");
        		out.println("</html>");	
		}
		catch(Exception e){
			response.setContentType("text/html");
        		PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("</head>");
			out.println("<body>");
			System.out.println("fuck");
			out.println("</body>");
			out.println("</html>");
		}
	}
}
