package com.re_form_shop_2605.service.trade;

import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

class PostImageServiceImplTest {

    @Test
    void saveTemporaryPostImages_createsThumbnailFile() throws IOException {
        Path tempDir = createTestDirectory();
        PostImageServiceImpl service = new PostImageServiceImpl(tempDir.toString());
        MockMultipartFile imageFile = new MockMultipartFile(
                "images",
                "sample.jpg",
                "image/jpeg",
                createImageBytes(1200, 900, "jpg")
        );

        List<String> imageUrls = service.saveTemporaryPostImages(7L, List.of(imageFile));

        assertEquals(1, imageUrls.size());

        Path postRoot = tempDir.resolve("post").resolve("temp").resolve("7");
        Path savedImagePath = postRoot.resolve(extractFileName(imageUrls.get(0)));
        Path thumbnailPath = postRoot.resolve(extractFileName(service.getThumbnailUrl(imageUrls.get(0))));

        assertTrue(Files.exists(savedImagePath));
        assertTrue(Files.exists(thumbnailPath));

        BufferedImage thumbnailImage = ImageIO.read(thumbnailPath.toFile());
        assertNotNull(thumbnailImage);
        assertTrue(thumbnailImage.getWidth() <= 480);
        assertTrue(thumbnailImage.getHeight() <= 480);
    }

    @Test
    void finalizePostImageUrls_movesThumbnailTogether() throws IOException {
        Path tempDir = createTestDirectory();
        PostImageServiceImpl service = new PostImageServiceImpl(tempDir.toString());
        MockMultipartFile imageFile = new MockMultipartFile(
                "images",
                "sample.jpg",
                "image/jpeg",
                createImageBytes(1000, 700, "jpg")
        );

        List<String> tempUrls = service.saveTemporaryPostImages(9L, List.of(imageFile));
        String tempImageUrl = tempUrls.get(0);
        String tempThumbnailUrl = service.getThumbnailUrl(tempImageUrl);

        List<String> finalizedUrls = service.finalizePostImageUrls(21L, 9L, tempUrls);
        String finalizedImageUrl = finalizedUrls.get(0);
        String finalizedThumbnailUrl = service.getThumbnailUrl(finalizedImageUrl);

        assertEquals("/uploads/post/21/", finalizedImageUrl.substring(0, "/uploads/post/21/".length()));
        assertEquals("/uploads/post/21/", finalizedThumbnailUrl.substring(0, "/uploads/post/21/".length()));

        Path tempPostRoot = tempDir.resolve("post").resolve("temp").resolve("9");
        Path finalPostRoot = tempDir.resolve("post").resolve("21");

        assertFalse(Files.exists(tempPostRoot.resolve(extractFileName(tempImageUrl))));
        assertFalse(Files.exists(tempPostRoot.resolve(extractFileName(tempThumbnailUrl))));
        assertTrue(Files.exists(finalPostRoot.resolve(extractFileName(finalizedImageUrl))));
        assertTrue(Files.exists(finalPostRoot.resolve(extractFileName(finalizedThumbnailUrl))));
    }

    private byte[] createImageBytes(int width, int height, String format) throws IOException {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics2D graphics = image.createGraphics();
        try {
            graphics.setColor(new Color(17, 34, 51));
            graphics.fillRect(0, 0, width, height);
            graphics.setColor(new Color(240, 245, 250));
            graphics.fillRoundRect(80, 80, width - 160, height - 160, 48, 48);
        } finally {
            graphics.dispose();
        }

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        ImageIO.write(image, format, outputStream);
        return outputStream.toByteArray();
    }

    private String extractFileName(String imageUrl) {
        return imageUrl.substring(imageUrl.lastIndexOf('/') + 1);
    }

    private Path createTestDirectory() throws IOException {
        return Files.createTempDirectory("post-image-service-test");
    }
}
