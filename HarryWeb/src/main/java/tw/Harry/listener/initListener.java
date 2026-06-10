package tw.Harry.listener;

import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;

@WebListener
public class initListener implements ServletContextListener {

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		System.out.println("Init");
	}

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		System.out.println("Gone");
	}

}
