package net.jgp.labs.spark.datasources.x.extlib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;

import org.mortbay.log.Log;

public class ExifUtils {

    public static PhotoMetadata processFromFilename(String absolutePathToPhoto) {
        PhotoMetadata photo = new PhotoMetadata();
        photo.setFilename(absolutePathToPhoto);

        // Info from file
        File photoFile = new File(absolutePathToPhoto);
        photo.setName(photoFile.getName());
        photo.setSize(photoFile.length());
        photo.setDirectory(photoFile.getParent());

        Path file = Paths.get(absolutePathToPhoto);
        BasicFileAttributes attr = null;
        try {
            attr = Files.readAttributes(file, BasicFileAttributes.class);
        } catch (IOException e) {
            Log.warn(
                    "I/O error while reading attributes of {}. Got {}. Ignoring attributes.",
                    absolutePathToPhoto, e.getMessage());
        }
        if (attr != null) {
            photo.setFileCreationDate(attr.creationTime());
            photo.setFileLastAccessDate(attr.lastAccessTime());
            photo.setFileLastModifiedDate(attr.lastModifiedTime());
        }

        // Info from EXIF

        // photo.setDateTaken(dateTaken);
        // photo.setExtension(extension);
        // photo.setFileDate();
        // photo.setGeoX(geoX);
        // photo.setGeoY(geoY);
        // photo.setHeight(height);
        // photo.setWidth(width);

        photo.setMimeType("image/jpeg");

        return photo;
    }

}
