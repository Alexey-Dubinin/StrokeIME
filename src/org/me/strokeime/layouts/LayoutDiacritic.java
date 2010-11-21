
package org.me.strokeime.layouts;

import org.me.strokeime.Layout;
import org.me.strokeime.Action;
import static org.me.strokeime.GliphBank.*;
import static android.view.KeyEvent.*;

public class LayoutDiacritic extends Layout {
    @Override protected String getTitle()          { return "Diacritic Symbols"; }
    @Override protected String getName()           { return "diacritic"; }
    @Override protected String getLabelPrimary()   { return "êґå"; }
    @Override protected int    getType()           { return TYPE_SECONDARY_CHAR; }

    @Override
    protected void initializeLayout() {

        l(MT, OT, Action.LAYOUT_BACK_TO_PRIMARY);
        //l(RT, OT, Action.LAYOUT_NEXT_PRIMARY);
        //l(RB, OT, Action.LAYOUT_PREV_PRIMARY);

        t(MC, LT, "ç", "Ç");
        t(MC, MT, "î", "Î");
        t(MC, RT, "ñ", "Ñ");
        t(MC, MC, "ß", "S", "ß", "ss");
        t(MC, LB, "ì", "Ì");
        t(MC, MB, "ï", "Ï");
        t(MC, RB, "í", "Í");

        c(LT, LT, KEYCODE_SHIFT_LEFT, GLIPH_SHIFT, GLIPH_SHIFT, GLIPH_SHIFT_LOCK);
        t(LT, MT, "é", "É");
        t(LT, RT, "ê", "Ê");
        t(LT, MC, "ë", "Ë");
        t(LT, LB, "ý", "Ý");
        t(LT, MB, "ÿ", "Ÿ");
        t(LT, RB, "è", "È");

        t(MT, LT, "à", "À");
        t(MT, MT, "â", "Â");
        t(MT, RT, "á", "Á");
        t(MT, MC, "å", "Å");
        t(MT, LB, "ã", "Ã");
        t(MT, MB, "ä", "Ä");
        t(MT, RB, "æ", "Æ");

        // Belarusian, Ukrainian
        //t(RT, LT, "", "");
        t(RT, MT, "ї", "Ї");
        t(RT, RT, "і", "І");
        t(RT, MC, "є", "Є");
        //t(RT, LB, "", "");
        t(RT, MB, "ў", "Ў");
        t(RT, RB, "ґ", "Ґ");

        t(LB, LT, "ù", "Ù");
        t(LB, MT, "û", "Û");
        t(LB, RT, "§", "§");
        //t(LB, MC, "", "");
        t(LB, LB, "ü", "Ü");
        t(LB, MB, "ú", "Ú");
        //t(LB, RB, "", "");

        t(MB, LT, "õ", "Õ");
        t(MB, MT, "ö", "Ö");
        t(MB, RT, "œ", "Œ");
        t(MB, MC, "ø", "Ø");
        t(MB, LB, "ò", "Ò");
        t(MB, MB, "ô", "Ô");
        t(MB, RB, "ó", "Ó");

        // Esperanto
        //t(RB, LT, "", "");
        t(RB, MT, "ĝ", "Ĝ");
        t(RB, RT, "ĥ", "Ĥ");
        t(RB, MC, "ŝ", "Ŝ");
        t(RB, LB, "ĵ", "Ĵ"); 
        t(RB, MB, "ŭ", "Ŭ");
        t(RB, RB, "ĉ", "Ĉ");
    }
}
