package web02.lesson02.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleHttpClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		// ���� �� ����� ��Ʈ�� �غ�
		Socket socket = new Socket("www.naver.com",80);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		
		// ������ �� ������ '����'����Ʈ �Դϴ�.
		// �� ������ �⺻ ��Ʈ��ȣ�� 80�̱� ������ ������ ������ ��Ʈ��ȣ�� 80���� ����
		// �׸��� �������� ��,����� �ϱ� ���� ��ü�� �غ��մϴ�.
		
		PrintStream out = new PrintStream(
				socket.getOutputStream());
		
		// �������� ������ �۾��� �˷��ִ� ��û���� ���
		out.println("GET / HTTP/1.1");
		// ��û ������ GET, ���ϴ� �ڿ��� �� ���� ��Ʈ������ �ִ� �⺻����(/)
		// ����� ���������� HTTP , ������ 1.1 �Դϴ�.
		
		
		// ��� ����
		out.println("Host: www.naver.com");
		out.println("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0)"
				+ " AppleWebKit/537.36 (KHTML, like Gecko)"
				+ " Chrome/30.0.1599.101 Safari/537.36");
		
		// ���� �ּҴ�  ���� , ��û���� ������ ũ�� ������
		// Host , User-Agent  �ΰ��� ����� ����
		
		
		// ��û�� ���� ǥ���ϱ� ���� ���� ������ ����
		out.println();
		
		// ���� ���� ���
		String line = null;
		while( (line = in.readLine() ) != null) {
			System.out.println(line);
		}
		
		in.close();
		out.close();
		socket.close();
		
	}

}
