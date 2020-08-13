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
    public void writeFile(FileItem item, String filePath, long id) throws Exception {
        String fileName = FilenameUtils.getName(item.getName());
        File fileDir = new File(filePath + id);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        }
        File attachment = new File(fileDir + "\\" + fileName);
        item.write(attachment);
        String dirPath = getClass().getResource("/").getPath();
        String fileType = "attachment";
        File serverDir = new File(buildNewDirPath(dirPath, fileType) + id + "\\");
        if (!serverDir.exists()) {
            serverDir.mkdir();
        }
        FileUtils.copyFileToDirectory(attachment, serverDir);
    }

    //write photo on hard disk
    public void writePhoto(FileItem item, String photoPath, long id) throws Exception {
        String fileName = FilenameUtils.getName(item.getName());
        File fileDir = new File(photoPath + id);
        if (!fileDir.exists()) {
            fileDir.mkdir();
        } else {
            for (File file: fileDir.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
        File photo = new File(fileDir + "\\" + fileName);
        item.write(photo);
        String dirPath = getClass().getResource("/").getPath();
        String fileType = "photo";
        File serverDir = new File(buildNewDirPath(dirPath, fileType) + id + "\\");
        if (!serverDir.exists()) {
            serverDir.mkdir();
        } else {
            for (File file: serverDir.listFiles()) {
                if (file.isFile()) {
                    file.delete();
                }
            }
        }
        FileUtils.copyFileToDirectory(photo, serverDir);
    }

    //delete file
    public void deleteFile(String filePath, String name, long id) {
        File file = new File(filePath + id + "\\" + name);
        if (file.exists()) {
            file.delete();
        }
        String serverPath = getClass().getResource("/").getPath() + id + "/" + name;
        String fileType = "attachment";
        File serverFile = new File(buildNewDirPath(serverPath, fileType));
        if (serverFile.exists()) {
            serverFile.delete();
        }
    }

    //building the new dir path for copying the loaded file to container
    private String buildNewDirPath(String dirPath, String fileType) {
        dirPath = dirPath.substring(1);
        if (fileType.equals("photo")) {
            dirPath = dirPath.replace("WEB-INF/classes/", "image/photos/");
        } else {
            dirPath = dirPath.replace("WEB-INF/classes/", "attachments/");
        }
        dirPath = dirPath.replace("/", "\\");
        dirPath = dirPath.replace("%20", " ");
        return dirPath;
    }
}
