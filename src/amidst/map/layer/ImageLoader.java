package amidst.map.layer;

import java.awt.image.BufferedImage;

import amidst.map.Fragment;
import amidst.minecraft.world.CoordinatesInWorld;
import amidst.minecraft.world.Resolution;

public class ImageLoader implements FragmentLoader {
	private final LayerType layerType;
	private final ColorProvider colorProvider;
	private final Resolution resolution;
	protected final int size;
	private final int[] rgbArray;
	private BufferedImage bufferedImage;

	public ImageLoader(LayerType layerType, ColorProvider colorProvider,
			Resolution resolution) {
		this.layerType = layerType;
		this.colorProvider = colorProvider;
		this.resolution = resolution;
		this.size = resolution.getStepsPerFragment();
		this.rgbArray = new int[size * size];
		this.bufferedImage = createBufferedImage();
	}

	private BufferedImage createBufferedImage() {
		return new BufferedImage(size, size, BufferedImage.TYPE_INT_ARGB);
	}

	@Override
	public void construct(Fragment fragment) {
		fragment.putImage(layerType, createBufferedImage());
	}

	@Override
	public void load(Fragment fragment) {
		doLoad(fragment);
	}

	@Override
	public void reload(Fragment fragment) {
		doLoad(fragment);
	}

	protected void doLoad(Fragment fragment) {
		CoordinatesInWorld corner = fragment.getCorner();
		long cornerX = corner.getXAs(resolution);
		long cornerY = corner.getYAs(resolution);
		drawToCache(fragment, cornerX, cornerY);
		bufferedImage.setRGB(0, 0, size, size, rgbArray, 0, size);
		bufferedImage = fragment.getAndSetImage(layerType, bufferedImage);
	}

	protected void drawToCache(Fragment fragment, long cornerX, long cornerY) {
		for (int y = 0; y < size; y++) {
			for (int x = 0; x < size; x++) {
				int index = getCacheIndex(x, y);
				rgbArray[index] = colorProvider.getColorAt(fragment, cornerX,
						cornerY, x, y);
			}
		}
	}

	private int getCacheIndex(int x, int y) {
		return x + y * size;
	}
}
