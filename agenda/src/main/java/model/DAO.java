package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
}
