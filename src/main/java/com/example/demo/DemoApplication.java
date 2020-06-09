package com.example.demo;

import com.example.demo.dto.CovidData;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.LongDeserializer;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.Serializable;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.*;

import static org.apache.spark.sql.functions.*;
import static org.springframework.kafka.support.KafkaHeaders.TOPIC;

@SpringBootApplication
@Slf4j
public class DemoApplication implements Serializable {

    @PostConstruct
    public void init() {
        // Установить для приложения таймзону по дефолту
        TimeZone.setDefault(TimeZone.getTimeZone("Europe/Moscow"));
        System.setProperty("hadoop.home.dir", "D:/Winutils");
    }

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }
}

//	private static final String JSON_PATH = "C:\\Users\\r.kamnev\\Downloads\\demo (3)\\demo\\src\\main\\java\\com\\example\\demo\\data\\linkedIn\\profiles.json";

//    private static final String JSON_PATH = "C:\\Users\\r.kamnev\\Downloads\\demo (3)\\demo\\src\\main\\java\\com\\example\\demo\\data\\covid\\covid.csv";

//	public static void main(String[] args) throws InterruptedException, IOException {
//
//		System.setProperty("hadoop.home.dir", "D:/Winutils");
//
//		SparkSession session = SparkSession
//				.builder()
//				.appName("SparkJavaExample")
//				.master("local[3]")
//				.config("spark.driver.bindAddress", "127.0.0.1")
//				.getOrCreate();
//
//        Encoder<CovidData> covidEncoder = Encoders.bean(CovidData.class);
//        Dataset<CovidData> javaBeanDS = session.read().option("header", "true").csv(JSON_PATH).as(covidEncoder);
//        javaBeanDS.select("location","totalCases", "newCases", "totalDeaths", "newDeaths", "totalCasesPeMillion", "newCasesPerMillion")
//                .where(col("totalCases").$greater$eq(100)
//                        .and(col("totalDeaths").$greater$eq(100)
//                        .and(col("newDeaths").$greater$eq(100))))
//                .orderBy(asc("totalDeaths"))
//                .show(5);

//        Dataset<Row> ds = session.read().csv(JSON_PATH);
//        ds.show();


//
//		List<String> data = Arrays.asList("hello", "world");
//		Dataset<String> ds = session.createDataset(data, Encoders.STRING());
//		final String first = ds.first();
//
//		StructField[] structFields = new StructField[]{
//				new StructField("intColumn", DataTypes.IntegerType, true, Metadata.empty()),
//				new StructField("stringColumn", DataTypes.StringType, true, Metadata.empty()),
//		};
//
//		StructType structType = new StructType(structFields);
//
//		List<Row> rows = new ArrayList<>();
//		rows.add(RowFactory.create(1, "v1"));
//		rows.add(RowFactory.create(2, "v2"));
//
//		Dataset<Row> df = session.createDataFrame(rows, structType);
//		df.show();
//		List<Row> rows1 = df.collectAsList();
//		System.out.println(rows1);
//
//		Encoder<Person> personEncoder = Encoders.bean(Person.class);
//
//		// read JSON file to Dataset
//		Dataset<Person> persons = session.read().json(JSON_PATH).as(personEncoder);
//		persons = persons.filter(persons.col("age").geq(30));
//		persons.show();
//
//
//
////		try (JavaSparkContext context = new JavaSparkContext(session.sparkContext())) {
//
//
////			SQLContext sqlContext = new SQLContext(context);
////			DataFrame linkedIn = sqlContext.read().json("data/linkedIn/*.json");
////			linkedIn.show();
//
////			List<org.apache.spark.sql.types.StructField> listOfStructField=new ArrayList<org.apache.spark.sql.types.StructField>();
////			listOfStructField.add(DataTypes.createStructField("test", DataTypes.StringType, true));
////			StructType structType=DataTypes.createStructType(listOfStructField);
////
////			JavaRDD<Row> items = session.read().json(JSON_PATH).toJavaRDD();
//////			Dataset<Row> jsonRead = DataFrameReader.json(String jsonFilePath)
////			Dataset<Row> dataFrame = session.createDataFrame(items, structType);
////			dataFrame.show();
////
////			File file = new File("C:\\Users\\r.kamnev\\Downloads\\demo (3)\\demo\\src\\main\\resources\\anthems\\Russian.txt");
////			File garbageFile = new File("C:\\Users\\r.kamnev\\Downloads\\demo (3)\\demo\\src\\main\\resources\\anthems\\garbagewords.txt");
////
////			BufferedReader br = new BufferedReader(new FileReader(file));
////
////			List<String> song = new ArrayList<>();
////			String st;
////			while ((st = br.readLine()) != null)
////				song.add(st);
////
////			BufferedReader garbageReader = new BufferedReader(new FileReader(garbageFile));
////
////			List<String> garbageList = new ArrayList<>();
////			String gb;
////			while ((gb = garbageReader.readLine()) != null)
////				garbageList.add(gb);
////
////			final Set<String> gbSet = garbageList.stream()
////					.map(e -> e.replace(",", " "))
////					.map(line -> line.split(" "))
////					.flatMap(Arrays::stream)
////					.collect(Collectors.toSet());
////
////
////			List<String> collect = song.stream()
////					.map(line -> line.split(" "))
////					.flatMap(Arrays::stream)
////					.map(String::toLowerCase)
////					.collect(Collectors.toList());
////
////
////			System.out.println(song);
////		}
//
////			JavaRDD<String> anthem = context.textFile("C:\\Users\\r.kamnev\\Downloads\\demo (3)\\demo\\src\\main\\resources\\anthems\\Russian.txt");
////			List<String> words;
////			words = anthem.map(String::toLowerCase)
////					.map(WordsUtil::getWords)
////					.mapToPair(w -> new Tuple2<>(w, 1))
////					.reduceByKey((a, b) -> a + b)
////					.mapToPair(Tuple2::swap).sortByKey(false).map(Tuple2::_2).take(3);
////
////			System.out.println("Finish");
//
//
////			List<Integer> integers = Arrays.asList(1, 4, 5, 6, 7, 8, 9,
////					10, 11, 12, 13, 14, 15);
////-
////			JavaRDD<Integer> javaRDD = context.parallelize(integers, 3);
////
////			javaRDD
////					.foreach((VoidFunction<Integer>) integer -> {
////
////						System.out.println("Java RDD:" + integer);
////						Thread.sleep(3000);
////					});
////
////			Thread.sleep(1000000);
////			context.stop();
////		}
//
//
////		System.out.println("tff");
//
//
//	}

//    public static void main(String[] args) throws UnknownHostException {
//
//
//        final String TOPIC = "TutorialTopic";
//        final String BOOTSTRAP_SERVERS = "10.5.121.180:9092";
//
//        final Properties props = new Properties();
//        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG,
//                BOOTSTRAP_SERVERS);
//        props.put(ConsumerConfig.GROUP_ID_CONFIG,
//                "KafkaExampleConsumer");
//        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
//                LongDeserializer.class.getName());
//        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
//                StringDeserializer.class.getName());
//        // Create the consumer using props.
//        final Consumer<Long, String> consumer =
//                new KafkaConsumer<>(props);
//        // Subscribe to the topic.
//        consumer.subscribe(Collections.singletonList(TOPIC));
//        System.out.println("t");
//
//    }
//}
