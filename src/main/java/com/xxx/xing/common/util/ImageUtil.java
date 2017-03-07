package com.xxx.xing.common.util;

import org.imgscalr.Scalr;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import static org.imgscalr.Scalr.resize;

/**
 * @author xing
 * @Created by 2017-03-07 下午2:33.
 */
public class ImageUtil {
    public static void convertImage(InputStream ins, String newFilename) {
        BufferedImage originalImage;
        try {
            originalImage = ImageIO.read(ins);
            BufferedImage thumbnail = resize(originalImage, Scalr.Method.AUTOMATIC, Scalr.Mode.FIT_TO_WIDTH,
                    50, 50, Scalr.OP_ANTIALIAS);

            if (!ImageIO.write(thumbnail, "JPEG", new File(newFilename)))
                System.out.println("File write failed.");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
