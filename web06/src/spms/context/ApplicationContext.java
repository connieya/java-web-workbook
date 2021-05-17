package spms.context;

import java.io.FileReader;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Properties;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;



//프로퍼티 파일을 이용한 객체 준비
public class ApplicationContext {
	Hashtable<String, Object> objTable = new Hashtable<>();
	
	public Object getBean(String key) {
		return objTable.get(key);
	}
	
	public ApplicationContext(String propertiesPath) throws Exception{
		Properties props = new Properties();
		props.load(new FileReader(propertiesPath));
		
		prepareObject(props);
		injectDependency();
	}

	private void injectDependency() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		for(String key : objTable.keySet()) {
			if(!key.startsWith("jndi.")) {
				callSetter(objTable.get(key));
			}
		}
		
	}

	private void callSetter(Object object) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		// TODO Auto-generated method stub
		Object dependency = null;
		for(Method m : object.getClass().getMethods()) {
			if(m.getName().startsWith("set")) {
				dependency = findObjectByType(m.getParameterTypes()[0]);
				if(dependency != null) {
					m.invoke(object, dependency);
				}
			}
		}
	}

	private Object findObjectByType(Class<?> type) {
	    for (Object obj : objTable.values()) {
	      if (type.isInstance(obj)) {
	        return obj;
	      }
	    }
	    return null;
	  }

	private void prepareObject(Properties props) throws NamingException, InstantiationException, IllegalAccessException, ClassNotFoundException {
		Context ctx = new InitialContext();
		String key = null;
		String value = null;
		
		for(Object item : props.keySet()) {
			key = (String) item;
			value = props.getProperty(key);
			if (key.startsWith("jndi.")) {
				objTable.put(key, ctx.lookup(value));
			}else {
				objTable.put(key, Class.forName(value).newInstance());
			}
		}
	}
	
	

}
