package com.example.Contact_manager_web.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
public interface ImageService {

    String uploadImage(MultipartFile contactImage) ;

    String getUrlFromPublicId (String publicId);


}
