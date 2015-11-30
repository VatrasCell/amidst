package amidst.map.layer;

import amidst.map.Fragment;
import amidst.minecraft.world.BiomeDataOracle;
import amidst.minecraft.world.Resolution;

public class BiomeDataLoader extends ImageLoader {
	private final BiomeDataOracle biomeDataOracle;

	public BiomeDataLoader(LayerType layerType, ColorProvider colorProvider,
			Resolution resolution, BiomeDataOracle biomeDataOracle) {
		super(layerType, colorProvider, resolution);
		this.biomeDataOracle = biomeDataOracle;
	}

	@Override
	public void construct(Fragment fragment) {
		fragment.initBiomeData(size, size);
		super.construct(fragment);
	}

	@Override
	public void load(Fragment fragment) {
		fragment.populateBiomeData(biomeDataOracle);
		super.load(fragment);
	}

	@Override
	public void reload(Fragment fragment) {
		// noop
	}
}
