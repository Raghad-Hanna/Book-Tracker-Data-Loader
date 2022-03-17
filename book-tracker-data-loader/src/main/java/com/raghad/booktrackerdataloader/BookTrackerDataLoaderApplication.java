package com.raghad.booktrackerdataloader;

import com.raghad.booktrackerdataloader.authors.Author;
import com.raghad.booktrackerdataloader.authors.AuthorRepository;
import com.raghad.booktrackerdataloader.connection.DataStaxAstraProperties;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.cassandra.CqlSessionBuilderCustomizer;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.stream.Stream;

@SpringBootApplication
@EnableConfigurationProperties(DataStaxAstraProperties.class)
@EnableCassandraRepositories
public class BookTrackerDataLoaderApplication {
	@Autowired
	private AuthorRepository authorRepository;

	@Value("${custom.datadump.location.authors}")
	private String authorsDumpLocation;

	@Value("${custom.datadump.location.works}")
	private String worksDumpLocation;

	public static void main(String[] args) {
		SpringApplication.run(BookTrackerDataLoaderApplication.class, args);
	}

	@PostConstruct
	public void start() {
		this.loadAuthorsToAstraDB();
		this.loadWorksToAstraDB();
	}

	private void loadAuthorsToAstraDB() {
		Path authorsPath = Paths.get(this.authorsDumpLocation);
		try(Stream<String> lines = Files.lines(authorsPath)) {
			lines.forEach(line -> {
				String jsonString = line.substring(line.indexOf("{"));
				try {
					JSONObject jsonObject = new JSONObject(jsonString);

					Author author = new Author();
					author.setId(jsonObject.optString("key").replace("/authors/", ""));
					author.setName(jsonObject.optString("name"));
					author.setPersonalName(jsonObject.optString("personal_name"));

					System.out.println("loading " + author.getName() + "to astra db");
					this.authorRepository.save(author);
				}
				catch(JSONException e) {
					e.printStackTrace();
				}
			});
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	private void loadWorksToAstraDB() {
		Path worksPath = Paths.get(this.worksDumpLocation);
		try(Stream<String> lines = Files.lines(worksPath)) {
			lines.forEach(line -> {
				String jsonString = line.substring(line.indexOf("{"));
				try {
					JSONObject jsonObject = new JSONObject(jsonString);

				}
				catch(JSONException e) {
					e.printStackTrace();
				}
			});
		}
		catch(IOException e) {
			e.printStackTrace();
		}
	}

	@Bean
	public CqlSessionBuilderCustomizer sessionBuilderCustomizer(DataStaxAstraProperties astraProperties) {
		Path bundle = astraProperties.getSecureConnectBundle().toPath();
		return builder -> builder.withCloudSecureConnectBundle(bundle);
	}
}
