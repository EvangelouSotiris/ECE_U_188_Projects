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
			this.RenderMessage("Form error","The two passwords inserted are different.",response);
		}
		else if (pass.isEmpty() || name.isEmpty() || fullname.isEmpty() || age.isEmpty()) {	
			this.RenderMessage("Form error","Fields must not be empty.",response);
		}
		else {
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
        			if (result == 1) {
					this.RenderMessage("Registration Status","You have been successfully registered as " + name,response);
				}
				else {
					this.RenderMessage("Registration Status","Your registration has a problem and was not succesfull.",response);
				}
			}
			catch(Exception e){
				if (e.toString().contains("Duplicate entry")){
					this.RenderMessage("Registration error","Username already exists.",response);
				}
				else{
					this.RenderMessage("Exception",e.toString(),response);
				}
			}
		}
	}

	public void RenderMessage(String title, String msg, HttpServletResponse response) throws IOException, ServletException {	
		response.setContentType("text/html");
        	PrintWriter out = response.getWriter();
		out.println("<html>");
		out.println("<head><title>");
		out.println(title);
		out.println("</title><link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\"></head>");
		out.println("<body style='text-align: center;'>");
		out.println(msg);
		out.println("<br><form action='Login' method='get'><input class='btn btn-primary' type='submit' value='Back'/><br></form>");
		out.println("</body>");
		out.println("</html>");
	}
}
