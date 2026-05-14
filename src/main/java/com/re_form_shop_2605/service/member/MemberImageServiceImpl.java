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
/**
 * ─────────────────────────────────────────────────────
 * 작성자: 김민기
 * 작성일: 2026-05-10
 * 설명: 회원 이미지 저장 및 관리 서비스 구현체
 * ─────────────────────────────────────────────────────
 */
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
        Path memberDirectory = uploadRootPath.resolve(String.valueOf(memberId));
        String urlPrefix = "/uploads/member/" + memberId + "/";
        return saveImage(memberDirectory, urlPrefix, profileImage);
    }

    @Override
    // 프로필 수정 전에 업로드한 이미지를 temp 경로에 저장하고 URL을 반환한다.
    public String saveTemporaryProfileImage(Long memberId, MultipartFile profileImage) {
        Path tempDirectory = uploadRootPath.resolve("temp").resolve(String.valueOf(memberId));
        String urlPrefix = "/uploads/member/temp/" + memberId + "/";
        return saveImage(tempDirectory, urlPrefix, profileImage);
    }

    @Override
    // temp 경로에 올라간 이미지를 실제 프로필 경로로 이동해 최종 URL을 반환한다.
    public String promoteTemporaryProfileImage(Long memberId, String profileImageUrl) {
        String tempPrefix = "/uploads/member/temp/" + memberId + "/";
        if (!StringUtils.hasText(profileImageUrl) || !profileImageUrl.startsWith(tempPrefix)) {
            return profileImageUrl;
        }

        String fileName = profileImageUrl.substring(tempPrefix.length());
        Path tempPath = uploadRootPath.resolve("temp").resolve(String.valueOf(memberId)).resolve(fileName);
        if (!Files.exists(tempPath)) {
            throw new IllegalArgumentException("임시 프로필 이미지가 존재하지 않습니다.");
        }

        try {
            Path memberDirectory = uploadRootPath.resolve(String.valueOf(memberId));
            Files.createDirectories(memberDirectory);

            Path targetPath = memberDirectory.resolve(fileName);
            Files.move(tempPath, targetPath, StandardCopyOption.REPLACE_EXISTING);

            Path tempDirectory = tempPath.getParent();
            if (tempDirectory != null && Files.exists(tempDirectory)) {
                try (var walk = Files.walk(tempDirectory)) {
                    walk.sorted((left, right) -> right.getNameCount() - left.getNameCount())
                            .forEach(path -> {
                                try {
                                    Files.deleteIfExists(path);
                                } catch (IOException e) {
                                    throw new IllegalStateException("임시 프로필 이미지 디렉터리 정리에 실패했습니다.", e);
                                }
                            });
                }
            }

            return "/uploads/member/" + memberId + "/" + fileName;
        } catch (IOException e) {
            log.error("임시 프로필 이미지 이동 실패. memberId={}, profileImageUrl={}", memberId, profileImageUrl, e);
            throw new IllegalStateException("임시 프로필 이미지 반영에 실패했습니다.", e);
        }
    }

    // 실제 파일 저장 공통 로직을 모아 프로필 이미지와 임시 이미지가 같은 규칙을 쓰게 한다.
    private String saveImage(Path targetDirectory, String urlPrefix, MultipartFile profileImage) {
        if (profileImage == null || profileImage.isEmpty()) {
            return null;
        }

        try {
            Files.createDirectories(targetDirectory);

            String extension = extractExtension(profileImage.getOriginalFilename());
            String savedFileName = "profile_" + UUID.randomUUID() + extension;
            Path targetPath = targetDirectory.resolve(savedFileName);

            try (InputStream inputStream = profileImage.getInputStream()) {
                Files.copy(inputStream, targetPath, StandardCopyOption.REPLACE_EXISTING);
            }

            return urlPrefix + savedFileName;
        } catch (IOException e) {
            log.error("회원 프로필 이미지 저장 실패. directory={}", targetDirectory, e);
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
