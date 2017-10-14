package net.jgp.labs.spark.datasources.x;

import java.io.Serializable;

import org.apache.spark.rdd.RDD;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.TableScan;
import org.apache.spark.sql.types.StructType;

public class ExifDirectoryRelation extends BaseRelation
        implements Serializable, TableScan {
    private static final long serialVersionUID = 4598175080399877334L;
    private SQLContext sqlContext;

    @Override
    public RDD<Row> buildScan() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public StructType schema() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public SQLContext sqlContext() {
        return this.sqlContext;
    }

    public void setSqlContext(SQLContext sqlContext) {
        this.sqlContext = sqlContext;
    }

}
