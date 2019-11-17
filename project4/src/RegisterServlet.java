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

public class RegisterServlet extends HttpServlet {
	
	private static final EntityManagerFactory ENTITY_MANAGER_FACTORY = Persistence.createEntityManagerFactory("userinfo");
	
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		
		
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
			create(name, pass, fullname, Integer.parseInt(age),response);
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

    	public void create(String username, String password, String fullname, int age, HttpServletResponse response) {
        	// Create an EntityManager
        	EntityManager manager = ENTITY_MANAGER_FACTORY.createEntityManager();
        	EntityTransaction transaction = null;

        	try {
            		// Get a transaction
            		transaction = manager.getTransaction();
            		// Begin the transaction
            		transaction.begin();

            		// Create a new Student object
            		User usr = new User();
            		usr.setUsername(username);
            		usr.setPassword(password);
            		usr.setFullname(fullname);
            		usr.setAge(age);
	
            		// Save the student object
            		manager.persist(usr);

            		// Commit the transaction
            		transaction.commit();
			
			this.RenderMessage("Registration Status","You have been successfully registered as " + username,response);	
        	} catch (Exception ex) {
        		try {
            			// Get a transaction
            			transaction = manager.getTransaction();
            			// Begin the transaction
            			transaction.begin();
            			User usr = manager.find(User.class, username);
				this.RenderMessage("Registration Status","Registration failed : Account with username " + username + " already exists",response);	
			} catch (Exception ex2) {
				ex.printStackTrace();
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
