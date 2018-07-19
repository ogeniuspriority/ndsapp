package com.ogeniuspriority.nds.nds;

import android.content.Context;
import android.text.Layout;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by USER on 2/23/2017.
 */
public class CustomLinkMovementMethod extends LinkMovementMethod {

    private static Context movementContext;

    private static CustomLinkMovementMethod linkMovementMethod = new CustomLinkMovementMethod();

    public boolean onTouchEvent(android.widget.TextView widget, android.text.Spannable buffer, android.view.MotionEvent event) {
        int action = event.getAction();

        if (action == MotionEvent.ACTION_UP) {
            int x = (int) event.getX();
            int y = (int) event.getY();

            x -= widget.getTotalPaddingLeft();
            y -= widget.getTotalPaddingTop();

            x += widget.getScrollX();
            y += widget.getScrollY();

            Layout layout = widget.getLayout();
            int line = layout.getLineForVertical(y);
            int off = layout.getOffsetForHorizontal(line, x);

            URLSpan[] link = buffer.getSpans(off, off, URLSpan.class);
            //StyleSpan[] mSpans = buffer.getSpans(0, widget.length(), StyleSpan.class);
            for(int i=0;i<link.length;i++) {
                String url = link[i].getURL();
                if (link.length != 0) {

                        Toast.makeText(movementContext, url, Toast.LENGTH_LONG).show();


                    return true;
                } else {
                    Toast.makeText(movementContext, "Nothing", Toast.LENGTH_LONG).show();
                    return true;
                }
            }
        }

        return super.onTouchEvent(widget, buffer, event);
    }

    public static android.text.method.MovementMethod getInstance(Context c) {
        movementContext = c;
        return linkMovementMethod;
    }
}
