package cloud.sparkflows.tenant;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
//@EnableJpaRepositories(basePackages = "cloud.sparkflows.tenant.repository")
//@EntityScan(basePackages = "cloud.sparkflows.tenant.model")
public class TenantApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenantApplication.class, args);
	}
}
