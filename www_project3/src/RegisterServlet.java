import java.io.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class RegisterServlet extends HttpServlet {
	public void doPost (HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
		String name=request.getParameter("username");
		String pass=request.getParameter("password");
		String pass2=request.getParameter("password2");
		String firstname=request.getParameter("firstname");
		String lastname=request.getParameter("lastname");

		if (pass == pass2) {
			String password=pass;
		}

		response.setContentType("text/html");
        	PrintWriter out = response.getWriter();
       		out.println("<html>");
        	out.println("<head>");
        	out.println("<title>Hello World!</title>");
        	out.println("</head>");
        	out.println("<body>");
        	out.println("<h1>Hesfpomespmfpesmpfomorld!</h1>");
        	out.println(name);
		out.println(pass);
		out.println("</body>");
        	out.println("</html>");	
	}
}
