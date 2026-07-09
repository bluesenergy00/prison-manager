package it.unina.prisonmanager.utility;

import java.awt.Image;
import java.io.IOException;
import java.net.URL;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;

public final class ScaledImageLoader
{
	private ScaledImageLoader() {
		throw new UnsupportedOperationException(
			"it.unina.prisonmanager.utility.ScaledImageLoader is a static class."
		);
	}
	
	public static ImageIcon loadScaledImageIcon(
		String resourcePath, int width, int height
	) {
		URL imagePath = ScaledImageLoader.class.getResource(resourcePath);
		if (imagePath != null) {
			try {
				return new ImageIcon(
					ImageIO.read(imagePath).getScaledInstance(
						width, height, Image.SCALE_SMOOTH
					)
				);
			} catch (IOException e) {e.printStackTrace();}
		} return new ImageIcon();
	}
}
