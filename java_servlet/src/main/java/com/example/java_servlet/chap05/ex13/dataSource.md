# DataSource 와 JNDI

JDK 1.4 버전 부터 포함된 DataSource는 JDBC 확장 API를 정의한 javax.sql 패키지에 들어 있다.
Java EE서버(예: 톰캣 서버)에서 DB 커넥션 풀을 관리하는 방법에 대해 알아보자

JNDI API를 사용하여 Java EE 서버 자원에 접근하는 방법을 배워보자


### javax.sql 확장 패키지

javax.sql 패키지는 java.sql 패키지의 기능을 보조하기 위해 만든 확장 패키지이다.
서버 쪽 데이터 소스에 대한 접근을 쉽게 하고, 좀 더 다양한 방법으로 데이터를 다룰 수 있는
API를 제공한다. 이 패키지는 JDK 1.4 부터 포함되었다.
JAVA EE 에서도 기본 패키지러 정의 되어 있다.

javax.sql 패키지가 제공하는 주오 기능은 다음과 같다.
- DriverManager를 대체할 수 있는 DataSource 인터페이스 제공
- Connection 및 Statement 객체의 풀링
- 분산 트랜잭션 처리
- Rowsets의 지원

이 중에서 DriverManager를 대체할 수 있는 DataSource에 대해 알아보자

### DataSource

DataSource 는 DriverManager를 통해 DB 커넥션을 얻는 것보다 더 좋은 기법을
제공한다. 특히 다음 두 가지 점에서 DriverManager 보다 낫다.

첫째, DataSource는 서버에서 관리하기 때문에 데이터베이스나 JDBC 드라이버가 변경되더라도
애플리케이션을 바꿀 필요가 없다.



### DataSource의 적용

### 서버에서 제공하는 DataSource 사용하기

인간 세상의 자원도 늘 부족하듯이, 컴퓨터 세상이라고 별다를 바 없다. 메모리, 하드 디스크,
네트워크 , 데이터베이스 등 애플리케이션이 사용하는 자원들도 늘 부족합니다. 따라서 
공유하는 자원일 수록 사용하지 않을 경우 바로 해제해 주어야 다른 애플리케이션이 사용할 수 있다.

웹 애플리케이션이 종료될 때도 서블릿들이 사용한 공용 자원들을 바로 해제해 주어야 한다.
그런 의미에서 웹 애플리케이션을 종료할 때 호출되는 contextDestroy() 메서드는 , 공용
자원을 해제하는 코드가 놓이는 최적의 장소라 할 수 있다.

`MemberDao에 DataSource 적용`

```java

public class UserDAO {
	DataSource ds;
	
	public void setDataSource(DataSource ds) {
		this.ds = ds;
	}
    }
```

인스턴스 변수 타입이 BasicDataSource 가 아니라 DataSource이다. 
BasicDataSource는 DataSource의 구현체이다. 구현체의 이름을 직접 사용하면 ,
나중에 다른 구현체로 교체할 수가 없다. 대신 인터페이스 이름을 사용하면 나중에 다른
구현체로 손쉽게 대체할 수 있다. 이런 이유로 참조  변수의 타입은 가능한 가능한 구현체의 이름보다
인터페이스의 이름을 사용한다.

### 서버에서 제공하는 DataSource 사용하기

DataSource를 사용하는 이유는 서버에서 관리하기 때문에 데이터베이스에 대한 정보가 
바뀌거나 JDBC 드라이버가 교체되더라도 애플리케이션에는 영향을 주지 않기 때문이다.

그런데 이전 에는 우리가 직접 BasicDataSource를 사용하기 때문에, 
진정으로 DataSource의 이점을 누릴 수 없다.

서버에서 관리하는 DataSource를 사용하자. 실무에서는 주로 이 방법을 사용한다.

` 톰켓 서버에 DataSource 설정하기`

context.xml

```xml
  <Resource name="jdbc/pgh" auth="Container" type="javax.sql.DataSource"
               maxTotal="100" maxIdle="30" maxWaitMillis="10000"
               username="root" password="1234" driverClassName="com.mysql.cj.jdbc.Driver"
               url="jdbc:mysql://localhost:3306/pgh"/>
```


`웹 애플리케이션에서 톰캣 서버의 자원 사용`

톰캣 서버에 설정한 DataSource를 웹 애플리케이션에서 사용하려면 DD 파일(web.xml)에 서버
자원을 참조한다는 선언을 해 주어야 한다.

```xml
    <resource-ref>
  		<res-ref-name>jdbc/pgh</res-ref-name>
  		<res-type>javax.sql.DataSource</res-type>
  		<res-auth>Container</res-auth>
  	</resource-ref>
```


`JNDI란 무엇인가?`

JNDI는 Java Naming and Directory Interface API의 머리 글자이다.
디렉터리 서비스에 접근하는 데 필요한 API이며 애플리케이션은 이 API를 사용하여
서버의 자원을 찾을 수 있다. 자원이라함은 데이터베이스 서버나 메시징 시스템과 같이
다른 시스템과의 연결을 제공하는 객체이다. 특히 JDBC 자원을 데이터 소스라고 부른다.

자원을 서버에 등록할 때는 고유한 JNDI 이름을 붙인다. JNDI 이름은 사용자에게 친숙한 디렉터리
경로 형태를 가진다. 예를 들어, JDBC 자원에 대한 JNDI 이름은 jdbc/mydb의 형식으로 짓는다.


`ContextLoaderListener 클래스 변경`

```java

@WebListener
public class ContextLoaderListener implements ServletContextListener {
	@Override
	public void contextInitialized(ServletContextEvent event) {
		try {
			ServletContext sc = event.getServletContext();
			
			InitialContext initialContext = new InitialContext();
			DataSource ds = (DataSource) initialContext.lookup(
					"java:comp/env/jdbc/pgh");
					
			BoardDAO boardDAO = new BoardDAO();
			boardDAO.setDataSource(ds);
			
			UserDAO userDao = new UserDAO();
			userDao.setDataSource(ds);;
			
			sc.setAttribute("dao", boardDAO);
			sc.setAttribute("user", userDao);
		} catch (Throwable e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void contextDestroyed(ServletContextEvent event) {
		
	}
	
}
```

BasicDataSource를 직접 사용하지 않기 때문에 참조 변수와 객체를 생성하는 코드를 제거하였다.
대신 톰캣 서버에서 자원을 찾기 위해 InitialContext 객체를 생성하였다.
InitialContext의 lookUp() 메서드를 이용하면, JNDI 이름으로 등록 되어 있는
서버 자원을 찾을 수 있다. 

lookup()의 매개변수 값은 서버 자원의 JNDI 이름이다. 찾으려는 자원이 JDBC 데이터 소스이기 때문에
JNDI 이름 앞에 "java:comp/env"가 붙는다. lookup()이 리턴하는 자원이 DataSource이기 
때문에 형변환을 하였다.

웹 애플리케이션이 종료 될 때 DataSource가 만든 커넥션 객체들도 모두 닫아야 하는데,
contextDestroyed() 메서드를 보면 자원을 해제하는 어떤 코드도 발견할 수 없다.

왜냐 하면 톰캣 서버에 자동으로 해제하라고 설정되어 있기 때문이다.

context.xml 파일 <Resource> 태그의 closeMethod 속성값이 close라고 되어 있다.



DB 커넥션의 원리에서 실무에서 사용되는 기법까지 살펴보았다. 확실히 이해하고 가자
이런 기초적인 내용도 모르고 프레임워크를 사용하게 된다면, 모래로 성을 쌓는 것과 같다.
그리고 기술은 항상 이전의 기술을 참고하고 발전하기 때문에, 현재의 기술을 이해하려면
과거의 기술을 알아야하고, 미래의 기술을 알고 싶다면 현재의 기술을 명확히 이해해야 한다.

