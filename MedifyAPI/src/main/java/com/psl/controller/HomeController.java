package com.psl.controller;

import java.io.File;

import org.apache.commons.io.FileUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.psl.exception.MedifyException;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@Slf4j
//@RequestMapping("/api/user")
public class HomeController {

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello";
	}
	
	@GetMapping("/image/{id}")
	public ResponseEntity<ByteArrayResource> getImage(@PathVariable String id) {
		byte[] image = new byte[0];
		try {
			//String UPLOAD_DIR = new ClassPathResource("static/image/").getFile().getAbsolutePath();
			//String UPLOAD_DIR = ".\\tmp\\images";
			String UPLOAD_DIR = System.getProperty("user.dir")+"/tmp/image/";
			new File(UPLOAD_DIR).mkdir();
			image = FileUtils.readFileToByteArray(new File(UPLOAD_DIR+"\\"+id));
			ByteArrayResource resource = new ByteArrayResource(image);
		    return ResponseEntity.ok().contentType(MediaType.IMAGE_JPEG).contentLength(resource.contentLength()).body(resource);
		} catch (Exception e) {
			log.error((e.toString()));
			throw new MedifyException(e.toString());
		}
	}
}
