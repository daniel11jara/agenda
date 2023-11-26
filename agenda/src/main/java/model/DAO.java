package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	
	//essa linha so funciona com o driver copiado na pasta lib
	private String driver = "com.mysql.cj.jdbc.Driver";
	//informando aqui o ip do servidor, o nome do banco de dados e o fuso horario de referencia universal
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?userTimezone=true&serverTimezone=UTC";
	//usuario que pode acessar o banco de dados
	private String user = "root";
	//senha
	private String password = "admin";
	
	//usando a classe Connection para fazer o metodo conectar
	private Connection conectar() {
		
		//objeto con para estabelecer uma conexao com o banco de dados
		Connection con = null;
		
		try {
			//essa linha vai ler o drive do banco de dados
			Class.forName(driver);//buscanco o driver colocado na linha 8
			
			//DriverManager e uma classe que vai gerenciar o drive
			//pegando as variaves definidas acima
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			//imprimido na tela e excecao
			System.out.println(e);
			return null;
		}
	}
	
	//CREATE - aula 15
	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos (nome, fone, email) values (?, ?, ?)";
		
		try {//passos 7  e 8
			//1 - abrir a conexao com o banco
			Connection con = conectar();
			
			//2 - preparando a query para a execucao no banco de dados
			PreparedStatement pst = con.prepareStatement(create);
			
			//3 - substituindo os parametros (?) pelo dados salvos temporariamente no javabeans
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			
			//passo 9
			//4 - executando a query - inserir os dados no banco
			pst.executeUpdate();
			
			//5 - encerrando a conexao com o banco
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	//CRUD READ - aula 16
	public ArrayList<JavaBeans> listarContatos(){
		
		//criando um objeto para acessar a classe JavaBeans
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		
		String read = "select * from contatos order by nome";
		
		try {
			//1 - conectando com o banco de dados
			Connection con = conectar();
			
			//2 - preparando a query para o java executar no servidor
			PreparedStatement pst = con.prepareStatement(read);
			
			//ResulSet faz parte do pacote JDBC, usado para armazenar o retorno do banco de dados temporariamente em um objeto
			//o objeto rs executa a query feita na linha 70
			//Passo 3 do diagrama da aula 16
			ResultSet rs = pst.executeQuery();
			
			//para exibir o resultado usaremos o while
			//laço sera executado enquanto houver contatos
			while(rs.next()) {
				//variaveis de apoio que recebem os dados do banco
				
				//variavel idcon atraves do objeto rs recebe o primeiro campo do banco de dados
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				
				//preenchendo o ArrayList
				//objeto contatos adiciona nas variaveis da classe JavaBeans o conteudo dessas variaveis
				//Passo 5 - aula 16
				contatos.add(new JavaBeans(idcon, nome, fone, email));
			}
			//encerrando a conexao com o banco
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}
	
	//CRUD UPDATE - aula 19
	//selecionando o contato
	public void selecionarContato(JavaBeans contato) {
		
		String read2 = "select * from contatos where idcon = ?";
		
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			//passo 4 da aula 18 - contato.getIdcon()
			//passo 5 da aula 18 - pst.setString
			pst.setString(1, contato.getIdcon());
			
			//passo 6 da aula 18 - pst.executeQuery()
			ResultSet rs = pst.executeQuery();
			
			while (rs.next()) {
				//setando as variaves JavaBeans
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
			con.close();
			
		} catch (Exception e) {
			System.out.println(e);
		}
	}
}
