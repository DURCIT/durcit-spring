package org.durcit.be.upload.controller;

import lombok.RequiredArgsConstructor;
import org.durcit.be.system.response.ResponseCode;
import org.durcit.be.system.response.ResponseData;
import org.durcit.be.upload.dto.ProfileImageRequest;
import org.durcit.be.upload.service.ProfileUploadService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/members/profile")
@RequiredArgsConstructor
public class ProfileUploadController {

    private final ProfileUploadService profileUploadService;

    @PutMapping
    public ResponseEntity<ResponseData> profileImageUpdate(ProfileImageRequest profileImageRequest) {
        profileUploadService.uploadProfileReturnUrl(profileImageRequest);
        return ResponseData.toResponseEntity(ResponseCode.UPDATE_PROFILE_IMAGE_SUCCESS);
    }

}
