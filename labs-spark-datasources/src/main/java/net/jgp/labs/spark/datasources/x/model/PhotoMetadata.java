package net.jgp.labs.spark.datasources.x.model;

import java.util.Date;

import net.jgp.labs.spark.datasources.x.utils.SparkColumn;

public class PhotoMetadata {
    private Date dateTaken;
    private String directory;
    private String extension;
    private Date fileDate;
    private String filename;
    private float geoX;
    private float geoY;
    private int height;
    private String mimeType;
    private String name;
    private long size;
    private int width;

    /**
     * @return the dateTaken
     */
    public Date getDateTaken() {
        return dateTaken;
    }

    /**
     * @return the directory
     */
    public String getDirectory() {
        return directory;
    }

    /**
     * @return the extension
     */
    public String getExtension() {
        return extension;
    }

    /**
     * @return the fileDate
     */
    public Date getFileDate() {
        return fileDate;
    }

    /**
     * @return the filename
     */
    @SparkColumn(nullable = false)
    public String getFilename() {
        return filename;
    }

    /**
     * @return the geoX
     */
    public float getGeoX() {
        return geoX;
    }

    /**
     * @return the geoY
     */
    public float getGeoY() {
        return geoY;
    }

    /**
     * @return the height
     */
    public int getHeight() {
        return height;
    }

    /**
     * @return the mimeType
     */
    public String getMimeType() {
        return mimeType;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the size
     */
    public long getSize() {
        return size;
    }

    /**
     * @return the width
     */
    public int getWidth() {
        return width;
    }

    /**
     * @param dateTaken
     *            the dateTaken to set
     */
    public void setDateTaken(Date dateTaken) {
        this.dateTaken = dateTaken;
    }

    /**
     * @param directory
     *            the directory to set
     */
    public void setDirectory(String directory) {
        this.directory = directory;
    }

    /**
     * @param extension
     *            the extension to set
     */
    public void setExtension(String extension) {
        this.extension = extension;
    }

    /**
     * @param fileDate
     *            the fileDate to set
     */
    public void setFileDate(Date fileDate) {
        this.fileDate = fileDate;
    }

    /**
     * @param filename
     *            the filename to set
     */
    public void setFilename(String filename) {
        this.filename = filename;
    }

    /**
     * @param geoX
     *            the geoX to set
     */
    public void setGeoX(float geoX) {
        this.geoX = geoX;
    }

    /**
     * @param geoY
     *            the geoY to set
     */
    public void setGeoY(float geoY) {
        this.geoY = geoY;
    }

    /**
     * @param height
     *            the height to set
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * @param mimeType
     *            the mimeType to set
     */
    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    /**
     * @param name
     *            the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param size
     *            the size to set
     */
    public void setSize(long size) {
        this.size = size;
    }

    /**
     * @param width
     *            the width to set
     */
    public void setWidth(int width) {
        this.width = width;
    }
}
