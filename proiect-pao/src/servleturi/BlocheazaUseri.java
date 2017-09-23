package servleturi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Servlet implementation class BlocheazaUseri
 */
@WebServlet("/BlocheazaUseri")
public class BlocheazaUseri extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/bd")
    private DataSource dbRes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BlocheazaUseri() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		try {
			con = dbRes.getConnection();
			int iduser=Integer.parseInt(request.getParameter("userBlocat"));
			PreparedStatement ps1=con.prepareStatement("select parola from useri where id_user="+iduser);
			ResultSet rs1=ps1.executeQuery(); 
			
			if(rs1.next())	
			{
				String parola=rs1.getString("parola");
				if(!(parola.charAt(0)=='-' && parola.charAt(1)=='1'))
				{
					PreparedStatement ps=con.prepareStatement("update useri set parola=concat('-1',parola) where id_user=?");
					ps.setInt(1, iduser);
					ps.executeUpdate();	
				}
			}
			request.getRequestDispatcher("index.jsp").include(request, response);
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

}
