package swDBService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Servlet implementation class Booking
 */
@WebServlet("/Booking")
public class Booking extends HttpServlet {
    private static final long serialVersionUID = 1L;
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Booking() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
        doPost(request, response);
		//response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		//doGet(request, response);
		SWDB mediator = new SWDB();

		if(request.getParameter("reqType").toString().equals("doQuery")){
			String param01 = request.getParameter("param01").toString();
			String param02 = request.getParameter("param02").toString();
			String param03 = request.getParameter("param03").toString();
            String param04 = request.getParameter("param04").toString();
            String param05 = request.getParameter("param05").toString();
            String param06 = request.getParameter("param06").toString();
            String param07 = request.getParameter("param07").toString();
            String param08 = request.getParameter("param08").toString();
            String param09 = request.getParameter("param09").toString();
			String pathToDB = this.getServletContext().getRealPath("/BookingDB.ttl");
			mediator.searchForResult(pathToDB, param01, param02, param03, param04, param05, param06, param07, param08, param09);
        }

        PrintWriter out = response.getWriter();
		out.write(mediator.getResult());
        out.flush();
        out.close();
    }

}
