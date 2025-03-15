package edu.rims.medi_track;

import edu.rims.medi_track.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@RequiredArgsConstructor
@EnableJpaAuditing(auditorAwareRef = "auditingImpl")
public class MediTrackApplication implements CommandLineRunner {
	private final FileService fileService;

	public static void main(String[] args) {
		SpringApplication.run(MediTrackApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		fileService.createRootDirectory();
	}
}
