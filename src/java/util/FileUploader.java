/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package util;

import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import io.github.cdimascio.dotenv.Dotenv;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Map;

/**
 *
 * @author duyqu
 */
public class FileUploader {

    private Cloudinary cloudinary;

    public FileUploader() {
//        String envFilePath = "../../../web/WEB-INF/config/public/.env";
//        Dotenv dotenv = Dotenv.configure().directory(envFilePath).load();

        cloudinary = new Cloudinary("cloudinary://862897862624195:HOEMk8jKvsO2HsKZf5aBSq05x90@dsvllb1am");//hard-coded url
//        System.out.println(cloudinary.toString());
//        System.out.println(dotenv.get("CLOUDINARY_URL"));
    }

    public String uploadAndReturnUrl(File uploadFile, String fileName, String uploadFolder) throws IOException {
        String result = "";
        Map<String, Object> uploadParams = ObjectUtils.asMap(
                "use_filename", true,
                "unique_filename", false,
                "overwrite", true,
                "public_id", fileName, // Set the desired name for the uploaded image
                "folder", uploadFolder // Specify the nested folder path
        );

        Map uploadResult = cloudinary.uploader().upload(uploadFile, uploadParams);

        result = (String) uploadResult.get("secure_url");
        return result;
    }

    public static void main(String[] args) throws IOException {
        FileUploader fu = new FileUploader();
//        File tempFile = new File("/Demo.jpg");
//        URL path = FileUploader.class.getResource("Demo.jpg");
//        File f = new File(path.getFile());
//        fu.uploadAndReturnUrl(f, "check", "user");
    }

}
