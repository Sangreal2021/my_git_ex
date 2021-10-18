package listener;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import java.sql.DriverManager;

import org.apache.commons.dbcp2.BasicDataSource;
import org.apache.commons.dbcp2.BasicDataSourceFactory;
import org.apache.commons.dbcp2.ConnectionFactory;
import org.apache.commons.dbcp2.DriverManagerConnectionFactory;
import org.apache.commons.dbcp2.PoolableConnection;
import org.apache.commons.dbcp2.PoolableConnectionFactory;
import org.apache.commons.dbcp2.PoolingDriver;
import org.apache.commons.pool2.ObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

// 웹 서버가 구동될 때 JDBC 드라이버 클래스를 로딩하기 위한 리스너
@WebListener
public class JDBCDriverLoader implements ServletContextListener {

	public void contextInitialized(ServletContextEvent sce)  {
		ServletContext application = sce.getServletContext();
		String jdbc_driver = application.getInitParameter("JDBC_DRIVER");
		try {
			Class.forName(jdbc_driver);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		try {
			String jdbcUrl = application.getInitParameter("JDBC_URL");
			String username = application.getInitParameter("JDBC_USER");
			String pw = application.getInitParameter("JDBC_PASSWORD");

			ConnectionFactory connFactory = 
					new DriverManagerConnectionFactory(jdbcUrl, username, pw);

			PoolableConnectionFactory poolableConnFactory = 
					new PoolableConnectionFactory(connFactory, null);
			
			//커넥션풀들이 잘 실행되고있는지 확인해보는 코드
			poolableConnFactory.setValidationQuery("select 1");

			GenericObjectPoolConfig poolConfig = new GenericObjectPoolConfig();
			poolConfig.setTimeBetweenEvictionRunsMillis(1000L * 60L * 5L);
			poolConfig.setTestWhileIdle(true);
			poolConfig.setMinIdle(4);
			poolConfig.setMaxTotal(50);

			GenericObjectPool<PoolableConnection> connectionPool = 
					new GenericObjectPool<>(poolableConnFactory, poolConfig);
			poolableConnFactory.setPool(connectionPool);
			
			//아래가 중요한 코드
			Class.forName("org.apache.commons.dbcp2.PoolingDriver");
			PoolingDriver driver = 
					(PoolingDriver) DriverManager.getDriver("jdbc:apache:commons:dbcp:");
			
			driver.registerPool("board", connectionPool);
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
    }
	
    public void contextDestroyed(ServletContextEvent sce)  { 

    }
}
