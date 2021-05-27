package servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Map;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import model.DBModel;
import model.User;

@WebServlet(name = "RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

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
        request.setCharacterEncoding("UTF-8");

        String userName = request.getParameter("username");
        String pass = request.getParameter("pass");
        String name = request.getParameter("name");
        boolean elfogad = Boolean.parseBoolean(request.getParameter("agree"));

        Context initCtx;
        String hiba = "";
        if (elfogad) {
            try {
                initCtx = new InitialContext();
                Context envCtx = (Context) initCtx.lookup("java:comp/env");
                DataSource ds = (DataSource) envCtx.lookup("jdbc/chatdb");
                Connection conn = ds.getConnection();

                DBModel model = new DBModel(conn);
                User user = new User(userName, pass, "", name);
                model.addUser(user);

                conn.close();
            } catch (NamingException ex) {
                hiba += ex.toString();
            } catch (SQLException ex) {
                hiba += ex.toString();
            }
        } else {
            hiba += "A regisztrációhoz el kell fogadni a szabályzatot!";
        }

        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet RegisterServlet</title>");
            out.println("</head>");
            out.println("<body>");
            
            if (hiba.isEmpty()) {
                out.println("<h1>Sikeres regisztráció az alábbi adatokkal:</h1>");
                out.println("User név: "+userName+" <br/>");
                out.println("Teljes név: "+name);
            } else {
                out.println("<h1>"+hiba+"</h1>");
            }
            
//            out.println("Név: " + name + "<br/>");
//            out.println("Jelszó: " + pass + "<br/>");
//            // out.println("Neme: "+pMap.get("gender")[0]+"<br/>");
//            
//            out.println("Elfogad: " + request.getParameter("agree") + "<br/>");
//            out.println("Elfogadom a feltételeket: " + (elfogad ? "IGEN" : "NEM") + "<br/>");
//
//            for (String s : pMap.keySet()) {
//                out.println(s + ": " + pMap.get(s)[0] + "<br/>");
//            }
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
