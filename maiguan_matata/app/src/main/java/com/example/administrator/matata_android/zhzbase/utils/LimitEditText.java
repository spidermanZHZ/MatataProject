package com.example.administrator.matata_android.zhzbase.utils;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputConnection;
import android.view.inputmethod.InputConnectionWrapper;
import android.widget.EditText;

import java.util.regex.Pattern;

public class LimitEditText extends EditText {
    public LimitEditText(Context context) {
        super(context);
    }

    public LimitEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public LimitEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public InputConnection onCreateInputConnection(EditorInfo outAttrs) {
        return new InnerInputConnecttion(super.onCreateInputConnection(outAttrs),
                false);
    }
    class InnerInputConnecttion extends InputConnectionWrapper implements InputConnection {

        public InnerInputConnecttion(InputConnection target, boolean mutable) {
            super(target, mutable);
        }

        /**
         * 对输入的内容进行拦截
         *
         * @param text
         * @param newCursorPosition
         * @return
         */
        @Override
        public boolean commitText(CharSequence text, int newCursorPosition) {

            // 只能输入字母或者数字

            String regex = "^[\u4E00-\u9FA5]+$";
            boolean isChinese = Pattern.matches(regex, text.toString());

            if (!Character.isLetterOrDigit(text.charAt(newCursorPosition)) || isChinese)  {
                return false;
            }
            return super.commitText(text, newCursorPosition);
        }

        @Override
        public boolean sendKeyEvent(KeyEvent event) {
            return super.sendKeyEvent(event);
        }

        @Override
        public boolean setSelection(int start, int end) {
            return super.setSelection(start, end);
        }
    }

}
