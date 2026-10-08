package br.com.unificaerp;

import br.com.unificaerp.shared.UnificaRepositoryImpl;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.persistence.autoconfigure.EntityScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

@SpringBootApplication
@EnableCaching
@EnableScheduling
@EnableAsync
@EnableJpaRepositories(
		basePackages = "br.com.unificaerp",
		repositoryBaseClass = UnificaRepositoryImpl.class
)
public class UnificaerpApplication {

	@Autowired
	private PasswordEncoder passwordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(UnificaerpApplication.class, args);



	}
	@PostConstruct
	private void configTimeZone() {
		Locale.setDefault(Locale.forLanguageTag("pt_BR"));
		TimeZone sp = TimeZone.getTimeZone("America/Sao_Paulo");
		TimeZone.setDefault(sp);
		Calendar.getInstance().setTimeZone(sp);

		System.out.println("Senha padrão: --------> " + passwordEncoder.encode("&164Al#"));
	}

}
