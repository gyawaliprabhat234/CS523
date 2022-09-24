package com.miu.edu;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileReader;
import java.util.List;

@SpringBootApplication
public class ImdbProducerApplication implements CommandLineRunner {

	@Autowired
	private ImdbProducer producer;

	public static void main(String[] args) {
		SpringApplication.run(ImdbProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		String filename = args != null && args.length > 0 ? args[0] : "C:\\Users\\gyawa\\Downloads\\archive\\imdb_top_1000.csv";
		Integer dataSendingInterval = args != null && args.length > 0? Integer.parseInt(args[1]): 20;

		List<Movie> movieList = new CsvToBeanBuilder(new FileReader(filename))
				.withType(Movie.class)
				.build()
				.parse();
		movieList.forEach(movie-> {
			try {
				Thread.sleep(dataSendingInterval);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			producer.produce(movie);
		});

	}
}
