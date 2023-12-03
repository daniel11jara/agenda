package controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import model.DAO;//importando a classe DAO
import model.JavaBeans;

// TODO: Auto-generated Javadoc
/**
 * The Class Controller.
 */
@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
public class Controller extends HttpServlet {
	
	/** The Constant serialVersionUID. */
	private static final long serialVersionUID = 1L;

	/** The dao. */
	// criando o objeto DAO
	DAO dao = new DAO();

	// criando o objeto no javabeans
	// esse objeto acessa os metodos publicos dessa classe, os setNome, setFone e
	/** The contato. */
	// setEmail
	JavaBeans contato = new JavaBeans();

	/**
	 * Instantiates a new controller.
	 */
	public Controller() {
		super();
	}

	/**
	 * Do get.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// metodo principal do servlet
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// vamos redirecionar as requisicoes que foram configuradas no @WebServlet para
		// metodos especificos

		// esta variavel vai armazenar o caminho da requisicao
		// quais requisicoes esta armazenando? "/main" - "/insert" - "/select"
		String action = request.getServletPath();
		System.out.println(action);

		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			// se o conteudo da variavel action for insert, vai redirecionar ao metodo
			// responsavel por encaminhar a camda Model
			novoContato(request, response);// aula 14
		} else if (action.equals("/select")) {// aula 18
			listarContato(request, response);
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			removerContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
		} else {
			response.sendRedirect("index.html");
		}
	}

	/**
	 * Contatos.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// listar contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// redirecionando ao documento agenda.jsp
		// response.sendRedirect("agenda.jsp");

		// Criando um objeto que ira receber os dados do JavaBeans - aula 16
		ArrayList<JavaBeans> lista = dao.listarContatos();

		// teste de recebimento da lista
		/*
		 * for (int i = 0; i < lista.size(); i++) {
		 * System.out.println(lista.get(i).getIdcon());
		 * System.out.println(lista.get(i).getNome());
		 * System.out.println(lista.get(i).getFone());
		 * System.out.println(lista.get(i).getEmail());
		 */

		// encaminhando a lista ao documento agenda.jsp - aula 17
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");

		// passo 7
		// encaminhado o objeto lista ao documento agenda.jsp
		rd.forward(request, response);

	}

	// passo 5
	/**
	 * Novo contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// Novo contatos - aula 14
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));

		// passo 5 da aula 14 - CREATE
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// passo 6 da aula 15 - CREATE
		// invocar o metodo inserirContato passando o objeto contato - inserindo os
		// dados no banco
		dao.inserirContato(contato);

		// passo 10 - aula 15
		// redirecionando para o documento agenda.jsp
		response.sendRedirect("main");
	}

	/**
	 * Listar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// editar contato - aula 18
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// variavel que esta recebendo o ID do formulario do contato que vai ser editado
		// passo 1 do diagrama da aula 18
		String idcon = request.getParameter("idcon");

		// setar a variavel javabeans - passo 2 da aula 18
		contato.setIdcon(idcon);

		// executar o metodo selecionarContato do DAO - aula 19
		// passo 3 da aula 18
		dao.selecionarContato(contato);

		// setar os atributos do formulario com o conteudo javabeans - aula 20
		// passo 9 e 10
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());

		// encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);

	}

	/**
	 * Editar contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// setar as variaveis javabeans
		// passo 13 e 14
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));

		// executar o metodo alterarContato - aula 21
		// passo 15
		dao.alterarContato(contato);

		// redirecionar para o documento agenda.jsp (atualizando )
		response.sendRedirect("main");
	}

	/**
	 * Remover contato.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	// remover contato - aula 23
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// recebimento do id do contato a ser excluido(validador.js)
		String idcon = request.getParameter("idcon");

		// passo 3
		// setar a variavel idcons do javabeans
		contato.setIdcon(idcon);

		// passo 4
		// executar o metodo deletarContato (DAO) passando o objeto contato como
		// parametro
		dao.deletarContato(contato);

		// redirecionar para o documento agenda.jsp (atualizando )
		response.sendRedirect("main");
	}
	
	/**
	 * Gerar relatorio.
	 *
	 * @param request the request
	 * @param response the response
	 * @throws ServletException the servlet exception
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	//gerar relatorio em pdf
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		Document documento = new Document();
		
		try {
			//tipo de conteudo
			response.setContentType("apllication/pdf");
			
			//nome do documento
			response.addHeader("Content-Disposition","inline; filename=" + "contatos.pdf");
			
			//cria o documento
			PdfWriter.getInstance(documento, response.getOutputStream());
			
			//abre o documento gerando o conteudo
			documento.open();
			documento.add(new Paragraph("Lista de Contatos:"));
			documento.add(new Paragraph("  "));
			
			//(3) significa 3 colunas
			PdfPTable tabela = new PdfPTable(3);
			
			//cabecalho
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			
			//colocando os contatos na tabela
			ArrayList<JavaBeans> lista = dao.listarContatos();
			
			//percorrendo o vetor dinamico de contatos
			for (int i = 0; i < lista.size(); i++) {
				tabela.addCell(lista.get(i).getNome());
				tabela.addCell(lista.get(i).getFone());
				tabela.addCell(lista.get(i).getEmail());
			}
			
			
			documento.add(tabela);
			documento.close();
			
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}
	}
}
