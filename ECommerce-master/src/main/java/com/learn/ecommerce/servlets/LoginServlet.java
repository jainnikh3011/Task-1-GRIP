
package com.learn.ecommerce.servlets;

import com.learn.ecommerce.dao.CategoryDao;
import com.learn.ecommerce.dao.UserDao;
import com.learn.ecommerce.entities.Category;
import com.learn.ecommerce.entities.User;
import com.learn.ecommerce.helper.FactoryProvider;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @author ashut
 */
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
           
         String email=request.getParameter("email");
         String password=request.getParameter("password");
         
         
            
       //validation form UserDao
       
      UserDao userDao=  new UserDao(FactoryProvider.getFactory());
         User user=userDao.getUserByEmailAndPassword(email, password);
          
         // System.out.println(user);
            HttpSession httpSession=request.getSession();
       if(user==null){
          System.out.println("<h1> Invalid details</h1>");
           
            
                httpSession.setAttribute("message", "Invalid Details !! Try with another one.");
                response.sendRedirect("login.jsp");
               return;
           
       }     
        else {
           System.out.println("<h1> welcome   "+user.getUserName()+"</h1>");
           
           
             httpSession.setAttribute("current-user", user);
             
             if (user.getUserType().equals("admin")) {
                 
                 
                 
                 //getting list of Categories
                 // again it is used in AddCategoryServlet
                 
                  try {
                 
               CategoryDao cdao=new CategoryDao(FactoryProvider.getFactory());
        List<Category> list=cdao.getCategories();
            HttpSession ss=request.getSession();
           ss.setAttribute("categoryList",list);
                
           
//           RequestDispatcher dispatcher = request.getRequestDispatcher("admin.jsp");
//
//            if (dispatcher != null){
//
//  
//                dispatcher.forward(request, response);
//                
//                
//
//           } 
                response.sendRedirect("admin.jsp");
                
                
            } catch (Exception e) {
                e.printStackTrace();
            }
                 
                 
                 
                 
                 
                 
                // response.sendRedirect("admin.jsp");
               
           } else if(user.getUserType().equals("normal")) {
               response.sendRedirect("normalUser.jsp");
               
           }else{
                 System.out.println("user type not found");
           }
           
           
           
          //admin.jsp
          
        
          
          
          
          
          
          
          //normalUser.jsp
           
           
           
           
           
           
           
           
         }
            
            
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
