package com.example.Contact_manager_web.service.Implementation;

import com.cloudinary.Cloudinary;
import com.cloudinary.Transformation;
import com.cloudinary.utils.ObjectUtils;
import com.example.Contact_manager_web.config.AppConfig;
import com.example.Contact_manager_web.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
public class ImageServiceImpl implements ImageService {
    @Autowired
    private AppConfig appdata;
    @Autowired
    private Cloudinary cloudinary;

    public ImageServiceImpl(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    @Override

    public String uploadImage(MultipartFile contactImage , String fileName) {
        //upload image to the server using Contact Image and cloudinary

//        String fileName = UUID.randomUUID().toString();

        try{
            byte[] data = new byte[contactImage.getInputStream().available()];
            System.out.println("Image file is came to the Image Service page");
            contactImage.getInputStream().read(data);
            cloudinary.uploader().upload(data, ObjectUtils.asMap(
                    "public_id", contactImage.getOriginalFilename()
            ));
            return this.getUrFormPublicId(fileName);
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }
    @Override
    public String getUrFormPublicId(String publicId) {
        return cloudinary
                .url()
                .transformation(
                        new Transformation<>()
                                .width(500)
                                .height(500)
                                .crop("fill")
                )
                .generate(publicId);
    }
}
