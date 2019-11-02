import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class LoginServlet extends HttpServlet {
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		String jdbcURL = "jdbc:mysql://localhost:3306/project3";
        	String dbUser = "root";
        	String dbPassword = "swt";
		
		String name=request.getParameter("username");
		String pass=request.getParameter("password");
		try {
			Class.forName("com.mysql.jdbc.Driver");
			Connection connection = DriverManager.getConnection(jdbcURL, dbUser, dbPassword);	
			String sql = "SELECT * FROM userinfo WHERE username = ? and password = ?";
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
			result.next();
        		out.println(result.getString("fullname"));
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
