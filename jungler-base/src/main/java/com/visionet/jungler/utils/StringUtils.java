/*
 * Copyright (c) 2015. Visionet and/or its affiliates. All right reserved.
 * VISIONET PROPRIETARY/CONFIDENTIAL.
 */
package com.visionet.jungler.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
	public static final String EMPTY = "";
	public static final char CHAR_SPACE = ' ';
	public static final String SPACE = " ";
	public static final int INDEX_NOT_FOUND = -1;
	public static final String ASSEMBLE_PLACEHOLDER = "{}";

	//	Empty check
	//-------------------------------------------------------------------

	/**
	 * <p>Checks if a String is empty ("") or null.</p>
	 *
	 * <pre>
	 * StringUtils.isEmpty(null)      = true
	 * StringUtils.isEmpty("")        = true
	 * StringUtils.isEmpty(" ")       = false
	 * StringUtils.isEmpty("bob")     = false
	 * StringUtils.isEmpty("  bob  ") = false
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @return {@code true} if the String is empty or null
	 */
	public static boolean isEmpty(final String str) {
		return str == null || str.length() == 0;
	}

	/**
	 * <p>Checks if a String is not empty ("") and not null.</p>
	 *
	 * <pre>
	 * StringUtils.isNotEmpty(null)      = false
	 * StringUtils.isNotEmpty("")        = false
	 * StringUtils.isNotEmpty(" ")       = true
	 * StringUtils.isNotEmpty("bob")     = true
	 * StringUtils.isNotEmpty("  bob  ") = true
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @return {@code true} if the String is not empty and not null
	 */
	public static boolean isNotEmpty(final String str) {
		return !isEmpty(str);
	}

	/**
	 * <p>Checks if a String is whitespace, empty ("") or null.</p>
	 *
	 * <pre>
	 * StringUtils.isBlank(null)      = true
	 * StringUtils.isBlank("")        = true
	 * StringUtils.isBlank(" ")       = true
	 * StringUtils.isBlank("bob")     = false
	 * StringUtils.isBlank("  bob  ") = false
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @return {@code true} if the String is null, empty or whitespace
	 */
	public static boolean isBlank(final String str) {
		int strLen;
		if (str == null || (strLen = str.length()) == 0) {
			return true;
		}
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * <p>Checks if a String is not empty (""), not null and not whitespace only.</p>
	 *
	 * <pre>
	 * StringUtils.isNotBlank(null)      = false
	 * StringUtils.isNotBlank("")        = false
	 * StringUtils.isNotBlank(" ")       = false
	 * StringUtils.isNotBlank("bob")     = true
	 * StringUtils.isNotBlank("  bob  ") = true
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @return {@code true} if the String is not empty and not null and not whitespace
	 */
	public static boolean isNotBlank(final String str) {
		return !isBlank(str);
	}

	// Trim
	//-------------------------------------------------------------------

	/**
	 * <p>Removes control characters (char &lt;= 32) from both ends of this String, handling {@code null} by returning
	 * {@code null}.</p>
	 *
	 * <p>The String is trimmed using {@link String#trim()}.
	 * Trim removes start and end characters &lt;= 32.
	 *
	 * <pre>
	 * StringUtils.trim(null)          = null
	 * StringUtils.trim("")            = ""
	 * StringUtils.trim("     ")       = ""
	 * StringUtils.trim("abc")         = "abc"
	 * StringUtils.trim("    abc    ") = "abc"
	 * </pre>
	 *
	 * @param str the String to be trimmed, may be null
	 * @return the trimmed string, {@code null} if null String input
	 */
	public static String trim(final String str) {
		return isEmpty(str) ? str : str.trim();
	}

	// IndexOf
	//-------------------------------------------------------------------

	/**
	 * <p>Finds the first index within a String, handling {@code null}.
	 * This method uses {@link String#indexOf(int, int)}.</p>
	 *
	 * <p>A {@code null} or empty ("") String will return {@code INDEX_NOT_FOUND (-1)}.</p>
	 *
	 * <pre>
	 * StringUtils.indexOf(null, *)         = -1
	 * StringUtils.indexOf("", *)           = -1
	 * StringUtils.indexOf("aabaabaa", 'a') = 0
	 * StringUtils.indexOf("aabaabaa", 'b') = 2
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchChar the character to find
	 * @return the first index of the search character,
	 * -1 if no match or {@code null} string input
	 */
	public static int indexOf(final String str, final int searchChar) {
		return indexOf(str, searchChar, 0);
	}

	/**
	 * <p>Finds the first index within a String from a start position, handling {@code null}.
	 * This method uses {@link String#indexOf(int, int)}.</p>
	 *
	 * <p>A {@code null} or empty ("") String will return {@code (INDEX_NOT_FOUND) -1}.
	 * A negative start position is treated as zero.
	 * A start position greater than the string length returns {@code -1}.</p>
	 *
	 * <pre>
	 * StringUtils.indexOf(null, *, *)          = -1
	 * StringUtils.indexOf("", *, *)            = -1
	 * StringUtils.indexOf("aabaabaa", 'b', 0)  = 2
	 * StringUtils.indexOf("aabaabaa", 'b', 3)  = 5
	 * StringUtils.indexOf("aabaabaa", 'b', 9)  = -1
	 * StringUtils.indexOf("aabaabaa", 'b', -1) = 2
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchChar the character to find
	 * @param startPos the start position, negative treated as zero
	 * @return the first index of the search character (always &ge; startPos), -1 if no match or {@code null} string input
	 */
	public static int indexOf(final String str, final int searchChar, int startPos) {
		return isEmpty(str) ? INDEX_NOT_FOUND : str.indexOf(searchChar, startPos);
	}

	/**
	 * <p>Finds the first index within a String, handling {@code null}.
	 * This method uses {@link String#indexOf(String, int)}.</p>
	 *
	 * <p>A {@code null} String will return {@code -1}.</p>
	 *
	 * <pre>
	 * StringUtils.indexOf(null, *)          = -1
	 * StringUtils.indexOf(*, null)          = -1
	 * StringUtils.indexOf("", "")           = 0
	 * StringUtils.indexOf("", *)            = -1 (except when * = "")
	 * StringUtils.indexOf("aabaabaa", "a")  = 0
	 * StringUtils.indexOf("aabaabaa", "b")  = 2
	 * StringUtils.indexOf("aabaabaa", "ab") = 1
	 * StringUtils.indexOf("aabaabaa", "")   = 0
	 * </pre>
	 *
	 * @param seq the String to check, may be null
	 * @param searchSeq the String to find, may be null
	 * @return the first index of the search String,
	 * -1 if no match or {@code null} string input
	 */
	public static int indexOf(final String seq, final String searchSeq) {
		return indexOf(seq, searchSeq, 0);
	}

	/**
	 * <p>Finds the first index within a String, handling {@code null}.
	 * This method uses {@link String#indexOf(String, int)}.</p>
	 *
	 * <p>A {@code null} String will return {@code -1}.
	 * A negative start position is treated as zero.
	 * An empty ("") search String always matches.
	 * A start position greater than the string length only matches
	 * an empty search String.</p>
	 *
	 * <pre>
	 * StringUtils.indexOf(null, *, *)          = -1
	 * StringUtils.indexOf(*, null, *)          = -1
	 * StringUtils.indexOf("", "", 0)           = 0
	 * StringUtils.indexOf("", *, 0)            = -1 (except when * = "")
	 * StringUtils.indexOf("aabaabaa", "a", 0)  = 0
	 * StringUtils.indexOf("aabaabaa", "b", 0)  = 2
	 * StringUtils.indexOf("aabaabaa", "ab", 0) = 1
	 * StringUtils.indexOf("aabaabaa", "b", 3)  = 5
	 * StringUtils.indexOf("aabaabaa", "b", 9)  = -1
	 * StringUtils.indexOf("aabaabaa", "b", -1) = 2
	 * StringUtils.indexOf("aabaabaa", "", 2)   = 2
	 * StringUtils.indexOf("abc", "", 9)        = 3
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchSeq the String to find, may be null
	 * @param startPos the start position, negative treated as zero
	 * @return the first index of the search String (always &ge; startPos), -1 if no match or {@code null} string input
	 */
	public static int indexOf(final String str, final String searchSeq, final int startPos) {
		return (str == null || searchSeq == null) ? INDEX_NOT_FOUND : str.indexOf(searchSeq, startPos);
	}

	/**
	 * <p>Case in-sensitive find of the first index within a String.</p>
	 *
	 * <p>A {@code null} String will return {@code -1}.
	 * A negative start position is treated as zero.
	 * An empty ("") search String always matches.
	 * A start position greater than the string length only matches an empty search String.</p>
	 *
	 * <pre>
	 * StringUtils.indexOfIgnoreCase(null, *)          = -1
	 * StringUtils.indexOfIgnoreCase(*, null)          = -1
	 * StringUtils.indexOfIgnoreCase("", "")           = 0
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "a")  = 0
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "B")  = 2
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "ab") = 1
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchStr the String to find, may be null
	 * @return the first index of the search String,
	 * -1 if no match or {@code null} string input
	 */
	public static int indexOfIgnoreCase(final String str, final String searchStr) {
		return indexOfIgnoreCase(str, searchStr, 0);
	}

	/**
	 * <p>Case in-sensitive find of the first index within a String from the specified position.</p>
	 *
	 * <p>A {@code null} String will return {@code -1}.
	 * A negative start position is treated as zero.
	 * An empty ("") search String always matches.
	 * A start position greater than the string length only matches an empty search String.</p>
	 *
	 * <pre>
	 * StringUtils.indexOfIgnoreCase(null, *, *)          = -1
	 * StringUtils.indexOfIgnoreCase(*, null, *)          = -1
	 * StringUtils.indexOfIgnoreCase("", "", 0)           = 0
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "A", 0)  = 0
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 0)  = 2
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "AB", 0) = 1
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 3)  = 5
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "B", 9)  = -1
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "B", -1) = 2
	 * StringUtils.indexOfIgnoreCase("aabaabaa", "", 2)   = 2
	 * StringUtils.indexOfIgnoreCase("abc", "", 9)        = 3
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchStr the String to find, may be null
	 * @param startPos the start position, negative treated as zero
	 * @return the first index of the search String (always &ge; startPos), -1 if no match or {@code null} string input
	 */
	public static int indexOfIgnoreCase(final String str, final String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		if (startPos < 0) {
			startPos = 0;
		}
		final int endLimit = str.length() - searchStr.length() + 1;
		if (startPos > endLimit) {
			return INDEX_NOT_FOUND;
		}
		if (searchStr.length() == 0) {
			return startPos;
		}
		for (int i = startPos; i < endLimit; i++) {
			if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
				return i;
			}
		}
		return INDEX_NOT_FOUND;
	}

	// LastIndexOf
	//-----------------------------------------------------------------------

	/**
	 * <p>Finds the last index within a String, handling {@code null}.
	 * This method uses {@link String#lastIndexOf(int)}.</p>
	 *
	 * <p>A {@code null} or empty ("") String will return {@code -1}.</p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *)         = -1
	 * StringUtils.lastIndexOf("", *)           = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'a') = 7
	 * StringUtils.lastIndexOf("aabaabaa", 'b') = 5
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchChar the character to find
	 * @return the last index of the search character, -1 if no match or {@code null} string input
	 */
	public static int lastIndexOf(final String str, final int searchChar) {
		return lastIndexOf(str, searchChar, str.length());
	}

	/**
	 * <p>Finds the last index within a String from a start position, handling {@code null}.
	 * This method uses {@link String#lastIndexOf(int, int)}.</p>
	 *
	 * <p>A {@code null} or empty ("") String will return {@code -1}.
	 * A negative start position returns {@code -1}.
	 * A start position greater than the string length searches the whole string.
	 * The search starts at the startPos and works backwards; matches starting after the start position are ignored.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *, *)          = -1
	 * StringUtils.lastIndexOf("", *,  *)           = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 8)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 4)  = 2
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 0)  = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'b', 9)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", 'b', -1) = -1
	 * StringUtils.lastIndexOf("aabaabaa", 'a', 0)  = 0
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchChar the character to find
	 * @param startPos the start position
	 * @return the last index of the search character (always &le; startPos), -1 if no match or {@code null} string input
	 */
	public static int lastIndexOf(final String str, final int searchChar, int startPos) {
		return isEmpty(str) ? INDEX_NOT_FOUND : str.lastIndexOf(searchChar, startPos);

	}

	/**
	 * <p>Finds the last index within a String, handling {@code null}.
	 * This method uses {@link String#lastIndexOf(String)}.</p>
	 *
	 * <p>A {@code null} String will return {@code -1}.</p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *)          = -1
	 * StringUtils.lastIndexOf(*, null)          = -1
	 * StringUtils.lastIndexOf("", "")           = 0
	 * StringUtils.lastIndexOf("aabaabaa", "a")  = 7
	 * StringUtils.lastIndexOf("aabaabaa", "b")  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "ab") = 4
	 * StringUtils.lastIndexOf("aabaabaa", "")   = 8
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchStr the String to find, may be null
	 * @return the last index of the search String, -1 if no match or {@code null} string input
	 */
	public static int lastIndexOf(final String str, final String searchStr) {
		return lastIndexOf(str, searchStr, str.length());
	}

	/**
	 * <p>Finds the last index within a String, handling {@code null}.
	 * This method uses {@link String#lastIndexOf(String, int)}.</p>
	 *
	 * <p>A {@code null} String will return {@code -1}.
	 * A negative start position returns {@code -1}.
	 * An empty ("") search String always matches unless the start position is negative.
	 * A start position greater than the string length searches the whole string.
	 * The search starts at the startPos and works backwards; matches starting after the start position are ignored.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOf(null, *, *)          = -1
	 * StringUtils.lastIndexOf(*, null, *)          = -1
	 * StringUtils.lastIndexOf("aabaabaa", "a", 8)  = 7
	 * StringUtils.lastIndexOf("aabaabaa", "b", 8)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "ab", 8) = 4
	 * StringUtils.lastIndexOf("aabaabaa", "b", 9)  = 5
	 * StringUtils.lastIndexOf("aabaabaa", "b", -1) = -1
	 * StringUtils.lastIndexOf("aabaabaa", "a", 0)  = 0
	 * StringUtils.lastIndexOf("aabaabaa", "b", 0)  = -1
	 * StringUtils.lastIndexOf("aabaabaa", "b", 1)  = -1
	 * StringUtils.lastIndexOf("aabaabaa", "b", 2)  = 2
	 * StringUtils.lastIndexOf("aabaabaa", "ba", 2)  = -1
	 * StringUtils.lastIndexOf("aabaabaa", "ba", 2)  = 2
	 * </pre>
	 *
	 * @param seq the String to check, may be null
	 * @param searchSeq the String to find, may be null
	 * @param startPos the start position, negative treated as zero
	 * @return the last index of the search String (always &le; startPos), -1 if no match or {@code null} string input
	 */
	public static int lastIndexOf(final String seq, final String searchSeq, final int startPos) {
		return (seq == null || searchSeq == null) ? INDEX_NOT_FOUND : seq.lastIndexOf(searchSeq, startPos);
	}

	/**
	 * <p>Case in-sensitive find of the last index within a String.</p>
	 *
	 * <p>A {@code null} String will return {@code -1}.
	 * A negative start position returns {@code -1}.
	 * An empty ("") search String always matches unless the start position is negative.
	 * A start position greater than the string length searches the whole string.</p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOfIgnoreCase(null, *)          = -1
	 * StringUtils.lastIndexOfIgnoreCase(*, null)          = -1
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A")  = 7
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B")  = 5
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB") = 4
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchStr the String to find, may be null
	 * @return the first index of the search String, -1 if no match or {@code null} string input
	 */
	public static int lastIndexOfIgnoreCase(final String str, final String searchStr) {
		return lastIndexOfIgnoreCase(str, searchStr, str.length());
	}

	/**
	 * <p>Case in-sensitive find of the last index within a String from the specified position.</p>
	 *
	 * <p>A {@code null} String will return {@code -1}.
	 * A negative start position returns {@code -1}.
	 * An empty ("") search String always matches unless the start position is negative.
	 * A start position greater than the string length searches the whole string.
	 * The search starts at the startPos and works backwards; matches starting after the start position are ignored.
	 * </p>
	 *
	 * <pre>
	 * StringUtils.lastIndexOfIgnoreCase(null, *, *)          = -1
	 * StringUtils.lastIndexOfIgnoreCase(*, null, *)          = -1
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 8)  = 7
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 8)  = 5
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "AB", 8) = 4
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 9)  = 5
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", -1) = -1
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "A", 0)  = 0
	 * StringUtils.lastIndexOfIgnoreCase("aabaabaa", "B", 0)  = -1
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchStr the String to find, may be null
	 * @param startPos the start position
	 * @return the last index of the search String (always &le; startPos), -1 if no match or {@code null} input
	 */
	public static int lastIndexOfIgnoreCase(final String str, final String searchStr, int startPos) {
		if (str == null || searchStr == null) {
			return INDEX_NOT_FOUND;
		}
		if (startPos > str.length() - searchStr.length()) {
			startPos = str.length() - searchStr.length();
		}
		if (startPos <= INDEX_NOT_FOUND) {
			return INDEX_NOT_FOUND;
		}
		if (searchStr.length() == 0) {
			return startPos;
		}

		for (int i = startPos; i >= 0; i--) {
			if (str.regionMatches(true, i, searchStr, 0, searchStr.length())) {
				return i;
			}
		}
		return INDEX_NOT_FOUND;
	}

	// Contains
	//-----------------------------------------------------------------------

	/**
	 * <p>Checks if String contains a search character, handling {@code null}.
	 * This method uses {@link String#indexOf(int)}.</p>
	 *
	 * <p>A {@code null} or empty ("") String will return {@code false}.</p>
	 *
	 * <pre>
	 * StringUtils.contains(null, *)    = false
	 * StringUtils.contains("", *)      = false
	 * StringUtils.contains("abc", 'a') = true
	 * StringUtils.contains("abc", 'z') = false
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchChar the character to find
	 * @return true if the String contains the search character, false if not or {@code null} string input
	 */
	public static boolean contains(final String str, final int searchChar) {
		return !isEmpty(str) && indexOf(str, searchChar, 0) > INDEX_NOT_FOUND;
	}

	/**
	 * <p>Checks if String contains a search String, handling {@code null}.
	 * This method uses {@link String#indexOf(String)}.</p>
	 *
	 * <p>A {@code null} String will return {@code false}.</p>
	 *
	 * <pre>
	 * StringUtils.contains(null, *)     = false
	 * StringUtils.contains(*, null)     = false
	 * StringUtils.contains("", "")      = true
	 * StringUtils.contains("abc", "")   = true
	 * StringUtils.contains("abc", "a")  = true
	 * StringUtils.contains("abc", "z")  = false
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchSeq the String to find, may be null
	 * @return true if the String contains the search String, false if not or {@code null} string input
	 */
	public static boolean contains(final String str, final String searchSeq) {
		return !(str == null || searchSeq == null) && (indexOf(str, searchSeq, 0) > INDEX_NOT_FOUND);
	}

	/**
	 * <p>Checks if String contains a search String irrespective of case,
	 * handling {@code null}. Case-insensitivity is defined as by
	 * {@link String#equalsIgnoreCase(String)}.
	 *
	 * <p>A {@code null} String will return {@code false}.</p>
	 *
	 * <pre>
	 * StringUtils.contains(null, *) = false
	 * StringUtils.contains(*, null) = false
	 * StringUtils.contains("", "") = true
	 * StringUtils.contains("abc", "") = true
	 * StringUtils.contains("abc", "a") = true
	 * StringUtils.contains("abc", "z") = false
	 * StringUtils.contains("abc", "A") = true
	 * StringUtils.contains("abc", "Z") = false
	 * </pre>
	 *
	 * @param str the String to check, may be null
	 * @param searchStr the String to find, may be null
	 * @return true if the String contains the search String irrespective of
	 * case or false if not or {@code null} string input
	 */
	public static boolean containsIgnoreCase(final String str, final String searchStr) {
		if (str == null || searchStr == null) {
			return false;
		}
		final int len = searchStr.length();
		final int max = str.length() - len;
		for (int i = 0; i <= max; i++) {
			if (str.regionMatches(true, i, searchStr, 0, len)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check whether the given String contains any whitespace characters.
	 *
	 * @param seq the String to check (may be {@code null})
	 * @return {@code true} if the String is not empty and contains at least 1 whitespace character
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean containsWhitespace(final String seq) {
		if (isEmpty(seq)) {
			return false;
		}
		final int strLen = seq.length();
		for (int i = 0; i < strLen; i++) {
			if (Character.isWhitespace(seq.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	// Splitting
	//-----------------------------------------------------------------------

	/**
	 * <p>Splits the provided text into an array, using whitespace as the separator.
	 * Whitespace is defined by {@link Character#isWhitespace(char)}.</p>
	 *
	 * <p>The separator is not included in the returned String array.
	 * Adjacent separators are treated as one separator.
	 * For more control over the split use the StrTokenizer class.</p>
	 *
	 * <p>A {@code null} input String returns {@code null}.</p>
	 *
	 * <pre>
	 * StringUtils.split(null)       = null
	 * StringUtils.split("")         = []
	 * StringUtils.split("abc def")  = ["abc", "def"]
	 * StringUtils.split("abc  def") = ["abc", "def"]
	 * StringUtils.split(" abc ")    = ["abc"]
	 * </pre>
	 *
	 * @param str the String to parse, may be null
	 * @return an array of parsed Strings, {@code null} if null String input
	 */
	public static String[] split(final String str) {
		return split(str, null);
	}

	/**
	 * <p>Splits the provided text into an array, separators specified.
	 * This is an alternative to using StringTokenizer.</p>
	 *
	 * <p>The separator is not included in the returned String array.
	 * Adjacent separators are treated as one separator.
	 * For more control over the split use the StrTokenizer class.</p>
	 *
	 * <p>A {@code null} input String returns {@code null}. A {@code null} separatorChars splits on whitespace.</p>
	 *
	 * <pre>
	 * StringUtils.split(null, *)         = null
	 * StringUtils.split("", *)           = []
	 * StringUtils.split("abc def", null) = ["abc", "def"]
	 * StringUtils.split("abc def", " ")  = ["abc", "def"]
	 * StringUtils.split("abc  def", " ") = ["abc", "def"]
	 * StringUtils.split("ab:cd:ef", ":") = ["ab", "cd", "ef"]
	 * </pre>
	 *
	 * @param str the String to parse, may be null
	 * @param separatorChars the characters used as the delimiters,
	 * {@code null} splits on whitespace
	 * @return an array of parsed Strings, {@code null} if null String input
	 */
	public static String[] split(final String str, final String separatorChars) {
		return split(str, separatorChars, -1);
	}

	/**
	 * @param str the String to parse, may be null
	 * @param separatorChars the separate character
	 * @param preserveAllTokens if {@code true}, adjacent separators are
	 * treated as empty token separators; if {@code false}, adjacent
	 * separators are treated as one separator.
	 * @return an array of parsed Strings, {@code null} if null String input
	 */
	public static String[] split(final String str, final String separatorChars, boolean preserveAllTokens) {
		return split(str, separatorChars, -1, preserveAllTokens);
	}

	/**
	 * <p>Splits the provided text into an array with a maximum length, separators specified.</p>
	 *
	 * <p>The separator is not included in the returned String array. Adjacent separators are treated as one separator.</p>
	 *
	 * <p>A {@code null} input String returns {@code null}.
	 * A {@code null} separatorChars splits on whitespace.</p>
	 *
	 * <p>If more than {@code max} delimited substrings are found, the last
	 * returned string includes all characters after the first {@code max - 1}
	 * returned strings (including separator characters).</p>
	 *
	 * <pre>
	 * StringUtils.split(null, *, *)            = null
	 * StringUtils.split("", *, *)              = []
	 * StringUtils.split("ab cd ef", null, 0)   = ["ab", "cd", "ef"]
	 * StringUtils.split("ab   cd ef", null, 0) = ["ab", "cd", "ef"]
	 * StringUtils.split("ab:cd:ef", ":", 0)    = ["ab", "cd", "ef"]
	 * StringUtils.split("ab:cd:ef", ":", 2)    = ["ab", "cd:ef"]
	 * </pre>
	 *
	 * @param str the String to parse, may be null
	 * @param separatorChars the characters used as the delimiters, {@code null} splits on whitespace
	 * @param max the maximum number of elements to include in the array. A zero or negative value implies no limit
	 * @return an array of parsed Strings, {@code null} if null String input
	 */
	public static String[] split(final String str, final String separatorChars, final int max) {
		return split(str, separatorChars, max, false);
	}

	/**
	 * @param str the String to parse, may be {@code null}
	 * @param separatorChars String containing the String to be used as a delimiter,
	 * {@code null} splits on whitespace
	 * @param max the maximum number of elements to include in the returned
	 * array. A zero or negative value implies no limit.
	 * @param preserveAllTokens if {@code true}, adjacent separators are
	 * treated as empty token separators; if {@code false}, adjacent
	 * separators are treated as one separator.
	 * @return an array of parsed Strings, {@code null} if null String input
	 */
	public static String[] split(
			final String str, final String separatorChars, final int max, final boolean preserveAllTokens) {
		return splitWorker(str, separatorChars, max, preserveAllTokens);
	}

	/**
	 * Performs the logic for the {@code split} and {@code splitPreserveAllTokens} methods that return a maximum array
	 * length.
	 *
	 * @param str the String to parse, may be {@code null}
	 * @param separatorChars the separate character
	 * @param max the maximum number of elements to include in the array. A zero or negative value implies no limit.
	 * @param preserveAllTokens if {@code true}, adjacent separators are
	 * treated as empty token separators; if {@code false}, adjacent
	 * separators are treated as one separator.
	 * @return an array of parsed Strings, {@code null} if null String input
	 */
	private static String[] splitBySingleSeparatorWorker(
			final String str, final String separatorChars, final int max, final boolean preserveAllTokens) {
		// Performance tuned for 2.0 (JDK1.4)
		// Direct code is quicker than StringTokenizer.
		// Also, StringTokenizer uses isSpace() not isWhitespace()
		final int len = str.length();
		if (len == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}
		final List<String> list = new ArrayList<>();
		int sizePlus1 = 1;
		int i = 0, start = 0;
		boolean match = false;
		boolean lastMatch = false;
		boolean nullSeparatorChars;
		char sep = StringUtils.CHAR_SPACE;
		if (!(nullSeparatorChars = separatorChars == null)) {
			sep = separatorChars.charAt(0);
		}
		// Null separator means use whitespace
		while (i < len) {
			if ((nullSeparatorChars && Character.isWhitespace(str.charAt(i))) || (!nullSeparatorChars
					&& str.charAt(i) == sep)) {
				if (match || preserveAllTokens) {
					lastMatch = true;
					if (sizePlus1++ == max) {
						i = len;
						lastMatch = false;
					}
					list.add(str.substring(start, i));
					match = false;
				}
				start = ++i;
				continue;
			}
			lastMatch = false;
			match = true;
			i++;
		}
		if (match || preserveAllTokens && lastMatch) {
			list.add(str.substring(start, i));
		}
		return list.toArray(new String[list.size()]);
	}

	/**
	 * Performs the logic for the {@code splitByWholeSeparatorPreserveAllTokens} methods.
	 *
	 * @param str the String to parse, may be {@code null}
	 * @param separator String containing the String to be used as a delimiter,
	 * {@code null} splits on whitespace
	 * treated as empty token separators; if {@code false}, adjacent separators are treated as one separator.
	 * @return an array of parsed Strings, {@code null} if null String input
	 */
	private static String[] splitWorker(
			final String str, final String separator, final int max, final boolean preserveAllTokens) {
		if (str == null) {
			return null;
		}


		final int len = str.length();

		if (len == 0) {
			return ArrayUtils.EMPTY_STRING_ARRAY;
		}

		if (StringUtils.isEmpty(separator) || separator.length() == 1) {
			return splitBySingleSeparatorWorker(str, separator, max, preserveAllTokens);
		}

		final int separatorLength = separator.length();

		final ArrayList<String> substrings = new ArrayList<>();
		int numberOfSubstrings = 0;
		int beg = 0;
		int end = 0;
		while (end < len) {
			end = str.indexOf(separator, beg);

			if (end > -1) {
				if (end > beg) {
					numberOfSubstrings += 1;

					if (numberOfSubstrings == max) {
						end = len;
						substrings.add(str.substring(beg));
					} else {
						// The following is OK, because String.substring( beg, end ) excludes
						// the character at the position 'end'.
						substrings.add(str.substring(beg, end));

						// Set the starting point for the next search.
						// The following is equivalent to beg = end + (separatorLength - 1) + 1,
						// which is the right calculation:
						beg = end + separatorLength;
					}
				} else {
					// We found a consecutive occurrence of the separator, so skip it.
					if (preserveAllTokens) {
						numberOfSubstrings += 1;
						if (numberOfSubstrings == max) {
							end = len;
							substrings.add(str.substring(beg));
						} else {
							substrings.add(EMPTY);
						}
					}
					beg = end + separatorLength;
				}
			} else {
				// String.substring( beg ) goes from 'beg' to the end of the String.
				substrings.add(str.substring(beg));
				end = len;
			}
		}
		return substrings.toArray(new String[substrings.size()]);
	}

	/**
	 * @param str the String to assemble
	 * @param args arguments will be assemble to the String
	 * @return a assembled String
	 */
	public static String assemble(String str, String... args) {
		if (str == null) {
			return null;
		}
		String[] strArrs = StringUtils.split(str, ASSEMBLE_PLACEHOLDER, true);
		final int startIndex = 0;
		final int endIndex = strArrs.length;
		final int argsLen = args.length;
		final int noOfItems = endIndex - startIndex;
		if (noOfItems <= 0) {
			return str;
		}
		final StringBuilder builder = new StringBuilder(noOfItems * 16);
		for (int i = startIndex, argsIndex = 0; i < endIndex; i++) {
			if (i > startIndex && argsIndex < argsLen) {
				builder.append(args[argsIndex++]);
			} else if (argsIndex >= argsLen) {
				builder.append(ASSEMBLE_PLACEHOLDER);
			}
			if (strArrs[i] != null) {
				builder.append(strArrs[i]);
			}
		}
		return builder.toString();
	}

}
