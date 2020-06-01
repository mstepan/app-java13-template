package com.max.app.util;

import java.util.HashSet;
import java.util.Set;

import static com.google.common.base.Preconditions.checkArgument;

public final class StringUtil {

    private StringUtil() {
        throw new AssertionError("Can't instantiate utility-only class");
    }

    public static boolean isPermutationPalindromic(String str) {
        checkArgument(str != null, "null string detected");

        if (str.length() < 2) {
            return true;
        }

        char[] arr = str.toCharArray();
        Set<Character> pairs = new HashSet<>(arr.length);

        for (char ch : arr) {
            if (pairs.contains(ch)) {
                pairs.remove(ch);
            }
            else {
                pairs.add(ch);
            }
        }

        return pairs.size() < 2;
    }

}
