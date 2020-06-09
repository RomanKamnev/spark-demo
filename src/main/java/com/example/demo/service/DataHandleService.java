package com.example.demo.service;

import com.example.demo.Person;
import org.apache.spark.sql.*;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.Metadata;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import scala.collection.Seq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Service
public class DataHandleService {

    @Autowired
    SparkSession session;

    private static final String JSON_PATH = "C:\\Users\\r.kamnev\\Downloads\\demo (3)\\demo\\src\\main\\java\\com\\example\\demo\\data\\linkedIn\\profiles.json";

    public Dataset<Person> getDataFromKafka(){
        List<String> data = Arrays.asList("hello", "world");
        Dataset<String> ds = session.createDataset(data, Encoders.STRING());
        final String first = ds.first();

        StructField[] structFields = new StructField[]{
                new StructField("intColumn", DataTypes.IntegerType, true, Metadata.empty()),
                new StructField("stringColumn", DataTypes.StringType, true, Metadata.empty()),
        };

        StructType structType = new StructType(structFields);

        List<Row> rows = new ArrayList<>();
        rows.add(RowFactory.create(1, "v1"));
        rows.add(RowFactory.create(2, "v2"));

        Dataset<Row> df = session.createDataFrame(rows, structType);
        df.show();
        List<Row> rows1 = df.collectAsList();
        System.out.println(rows1);

        Encoder<Person> personEncoder = Encoders.bean(Person.class);

        // read JSON file to Dataset
        Dataset<Person> persons = session.read().json(JSON_PATH).as(personEncoder);
        persons = persons.filter(persons.col("age").geq(30));
        persons.show();
        Seq<Seq<String>> rows2 = persons.getRows(0, 1);
        session.stop();
        
        return persons;
    }
}
