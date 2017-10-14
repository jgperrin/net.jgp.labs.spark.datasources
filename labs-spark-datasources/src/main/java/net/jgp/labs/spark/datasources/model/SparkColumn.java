package net.jgp.labs.spark.datasources.model;

import org.apache.spark.sql.types.DataType;
import org.apache.spark.sql.types.StringType;

public @interface SparkColumn {

    String name() default "";

    Class<? extends DataType> type() default StringType.class;

    boolean nullable() default true;
}
