package com.max.app.util;

import org.junit.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

public class StringUtilTest {


    @Test
    public void isPermutationPalindromicNormalCase() {
        assertThat(StringUtil.isPermutationPalindromic("LEVEL")).isTrue();
        assertThat(StringUtil.isPermutationPalindromic("L")).isTrue();
        assertThat(StringUtil.isPermutationPalindromic("")).isTrue();
        assertThat(StringUtil.isPermutationPalindromic("a")).isTrue();
        assertThat(StringUtil.isPermutationPalindromic("CC")).isTrue();
        assertThat(StringUtil.isPermutationPalindromic("@t8Yt@8")).isTrue();
        assertThat(StringUtil.isPermutationPalindromic("ELLVEV")).isTrue();
        assertThat(StringUtil.isPermutationPalindromic("eLLvev")).isTrue();
        assertThat(StringUtil.isPermutationPalindromic("?")).isTrue();

        assertThat(StringUtil.isPermutationPalindromic("ELLV")).isFalse();
        assertThat(StringUtil.isPermutationPalindromic("eLLVev")).isFalse();
        assertThat(StringUtil.isPermutationPalindromic("cC")).isFalse();
    }

    @Test
    public void isPermutationPalindromicWithNullStringThrowsException() {
        assertThatThrownBy(() -> StringUtil.isPermutationPalindromic(null)).
                isInstanceOf(IllegalArgumentException.class).
                hasMessage("null string detected");
    }
}
