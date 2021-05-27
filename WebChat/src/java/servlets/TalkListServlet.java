package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import model.DBModel;
import model.Talk;
import model.User;

@WebServlet(name = "TalkListServlet", urlPatterns = {"/vedett/talks"})
public class TalkListServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        String hiba = "";
        List<Talk> talks = null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd HH:mm:ss");

        Context initCtx;
        try {
            initCtx = new InitialContext();
            Context envCtx = (Context) initCtx.lookup("java:comp/env");
            DataSource ds = (DataSource) envCtx.lookup("jdbc/chatdb");
            Connection conn = ds.getConnection();

            DBModel model = new DBModel(conn);
            talks = model.getTalks(user);
            
            conn.close();
        } catch (NamingException ex) {
            hiba += ex.toString();
        } catch (SQLException ex) {
            hiba += ex.toString();
        }
     
        try (PrintWriter out = response.getWriter()) {

            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet TalkListServlet</title>");
            out.println("</head>");
            out.println("<body>");
            if (hiba.isEmpty()) {
                out.println("<h1>" + user.getName() + " beszélgetéseinek listája </h1>");
                //beszélgetések listázása egy táblázatban
                out.println("<table>");
                out.println("<th>Beszélgetés ideje</th>");
                out.println("<th>Egyik résztvevő</th>");
                out.println("<th>Másik résztvevő</th>");
                for (Talk t : talks) {
                    out.println("<tr>");
                    
                    out.println("<td>"+sdf.format(t.getStartTime())+"</td>");
                    out.println("<td>"+t.getUser1().getName()+"</td>");
                    out.println("<td>"+t.getUser2().getName()+"</td>");
                    
                    out.println("</tr>");
                }
                out.println("</table>");
                out.println("<a href=\"vedett/logout\">Kijelentkezés</a>");
            } else {
                out.println("<h1>" + hiba + "</h1>");
            }

            out.println("</body>");
            out.println("</html>");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
