
package org.me.strokeime;

import org.me.strokeime.layouts.*;
import android.inputmethodservice.InputMethodService;
import android.view.inputmethod.InputConnection;
import android.view.KeyEvent;
import android.view.View;
import android.content.res.Resources;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.BufferedReader;
import java.io.IOException;

public class StrokeIME extends InputMethodService implements InputEventListener
{
    private InputView mInputView;
    private int mLastDisplayWidth;

    private ColorTheme mColorThemeDark;
    private ColorTheme mColorThemeLight;

    private LayoutBank mLayoutBank;
    private Layout mCurrentLayout;
    private int mCurrentLayoutDuration;

    private int mShiftState;

    /**
     * Main initialization of the input method component.  Be sure to call
     * to super class.
     */
    @Override public void onCreate() {
        super.onCreate();

        Resources r = getResources();
        mColorThemeDark = new ColorTheme(
                r.getColor(R.color.dark_bg),
                r.getColor(R.color.dark_fg),
                r.getColor(R.color.dark_txt),
                r.getColor(R.color.dark_txt_hot),
                r.getColor(R.color.dark_txt_back)
                );
        mColorThemeLight = new ColorTheme(
                r.getColor(R.color.light_bg),
                r.getColor(R.color.light_fg),
                r.getColor(R.color.light_txt),
                r.getColor(R.color.light_txt_hot),
                r.getColor(R.color.light_txt_back)
                );

        mLayoutBank = new LayoutBank();

        mCurrentLayout = mLayoutBank.defaultLayout;
        mCurrentLayoutDuration = LayoutBank.DURATION_FOREVER;

        mShiftState = Layout.SHIFT_OFF;
    }
    
    /**
     * This is the point where you can do all of your UI initialization.  It
     * is called after creation and any configuration change.
     */
    @Override public void onInitializeInterface() {
        /*if (mQwertyKeyboard != null) {
            // Configuration changes can happen after the keyboard gets recreated,
            // so we need to be able to re-build the keyboards if the available
            // space has changed.
            int displayWidth = getMaxWidth();
            if (displayWidth == mLastDisplayWidth) return;
            mLastDisplayWidth = displayWidth;
        }*/
    }

    /**
     * Called by the framework when your view for creating input needs to
     * be generated.  This will be called the first time your input method
     * is displayed, and every time it needs to be re-created such as due to
     * a configuration change.
     */
    @Override public View onCreateInputView() {
        mInputView = new InputView(this);
        mInputView.setInputEventListener(this);

        // TODO: set prefered color theme
        mInputView.setColorTheme(mColorThemeDark);
        mInputView.setLayout(mCurrentLayout, mShiftState);

        return mInputView;
    }

    /**
     * Called by the framework when your view for showing candidates needs to
     * be generated, like {@link #onCreateInputView}.
     */
    @Override public View onCreateCandidatesView() {
        return null;
    }

    private final void processBackspace() {

    }

    private final void processShift() {
        switch(mShiftState) {
            case Layout.SHIFT_LOCK:
                mShiftState = Layout.SHIFT_OFF;
                break;
            case Layout.SHIFT_ON:
                mShiftState = Layout.SHIFT_LOCK;
                break;
            case Layout.SHIFT_OFF:
                mShiftState = Layout.SHIFT_ON;
                break;
        }
        updateView();
    }

    private final void updateView() {
        mInputView.setLayout(mCurrentLayout, mShiftState);
    }

    public void onInput(InputEvent event) {
        //android.content.Context context = getApplicationContext();
        //android.widget.Toast toast = android.widget.Toast.makeText(context, "HELLO!", android.widget.Toast.LENGTH_SHORT);
        //toast.show();
        InputConnection c = getCurrentInputConnection();
        
        if(event.action == null)
            return; // TODO: better action

        switch(event.action.actionType) {
            case Action.TYPE_TEXT:
                // TODO: process shift, process duration
                c.commitText(event.action.value, 0);
                if(mShiftState == Layout.SHIFT_ON) {
                    mShiftState = Layout.SHIFT_OFF;
                    updateView();
                }
                break;
            case Action.TYPE_CODE:
                // Special case: Shift key was pressed
                if(event.action.keyCode == KeyEvent.KEYCODE_SHIFT_LEFT ||
                        event.action.keyCode == KeyEvent.KEYCODE_SHIFT_RIGHT) {
                    processShift();
                    break;
                }

                // Otherwise, perform some action and release shift if necessary
                switch(event.action.keyCode) {
                    case KeyEvent.KEYCODE_A:
                        // TODO: remove this experimental feature
                        mInputView.setColorTheme(mColorThemeLight);
                        break;
                    // TODO: process enter, tabulation etc manually
                    default:
                        c.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_DOWN, event.action.keyCode));
                        c.sendKeyEvent(new KeyEvent(KeyEvent.ACTION_UP, event.action.keyCode));
                        break;
                }

                if(mShiftState == Layout.SHIFT_ON) {
                    mShiftState = Layout.SHIFT_OFF;
                    updateView();
                }
                break;
            case Action.TYPE_LAYOUT:
                // TODO: process duration
                mCurrentLayout = mLayoutBank.getLayout(event.action.value).layout;
                mShiftState = Layout.SHIFT_OFF;
                updateView();
                break;
        }
    }
}
