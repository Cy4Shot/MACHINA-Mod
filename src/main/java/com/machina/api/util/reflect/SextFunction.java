package com.machina.api.util.reflect;

@FunctionalInterface
public interface SextFunction<P1, P2, P3, P4, P5, P6, R> {
   R apply(P1 a, P2 b, P3 c, P4 d, P5 e, P6 f);
}