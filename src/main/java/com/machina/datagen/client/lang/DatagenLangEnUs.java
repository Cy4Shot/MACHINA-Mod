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
		
		add(BlockInit.FELDSPAR, "Feldspar");
		add(BlockInit.FELDSPAR_SLAB, "Feldspar Slab");
		add(BlockInit.FELDSPAR_STAIRS, "Feldspar Stairs");
		add(BlockInit.FELDSPAR_WALL, "Feldspar Wall");
		
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
		add(BlockInit.DRAGON_PEONY, "Dragon Peony");
		add(BlockInit.POTTED_DRAGON_PEONY, "Potted Dragon Peony");
		add(BlockInit.ORHPEUM, "Orpheum");

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