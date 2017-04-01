package br.ufpe.servidorautenticacao;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import br.ufpe.servidorautenticacao.communication.SocketUtil;
import br.ufpe.servidorautenticacao.dao.UsuarioDAO;
import br.ufpe.servidorautenticacao.model.Usuario;
import br.ufpe.servidorautenticacao.util.HibernateUtil;

public class ServidorAutenticacaoMain {
	public static void main(String[] args) {

		try {
			/*
			 * abre a porta 
			 */
			ServerSocket servidor = new ServerSocket(12350);
			System.out.println("Servidor ouvindo a porta 12350");

			while (true) {
				Socket cliente = servidor.accept();
				System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());

				/*
				 * recebe a mensagem
				 */
				Scanner entrada = new Scanner(cliente.getInputStream());
				String loginSenha = entrada.next();
				System.out.println("mensagem recebida do cliente:" + loginSenha);
				
				String[] loginSenhaAux = loginSenha.split("#");
				
				/*
				 * vai receber o usuario e senha do cliente
				 */
				String login = loginSenhaAux[0];
				String senha = loginSenhaAux[1];
				String senhaCriptografada;

				UsuarioDAO ud = new UsuarioDAO();

				/*
				 * conecta no servidor para criptografa a senha
				 */
				SocketUtil sc = new SocketUtil("127.0.0.1", 12351);
				try {
					sc.conectServer();
					sc.sendMenssage(senha);

					senhaCriptografada = sc.getMessage();

					System.out.println("Recebeu a senha criptografa do servidor: " + senhaCriptografada);
					sc.close();

					/*
					 * Salva no banco de dados o usuario e a senha criptografada
					 */
					Usuario u = new Usuario(login, senhaCriptografada);
					ud.save(u);

				} catch (UnknownHostException e) {
					System.err.println("Não encontrou o host");
				} catch (IOException e) {
					System.err.println("Não conectou o host");
				}

				/*
				 * lista dos usuarios cadastrados no banco de dados
				 */
				for (Usuario temp : ud.findAll()) {
					System.out.println(temp.getLogin() + " -" + temp.getSenha());
				}

				ud.close();
				HibernateUtil.close();

				/*
				 * envia resposta usando o mesmo scoket
				 */
				PrintStream saida;
				saida = new PrintStream(cliente.getOutputStream());
				saida.println("OK");

				cliente.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
