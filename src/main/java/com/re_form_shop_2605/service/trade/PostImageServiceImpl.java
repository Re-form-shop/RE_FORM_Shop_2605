package com.re_form_shop_2605.service.trade;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 판매글 이미지 저장 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
@Service
public class PostImageServiceImpl implements PostImageService {

    private static final Logger log = LogManager.getLogger(PostImageServiceImpl.class);
    private static final int MAX_POST_IMAGES = 10;
    private final Path uploadRootPath;

    public PostImageServiceImpl(
            @Value("${spring.servlet.multipart.location}") String uploadRoot
    ) {
        this.uploadRootPath = Paths.get(uploadRoot).toAbsolutePath().normalize().resolve("post");
    }

    @Override
    // 게시글별 전용 폴더를 만들고 이미지 URL 목록을 반환
    public List<String> savePostImages(Long postId, List<MultipartFile> images) {
        return saveImages(uploadRootPath.resolve(String.valueOf(postId)), "/uploads/post/" + postId + "/", images);
    }

    @Override
    // 판매글 작성 전에 임시 업로드한 이미지를 회원별 temp 폴더에 저장하고 URL 목록을 반환한다.
    public List<String> saveTemporaryPostImages(Long memberId, List<MultipartFile> images) {
        Path tempDirectory = uploadRootPath.resolve("temp").resolve(String.valueOf(memberId));
        String urlPrefix = "/uploads/post/temp/" + memberId + "/";
        return saveImages(tempDirectory, urlPrefix, images);
    }

    // 실제 파일 저장 공통 로직을 한 곳으로 모아 게시글 업로드와 임시 업로드가 같은 규칙을 쓰게 한다.
    private List<String> saveImages(Path targetDirectory, String urlPrefix, List<MultipartFile> images) {
        if (images == null || images.isEmpty()) {
            return List.of();
        }

        List<MultipartFile> validImages = new ArrayList<>();

        for (MultipartFile image : images) {
            if (image != null && !image.isEmpty()) {
                validImages.add(image);
            }
        }

        if (validImages.size() > MAX_POST_IMAGES) {
            throw new IllegalArgumentException("이미지는 최대 10장까지 업로드할 수 있습니다.");
        }

        try {
            Files.createDirectories(targetDirectory);

            List<String> savedImageUrls = new ArrayList<>();

            for (int i = 0; i < validImages.size(); i++) {
                MultipartFile image = validImages.get(i);
                String extension = extractExtension(image.getOriginalFilename());
                String savedFileName = String.format("%02d_%s%s", i + 1, UUID.randomUUID(), extension);
                Path targetPath = targetDirectory.resolve(savedFileName);

                try (InputStream inputStream = image.getInputStream()) {
                    Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
                }
                savedImageUrls.add(urlPrefix + savedFileName);
            }
            return savedImageUrls;
        } catch (IOException e) {
            log.error("판매글 이미지 저장 실패. directory={}", targetDirectory, e);
            throw new IllegalStateException("판매글 이미지 저장에 실패했습니다.", e);
        }
    }

    @Override
    // 게시글별 이미지 폴더 삭제
    public void deletePostImageDirectory(Long postId) {
        Path postDirectory = uploadRootPath.resolve(String.valueOf(postId));

        if (!Files.exists(postDirectory)) {
            return;
        }

        try (var walk = Files.walk(postDirectory)) {
            walk.sorted((left, right) -> right.getNameCount() - left.getNameCount())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            throw new IllegalStateException("판매글 이미지 디렉터리 삭제에 실패했습니다.", e);
                        }
                    });
        } catch (IOException e) {
            log.error("판매글 이미지 디렉터리 삭제 실패. postId={}", postId, e);
            throw new IllegalStateException("판매글 이미지 디렉터리 삭제에 실패했습니다.", e);
        }
    }

    // 원본 파일명에서 확장자만 분리
    private String extractExtension(String originalFilename) {
        if (!StringUtils.hasText(originalFilename)) {
            return "";
        }

        int dotIndex = originalFilename.lastIndexOf('.');
        if (dotIndex < 0 || dotIndex == originalFilename.length() - 1) {
            return "";
        }

        return originalFilename.substring(dotIndex);
    }
}
