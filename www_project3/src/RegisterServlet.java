import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class RegisterServlet extends HttpServlet {
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String jdbcURL = "jdbc:mysql://localhost:3306/project3";
        	String dbUser = "root";
        	String dbPassword = "swt";
		
		String name=request.getParameter("username");
		String pass=request.getParameter("password");
		String pass2=request.getParameter("password2");
		String fullname=request.getParameter("fullname");
		String age=request.getParameter("age");
		if (! pass.equals(pass2)) {	
			response.setContentType("text/html");
        		PrintWriter out = response.getWriter();
       			out.println("<html>");
        		out.println("<head>");
        		out.println("<title>Hello World!</title>");
        		out.println("</head>");
        		out.println("<body>");
			out.println("Passwords are different");
			out.println(pass);
			out.println(pass2);
			out.println("</body>");
        		out.println("</html>");
		}
		if (pass.isEmpty() || name.isEmpty() || fullname.isEmpty() || age.isEmpty()) {	
			response.setContentType("text/html");
        		PrintWriter out = response.getWriter();
       			out.println("<html>");
        		out.println("<head>");
        		out.println("<title>Hello World!</title>");
        		out.println("</head>");
        		out.println("<body>");
			out.println("all fields are mandatory.");
			out.println("</body>");
        		out.println("</html>");	
		}
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);	
			String sql = "INSERT INTO userinfo (username,password,fullname,age) VALUES(?,?,?,?)";
        		PreparedStatement statement = connection.prepareStatement(sql);
        		statement.setString(1, name);
        		statement.setString(2, pass);
			statement.setString(3, fullname);
			statement.setString(4, age);
 		     	Integer result = statement.executeUpdate();
			response.setContentType("text/html");
        		PrintWriter out = response.getWriter();
       			out.println("<html>");
        		out.println("<head>");
        		out.println("<title>Hello World!</title>");
        		out.println("</head>");
        		out.println("<body>");
        		if (result == 1) {
				out.println("You have been successfully registered as " + name);
			}
			else {
				out.println("Registration has failed.");
			}
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
			out.println(e);
			out.println("</body>");
			out.println("</html>");
		}
	}
}
