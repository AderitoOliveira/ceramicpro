package nuvemdesoftware.ceramicpro.utils;

import nuvemdesoftware.ceramicpro.exception.CopyFileException;
import nuvemdesoftware.ceramicpro.exception.FileTooLargeException;
import nuvemdesoftware.ceramicpro.exception.MissingFileException;
import nuvemdesoftware.ceramicpro.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Component
public class FileUtil {

    private static final Logger LOG = LoggerFactory.getLogger(FileUtil.class);

    private static final String PROFILE_DIR = "profile";

    @Value("${products.files.base.path}")
    private String productsFilesBasePath;

    @Value("${client.files.base.path}")
    private String clientsFilesBasePath;

    @Value("${max.file.upload.size}")
    private Long maxFileUploadSize;


    //public void copyFile(MultipartFile file, User user) throws MissingFileException, FileTooLargeException, CopyFileException {
    public void copyFile(MultipartFile file) throws MissingFileException, FileTooLargeException, CopyFileException {

        validateFile(file, maxFileUploadSize);
        //saveFile(file, user);
        saveFile(file);

    }


    //private void saveFile(MultipartFile file, User user) throws CopyFileException {
    private void saveFile(MultipartFile file) throws CopyFileException {
        try (InputStream is = file.getInputStream()) {
            User user = new User();
            String newFileName = getNewFileName(file, user);
            //Path rootLocation = Paths.get(getRootLocationForUserProfileImageUpload(user));

            //LOG.debug("Saving file to " + rootLocation);

            //Path fileRelative = Paths.get(userFilesBasePath + "\\" + "ABCD.jpeg");
            Path fileRelative = Paths.get(productsFilesBasePath + "\\");
            //Files.createFile(fileRelative);

            //Path newFilePath = Paths.get("C:\\Users\\anoliveira\\Desktop\\ceramicpro\\src\\main\\resources\\images\\XYZ.jpeg");
            //Files.createFile(newFilePath);

            Files.copy(is, fileRelative.resolve(file.getOriginalFilename()));
        } catch (IOException ie) {
            LOG.error("Problem uploading file!", ie);
            throw new CopyFileException("Failed to upload!");
        }
    }


    private void validateFile(MultipartFile file, Long maxFileUploadSize) throws MissingFileException, FileTooLargeException {
        checkFileExistence(file);
        checkFileSize(file, maxFileUploadSize);
    }


    private String getNewFileName(MultipartFile file, User user) {
        LOG.debug("File name is " + file.getOriginalFilename());

        String newFileName = FileNameUtil.createFileName(user, file.getOriginalFilename());
        LOG.debug("New file name is " + newFileName);

        return newFileName;
    }


    public void checkFileSize(MultipartFile file, Long maxFileUploadSize) throws FileTooLargeException {
        if (file.getSize() > maxFileUploadSize) {
            String message = "File is too large - max size is " + maxFileUploadSize;
            throw new FileTooLargeException(message);
        }
    }


    public void checkFileExistence(MultipartFile file) throws MissingFileException {
        if (file == null) throw new MissingFileException("No file sent!");
        if (StringUtils.isEmpty(file.getName())) throw new MissingFileException("No file sent!");
    }


    private void createDirectoryIfItDoesntExist(String dir) {
        final Path path = Paths.get(dir);

        if (Files.notExists(path)) {
            try {
                Files.createDirectories(path);
            } catch (IOException ie) {
                LOG.error("Problem creating directory " + dir);
            }
        }
    }


    public String properSeparators(String filePath) {
        if (filePath != null) {
            String properPath = filePath.replaceAll("\\\\", "/");
            return properPath;
        } else {
            return null;
        }
    }


    public String getRootLocationForUserUpload(User user) {
        if (user == null) throw new IllegalArgumentException("No user provided!");
        if (StringUtils.isEmpty(user.getUsername())) throw new IllegalArgumentException("No user id!");

        StringBuilder builder = new StringBuilder();

        builder.append(productsFilesBasePath);
        builder.append("/");
        builder.append(user.getUsername());

        String location = builder.toString();

        createDirectoryIfItDoesntExist(location);

        return location;
    }


    public String getRootLocationForUserProfileImageUpload(User user) {
        if (user == null) throw new IllegalArgumentException("No user provided!");
        if (StringUtils.isEmpty(user.getUsername())) throw new IllegalArgumentException("No user id!");

        String base = getRootLocationForUserUpload(user);

        StringBuilder builder = new StringBuilder(base);
        builder.append("/");
        builder.append(PROFILE_DIR);

        String location = builder.toString();

        createDirectoryIfItDoesntExist(location);

        return productsFilesBasePath;
    }
}
