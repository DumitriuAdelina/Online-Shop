package servleturi;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import entitati.Produse;
import entitati.Useri;

/**
 * Servlet implementation class afisUseriServlet
 */
@WebServlet("/afisUseriServlet")
public class afisUseriServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/bd")
    private DataSource dbRes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public afisUseriServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection con=null;
		try{
			List<Useri> listaUseri = new ArrayList<Useri>();
			con=dbRes.getConnection();
			
			PreparedStatement ps = con.prepareStatement("select * from useri");

	        ResultSet rs;
	        rs = ps.executeQuery();
	        while(rs.next()){
	        	Useri user=new Useri();
	        	user.setId_user(rs.getInt("id_user"));
	        	user.setParola(rs.getString("parola"));
	        	user.setNume_prenume(rs.getString("nume_prenume"));
	        	user.setEmail(rs.getString("email"));
	        	user.setTelefon(rs.getString("telefon"));
	        	user.setAdresa(rs.getString("adresa"));
	        	user.setTip(rs.getString("tip"));
	        	
	            listaUseri.add(user);
	        	}
	        
	        request.getSession().setAttribute("listaUseri",listaUseri);
	        response.sendRedirect("afisareUseri.jsp");
	          
	      	
		    }
		catch (SQLException e){
			System.out.println(e);
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
