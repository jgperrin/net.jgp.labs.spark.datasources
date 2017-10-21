package net.jgp.labs.spark.datasources.l100_photo_datasource;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;

public class PhotoMetadataIngestionApp {
    public static void main(String[] args) {
        PhotoMetadataIngestionApp app = new PhotoMetadataIngestionApp();
        app.start();
    }

    private boolean start() {
        SparkSession spark = SparkSession.builder()
                .appName("EXIF to Dataset")
                .master("local[*]").getOrCreate();
        
        String importDirectory = "/Users/jgp/Pictures";
        
        Dataset<Row> df = spark.read()
                .format("net.jgp.labs.spark.datasources.x.ds.exif.ExifDirectoryDataSource")
                .option("recursive", "true")
                .option("limit", "500")
                .option("extensions", "jpg,jpeg")
                .load(importDirectory);
        
        df.printSchema();
        System.out.println("I have imported " + df.count() + " photos.");
        df.show();
        
        return true;
    }
}
