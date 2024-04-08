package com.nhdic_backend.controller;

import jakarta.validation.Valid;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping(value = "/public", produces = MediaTypes.HAL_JSON_VALUE)
public class IndexController {

  @PostMapping("/upload")
  public ResponseEntity<?> uploadFileWithRequestDto(
      @RequestPart(value = "file", required = true) MultipartFile file,
      @RequestPart(value = "requestDto") @Valid UploadRequest request) {

    System.out.println("file = " + file);
    System.out.println("request.getTitle() = " + request.getTitle());

    return ResponseEntity.ok().body("Uploaded!");
  }
}
