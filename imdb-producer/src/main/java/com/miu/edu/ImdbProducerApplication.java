package com.miu.edu;

import com.opencsv.bean.CsvToBeanBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;
/**
 * @author Prabhat Gyawali
 * @project BigDataProject
 */
@SpringBootApplication
public class ImdbProducerApplication implements CommandLineRunner {

	@Autowired
	private ImdbProducer producer;

	public static void main(String[] args) {
		SpringApplication.run(ImdbProducerApplication.class, args);
	}
	public Integer interval;

	@Override
	public void run(String... args) throws Exception {
//		C:\Users\gyawa\Downloads\archive\imdb_top_1000.csv
		String fileLocation = args[0];
		interval = args != null && args.length > 0? Integer.parseInt(args[1]): 20;
		List<Movie> movieList = readFileAndReturnMovies(fileLocation);
		movieList.forEach(this::simulateStreaming);

	}

	public void simulateStreaming(Movie movie){
		try {
			Thread.sleep(interval);
			producer.produce(movie);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

	}

	public List<Movie> readFileAndReturnMovies(String fileLocation) throws FileNotFoundException {
		FileReader reader = new FileReader(fileLocation);
		List<Movie> movieList = new CsvToBeanBuilder(reader)
				.withType(Movie.class)
				.build()
				.parse();
		return movieList;
	}
}
