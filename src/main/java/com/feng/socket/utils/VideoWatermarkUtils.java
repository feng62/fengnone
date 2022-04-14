package com.feng.socket.utils;

import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.bytedeco.javacv.Java2DFrameConverter;
import sun.font.FontDesignMetrics;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class VideoWatermarkUtils {
    /**
     * 给视频日添加水印
     *
     * @param VIDEO_OLD_PATH 原视频路径
     * @param VIDEO_NEW_PATH 添加水印后视频路径
     * @param context        添加水印的文字
     * @throws Exception
     */
    public static void addTextByGraphics(String VIDEO_OLD_PATH, String VIDEO_NEW_PATH, String context) throws Exception {
        // 设置源视频、加字幕后的视频文件路径
        FFmpegFrameGrabber fFmpegFrameGrabber =
                new FFmpegFrameGrabber(new File("D:\\ReallyShare\\project\\logo.png"));
        fFmpegFrameGrabber.start();


        FFmpegFrameGrabber grabber =
                new FFmpegFrameGrabber(VIDEO_OLD_PATH);
        grabber.start();
        FFmpegFrameRecorder recorder =
                new FFmpegFrameRecorder(VIDEO_NEW_PATH,
                        grabber.getImageWidth(), grabber.getImageHeight(), grabber.getAudioChannels());

        // 视频相关配置，取原视频配置
        recorder.setFrameRate(grabber.getFrameRate());
        recorder.setVideoCodec(grabber.getVideoCodec());
        recorder.setVideoBitrate(grabber.getVideoBitrate());

        // 音频相关配置，取原音频配置
        recorder.setSampleRate(grabber.getSampleRate());
        recorder.setAudioCodec(grabber.getAudioCodec());
        recorder.setAudioBitrate(grabber.getAudioBitrate());
        recorder.start();

        Frame f = fFmpegFrameGrabber.grabFrame();
        int w = grabber.getImageWidth() / 13;
        int h = grabber.getImageHeight() / 13;
        Java2DFrameConverter converter = new Java2DFrameConverter();
        BufferedImage fecthedImage = converter.getBufferedImage(f);
        BufferedImage bi = new BufferedImage(w, h, BufferedImage.TYPE_3BYTE_BGR);
        bi.getGraphics().drawImage(fecthedImage.getScaledInstance(w, h, Image.SCALE_SMOOTH),
                0, 0, null);

        Frame frame;
        while ((frame = grabber.grab()) != null) {

            // 从视频帧中获取图片
            if (frame.image != null) {
                BufferedImage bufferedImage = converter.getBufferedImage(frame);
                // 对图片进行文本合入
                    bufferedImage = addText(bufferedImage,bi, context,w, 8);
                // 视频帧赋值，写入输出流
                recorder.record(converter.getFrame(bufferedImage));
            }
            //配置音乐
            if (frame.samples != null) {
                recorder.record(frame);
            }
        }
        recorder.close();
        grabber.close();
    }


    /**
     * 图片添加文本
     *
     * @param bufImg  bufImg
     * @param content content
     * @return BufferedImage
     */
    private static BufferedImage addText(BufferedImage bufImg,BufferedImage bufImg1, String content,int w, int bb) throws IOException {
        Graphics2D graphics = bufImg.createGraphics();
        graphics.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
        //设置字体
        Font font = new Font("宋体", Font.BOLD, 32);
        graphics.setFont(font);
        graphics.drawString(content, 30 + w , 30 + w / 2);
        graphics.drawImage(bufImg1,  20,  20, w, w, null);
        graphics.dispose();
        return bufImg;
    }

}
