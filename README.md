# Kafka Spark HBase

## Flow Diagram
<div><img width="578" alt="image" src="https://user-images.githubusercontent.com/54440920/192112762-904e3450-ca69-4d83-8253-b0c93b5d272f.png"></div>

### imdb-producer Application
This application reads the movies from csv file and it converts csv file movies into list of movies. It simulate the real time streaming by fetching items one by one from the list of movies and by using kafka data is publish to the imdb_movies topic after some miliseconds.

### imdb-consumer-spark Application
This application fetches the data from the imdb_movies topic when it is available. Once the data is consumed it saved the data into the HBase Database by the help of Spark. 

### imdb-hbase-sql Application
After all the data is stored in the HBase database this application is used to read the data from hbase and store into the csv file in hadoop.

## Commands to start the application
### 1. Zookeeper Server
bin/zookeeper-server-start.sh config/zookeeper.properties

The above command is used to start the zookeeper

### 2. Kafka Server
bin/kafka-server-start.sh config/server.properties

This command is used to start the kafka server

### 3. Create Kafka Topic
bin/kafka-topics.sh --create --topic imdb_movies --bootstrap-server localhost:9092

This command is used to create the topic in kafka


### 4. Running Producer Application:
imdb-producer is an Producer application which produces the data. To run the application we can run application by importing the project into eclipse or intellij. 
For simplicity I have created the jar file producer application which can be found in https://drive.google.com/file/d/1jd90Q8b7j1crp6A9R6t6YYa7xxEClf0g/view?usp=sharing url and after downloading the jar file by typing the command 

java -jar imdb-producer-0.0.1-SNAPSHOT.jar {param1} {param2}
where param1: required parameter and it is movies_file_location
      param2: optional parameter delay of publishing each movie in topic i.e in milliseconds
      
example: java -jar imdb-producer-0.0.1-SNAPSHOT.jar '/home/cloudera/Desktop/bigdataproject/imdb_movies.csv' 50
In the above example application takes the file from '/home/cloudera/Desktop/bigdataproject/imdb_movies.csv' location and publish each movie in the interval of 50ms


### 5. Running Consumer Application:
In the similar way, The jar file can be downloaded from https://drive.google.com/file/d/1HyGUCyWWlS0CN2JCV8yMrQwUnFiC9I3F/view?usp=sharing
java -jar imdb-consumer-spark-0.0.1-SNAPSHOT.jar {param1}  {param2}
where param1: optional parameter we can pass the realease year in this param so that the movie which is released in that sepecified year is gets added in database
      param2: optional parameter we filter the movie by genre and we can pass multiple genre in comma seperated value.

example: java -jar imdb-consumer-spark-0.0.1-SNAPSHOT.jar 1994 Drama,Action
In the above example only movies which is released in 1994 with the genre Action or Drama is gets stored in the hbase database

### 6. Running imdb-hbase-sql Application:
After the movies is stored in the hbase database using this application we can read the data from database and store the data into the csv file. The can can be found on https://drive.google.com/file/d/14I_-qPiYJa89hEPwABermKRyRyF25h7t/view?usp=sharing

java -jar imdb-hbase-sql-0.0.1-SNAPSHOT.jar {param1}
where param1: required parameter and it is movie table name in hbase database

exampe: java -jar imdb-hbase-sql-0.0.1-SNAPSHOT.jar movies
In the above example it fetch all the data into movies table and stored it into the hdfs location i.e. hdfs://localhost/user/cloudera with the folder named as table-{movie_name i.e passed in parameter} eg for above example command it will be hdfs://localhost/user/cloudera/table-movies








