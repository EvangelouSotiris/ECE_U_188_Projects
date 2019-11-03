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
			String sql = "SELECT count(*) as total FROM userinfo WHERE username = ? and password = ?";
        		PreparedStatement statement = connection.prepareStatement(sql);
        		statement.setString(1, name);
        		statement.setString(2, pass);
 		     	ResultSet result = statement.executeQuery();
       			result.next();
			if (result.getInt("total") == 1){	
				RequestDispatcher view = request.getRequestDispatcher("html/welcome.html");
				view.forward(request, response);
			}
			else {
				
				response.setContentType("text/html");
	        		PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Unsuccessful login</title>");
				out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\"></head>");
				out.println("<body style='text-align: center;'>");
				out.println("Unsuccessful login, username or password is wrong.");
				out.println("<br><form action='Login' method='get'><input class='btn btn-primary' type='submit' value='Back'/><br></form>");
				out.println("</body>");
				out.println("</html>");
			}
		}
		catch(Exception e){
			response.setContentType("text/html");
        		PrintWriter out = response.getWriter();
			out.println("<html>");
			out.println("<head>");
			out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\"></head>");
			out.println("<body style='text-align: center;'>");
			out.println(e);
			out.println("<br><form action='Login' method='get'><input class='btn btn-primary' type='submit' value='Back'/><br></form>");
			out.println("</body>");
			out.println("</html>");
		}
	}
}
