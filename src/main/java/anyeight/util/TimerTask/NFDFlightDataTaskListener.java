package anyeight.util.TimerTask;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Created by 啊 on 2017/6/13.
 */
public class NFDFlightDataTaskListener implements ServletContextListener {
    public void contextInitialized(ServletContextEvent sce) {
        new TimerManager();
    }
    public void contextDestroyed(ServletContextEvent sce) {
        // TODO Auto-generated method stub
    }
}
