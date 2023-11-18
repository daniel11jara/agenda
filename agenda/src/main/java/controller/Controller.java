package controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import model.DAO;//importando a classe DAO
import model.JavaBeans;


@WebServlet(urlPatterns = {"/Controller", "/main", "/insert"})
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	//criando o objeto DAO
	DAO dao = new DAO();
	
	//criando o objeto no javabeans
	//esse objeto acessa os metodos publicos dessa classe, os setNome, setFone e setEmail
	JavaBeans contato = new JavaBeans();
       
    
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
		}else if (action.equals("/insert")){//se o conteudo da variavel action for insert, vai redirecionar ao metodo responsavel por encaminhar a camda Model
			novoContato(request, response);
		}else {
			response.sendRedirect("index.html");
		}
	}
	
	//listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//redirecionando ao documento agenda.jsp
		response.sendRedirect("agenda.jsp");
	}
	//passo 5
	//Novo contatos
		protected void novoContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			System.out.println(request.getParameter("nome"));
			System.out.println(request.getParameter("fone"));
			System.out.println(request.getParameter("email"));
			
			contato.setNome(request.getParameter("nome"));
			contato.setFone(request.getParameter("fone"));
			contato.setEmail(request.getParameter("email"));
		}

}
