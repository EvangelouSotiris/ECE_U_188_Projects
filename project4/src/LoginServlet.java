import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;
import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "userinfo")
class User implements Serializable {
	@Id
	@Column(name = "username", unique = true)
	private String username;

	@Column(name = "password", nullable = false)
	private String password;

	@Column(name = "fullname", nullable = false)
	private String fullname;

	@Column(name = "age", nullable = false)
	private int age;

	// GET / SET

	public String getUsername() {
		return username;
	}

	public void setUsername(String in_username) {
		this.username = in_username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String in_password) {
 		this.password = in_password;
	}

  	public String getFullname() {
		return fullname;
	}

	public void setFullname(String in_fullname) {
        	this.fullname = in_fullname;
    	}
	
	public void setAge(int in_age) {
		this.age = in_age;
	}

    	public int getAge() {
       		return age;
	}
}


public class LoginServlet extends HttpServlet {
	
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("userinfo");
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
			
		String name=request.getParameter("username");
		String pass=request.getParameter("password");
		authenticate(name,pass,request,response);
	}

	public static void authenticate(String username, String password,HttpServletRequest request,  HttpServletResponse  response) {
        	// Create an EntityManager
        	EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        	EntityTransaction transaction = null;

        	try {
            		// Get a transaction
            		transaction = manager.getTransaction();
            		// Begin the transaction
            		transaction.begin();
            		User usr = manager.find(User.class, username);
            		String pass_in = usr.getPassword();
			if (pass_in.equals(password)){	
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
				out.println("Unsuccessful login, password is wrong");
				out.println("<br><form action='Login' method='get'><input class='btn btn-primary' type='submit' value='Back'/><br></form>");
				out.println("</body>");
				out.println("</html>");
			}
		} catch (Exception ex) {
			response.setContentType("text/html");
	        	try{
				PrintWriter out = response.getWriter();
				out.println("<html>");
				out.println("<head>");
				out.println("<title>Unsuccessful login</title>");
				out.println("<link rel=\"stylesheet\" href=\"https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css\" integrity=\"sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T\" crossorigin=\"anonymous\"></head>");
				out.println("<body style='text-align: center;'>");
				out.println("Unsuccessful login, username does not exist");
				out.println("<br><form action='Login' method='get'><input class='btn btn-primary' type='submit' value='Back'/><br></form>");
				out.println("</body>");
				out.println("</html>");
			} catch (Exception ex2) {
				ex2.printStackTrace();
			}
            		// If there are any exceptions, roll back the changes
            		if (transaction != null) {
                		transaction.rollback();
            		}
            		// Print the Exception
            		ex.printStackTrace();
        	} finally {
            		// Close the EntityManager
 			manager.close();
        	}
    	}
}
