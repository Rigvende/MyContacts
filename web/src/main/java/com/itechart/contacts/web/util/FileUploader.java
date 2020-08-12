package com.itechart.contacts.web.util;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import java.io.File;

/**
 * Class for file upload operations.
 * @author Marianna Patrusova
 * @version 1.0
 */
public class FileUploader {

    //write photo on hard disk
    public void writePhoto(FileItem item, String photoPath, long id) throws Exception {
        String fileName = FilenameUtils.getName(item.getName());
        File fileDir = new File(photoPath + id);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        File file = new File(fileDir + "\\" + fileName);
        item.write(file);
        File server_dir;
        String dirPath = getClass().getResource("/").getPath();
        server_dir = new File(buildNewDirPath(dirPath) + id + "\\");
        FileUtils.copyFileToDirectory(file, server_dir);
    }

    //building the new dir path for copying the loaded file to container
    public String buildNewDirPath(String dirPath) {
        dirPath = dirPath.substring(1);
        dirPath = dirPath.replace("WEB-INF/classes/", "image/photos/");
        dirPath = dirPath.replace("/", "\\");
        dirPath = dirPath.replace("%20", " ");
        return dirPath;
    }
}
