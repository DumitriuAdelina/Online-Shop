package servleturi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import entitati.Useri;

/**
 * Servlet implementation class RegisterServlet
 */
@WebServlet("/RegisterServlet")
public class RegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/bd")
    private DataSource dbRes;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RegisterServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		try {
			con = dbRes.getConnection();
			Useri u=new Useri(request.getParameter("nume_prenume"),request.getParameter("parola"),request.getParameter("email"),request.getParameter("telefon"),request.getParameter("adresa"),request.getParameter("tip"));
			PreparedStatement ps=con.prepareStatement("insert into useri(nume_prenume, parola, email, telefon, adresa, tip) values(?,?,?,?,?,?)");
			ps.setString(1,u.getNume_prenume()); 
			ps.setString(2,u.getParola()); 
			ps.setString(3,u.getEmail()); 
			ps.setString(4,u.getTelefon()); 
			ps.setString(5,u.getAdresa()); 
			ps.setString(6,u.getTip()); 
			
			ps.executeUpdate();
			
			response.sendRedirect("index.jsp");		
		}
		catch (SQLException e) {
			System.out.println(e);
		}
	}

}
