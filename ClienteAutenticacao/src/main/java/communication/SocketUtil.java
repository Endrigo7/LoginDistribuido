/**
 * 
 */
package communication;

import java.io.IOException;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

/**
 * @author Endrigo
 *
 */
public class SocketUtil {
	private Socket cliente;
	private String ip;
	private int porta;

	public SocketUtil(String ip, int porta) {
		this.ip = ip;
		this.porta = porta;
	}

	public void conectServer() throws UnknownHostException, IOException {
		this.cliente = new Socket(this.ip, this.porta);
	}

	public void sendMenssage(String mensagem) throws IOException {
		if (cliente != null) {
			PrintStream saida;
			saida = new PrintStream(cliente.getOutputStream());
			saida.println(mensagem);
		}
	}
	
	public String getMessage() throws IOException{
		Scanner entrada = new Scanner(cliente.getInputStream());
		String resposta =  entrada.next();
		entrada.close();
		return resposta;
	}

	public void close() throws IOException {
		this.cliente.close();
	}

}
