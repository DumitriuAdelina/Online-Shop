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

/**
 * Servlet implementation class afisProdServlet
 */
@WebServlet("/afisProdServlet")
public class afisProdServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	@Resource(name = "jdbc/bd")
    private DataSource dbRes;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public afisProdServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		Connection con=null;
		try{
			List<Produse> listaProduse = new ArrayList<Produse>();
			con=dbRes.getConnection();
			
			int id_categ = Integer.parseInt(request.getParameter("idCategorie"));
			PreparedStatement ps = con.prepareStatement("select * from produse where id_categorie="+id_categ);

	        ResultSet rs;
	        rs = ps.executeQuery();
	        while(rs.next()){
	        	Produse produs=new Produse();
	        	produs.setId_produs(rs.getInt("id_produs"));
	            produs.setNume_produs(rs.getString("nume_produs"));
	            produs.setStoc(rs.getInt("stoc"));
	            produs.setPret(rs.getInt("pret"));
	            produs.setId_categorie(rs.getInt("id_categorie"));
	            listaProduse.add(produs);
	        	}
	        
	        request.getSession().setAttribute("listaProduse",listaProduse);
	        response.sendRedirect("afisareProduse.jsp");
	        
	        HttpSession session1=request.getSession();
			session1.removeAttribute("negasit");
			session1.removeAttribute("gasit");	       
	      	
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
	}
}
