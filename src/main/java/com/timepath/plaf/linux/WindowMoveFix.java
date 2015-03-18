package com.timepath.plaf.linux;

import com.timepath.plaf.OS;
import org.jetbrains.annotations.NotNull;

import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * @author TimePath
 */
public class WindowMoveFix {

    public static void install(@NotNull final Frame f) {
        if (!OS.OBJECT$.isLinux()) return;
        f.addComponentListener(new ComponentAdapter() {
            private boolean moved;
            @NotNull
            private Point real = new Point();
            private boolean updateReal = true;

            /**
             * When maximizing windows on linux under gnome-shell and possibly others, the
             * JMenuBar
             * menus appear not to work. This is because the position of the
             * window never updates. This is an attempt to make them usable again.
             */
            @Override
            public void componentResized(ComponentEvent e) {
                @NotNull Rectangle b = f.getBounds();
                @NotNull Rectangle s = f.getGraphicsConfiguration().getBounds();
                if (moved) {
                    moved = false;
                    return;
                }
                if (updateReal) {
                    real.x = b.x;
                    real.y = b.y;
                }
                updateReal = true;
                b.x = real.x;
                b.y = real.y;
                if ((b.x + b.width) > s.width) {
                    b.x -= (b.x + b.width) - s.width;
                    updateReal = false;
                }
                if ((b.y + b.height) > s.height) {
                    b.y = 0;
                    updateReal = false;
                }
                f.setBounds(b);
            }

            @Override
            public void componentMoved(ComponentEvent e) {
                @NotNull Rectangle b = f.getBounds();
                moved = true;
                real.x = b.x;
                real.y = b.y;
            }
        });
    }

}
