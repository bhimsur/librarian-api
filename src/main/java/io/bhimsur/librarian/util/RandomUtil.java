package io.bhimsur.librarian.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.math.BigInteger;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RandomUtil {

    public static String getSequence(String prefix, BigInteger seq) {
        return prefix + String.format("%012d", seq);
    }
}
