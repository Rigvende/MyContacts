//package com.itechart.contacts.web.controller;
//
//import com.itechart.contacts.domain.entity.impl.Photo;
//import com.itechart.contacts.domain.service.UpdatePhotoService;
//import org.apache.commons.fileupload.FileItem;
//import org.apache.commons.fileupload.FileUploadBase;
//import org.apache.commons.fileupload.disk.DiskFileItemFactory;
//import org.apache.commons.fileupload.servlet.ServletFileUpload;
//import org.apache.commons.io.FilenameUtils;
//import org.apache.logging.log4j.Level;
//import org.apache.logging.log4j.LogManager;
//import org.apache.logging.log4j.Logger;
//import javax.servlet.RequestDispatcher;
//import javax.servlet.ServletContext;
//import javax.servlet.ServletException;
//import javax.servlet.annotation.WebServlet;
//import javax.servlet.http.HttpServlet;
//import javax.servlet.http.HttpServletRequest;
//import javax.servlet.http.HttpServletResponse;
//import java.io.File;
//import java.io.IOException;
//import java.util.List;
//
///**
// * Class for photo operations controller.
// * @author Marianna Patrusova
// * @version 1.0
// */
//@WebServlet(urlPatterns = "/photos")
//public class PhotoController extends HttpServlet {
//
//    private static final long serialVersionUID = 2936677620395102724L;
//    private String filePath, extraPath;
//    private final static int KBITE = 1024;
//    private final static int MAX_MEMORY_SIZE = 1024;
//    private final static int MAX_PIC_SIZE = 1024;                        //max size of picture in kB
//    private final static int ERROR_404 = 404;
//    private final static int ERROR_500 = 500;
//    private final static String INIT_PARAMETER = "photo-upload";         //set according with web.xml
//    private final static String INIT_PARAMETER_EXT = "file-upload-ext"; //set according with web.xml
//    private final static String CONTACT_PAGE = "/html/contactForm.html";
//    private final static String UTF_8 = "UTF-8";
//    private final static Logger LOGGER = LogManager.getLogger();
//    private final UpdatePhotoService photoService = new UpdatePhotoService();
//
//    //set dir for uploads during servlet initialization
//    public void init() {
//        filePath = getServletContext().getInitParameter(INIT_PARAMETER);
//        extraPath = getServletContext().getInitParameter(INIT_PARAMETER_EXT);
//    }
//
//    //handling request data with FileUpload
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//                                               throws IOException, ServletException {
//        ServletContext servletContext = getServletContext();
//        RequestDispatcher requestDispatcher = servletContext.getRequestDispatcher(CONTACT_PAGE);
//        boolean isMultipart = ServletFileUpload.isMultipartContent(request);
//        if (!isMultipart) {                            //check out if file is loaded; if no - return to the main page
//            requestDispatcher.forward(request, response);
//        }
//        DiskFileItemFactory factory = new DiskFileItemFactory();
//        factory.setSizeThreshold(MAX_MEMORY_SIZE * KBITE);
//        factory.setRepository(new File(extraPath));   //extra dir for overloaded size
//        ServletFileUpload upload = new ServletFileUpload(factory);
//        upload.setSizeMax(MAX_PIC_SIZE * KBITE);
//        try {
//            List<FileItem> fileItems = upload.parseRequest(request);
//            int id = 0;
//            for (FileItem item : fileItems) {
//                if (item.isFormField()) {             //thanks to FileUpload request parameters are null,
//                    id = Integer.parseInt(item.getString(UTF_8));
//                } else {
//                    writePicture(item, id);
//                }
//            }
//            requestDispatcher.forward(request, response);
//        } catch (FileUploadBase.SizeLimitExceededException e) {
//            LOGGER.log(Level.WARN, "File is too big");
//        } catch (IOException e) {
//            LOGGER.log(Level.ERROR, "Resource is not found // " + e.getClass());
//            e.printStackTrace();
//            response.sendError(ERROR_404);
//        } catch (Exception e) {
//            LOGGER.log(Level.ERROR, "Server error // " + e.getClass());
//            e.printStackTrace();
//            response.sendError(ERROR_500);
//        }
//    }
//
//    //write photo on server dir
//    private void writePicture(FileItem item, int id) throws Exception {
//        String fileName = FilenameUtils.getName(item.getName());
//        Photo photo = new Photo(id, filePath, fileName);
//        String dir;
//        if (id == 0) { //if new photo - save new
//            photo = (Photo) photoService.service(photo);
//            File server_dir;
//            dir = filePath + photo.getPhotoId() + "/";
//            server_dir = new File(dir); //create dir with id_photo
//            if (server_dir.mkdir()) {
//                File file;
//                file = new File(dir + fileName);
//                item.write(file);
//            } else {
//                throw new Exception("Cannot create photo dir");
//            }
//        } else {
//            //if update - delete old photo and save new
//            String oldName = photoService.service(id, fileName);
//            if (oldName != null) {
//                dir = filePath + photo.getPhotoId() + "/";
//                File oldFile = new File(dir + oldName);
//                if (oldFile.delete()) {
//                    File file;
//                    file = new File(dir + fileName);
//                    item.write(file);
//                }
//            } else {
//                throw new Exception("Cannot delete old photo");
//            }
//        }
//    }
//
//    //get method is not using here (use ContactsController)
//    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException {
//        LOGGER.log(Level.ERROR, "GET method used with " + getClass().getName() + ": POST method required.");
//        throw new ServletException();
//    }
//
//}
