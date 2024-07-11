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
		add(ItemInit.RAW_ALUMINUM.get(), "Raw Aluminum");
		add(ItemInit.ALUMINUM_INGOT.get(), "Aluminum Ingot");
		add(ItemInit.ALUMINUM_NUGGET.get(), "Aluminum Nugget");
		add(ItemInit.COPPER_COIL.get(), "Copper Coil");
		add(ItemInit.PROCESSOR.get(), "Processor");
		add(ItemInit.RAW_SILICON_BLEND.get(), "Raw Silicon Blend");
		add(ItemInit.SILICON.get(), "Silicon");
		add(ItemInit.SILICON_BOLUS.get(), "Silicon Bolus");
		add(ItemInit.HIGH_PURITY_SILICON.get(), "High Purity Silicon");
		add(ItemInit.TRANSISTOR.get(), "Transistor");
		add(ItemInit.AMMONIUM_NITRATE.get(), "Ammonium Nitrate");
		add(ItemInit.LDPE.get(), "LDPE");
		add(ItemInit.HDPE.get(), "HDPE");
		add(ItemInit.UHMWPE.get(), "UHMWPE");
		add(ItemInit.SODIUM_HYDROXIDE.get(), "Sodium Hydroxide");
		add(ItemInit.SODIUM_CARBONATE.get(), "Sodium Carbonate");
		add(ItemInit.CALCIUM_SULPHATE.get(), "Calcium Sulphate");
		add(ItemInit.PALLADIUM_CHLORIDE.get(), "Palladium Chloride");
		add(ItemInit.PALLADIUM_ON_CARBON.get(), "Palladium on Carbon");
		add(ItemInit.HEXAMINE.get(), "Hexamine");
		add(ItemInit.NITRONIUM_TETRAFLUOROBORATE.get(), "Nitronium Tetrafluoroborate");
		add(ItemInit.LOGIC_UNIT.get(), "Logic Unit");
		add(ItemInit.PROCESSOR_CORE.get(), "Processor Core");
		add(ItemInit.COAL_CHUNK.get(), "Coal Chunk");
		
		// Blocks
		add(BlockInit.ALUMINUM_BLOCK.get(), "Aluminum Block");
		add(BlockInit.ALUMINUM_ORE.get(), "Aluminum Ore");
		
		add(BlockInit.ANTHRACITE.get(), "Anthracite");
		add(BlockInit.ANTHRACITE_SLAB.get(), "Anthracite Slab");
		add(BlockInit.ANTHRACITE_STAIRS.get(), "Anthracite Stairs");
		add(BlockInit.ANTHRACITE_WALL.get(), "Anthracite Wall");
		
		add(BlockInit.FELDSPAR.get(), "Feldspar");
		add(BlockInit.FELDSPAR_SLAB.get(), "Feldspar Slab");
		add(BlockInit.FELDSPAR_STAIRS.get(), "Feldspar Stairs");
		add(BlockInit.FELDSPAR_WALL.get(), "Feldspar Wall");
		
		add(BlockInit.TROPICAL_BUTTON.get(), "Tropical Button");
		add(BlockInit.TROPICAL_DOOR.get(), "Tropical Door");
		add(BlockInit.TROPICAL_FENCE.get(), "Tropical Fence");
		add(BlockInit.TROPICAL_FENCE_GATE.get(), "Tropical Fence Gate");
		add(BlockInit.TROPICAL_HANGING_SIGN.get(), "Tropical Hanging Sign");
		add(BlockInit.TROPICAL_LOG.get(), "Tropical Log");
		add(BlockInit.TROPICAL_PLANKS.get(), "Tropical Planks");
		add(BlockInit.TROPICAL_LEAVES.get(), "Tropical Leaves");
		add(BlockInit.TROPICAL_PRESSURE_PLATE.get(), "Tropical Pressure Plate");
		add(BlockInit.TROPICAL_SIGN.get(), "Tropical Sign");
		add(BlockInit.TROPICAL_SLAB.get(), "Tropical Slab");
		add(BlockInit.TROPICAL_STAIRS.get(), "Tropical Stairs");
		add(BlockInit.TROPICAL_TRAPDOOR.get(), "Tropical Trapdoor");
		add(BlockInit.TROPICAL_WOOD.get(), "Tropical Wood");
		add(BlockInit.STRIPPED_TROPICAL_LOG.get(), "Stripped Tropical Log");
		add(BlockInit.STRIPPED_TROPICAL_WOOD.get(), "Stripped Tropical Wood");
		
		add(BlockInit.CLOVER.get(), "Clover");
		add(BlockInit.DRAGON_PEONY.get(), "Dragon Peony");
		add(BlockInit.POTTED_DRAGON_PEONY.get(), "Potted Dragon Peony");
		add(BlockInit.ORHPEUM.get(), "Orpheum");

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