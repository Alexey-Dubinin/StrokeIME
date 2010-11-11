
package org.me.strokeime;

public abstract class Layout {
    public static final int SHIFT_DOWN = 0;
    public static final int SHIFT_UP = 1;

    public static final int MC = 0x0;
    public static final int LT = 0x1;
    public static final int MT = 0x2;
    public static final int RT = 0x3;
    public static final int LB = 0x4;
    public static final int MB = 0x5;
    public static final int RB = 0x6;

    // out zones (used Z_OT only)
    public static final int OT = 0xA;
    public static final int OR = 0xB;
    public static final int OB = 0xC;
    public static final int OL = 0xD;

    public Layout() {
        initialize();
    }

    /**
     * Initializes key actions.
     * Must be overriden in subclasses
     */
    protected abstract void initialize();

    /**
     * Main zones map.
     * Dimensions are:
     * shift state, stroke start zone, stroke end zone
     */
    protected final Key[][][] map = new Key[2][16][16];

    /**
     * Get key, associated with the stroke from start to end.
     */
    public final Key getKey(int shiftState, int start, int end) {
        return map[shiftState][start][end];
    }

    /**
     * Get action, associated with the stroke from start to end.
     */
    public final Action getAction(int shiftState, int start, int end) {
        Key k = map[shiftState][start][end];
        return k == null ? null : k.action;
    }

    /**
     * Register usual char stroke in the map.
     * Lower and Upper labels for this stroke will be set equal to the characters themselves
     * 
     * @param start Start zone
     * @param end   End zone
     * @param lower Character (may be, string) to type when Shift button is not pressed
     * @param upper Character (may be, string) to type when Shift button is pressed
     */
    protected final void s(int start, int end, String lower, String upper) {
        s(start, end, lower, upper, lower, upper);
    }

    /**
     * Register usual char stroke in the map.
     * 
     * @param start      Start zone
     * @param end        End zone
     * @param lower      Character (may be, string) to type when Shift button is not pressed
     * @param upper      Character (may be, string) to type when Shift button is pressed
     * @param labelLower Keyboard text for this character when Shift button is not pressed
     * @param labelUpper Keyboard text for this character when Shift button is pressed
     */
    protected final void s(int start, int end, String lower, String upper, String labelLower, String labelUpper) {
        map[SHIFT_DOWN][start][end] = new Key(Action.createTextAction(lower), labelLower);
        map[SHIFT_UP][start][end] = new Key(Action.createTextAction(upper), labelUpper);
    }

    /**
     * Register usual char stroke in the map.
     * 
     * @param start         Start zone
     * @param end           End zone
     * @param lower         Character (may be, string) to type when Shift button is not pressed
     * @param upper         Character (may be, string) to type when Shift button is pressed
     * @param drawableLower Keyboard picture for this character when Shift button is not pressed
     * @param drawableUpper Keyboard picture for this character when Shift button is pressed
     */
    protected final void s(int start, int end, String lower, String upper, int drawableLower, int drawableUpper) {
        map[SHIFT_DOWN][start][end] = new Key(Action.createTextAction(lower), drawableLower);
        map[SHIFT_UP][start][end] = new Key(Action.createTextAction(upper), drawableUpper);
    }

    /**
     * Register stroke in the map with keycode as the event param.
     * This is useful for creating Alt and Ctrl aware layouts or registering strokes
     * for ENTER, SPACE, BACKSPACE and other special characters
     * 
     * @param start      Start zone
     * @param end        End zone
     * @param code       Raw keycode to use with Alt and Ctrl
     * @param labelLower Keyboard text for this character when Shift button is not pressed
     * @param labelLower Keyboard text for this character when Shift button is pressed
     */
    protected final void c(int start, int end, int code, String labelLower, String labelUpper) {
        map[SHIFT_DOWN][start][end] = new Key(Action.createKeyCodeAction(code), labelLower);
        map[SHIFT_UP][start][end] = new Key(Action.createKeyCodeAction(code), labelUpper);
    }

    /**
     * Register ChangeLayout stroke in the map.
     * This will work for both shifted and not shifted states
     *
     * @param start      Start zone
     * @param end        End zone
     * @param layoutName Layout to change to
     * @param label      Keyboard text for this layout
     */
    protected final void l(int start, int end, String layoutName, String label) {
        map[SHIFT_UP][start][end] = map[SHIFT_DOWN][start][end] = new Key(Action.createLayoutAction(layoutName), label);
    }

    /**
     * Register ChangeLayout stroke in the map.
     * This will work for both shifted and not shifted states
     *
     * @param start      Start zone
     * @param end        End zone
     * @param layoutName Layout to change to
     * @param drawable   Keyboard picture for this layout
     */
    protected final void l(int start, int end, String layoutName, int drawable) {
        map[SHIFT_UP][start][end] = map[SHIFT_DOWN][start][end] = new Key(Action.createLayoutAction(layoutName), drawable);
    }
}

