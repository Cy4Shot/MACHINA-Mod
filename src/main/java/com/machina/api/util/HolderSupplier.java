package com.machina.api.util;

import java.util.Optional;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Stream;

import com.mojang.datafixers.util.Either;

import net.minecraft.core.Holder;
import net.minecraft.core.HolderOwner;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;

public record HolderSupplier<T>(Supplier<T> val) implements Holder<T> {
	public boolean isBound() {
		return true;
	}

	public boolean is(ResourceLocation p_205727_) {
		return false;
	}

	public boolean is(ResourceKey<T> p_205725_) {
		return false;
	}

	public boolean is(TagKey<T> p_205719_) {
		return false;
	}

	public boolean is(Predicate<ResourceKey<T>> p_205723_) {
		return false;
	}

	public Either<ResourceKey<T>, T> unwrap() {
		return Either.right(this.val.get());
	}

	public Optional<ResourceKey<T>> unwrapKey() {
		return Optional.empty();
	}

	public Holder.Kind kind() {
		return Holder.Kind.DIRECT;
	}

	public String toString() {
		return "Direct{" + this.val.get() + "}";
	}

	public boolean canSerializeIn(HolderOwner<T> p_256328_) {
		return true;
	}

	public Stream<TagKey<T>> tags() {
		return Stream.of();
	}

	public T value() {
		return this.val.get();
	}
}