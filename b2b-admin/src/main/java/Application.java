import com.b2b.mall.common.util.RunnableThreadWebCount;
import com.b2b.mall.common.util.Timers;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication(scanBasePackages = {"com.b2b.mall.db", "com.b2b.mall.common", "com.b2b.mall.admin"})
@MapperScan("com.b2b.mall.db.mapper")
@EnableTransactionManagement
public class Application {

	public static void main(String[] args) {
		ConfigurableApplicationContext context = SpringApplication.run(Application.class, args);
		//计数线程
		RunnableThreadWebCount runnableThreadWebCount = new RunnableThreadWebCount();
		runnableThreadWebCount.run();
		//计时器线程
		Timers timers = new Timers();
		timers.run();

	}
}
