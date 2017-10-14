package net.jgp.labs.spark.datasources.model;

import org.apache.spark.sql.types.StringType;

public class PhotoMetadata {
    private String filename;

    /**
     * @return the filename
     */
    public String getFilename() {
        return filename;
    }

    /**
     * @param filename
     *            the filename to set
     */
    @SparkColumn(name="filename", type=StringType.class)
    public void setFilename(String filename) {
        this.filename = filename;
    }
}
