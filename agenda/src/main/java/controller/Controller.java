package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.DAO;//importando a classe DAO


@WebServlet(urlPatterns = {"/Controller", "/main"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//criando o objeto DAO
	DAO dao = new DAO();
       
    
    public Controller() {
        super();
    }

	//metodo principal do servlet
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		//vamos redirecionar as requisicoes que foram configuradas no @WebServlet para metodos especificos
		
		//esta variavel vai armazenar o caminho da requisicao
		String action = request.getServletPath();
		System.out.println(action);
		
		if (action.equals("/main")) {
			contatos(request, response);
		}
	}
	
	//listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//redirecionando ao documento agenda.jsp
		response.sendRedirect("agenda.jsp");
	}

}
