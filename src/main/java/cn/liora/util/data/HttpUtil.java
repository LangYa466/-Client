package cn.liora.util.data;

import cn.liora.util.misc.Logger;

import javax.imageio.ImageIO;
import javax.imageio.ImageReader;
import javax.imageio.stream.ImageInputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;
import java.util.Iterator;

/**
 * @author ChengFeng
 * @since 2024/8/13
 **/
public class HttpUtil {
    public static void downloadImage(String imageURL, File file, boolean rewrite) throws IOException {
        if (file.exists() && !rewrite) return;

        URL url = new URL(imageURL);
        File tempFile = File.createTempFile("tempImage", ".tmp");

        try (InputStream inputStream = url.openStream()) {
            // 将图像下载到临时文件
            Files.copy(inputStream, tempFile.toPath(), StandardCopyOption.REPLACE_EXISTING);

            if (isSupportedImageFormat(tempFile)) {
                // 如果是 JPG 文件，将临时文件重命名为目标文件
                Files.move(tempFile.toPath(), file.toPath(), StandardCopyOption.REPLACE_EXISTING);
                Logger.debug("Downloaded image: " + url + " to " + file.getName());
            } else {
                // 使用 FFmpeg 转换图像
                if (file.exists()) file.delete();
                FFMPEGUtil.convertImage(tempFile, file);

                Logger.debug("Converted image: " + url + " to " + file.getName());
            }
        } catch (IOException e) {
            Logger.error("IO Exception: " + e.getMessage());
            e.printStackTrace();
        } finally {
            // 清理临时文件
            if (tempFile.exists()) {
                Files.delete(tempFile.toPath());
            }
        }
    }

    public static boolean isSupportedImageFormat(File file) throws IOException {
        // 尝试读取图像
        BufferedImage image = ImageIO.read(file);
        if (image != null) {
            return true; // 如果能够读取图像，说明是支持的格式
        }

        // 如果无法读取，尝试检查支持的格式
        try (ImageInputStream iis = ImageIO.createImageInputStream(file)) {
            if (iis == null) {
                return false; // 如果无法创建 ImageInputStream，文件可能不是图像格式
            }

            Iterator<ImageReader> readers = ImageIO.getImageReaders(iis);
            return readers.hasNext(); // 如果有 ImageReader，说明文件是支持的格式
        }
    }
}
