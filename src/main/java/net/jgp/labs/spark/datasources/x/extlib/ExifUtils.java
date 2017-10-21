package net.jgp.labs.spark.datasources.x.extlib;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.TimeZone;

import org.mortbay.log.Log;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
import com.drew.metadata.Metadata;
import com.drew.metadata.MetadataException;
import com.drew.metadata.Tag;
import com.drew.metadata.exif.ExifSubIFDDirectory;
import com.drew.metadata.jpeg.JpegDirectory;

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
            Log.warn("I/O error while reading attributes of {}. Got {}. Ignoring attributes.", absolutePathToPhoto,
                    e.getMessage());
        }
        if (attr != null) {
            photo.setFileCreationDate(attr.creationTime());
            photo.setFileLastAccessDate(attr.lastAccessTime());
            photo.setFileLastModifiedDate(attr.lastModifiedTime());
        }

        // Extra info
        photo.setMimeType("image/jpeg");
        String extension = absolutePathToPhoto.substring(absolutePathToPhoto.lastIndexOf('.') + 1);
        photo.setExtension(extension);

        // Info from EXIF
        Metadata metadata = null;
        try {
            metadata = ImageMetadataReader.readMetadata(photoFile);
        } catch (ImageProcessingException e) {
            Log.warn("Image processing exception while reading {}. Got {}. Cannot extract EXIF Metadata.", absolutePathToPhoto,
                    e.getMessage());
        } catch (IOException e) {
            Log.warn("I/O error while reading {}. Got {}. Cannot extract EXIF Metadata.", absolutePathToPhoto, e.getMessage());
        }
        if (metadata == null) {
            return photo;
        }

        Directory jpegDirectory = metadata.getFirstDirectoryOfType(JpegDirectory.class);
        try {
            photo.setHeight(jpegDirectory.getInt(1));
            photo.setWidth(jpegDirectory.getInt(3));
        } catch (MetadataException e) {
            Log.warn("Issues while extracting dimensions from {}. Got {}. Ignoring dimensions.", absolutePathToPhoto,
                    e.getMessage());
        }

        Directory exifSubIFDDirectory = metadata.getFirstDirectoryOfType(ExifSubIFDDirectory.class);
        if (exifSubIFDDirectory != null) {
            photo.setDateTaken(exifSubIFDDirectory.getDate(36867, TimeZone.getTimeZone("EST")));
        }
        // photo.setGeoX(geoX);
        // photo.setGeoY(geoY);

        return photo;
    }

}
