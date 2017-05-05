package name.pjh;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages={"name.pjh.api", "name.pjh.service", "name.pjh.filter"})
public class MasterApplication {

	private static final Logger logger = LoggerFactory.getLogger(MasterApplication.class);

	public static void main(String[] args) {

		logger.info("*********************正在启动Master服务*********************");
		SpringApplication.run(MasterApplication.class, args);
		logger.info("*********************Master服务启动成功*********************");
	}
}
