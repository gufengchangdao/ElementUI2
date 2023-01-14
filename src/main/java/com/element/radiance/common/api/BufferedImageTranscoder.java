package com.element.radiance.common.api;

import org.apache.batik.transcoder.TranscoderException;
import org.apache.batik.transcoder.TranscoderInput;
import org.apache.batik.transcoder.TranscoderOutput;
import org.apache.batik.transcoder.image.ImageTranscoder;
import org.apache.batik.transcoder.image.PNGTranscoder;
import org.jdesktop.swingx.util.GraphicsUtilities;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import java.io.Reader;

/**
 * SVG转换为BufferedImage
 * 不用特地转为SvgImage，但是缩放的话需要重新解析
 */
public class BufferedImageTranscoder extends ImageTranscoder {
	private BufferedImage img = null;

	@Override
	public BufferedImage createImage(int w, int h) {
		return GraphicsUtilities.createCompatibleTranslucentImage(w, h);
	}

	@Override
	public void writeImage(BufferedImage img, TranscoderOutput output) {
		this.img = img;
	}

	public BufferedImage getBufferedImage() {
		return img;
	}

	/**
	 * Svg转换为BufferedImage
	 *
	 * @param svgFile Svg文件
	 * @param width   宽度，为null则为原始大小
	 * @param height  高度，为null则为原始大小
	 */
	public static BufferedImage loadImage(Reader svgFile, Float width, Float height) {
		try {
			BufferedImageTranscoder imageTranscoder = new BufferedImageTranscoder();

			if (width != null && height != null) {
				imageTranscoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
				imageTranscoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height);
			}

			TranscoderInput input = new TranscoderInput(svgFile);
			imageTranscoder.transcode(input, null);

			return imageTranscoder.getBufferedImage();
		} catch (TranscoderException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Svg转换为BufferedImage
	 *
	 * @param svgFile Svg文件
	 * @param width   宽度，为null则为原始大小
	 * @param height  高度，为null则为原始大小
	 */
	public static BufferedImage loadImage(InputStream svgFile, Float width, Float height) {
		try {
			BufferedImageTranscoder imageTranscoder = new BufferedImageTranscoder();

			if (width != null && height != null) {
				imageTranscoder.addTranscodingHint(PNGTranscoder.KEY_WIDTH, width);
				imageTranscoder.addTranscodingHint(PNGTranscoder.KEY_HEIGHT, height);
			}

			TranscoderInput input = new TranscoderInput(svgFile);
			imageTranscoder.transcode(input, null);

			return imageTranscoder.getBufferedImage();
		} catch (TranscoderException e) {
			throw new RuntimeException(e);
		}
	}
}