/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 * @author Rangana De Silva and Kalana Suraweera
 */

import java.io.IOException;
import java.io.PrintWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


@WebServlet(urlPatterns = {"/UpdateGame"})
public class GameServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    
    int number_sessions=0;
    private final GameLogic backend=new GameLogic();
    
    @Override
    public void init(final ServletConfig config) {
        
        Logger.getGlobal().log(Level.INFO, "Started game servlet");
    }
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GameServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GameServlet at " + request.getContextPath() + "</h1>");
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
       
        //processRequest(request, response);
        
       HttpSession sess = request.getSession(true);
       if(sess.isNew() && number_sessions<4) {
           
               synchronized(this) {
                sess.setAttribute("id",number_sessions );
                this.number_sessions++;
               }
               
              //System.out.println(number_sessions);
//              System.out.println(sess.getAttribute("id"));
           
       }
       
       response.setContentType("text/event-stream,charset=UTF-8");
        try (PrintWriter writer = response.getWriter()) {
            while (!Thread.interrupted())
                synchronized (this) {
                    

                    writer.print("data:");
                    writer.println(backend.getReply());
                    //System.out.println(backend.getReply());
                    writer.println();
            
                    writer.flush();
                    this.wait();
                }
        } catch (InterruptedException e) {
            Logger.getGlobal().log(Level.INFO, "Terminating updates.");
            response.setStatus(HttpServletResponse.SC_GONE);
        }
        
       
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
        //processRequest(request, response);
       response.setContentType("text/event-stream,charset=UTF-8");
       String output = request.getParameter("keypress");
        HttpSession sess = request.getSession(true);
        //System.out.println(sess.getAttribute("id"));
//            try {
         
                synchronized (this) {
                   
                    //System.out.println(output);
                    //
                    if(number_sessions==4)
                        backend.updatePosition(output,(int)sess.getAttribute("id"));
                    else
                        System.out.println("waiting for players to join");
                    this.notifyAll();
                }
        
        
//                Thread.sleep(1000);
//            } catch (InterruptedException e) {
//                Logger.getGlobal().log(Level.INFO, "exception");
//                
//            }
        
        //System.out.println(output);
        
        
        
    }

    /**
     * Returns a short description of the servlet.b  
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
