package web02.lesson02.client;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintStream;
import java.net.Socket;
import java.net.UnknownHostException;

public class SimpleHttpClient {
	public static void main(String[] args) throws UnknownHostException, IOException {
		// 소켓 및 입출력 스트림 준비
		Socket socket = new Socket("www.naver.com",80);
		BufferedReader in = new BufferedReader(
				new InputStreamReader(socket.getInputStream()));
		
		// 접속할 웹 서버는 '다음'사이트 입니다.
		// 웹 서버의 기본 포트번호는 80이기 때문에 접속할 서버의 포트번호를 80으로 지정
		// 그리고 소켓으로 입,출력을 하기 위한 객체를 준비합니다.
		
		PrintStream out = new PrintStream(
				socket.getOutputStream());
		
		// 서버에게 수행할 작업을 알려주는 요청라인 출력
		out.println("GET / HTTP/1.1");
		// 요청 형식은 GET, 원하는 자원은 웬 서버 루트폴더에 있는 기본문서(/)
		// 사용할 프로토콜은 HTTP , 버전은 1.1 입니다.
		
		
		// 헤더 정보
		out.println("Host: www.naver.com");
		out.println("User-Agent: Mozilla/5.0 (Macintosh; Intel Mac OS X 10_9_0)"
				+ " AppleWebKit/537.36 (KHTML, like Gecko)"
				+ " Chrome/30.0.1599.101 Safari/537.36");
		
		// 접속 주소는  다음 , 요청자의 정보는 크롬 브라우저
		// Host , User-Agent  두가지 헤더만 보냄
		
		
		// 요청의 끝을 표시하기 위해 공백 라인을 보냄
		out.println();
		
		// 응답 내용 출력
		String line = null;
		while( (line = in.readLine() ) != null) {
			System.out.println(line);
		}
		
		in.close();
		out.close();
		socket.close();
		
	}

}
