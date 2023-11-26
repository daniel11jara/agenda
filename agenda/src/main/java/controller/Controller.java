package controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import model.DAO;//importando a classe DAO
import model.JavaBeans;


@WebServlet(urlPatterns = {"/Controller", "/main", "/insert", "/select"})
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
		}else if (action.equals("/select")) {//aula 18
			listarContato(request, response); 
		}else {
			response.sendRedirect("index.html");
			}
		}
	
	
	//listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//redirecionando ao documento agenda.jsp
		//response.sendRedirect("agenda.jsp");
		
		//Criando um objeto que ira receber os dados do JavaBeans - aula 16
		ArrayList<JavaBeans> lista = dao.listarContatos();
		
		//teste de recebimento da lista
		/*for (int i = 0; i < lista.size(); i++) {
			System.out.println(lista.get(i).getIdcon());
			System.out.println(lista.get(i).getNome());
			System.out.println(lista.get(i).getFone());
			System.out.println(lista.get(i).getEmail());*/
		
		
		//encaminhando a lista ao documento agenda.jsp - aula 17
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		
		//passo 7
		//encaminhado o objeto lista ao documento agenda.jsp
		rd.forward(request, response);
		
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
			
			//invocar o metodo inserirContato passando o objeto contato - aula 15
			dao.inserirContato(contato);
			
			//passo 10 - aula 15
			//redirecionando para o documento agenda.jsp
			response.sendRedirect("main");
		}
		
		//editar contato - aula 18
		protected void listarContato(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
			//variavel que esta recebendo o ID do formulario do contato que vai ser editado
			//passo 1 do diagrama da aula 18
			String idcon = request.getParameter("idcon");
			
			//setar a variavel javabeans - passo 2 da aula 18
			contato.setIdcon(idcon);
			
			//executar o metodo selecionarContato do DAO - aula 19
			//passo 3 da aula 18
			dao.selecionarContato(contato);
			
			//setar os atributos do formulario com o conteudo javabeans - aula 20
			//passo 9 e 10
			request.setAttribute("idcon", contato.getIdcon());
			request.setAttribute("nome", contato.getNome());
			request.setAttribute("fone", contato.getFone());
			request.setAttribute("email", contato.getEmail());
			
			//encaminhar ao documento editar.jsp
			RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
			rd.forward(request, response);
			
		}

}
