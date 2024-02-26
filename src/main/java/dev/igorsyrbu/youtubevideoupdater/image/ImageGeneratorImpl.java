package dev.igorsyrbu.youtubevideoupdater.image;

import jakarta.annotation.PostConstruct;
import java.awt.AlphaComposite;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import javax.imageio.ImageIO;
import lombok.SneakyThrows;
import lombok.val;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import static java.awt.AlphaComposite.SRC_OVER;
import static java.awt.Font.TRUETYPE_FONT;
import static java.awt.Font.createFont;

@Component
public class ImageGeneratorImpl implements ImageGenerator {

	private static final String PNG = "png";
	private static final Color TEXT_COLOR = new Color(255, 255, 255, 230);
	private static final int MAX_FONT_SIZE = 360;

	@Value("classpath:/static/thumbnail.png")
	private Resource thumbnailResource;

	@Value("classpath:/static/ProtestStrike-Regular.ttf")
	private Resource fontResource;

	private Font font;

	@PostConstruct
	@SneakyThrows
	private void initFont() {
		val file = fontResource.getFile();
		val fontStream = new FileInputStream(file);

		font = createFont(TRUETYPE_FONT, fontStream);
	}

	@Override
	@SneakyThrows
	public byte[] apply(String text) {
		val file = thumbnailResource.getFile();
		val image = ImageIO.read(file);
		val graphics = (Graphics2D) image.getGraphics();
		val composite = AlphaComposite.getInstance(SRC_OVER, 0.9f);

		drawText(text, image);
		graphics.setComposite(composite);
		graphics.dispose();

		val byteArrayOutputStream = new ByteArrayOutputStream();
		ImageIO.write(image, PNG, byteArrayOutputStream);

		return byteArrayOutputStream.toByteArray();
	}

	private void drawText(String textToAdd, BufferedImage image) {
		val graphics = (Graphics2D) image.getGraphics();

		val maxWidthPercentage = 0.8;
		val maxTextWidth = (int) (image.getWidth() * maxWidthPercentage);

		val fontSize = calculateFontSize(image, textToAdd, maxTextWidth);
		val scaledFont = font.deriveFont(fontSize);
		graphics.setFont(scaledFont);
		graphics.setColor(TEXT_COLOR);

		val fontMetrics = graphics.getFontMetrics(scaledFont);
		val textWidth = fontMetrics.stringWidth(textToAdd);
		val textHeight = fontMetrics.getHeight();
		val x = (image.getWidth() - textWidth) / 2;
		val y = (image.getHeight() - textHeight) / 2 + fontMetrics.getAscent();

		graphics.drawString(textToAdd, x, y);
	}

	private float calculateFontSize(BufferedImage image, String text, int maxWidth) {
		int textWidth;

		for (int fontSize = 1; ; fontSize++) {
			textWidth = calculateTextWidth(image, text, fontSize);

			if (textWidth >= maxWidth) {
				return Math.min(fontSize, MAX_FONT_SIZE);
			}
		}
	}

	private int calculateTextWidth(BufferedImage image, String text, float fontSize) {
		val scaledFont = font.deriveFont(fontSize);
		val fontMetrics = image.getGraphics()
							   .getFontMetrics(scaledFont);

		return fontMetrics.stringWidth(text);
	}

}
