package com.machina.datagen.client.lang;

import com.machina.Machina;
import com.machina.registration.init.BlockInit;
import com.machina.registration.init.FluidInit;
import com.machina.registration.init.ItemInit;
import com.machina.registration.init.TabInit;

import net.minecraft.data.PackOutput;

public class DatagenLangEnUs extends DatagenLang {

	public DatagenLangEnUs(PackOutput gen) {
		super(gen, "en_us", Machina.MOD_ID);
	}

	@Override
	protected void addTranslations() {
		// Creative Tabs
		add(TabInit.MACHINA_RESOURCES, "Machina");
		
		// Items
		add(ItemInit.RAW_ALUMINUM, "Raw Aluminum");
		add(ItemInit.ALUMINUM_INGOT, "Aluminum Ingot");
		add(ItemInit.ALUMINUM_NUGGET, "Aluminum Nugget");
		add(ItemInit.COPPER_COIL, "Copper Coil");
		add(ItemInit.PROCESSOR, "Processor");
		add(ItemInit.RAW_SILICON_BLEND, "Raw Silicon Blend");
		add(ItemInit.SILICON, "Silicon");
		add(ItemInit.SILICON_BOLUS, "Silicon Bolus");
		add(ItemInit.HIGH_PURITY_SILICON, "High Purity Silicon");
		add(ItemInit.TRANSISTOR, "Transistor");
		add(ItemInit.AMMONIUM_NITRATE, "Ammonium Nitrate");
		add(ItemInit.LDPE, "LDPE");
		add(ItemInit.HDPE, "HDPE");
		add(ItemInit.UHMWPE, "UHMWPE");
		add(ItemInit.SODIUM_HYDROXIDE, "Sodium Hydroxide");
		add(ItemInit.SODIUM_CARBONATE, "Sodium Carbonate");
		add(ItemInit.CALCIUM_SULPHATE, "Calcium Sulphate");
		add(ItemInit.PALLADIUM_CHLORIDE, "Palladium Chloride");
		add(ItemInit.PALLADIUM_ON_CARBON, "Palladium on Carbon");
		add(ItemInit.HEXAMINE, "Hexamine");
		add(ItemInit.NITRONIUM_TETRAFLUOROBORATE, "Nitronium Tetrafluoroborate");
		add(ItemInit.LOGIC_UNIT, "Logic Unit");
		add(ItemInit.PROCESSOR_CORE, "Processor Core");
		add(ItemInit.COAL_CHUNK, "Coal Chunk");
		
		// Blocks
		add(BlockInit.ALUMINUM_BLOCK, "Aluminum Block");
		add(BlockInit.ALUMINUM_ORE, "Aluminum Ore");
		
		add(BlockInit.ANTHRACITE, "Anthracite");
		add(BlockInit.ANTHRACITE_SLAB, "Anthracite Slab");
		add(BlockInit.ANTHRACITE_STAIRS, "Anthracite Stairs");
		add(BlockInit.ANTHRACITE_WALL, "Anthracite Wall");
		add(BlockInit.ANTHRACITE_BUTTON, "Anthracite Button");
		add(BlockInit.ANTHRACITE_PRESSURE_PLATE, "Anthracite Pressure Plate");
		
		add(BlockInit.FELDSPAR, "Feldspar");
		add(BlockInit.FELDSPAR_SLAB, "Feldspar Slab");
		add(BlockInit.FELDSPAR_STAIRS, "Feldspar Stairs");
		add(BlockInit.FELDSPAR_WALL, "Feldspar Wall");
		add(BlockInit.FELDSPAR_BUTTON, "Feldspar Button");
		add(BlockInit.FELDSPAR_PRESSURE_PLATE, "Feldspar Pressure Plate");
		
		add(BlockInit.GRAY_SOAPSTONE, "Gray Soapstone");
		add(BlockInit.GRAY_SOAPSTONE_SLAB, "Gray Soapstone Slab");
		add(BlockInit.GRAY_SOAPSTONE_STAIRS, "Gray Soapstone Stairs");
		add(BlockInit.GRAY_SOAPSTONE_WALL, "Gray Soapstone Wall");
		add(BlockInit.GRAY_SOAPSTONE_BUTTON, "Gray Soapstone Button");
		add(BlockInit.GRAY_SOAPSTONE_PRESSURE_PLATE, "Gray Soapstone Pressure Plate");
		
		add(BlockInit.GREEN_SOAPSTONE, "Green Soapstone");
		add(BlockInit.GREEN_SOAPSTONE_SLAB, "Green Soapstone Slab");
		add(BlockInit.GREEN_SOAPSTONE_STAIRS, "Green Soapstone Stairs");
		add(BlockInit.GREEN_SOAPSTONE_WALL, "Green Soapstone Wall");
		add(BlockInit.GREEN_SOAPSTONE_BUTTON, "Green Soapstone Button");
		add(BlockInit.GREEN_SOAPSTONE_PRESSURE_PLATE, "Green Soapstone Pressure Plate");
		
		add(BlockInit.WHITE_SOAPSTONE, "White Soapstone");
		add(BlockInit.WHITE_SOAPSTONE_SLAB, "White Soapstone Slab");
		add(BlockInit.WHITE_SOAPSTONE_STAIRS, "White Soapstone Stairs");
		add(BlockInit.WHITE_SOAPSTONE_WALL, "White Soapstone Wall");
		add(BlockInit.WHITE_SOAPSTONE_BUTTON, "White Soapstone Button");
		add(BlockInit.WHITE_SOAPSTONE_PRESSURE_PLATE, "White Soapstone Pressure Plate");
		
		add(BlockInit.SHALE, "Shale");
		add(BlockInit.SHALE_SLAB, "Shale Slab");
		add(BlockInit.SHALE_STAIRS, "Shale Stairs");
		add(BlockInit.SHALE_WALL, "Shale Wall");
		add(BlockInit.SHALE_BUTTON, "Shale Button");
		add(BlockInit.SHALE_PRESSURE_PLATE, "Shale Pressure Plate");
		
		add(BlockInit.TECTONITE, "Tectonite");
		add(BlockInit.TECTONITE_SLAB, "Tectonite Slab");
		add(BlockInit.TECTONITE_STAIRS, "Tectonite Stairs");
		add(BlockInit.TECTONITE_WALL, "Tectonite Wall");
		add(BlockInit.TECTONITE_BUTTON, "Tectonite Button");
		add(BlockInit.TECTONITE_PRESSURE_PLATE, "Tectonite Pressure Plate");
		
		add(BlockInit.MARBLE, "Marble");
		add(BlockInit.MARBLE_SLAB, "Marble Slab");
		add(BlockInit.MARBLE_STAIRS, "Marble Stairs");
		add(BlockInit.MARBLE_WALL, "Marble Wall");
		add(BlockInit.MARBLE_BUTTON, "Marble Button");
		add(BlockInit.MARBLE_PRESSURE_PLATE, "Marble Pressure Plate");
		
		add(BlockInit.CHALK, "Chalk");
		add(BlockInit.CHALK_SLAB, "Chalk Slab");
		add(BlockInit.CHALK_STAIRS, "Chalk Stairs");
		add(BlockInit.CHALK_WALL, "Chalk Wall");
		add(BlockInit.CHALK_BUTTON, "Chalk Button");
		add(BlockInit.CHALK_PRESSURE_PLATE, "Chalk Pressure Plate");
		
		add(BlockInit.LIMESTONE, "Limestone");
		add(BlockInit.LIMESTONE_SLAB, "Limestone Slab");
		add(BlockInit.LIMESTONE_STAIRS, "Limestone Stairs");
		add(BlockInit.LIMESTONE_WALL, "Limestone Wall");
		add(BlockInit.LIMESTONE_BUTTON, "Limestone Button");
		add(BlockInit.LIMESTONE_PRESSURE_PLATE, "Limestone Pressure Plate");
		
		add(BlockInit.MIGMATITE, "Migmatite");
		add(BlockInit.MIGMATITE_SLAB, "Migmatite Slab");
		add(BlockInit.MIGMATITE_STAIRS, "Migmatite Stairs");
		add(BlockInit.MIGMATITE_WALL, "Migmatite Wall");
		add(BlockInit.MIGMATITE_BUTTON, "Migmatite Button");
		add(BlockInit.MIGMATITE_PRESSURE_PLATE, "Migmatite Pressure Plate");
		
		add(BlockInit.GNEISS, "Gneiss");
		add(BlockInit.GNEISS_SLAB, "Gneiss Slab");
		add(BlockInit.GNEISS_STAIRS, "Gneiss Stairs");
		add(BlockInit.GNEISS_WALL, "Gneiss Wall");
		add(BlockInit.GNEISS_BUTTON, "Gneiss Button");
		add(BlockInit.GNEISS_PRESSURE_PLATE, "Gneiss Pressure Plate");
		
		add(BlockInit.TROPICAL_GRASS_BLOCK, "Tropical Grass Block");
		add(BlockInit.TROPICAL_DIRT, "Tropical Dirt");
		
		add(BlockInit.TROPICAL_BUTTON, "Tropical Button");
		add(BlockInit.TROPICAL_DOOR, "Tropical Door");
		add(BlockInit.TROPICAL_FENCE, "Tropical Fence");
		add(BlockInit.TROPICAL_FENCE_GATE, "Tropical Fence Gate");
		add(BlockInit.TROPICAL_HANGING_SIGN, "Tropical Hanging Sign");
		add(BlockInit.TROPICAL_LOG, "Tropical Log");
		add(BlockInit.TROPICAL_PLANKS, "Tropical Planks");
		add(BlockInit.TROPICAL_LEAVES, "Tropical Leaves");
		add(BlockInit.TROPICAL_PRESSURE_PLATE, "Tropical Pressure Plate");
		add(BlockInit.TROPICAL_SIGN, "Tropical Sign");
		add(BlockInit.TROPICAL_SLAB, "Tropical Slab");
		add(BlockInit.TROPICAL_STAIRS, "Tropical Stairs");
		add(BlockInit.TROPICAL_TRAPDOOR, "Tropical Trapdoor");
		add(BlockInit.TROPICAL_WOOD, "Tropical Wood");
		add(BlockInit.STRIPPED_TROPICAL_LOG, "Stripped Tropical Log");
		add(BlockInit.STRIPPED_TROPICAL_WOOD, "Stripped Tropical Wood");
		
		add(BlockInit.PINE_BUTTON, "Pine Button");
		add(BlockInit.PINE_DOOR, "Pine Door");
		add(BlockInit.PINE_FENCE, "Pine Fence");
		add(BlockInit.PINE_FENCE_GATE, "Pine Fence Gate");
		add(BlockInit.PINE_HANGING_SIGN, "Pine Hanging Sign");
		add(BlockInit.PINE_LOG, "Pine Log");
		add(BlockInit.PINE_PLANKS, "Pine Planks");
		add(BlockInit.PINE_LEAVES, "Pine Leaves");
		add(BlockInit.PINE_PRESSURE_PLATE, "Pine Pressure Plate");
		add(BlockInit.PINE_SIGN, "Pine Sign");
		add(BlockInit.PINE_SLAB, "Pine Slab");
		add(BlockInit.PINE_STAIRS, "Pine Stairs");
		add(BlockInit.PINE_TRAPDOOR, "Pine Trapdoor");
		add(BlockInit.PINE_WOOD, "Pine Wood");
		add(BlockInit.STRIPPED_PINE_LOG, "Stripped Pine Log");
		add(BlockInit.STRIPPED_PINE_WOOD, "Stripped Pine Wood");
		
		add(BlockInit.CLOVER, "Clover");
		add(BlockInit.ORHPEUM, "Orpheum");
		
		add(BlockInit.DRAGON_PEONY, "Dragon Peony");
		add(BlockInit.POTTED_DRAGON_PEONY, "Potted Dragon Peony");

		add(BlockInit.PURPLE_GLOWSHROOM, "Purple Glowshroom");
		add(BlockInit.POTTED_PURPLE_GLOWSHROOM, "Potted Purple Glowshroom");
		add(BlockInit.PINK_GLOWSHROOM, "Pink Glowshroom");
		add(BlockInit.POTTED_PINK_GLOWSHROOM, "Potted Pink Glowshroom");
		add(BlockInit.RED_GLOWSHROOM, "Red Glowshroom");
		add(BlockInit.POTTED_RED_GLOWSHROOM, "Potted Red Glowshroom");
		add(BlockInit.ORANGE_GLOWSHROOM, "Orange Glowshroom");
		add(BlockInit.POTTED_ORANGE_GLOWSHROOM, "Potted Orange Glowshroom");
		add(BlockInit.YELLOW_GLOWSHROOM, "Yellow Glowshroom");
		add(BlockInit.POTTED_YELLOW_GLOWSHROOM, "Potted Yellow Glowshroom");
		add(BlockInit.GREEN_GLOWSHROOM, "Green Glowshroom");
		add(BlockInit.POTTED_GREEN_GLOWSHROOM, "Potted Green Glowshroom");
		add(BlockInit.TURQUOISE_GLOWSHROOM, "Turquoise Glowshroom");
		add(BlockInit.POTTED_TURQUOISE_GLOWSHROOM, "Potted Turquoise Glowshroom");
		add(BlockInit.BLUE_GLOWSHROOM, "Blue Glowshroom");
		add(BlockInit.POTTED_BLUE_GLOWSHROOM, "Potted Blue Glowshroom");

		// Tooltips
		addTooltip("ldpe", "Low Density Polyethylene");
		addTooltip("hdpe", "High Density Polyethylene");
		addTooltip("uhmwpe", "Ultra High Molecular Weight Polyethylene");
		
		// Fluids
		add(FluidInit.OXYGEN, "Oxygen", "Bucket");
		add(FluidInit.NITROGEN, "Nitrogen", "Bucket");
		add(FluidInit.AMMONIA, "Ammonia", "Bucket");
		add(FluidInit.CARBON_DIOXIDE, "Carbon Dioxide", "Bucket");
		add(FluidInit.CARBON_DISULPHIDE, "Carbon Disulphide", "Bucket");
		add(FluidInit.HYDROGEN, "Hydrogen", "Bucket");
		add(FluidInit.METHANE, "Methane", "Bucket");
		add(FluidInit.ETHANE, "Ethane", "Bucket");
		add(FluidInit.ETHYLENE, "Ethylene", "Bucket");
		add(FluidInit.CHLORINE, "Chlorine", "Bucket");
		add(FluidInit.BORON_TRIFLUORIDE, "Boron Trifluoride", "Bucket");
		add(FluidInit.FORMALDEHYDE, "Formaldehyde", "Bucket");
		add(FluidInit.NITROGEN_DIOXIDE, "Nitrogen Dioxide", "Bucket");
		add(FluidInit.SULPHUR_DIOXIDE, "Sulphur Dioxide", "Bucket");
		add(FluidInit.HYDROGEN_BROMIDE, "Hydrogen Bromide", "Bucket");
		add(FluidInit.HYDROGEN_SULPHIDE, "Hydrogen Sulphide", "Bucket");
		add(FluidInit.CARBON_MONOXIDE, "Carbon Monoxide", "Bucket");
		add(FluidInit.ARGON, "Argon", "Bucket");

		add(FluidInit.ACETIC_ACID, "Acetic Acid", "Bucket");
		add(FluidInit.BRINE, "Brine", "Bucket");
		add(FluidInit.HYDROCHLORIC_ACID, "Hydrochloric Acid", "Bucket");
		add(FluidInit.SULPHURIC_ACID, "Sulphuric Acid", "Bucket");
		add(FluidInit.BROMINE, "Bromine", "Bucket");
		add(FluidInit.BENZENE, "Benzene", "Bucket");
		add(FluidInit.TOLUENE, "Toluene", "Bucket");
		add(FluidInit.METHANOL, "Methanol", "Bucket");
		add(FluidInit.ETHANOL, "Ethanol", "Bucket");
		add(FluidInit.HYDROGEN_FLUORIDE, "Hydrogen Fluoride", "Bucket");
		add(FluidInit.ACETALDEHYDE, "Acetaldehyde", "Bucket");
		add(FluidInit.BENZYL_CHLORIDE, "Benzyl Chloride", "Bucket");
		add(FluidInit.NITRIC_ACID, "Nitric Acid", "Bucket");
		add(FluidInit.BROMOBENZENE, "Bromobenzene", "Bucket");
		add(FluidInit.GLYOXAL, "Glyoxal", "Bucket");
		add(FluidInit.BENZYLAMINE, "Benzylamine", "Bucket");
		add(FluidInit.HNIW, "HNIW (CL-20)", "Bucket");
		add(FluidInit.HEXOGEN, "Hexogen (RDX)", "Bucket");
		add(FluidInit.NITROMETHANE, "Nitromethane", "Bucket");
		add(FluidInit.SULPHUR_TRIOXIDE, "Sulphur Trioxide", "Bucket");
		add(FluidInit.MOLTEN_LEAD, "Molten Lead", "Bucket");
		add(FluidInit.MOLTEN_BISMUTH, "Molten Bismuth", "Bucket");
		add(FluidInit.LEAD_BISMUTH_EUTECTIC, "Lead Bismuth Eutectic", "Bucket");
	}
}