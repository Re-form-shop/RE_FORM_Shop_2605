package com.re_form_shop_2605.service.member;

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
import java.util.UUID;

@Service
public class MemberImageServiceImpl implements MemberImageService {

    private static final Logger log = LogManager.getLogger(MemberImageServiceImpl.class);
    private final Path uploadRootPath;

    public MemberImageServiceImpl(
            @Value("${spring.servlet.multipart.location}") String uploadRoot
    ) {
        this.uploadRootPath = Paths.get(uploadRoot).toAbsolutePath().normalize().resolve("member");
    }

    @Override
    // 회원별 전용 폴더를 만들고 프로필 이미지 URL을 반환
    public String saveProfileImage(Long memberId, MultipartFile profileImage) {
        if (profileImage == null || profileImage.isEmpty()) {
            return null;
        }

        try {
            Path memberDirectory = uploadRootPath.resolve(String.valueOf(memberId));
            Files.createDirectories(memberDirectory);

            String extension = extractExtension(profileImage.getOriginalFilename());
            String savedFileName = "profile_" + UUID.randomUUID() + extension;
            Path targetPath = memberDirectory.resolve(savedFileName);

            try (InputStream inputStream = profileImage.getInputStream()) {
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }

            return "/uploads/member/" + memberId + "/" + savedFileName;
        } catch (IOException e) {
            log.error("회원 프로필 이미지 저장 실패. memberId={}", memberId, e);
            throw new IllegalStateException("회원 프로필 이미지 저장에 실패했습니다.", e);
        }
    }

    @Override
    // 회원별 이미지 폴더 삭제
    public void deleteProfileImageDirectory(Long memberId) {
        Path memberDirectory = uploadRootPath.resolve(String.valueOf(memberId));

        if (!Files.exists(memberDirectory)) {
            return;
        }

        try (var walk = Files.walk(memberDirectory)) {
            walk.sorted((left, right) -> right.getNameCount() - left.getNameCount())
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            throw new IllegalStateException("회원 프로필 이미지 디렉터리 삭제에 실패했습니다.", e);
                        }
                    });
        } catch (IOException e) {
            log.error("회원 프로필 이미지 디렉터리 삭제 실패. memberId={}", memberId, e);
            throw new IllegalStateException("회원 프로필 이미지 디렉터리 삭제에 실패했습니다.", e);
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
