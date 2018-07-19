package com.ogeniuspriority.nds.nds;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.CharacterStyle;
import android.text.style.ForegroundColorSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StyleSpan;
import android.text.style.URLSpan;
import android.view.View;

import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by USER on 2/3/2017.
 */
public class ClickableURLSpan_ extends URLSpan {
    public ClickableURLSpan_(String url) {
        super(url);
    }

    @Override
    public void onClick(View widget) {
        String clickedText = getURL();


        // START your activity here
    }
    public static SpannableStringBuilder colorTags(Context cxt, String text) {
        final Pattern hashtag = Pattern.compile("#\\w+");
        final Pattern at = Pattern.compile("(\\s|\\A)@(\\w+)");
        // final Pattern name = Pattern.compile("(\\s|\\A)\\+(\\w+)");

        final SpannableStringBuilder spannable = new SpannableStringBuilder(text);

        final ForegroundColorSpan at_color = new ForegroundColorSpan(Color.GREEN);
        final ForegroundColorSpan hash_color = new ForegroundColorSpan(Color.BLUE);
        final ForegroundColorSpan name_color = new ForegroundColorSpan(Color.parseColor("#994C00"));

        final Matcher matcher = hashtag.matcher(text);
        final Matcher at_matcher = at.matcher(text);
        // final Matcher name_matcher = name.matcher(text);

//        Spanned concatenated=(Spanned) TextUtils.concat(span1," => ",span2);
//
//        SpannableStringBuilder result = new SpannableStringBuilder(concatenated);
//        ///----------

        while (matcher.find()) {
            spannable.setSpan(
                    hash_color, matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_INCLUSIVE
            );
        }
        while (matcher.find()) {
            spannable.setSpan(
                    hash_color, matcher.start(), matcher.end(), Spanned.SPAN_INCLUSIVE_INCLUSIVE
            );
        }

        while (at_matcher.find()) {
            spannable.setSpan(
                    at_color, at_matcher.start(), at_matcher.end(), Spanned.SPAN_INCLUSIVE_INCLUSIVE
            );
        }


//        while (name_matcher.find()) {
//            spannable.setSpan(
//                    name_color, name_matcher.start(), name_matcher.end(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE
//            );
//
//        }
        //-----------------

        return spannable;
    }

    //------------------------
    private SpannableStringBuilder makeColoredSpannable(String text, String regex, int color) {
        // create a spannable to hold the final result
        SpannableStringBuilder spannable = new SpannableStringBuilder();
        // create a buffer to hold the replaced string
        StringBuffer sb = new StringBuffer();
        // create the pattern matcher
        Matcher m = Pattern.compile(regex).matcher(text);
        // iterate through all matches
        while (m.find()) {
            // get the match
            String word = m.group();
            // remove the first and last characters of the match
            String abbr = word.substring(1, word.length() - 1);
            // clear the string buffer
            sb.setLength(0);
            // appendReplacement handles replacing within the current match's bounds
            m.appendReplacement(sb, abbr);
            // append the new colored section to the spannable
            spannable.append(sb);
            //spannable.setSpan(new ForegroundColorSpan(color), spannable.length() - abbr.length(), spannable.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            spannable.setSpan(new RelativeSizeSpan(1.5f), 0, 5, 0);
        }
        // clear the string buffer
        sb.setLength(0);
        // add any text left over after the final match
        m.appendTail(sb);
        spannable.append(sb);
        return spannable;
    }

    public static CharSequence setSpanBetweenTokens(CharSequence text,
                                                    String token, CharacterStyle... cs) {
        // Start and end refer to the points where the span will apply
        int tokenLen = token.length();
        int start = text.toString().indexOf(token) + tokenLen;
        int end = text.toString().indexOf(token, start);

        if (start > -1 && end > -1) {
            // Copy the spannable string to a mutable spannable string
            SpannableStringBuilder ssb = new SpannableStringBuilder(text);
            for (CharacterStyle c : cs)
                ssb.setSpan(c, start, end, 0);

            // Delete the tokens before and after the span
            ssb.delete(end, end + tokenLen);
            ssb.delete(start - tokenLen, start);

            text = ssb;
        }

        return text;
    }

    public static SpannableStringBuilder makeSectionOfTextBold(String text, String... textToBold) {
        SpannableStringBuilder builder = new SpannableStringBuilder(text);

        for (String textItem :
                textToBold) {
            if (textItem.length() > 0 && !textItem.trim().equals("")) {
                //for counting start/end indexes
                String testText = text.toLowerCase(Locale.US);
                String testTextToBold = textItem.toLowerCase(Locale.US);
                int startingIndex = testText.indexOf(testTextToBold);
                int endingIndex = startingIndex + testTextToBold.length();

                if (startingIndex >= 0 && endingIndex >= 0) {
                    builder.setSpan(new StyleSpan(Typeface.BOLD), startingIndex, endingIndex, 0);
                }
            }
        }

        return builder;
    }
}
