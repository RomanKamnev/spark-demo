package com.example.demo.service;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.StructType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CsvReadService {

    @Autowired
    SparkSession sparkSession;

    private static final String JSON_PATH = "C:\\Users\\r.kamnev\\Downloads\\demo (3)\\demo\\src\\main\\java\\com\\example\\demo\\data\\covid\\covid.csv";

    StructType schema = new StructType()
            .add("department", "string")
            .add("designation", "string")
            .add("ctc", "long")
            .add("state", "string");

    public void getCovidInfo(){
        Dataset<Row> ds = sparkSession.read().csv(JSON_PATH);
        ds.show();

        Dataset<Row> df = sparkSession.read()
                .option("mode", "DROPMALFORMED")
                .schema(schema)
                .csv(JSON_PATH);
    }
}
