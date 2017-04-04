package br.ufpe.clienteautenticacao;

import java.io.IOException;
import java.net.UnknownHostException;
import java.util.Scanner;

import communication.SocketUtil;

/**
 * Hello world!
 *
 */
public class ClienteAuteticacaoMain {
	public static void main(String[] args) {
		Scanner leTeclado = new Scanner(System.in);

		System.out.println("Por favor, digite o login para ser cadastrado");
		String login = leTeclado.next();

		System.out.println("Por favor, digite a senha para ser cadastrado");
		String senha = leTeclado.next();

		/*
		 * conecta no servidor para criptografa a senha
		 */
		SocketUtil sc = new SocketUtil("172.20.10.3", 1234);
		try {
			sc.conectServer();
			sc.sendMenssage(login + "#" + senha);

			String retorno = sc.getMessage();

			System.out.println("Mensagem recebida " + retorno);
			sc.close();

		} catch (UnknownHostException e) {
			System.err.println("Não encontrou o host");
		} catch (IOException e) {
			System.err.println("Não conectou o host");
		}
	}
}