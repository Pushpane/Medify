package com.psl.controller;

import java.io.IOException;

import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

@RestController
@AllArgsConstructor
@RequestMapping("/api/user")
public class HomeController {

	@GetMapping("/hello")
	public String sayHello() {
		return "Hello";
	}
	
	@RequestMapping(value = "/Medify/Image/{id:.+}", method = RequestMethod.GET)
	public ResponseEntity<byte[]> getImage(@PathVariable("id") String id) {
		String UPLOAD_DIR = null;
		try {
			UPLOAD_DIR = new ClassPathResource("static/image/").getFile().getAbsolutePath();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(UPLOAD_DIR);
		return ResponseEntity.ok().contentType(MediaType.valueOf(FileTypeMap.getDefaultFileTypeMap().getContentType(img))).body(Files.readAllBytes(img.toPath()));
	}
}
