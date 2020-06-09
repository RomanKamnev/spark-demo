package com.example.demo.controller;

//import io.swagger.annotations.Api;
//import io.swagger.annotations.ApiOperation;
import com.example.demo.Person;
import com.example.demo.service.DataHandleService;
import org.apache.spark.sql.Dataset;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/spark")
//@Api(value = "Api for spark", description = "Контроллер spark")
public class SparkController {

    private final DataHandleService service;

    public SparkController(DataHandleService service) {
        this.service = service;
    }

    @GetMapping("/")
//    @ApiOperation("Получить адрес по ID")
    public Dataset<Person> getById(){
        return service.getDataFromKafka();
    }
}
