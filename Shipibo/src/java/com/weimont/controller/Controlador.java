package com.weimont.controller;

import com.weimont.config.GenerarSerie;
import com.weimont.model.Cliente;
import com.weimont.model.ClienteDao;
import com.weimont.model.Empleado;
import com.weimont.model.EmpleadoDao;
import com.weimont.model.Producto;
import com.weimont.model.ProductoDao;
import com.weimont.model.Venta;
import com.weimont.model.VentaDao;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author montespierreg
 */
public class Controlador extends HttpServlet {
    
    Empleado em = new Empleado();
    EmpleadoDao edao = new EmpleadoDao();
    ClienteDao cdao = new ClienteDao();
    Cliente c = new Cliente();
    Producto p = new Producto();
    ProductoDao pdao = new ProductoDao();
    int ide;
    int idc;
    int idp;
    
    //Carrito de venta - de la pantalla del carrito
    Venta v = new Venta();
    List<Venta> lista = new ArrayList<>();
    int item;
    int cod;
    String descripcion;
    double precio;
    int cant;
    double subtotal;
    double totalPagar;
    
    String numeroserie;
    VentaDao vdao = new VentaDao();
    
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String menu = request.getParameter("menu");
        
        String accion = request.getParameter("accion");
        
        if(menu.equals("Principal")){
            request.getRequestDispatcher("Principal.jsp").forward(request, response);
        }
        
        if(menu.equals("Empleado")){
            
            switch(accion){
                case "Listar":
                    List lista = edao.listar();
                    request.setAttribute("empleados", lista);
                    break;
                    
                case "Agregar":
                    String dni = request.getParameter("txtDni");
                    String nom = request.getParameter("txtNombres");
                    String tel = request.getParameter("txtTel");
                    String est = request.getParameter("txtEstado");
                    String user = request.getParameter("txtUser");
                    em.setDni(dni);
                    em.setNom(nom);
                    em.setTel(tel);
                    em.setEstado(est);
                    em.setUser(user);
                    edao.agregar(em);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);;
                    break;
                    
                case "Editar":
                    ide=Integer.parseInt(request.getParameter("id"));    
                    Empleado e = edao.listarId(ide);
                    request.setAttribute("empleado", e);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);;
                    break;
                    
                case "Actualizar":
                    String dni1 = request.getParameter("txtDni");
                    String nom1 = request.getParameter("txtNombres");
                    String tel1 = request.getParameter("txtTel");
                    String est1 = request.getParameter("txtEstado");
                    String user1 = request.getParameter("txtUser");
                    em.setDni(dni1);
                    em.setNom(nom1);
                    em.setTel(tel1);
                    em.setEstado(est1);
                    em.setUser(user1);
                    em.setId(ide);
                    edao.actualizar(em);
                    
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);      
                    break;    
                    
                case "Delete":         
                    ide=Integer.parseInt(request.getParameter("id"));
                    edao.delete(ide);
                    request.getRequestDispatcher("Controlador?menu=Empleado&accion=Listar").forward(request, response);        
                    break;    
                default:
                    throw new AssertionError();
            }
            request.getRequestDispatcher("Empleado.jsp").forward(request, response);
        }
        
        if(menu.equals("Cliente")){
            request.getRequestDispatcher("/clientes/listado.jsp").forward(request, response);
        }
        
        
        if(menu.equals("Producto")){
            request.getRequestDispatcher("/productos/listado.jsp").forward(request, response);
        }
        
        
        if(menu.equals("NuevaVenta")){
            switch(accion){
                case "BuscarCliente":
                    String dni = request.getParameter("codigocliente");
                    c.setDni(dni);
                    c = cdao.buscar(dni);
                    request.setAttribute("c", c);
                    break;
                case "BuscarProducto":
                    int id = Integer.parseInt(request.getParameter("codigoproducto"));
                    p = pdao.listarId(id);
                    request.setAttribute("c", c);
                    request.setAttribute("lista", lista);
                    request.setAttribute("producto", p);
                    request.setAttribute("totalpagar", totalPagar);
                    break;
                case "Agregar":
                    request.setAttribute("c", c);
                    totalPagar = 0.0;
                    //item = item + 1;
                    cod = p.getId();
                    descripcion = request.getParameter("nomproducto");
                    precio = Double.parseDouble(request.getParameter("precio"));
                    cant =Integer.parseInt(request.getParameter("cant"));
                    subtotal = precio * cant;
                    v = new Venta();
                    
                    int encontrado = 0;
                    
                    for(Venta actual: lista){
                        if (actual.getId().equals(cod)){
                            int otraCant = actual.getCantidad() + cant;
                            double otroSubtotal = actual.getSubtotal() + subtotal;
                            actual.setCantidad(otraCant);
                            actual.setSubtotal(otroSubtotal);
                            encontrado = 1;
                            
                            v = new Venta();
                            
                            for(int i =0; i < lista.size(); i++){
                            totalPagar = totalPagar + lista.get(i).getSubtotal();
                            }
                          
                            
                            request.setAttribute("lista", lista);
                            request.setAttribute("totalpagar", totalPagar);
                            //request.getRequestDispatcher("/ventas/registrarVenta.jsp").forward(request, response);
                            break;
                        }
                    }
                    
                    if (encontrado == 0){
                        item = item + 1;
                        v = new Venta();
                        v.setItem(item);
                        v.setIdproducto(cod);
                        v.setDescripcionP(descripcion);
                        v.setPrecio(precio);
                        v.setCantidad(cant);
                        v.setSubtotal(subtotal);
                        lista.add(v);
                        
                        for(int i =0; i < lista.size(); i++){
                            totalPagar = totalPagar + lista.get(i).getSubtotal();
                        }
                            
                        request.setAttribute("totalpagar", totalPagar);
                        request.setAttribute("lista", lista);
                        //break;
                    }
                    
                    request.getRequestDispatcher("/ventas/registrarVenta.jsp").forward(request, response); 
                    break;
                    
                case "GenerarVenta":
                    //Guardar venta
                    v.setIdcliente(c.getId());
                    v.setIdempleado(1);
                    v.setNumserie(numeroserie);
                    v.setFecha("2019-06-14");
                    v.setMonto(totalPagar);
                    v.setEstado("1");
                    vdao.guardarVenta(v);
                    
                    //Guardar Detalle Venta
                    int idv = Integer.parseInt(vdao.IdVentas());
                    for(int i = 0; i <lista.size(); i++){
                        v = new Venta();
                        v.setId(idv);
                        v.setIdproducto(lista.get(i).getIdproducto());
                        v.setCantidad(lista.get(i).getCantidad());
                        v.setPrecio(lista.get(i).getPrecio());
                        vdao.guardarDetalleventas(v);
                    }
                default:
                    numeroserie = vdao.GenerarSerie();
                    if(numeroserie==null){
                        numeroserie = "00000001";
                        request.setAttribute("nserie", numeroserie);
                    }else{
                        int incrementar = Integer.parseInt(numeroserie);
                        GenerarSerie gs = new GenerarSerie();
                        numeroserie = gs.NumeroSerie(incrementar);
                        request.setAttribute("nserie", numeroserie);
                    }
                    request.getRequestDispatcher("/ventas/registrarVenta.jsp").forward(request, response);
            }
            request.getRequestDispatcher("/ventas/registrarVenta.jsp").forward(request, response);
        }
        
        
        
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Controlador</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Controlador at " + request.getContextPath() + "</h1>");
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
