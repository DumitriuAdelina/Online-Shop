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
import javax.sql.DataSource;

/**
 * Servlet implementation class DeblocheazaUseri
 */
@WebServlet("/DeblocheazaUseri")
public class DeblocheazaUseri extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/bd")
    private DataSource dbRes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DeblocheazaUseri() {
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
			int iduser=Integer.parseInt(request.getParameter("userDeblocat"));
			PreparedStatement ps1=con.prepareStatement("select parola from useri where id_user="+iduser);
			ResultSet rs1=ps1.executeQuery(); 
			
			if(rs1.next())	
			{
				String parola=rs1.getString("parola");
				if(parola.charAt(0)=='-' && parola.charAt(1)=='1')
				{
					PreparedStatement ps=con.prepareStatement("update useri set parola=substr(parola,3) where id_user=?");
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
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
