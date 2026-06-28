package it.unina.prisonmanager.utility;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public interface ScaledImageLoader
{
	default ImageIcon loadScaledImageIcon(
		String resourcePath, int width, int height
	) {
		URL imagePath = getClass().getResource(resourcePath);
		if (imagePath != null) {
			try {
				return new ImageIcon(
					ImageIO.read(imagePath).getScaledInstance(
						width, height, Image.SCALE_SMOOTH
					)
				);
			} catch (IOException e) {
				e.printStackTrace();
			}
		} return new ImageIcon();
	}
}
