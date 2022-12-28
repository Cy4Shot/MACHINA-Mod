package com.machina.research;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.machina.registration.init.ResearchInit;
import com.machina.util.serial.BaseNBTList;

import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.StringNBT;
import net.minecraftforge.common.util.INBTSerializable;

public class ResearchTree implements INBTSerializable<CompoundNBT> {

	private BaseNBTList<String, StringNBT> researched = createResearchList();

	public ResearchTree() {
		// Root is researched by default.
		researched.add(ResearchInit.ROOT.getId());
	}

	@Override
	public CompoundNBT serializeNBT() {
		final CompoundNBT nbt = new CompoundNBT();
		nbt.put("researched", researched.serializeNBT());
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt) {
		researched.deserializeNBT(nbt.getCompound("researched"));
	}

	public static ResearchTree fromNBT(CompoundNBT nbt) {
		ResearchTree pd = new ResearchTree();
		pd.deserializeNBT(nbt);
		return pd;
	}

	private static BaseNBTList<String, StringNBT> createResearchList() {
		return new BaseNBTList<>(s -> StringNBT.valueOf(s), s -> s.getAsString());
	}
	
	public List<String> getResearched() {
		return this.researched;
	}
	
	public void complete(Research r) {
		researched.add(r.getId());
	}
	
	public List<Research> unlockedResearches() {
		List<Research> unlocked = new ArrayList<>();
		for (Map.Entry<String, Research> r : ResearchInit.RESEARCHES.entrySet()) {
			if (!researched.contains(r.getKey())) {
				Research parent = r.getValue().getParent();
				if (parent != null && researched.contains(parent.getId())) {
					unlocked.add(r.getValue());
				}
			}
		}
		return unlocked;
	}
	
	public boolean canCraft(ItemStack stack) {
		for (Map.Entry<String, Research> r : ResearchInit.RESEARCHES.entrySet()) {
			if (!researched.contains(r.getKey())) {
				if (r.getValue().getUnlock().getItems()[0].getItem().equals(stack.getItem())) {
					return false;
				}
			}
		}
		return true;
	}
}