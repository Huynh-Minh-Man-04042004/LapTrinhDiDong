package com.minhman.springbootthymeleaf;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.jpa.JpaRepositoriesAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.minhman.config.StorageProperties;
import com.minhman.service.IStorageService;

@SpringBootApplication(scanBasePackages = { "com.minhman" }, exclude = JpaRepositoriesAutoConfiguration.class)
@EnableTransactionManagement
@EnableJpaRepositories(basePackages = "com.minhman.repository") // Quét lại repository
@EntityScan(basePackages = "com.minhman.entity")
@EnableConfigurationProperties(StorageProperties.class) // Thêm cấu hình storage
public class SpringbootThymeleafApplication {
	public static void main(String[] args) {
		SpringApplication.run(SpringbootThymeleafApplication.class, args);
	}

	@Bean
	CommandLineRunner init(IStorageService storageService) {
		return (args -> {
			storageService.init();
		});
	}
}
