package org.durcit.be.upload.service.Impl;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.ObjectMetadata;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.durcit.be.post.service.PostService;
import org.durcit.be.system.exception.upload.ImageNotFoundException;
import org.durcit.be.system.exception.upload.S3UploadException;
import org.durcit.be.upload.domain.Images;
import org.durcit.be.upload.dto.UploadRequest;
import org.durcit.be.upload.dto.UploadUpdateRequest;
import org.durcit.be.upload.repository.ImagesRepository;
import org.durcit.be.upload.service.UploadService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

import static org.durcit.be.system.exception.ExceptionMessage.IMAGE_NOT_FOUND_ERROR;
import static org.durcit.be.system.exception.ExceptionMessage.S3_UPLOAD_ERROR;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class S3ServiceImpl implements UploadService {

    private final AmazonS3 amazonS3;
    private final PostService postService;
    private final ImagesRepository imagesRepository;

    @Value("${custom.s3.bucket-name}")
    private String bucketName;

    @Override
    @Transactional
    public void upload(UploadRequest request) {
        uploadFiles(request.getPostId(), request.getFiles());
    }

    @Transactional
    public void deleteImages(List<Long> imageIds) {
        if (imageIds != null && !imageIds.isEmpty()) {
            for (Long imageId : imageIds) {
                Images existingImage = imagesRepository.findById(imageId)
                        .orElseThrow(() -> new ImageNotFoundException(IMAGE_NOT_FOUND_ERROR));

                String existingFileKey = extractFileKeyFromUrl(existingImage.getUrl());
                amazonS3.deleteObject(bucketName, existingFileKey);

                imagesRepository.delete(existingImage);
            }
        }
    }

    @Transactional
    public void updateImages(UploadUpdateRequest request) {
        deleteImages(request.getImageIdsToDelete());
        uploadFiles(request.getPostId(), request.getNewFiles());
    }

    private void uploadFiles(Long postId, List<MultipartFile> files) {
        if (files != null && !files.isEmpty()) {
            for (MultipartFile file : files) {
                if (!file.isEmpty()) {
                    try {
                        String uniqueFileName = generateUniqueFileName(file.getOriginalFilename());

                        ObjectMetadata metadata = new ObjectMetadata();
                        metadata.setContentLength(file.getSize());
                        metadata.setContentType(file.getContentType());

                        amazonS3.putObject(bucketName, uniqueFileName, file.getInputStream(), metadata);

                        String s3Url = amazonS3.getUrl(bucketName, uniqueFileName).toString();

                        Images image = Images.builder()
                                .post(postService.getById(postId))
                                .url(s3Url)
                                .originalFilename(file.getOriginalFilename())
                                .build();

                        imagesRepository.save(image);

                    } catch (IOException e) {
                        log.error("Failed to upload file: {}", file.getOriginalFilename(), e);
                        throw new S3UploadException(S3_UPLOAD_ERROR);
                    }
                }
            }
        }
    }

    private String extractFileKeyFromUrl(String s3Url) {
        return s3Url.substring(s3Url.lastIndexOf("/") + 1);
    }

    private String generateUniqueFileName(String originalFilename) {
        return UUID.randomUUID().toString() + "_" + originalFilename;
    }
}
