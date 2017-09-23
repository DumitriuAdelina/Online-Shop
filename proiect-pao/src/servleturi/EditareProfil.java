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
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import entitati.Useri;

/**
 * Servlet implementation class EditareProfil
 */
@WebServlet("/EditareProfil")
public class EditareProfil extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/bd")
    private DataSource dbRes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EditareProfil() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		try {
			con = dbRes.getConnection();
			int iduser=(int) request.getSession().getAttribute("iduser");
				
			Useri u=new Useri(request.getParameter("nume_prenume"),request.getParameter("parola"),request.getParameter("email"),request.getParameter("telefon"),request.getParameter("adresa"),request.getParameter("tip"));
				
			PreparedStatement ps=con.prepareStatement("update useri set nume_prenume=?, parola=?, email=?, telefon=?, adresa=? where id_user=?");
			
			ps.setString(1,u.getNume_prenume()); 
			ps.setString(2,u.getParola()); 
			ps.setString(3,u.getEmail()); 
			ps.setString(4,u.getTelefon()); 
			ps.setString(5,u.getAdresa());
			ps.setInt(6, iduser);
				
			ps.executeUpdate();
				
			HttpSession session=request.getSession(true);
			session.removeAttribute("userCurent");	
			session.setAttribute("userCurent", u);
			
			response.sendRedirect("index.jsp");	
		}
		catch (SQLException e) {
			e.printStackTrace();
		}
		finally{
			if(con!=null)
				try {
					con.close();
				} 
				catch (SQLException e) {
					
					e.printStackTrace();
				}
		}
	}

}
