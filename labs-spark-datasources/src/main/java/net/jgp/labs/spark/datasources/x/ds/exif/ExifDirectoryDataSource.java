package net.jgp.labs.spark.datasources.x.ds.exif;

import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.sources.BaseRelation;
import org.apache.spark.sql.sources.RelationProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.jgp.labs.spark.datasources.x.utils.K;

import static scala.collection.JavaConverters.mapAsJavaMapConverter;
import scala.collection.immutable.Map;

public class ExifDirectoryDataSource implements RelationProvider {
    private static Logger log = LoggerFactory.getLogger(ExifDirectoryDataSource.class);

    @Override
    public BaseRelation createRelation(
            SQLContext sqlContext,
            Map<String, String> params) {
        log.debug("-> createRelation()");

        java.util.Map<String, String> javaMap = mapAsJavaMapConverter(params).asJava();

        ExifDirectoryRelation br = new ExifDirectoryRelation();
        br.setSqlContext(sqlContext);

        for (java.util.Map.Entry<String, String> entry : javaMap.entrySet()) {
            String key = entry.getKey();
            String value = entry.getValue();
            log.debug("[{}] --> [{}]", key, value);
            if (key.compareTo(K.PATH) == 0) {
                // br.setFilename(value);
            } else {
                // br.addCriteria(value);
            }
        }

        return br;
    }

}
