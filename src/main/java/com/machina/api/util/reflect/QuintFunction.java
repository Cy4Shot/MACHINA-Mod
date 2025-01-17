package com.machina.api.util.reflect;

@FunctionalInterface
public interface QuintFunction<P1, P2, P3, P4, P5, R> {
   R apply(P1 a, P2 b, P3 c, P4 d, P5 f);
}