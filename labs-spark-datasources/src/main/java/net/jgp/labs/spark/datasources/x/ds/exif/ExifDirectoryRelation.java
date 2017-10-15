package net.jgp.labs.spark.datasources.x.ds.exif;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.TableScan;
import org.apache.spark.sql.types.StructType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jgp.labs.spark.datasources.x.model.PhotoMetadata;
import net.jgp.labs.spark.datasources.x.utils.Schema;
import net.jgp.labs.spark.datasources.x.utils.SparkBeanUtils;

public class ExifDirectoryRelation extends BaseRelation
        implements Serializable, TableScan {
    private static final long serialVersionUID = 4598175080399877334L;
    private static Logger log = LoggerFactory.getLogger(ExifDirectoryRelation.class);
    private SQLContext sqlContext;
    private Schema schema = null;

    @Override
    public RDD<Row> buildScan() {
        log.debug("-> buildScan()");
        schema();

        // I have isolated the work to a method to keep the plumbing code as simple as
        // possible.
        List<PhotoMetadata> table = collectData();

        @SuppressWarnings("resource")
        JavaSparkContext sparkContext = new JavaSparkContext(sqlContext.sparkContext());
        JavaRDD<Row> rowRDD = sparkContext.parallelize(table)
                .map(photo -> SparkBeanUtils.getRowFromBean(schema, photo));

        return rowRDD.rdd();
    }

    private List<PhotoMetadata> collectData() {
        // TODO Auto-generated method stub
        List<PhotoMetadata> list = new ArrayList<PhotoMetadata>();
        PhotoMetadata photo = new PhotoMetadata();
        photo.setFilename("001");
        list.add(photo);
        photo = new PhotoMetadata();
        photo.setFilename("002");
        list.add(photo);
        return list;
    }

    @Override
    public StructType schema() {
        if (schema == null) {
            schema = SparkBeanUtils.getSchemaFromBean(PhotoMetadata.class);
        }
        return schema.getSparkSchema();
    }

    @Override
    public SQLContext sqlContext() {
        return this.sqlContext;
    }

    public void setSqlContext(SQLContext sqlContext) {
        this.sqlContext = sqlContext;
    }

}
