package br.com.unificaerp;

import br.com.unificaerp.shared.UnificaRepositoryImpl;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
@EnableJpaRepositories(
		basePackages = "br.com.unificaerp",
		repositoryBaseClass = UnificaRepositoryImpl.class
)
public class UnificaerpApplication {

	public static void main(String[] args) {
		SpringApplication.run(UnificaerpApplication.class, args);
	}

}
