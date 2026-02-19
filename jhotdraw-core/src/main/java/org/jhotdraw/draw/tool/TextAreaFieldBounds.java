package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.figure.TextHolderFigure;
import org.jhotdraw.geom.Insets2D;

import java.awt.geom.Rectangle2D;

public class TextAreaFieldBounds {

    public Rectangle2D.Double getRectangleFieldBounds (TextHolderFigure figure) {
        Rectangle2D.Double r = figure.getDrawingArea();
        Insets2D.Double insets = figure.getInsets();
        insets.subtractTo(r);
        r.x -= 1;
        r.y -= 2;
        r.width += 18;
        r.height += 4;
        return r;
    }

}
