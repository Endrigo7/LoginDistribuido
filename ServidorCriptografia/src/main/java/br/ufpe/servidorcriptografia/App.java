package br.ufpe.servidorcriptografia;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Scanner;

import br.ufpe.servidorcriptografia.util.Md5;

public class App {
	public static void main(String[] args) {
		
		try {
			ServerSocket servidor = new ServerSocket(12351);
			System.out.println("Servidor ouvindo a porta 12351");

			while (true) {
				Socket cliente = servidor.accept();
				System.out.println("Cliente conectado: " + cliente.getInetAddress().getHostAddress());
				
				/*
				 * recebe a mensagem
				 */
				Scanner entrada = new Scanner(cliente.getInputStream());
				String senha = entrada.next();
				System.out.println("mensagem recebida do cliente:" + senha);
				
				/*
				 * envia resposta usando o mesmo scoket
				 */
				PrintStream saida;
				saida = new PrintStream(cliente.getOutputStream());
				saida.println(Md5.criptografar(senha));
				
				cliente.close();
			}

		} catch (IOException e) {
			e.printStackTrace();
		}

	}
}
