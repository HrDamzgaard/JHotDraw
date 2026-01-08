package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.Figure;

import java.awt.event.InputEvent;
import java.awt.geom.Point2D;
import java.util.Set;

/**
 * Helper class to encapsulate figure selection logic.
 */
public final class SelectionHelper {

    private SelectionHelper() {
        throw new IllegalStateException("Utility class");
    }

    /**
     * Select a figure based on a point and current selection.
     * Supports "select behind" logic.
     */
    public static Figure selectFigure(
            DrawingView view,
            Point2D.Double p,
            boolean selectBehindEnabled,
            int modifiers
    ) {
        if (selectBehindEnabled && isSelectBehindKeyPressed(modifiers)) {
            return findFigureBehindSelection(view, p);
        }

        Figure figure = findFigureInCurrentSelection(view, p);
        if (figure != null) {
            return figure;
        }

        return findSelectableFigureInDrawing(view, p);
    }

    private static boolean isSelectBehindKeyPressed(int modifiers) {
        return (modifiers & (InputEvent.ALT_DOWN_MASK | InputEvent.CTRL_DOWN_MASK)) != 0;
    }

    private static Figure findFigureBehindSelection(DrawingView view, Point2D.Double p) {
        Drawing drawing = view.getDrawing();
        Figure figure = view.findFigure(p);

        figure = skipNonSelectable(drawing, p, figure);

        Set<Figure> selected = view.getSelectedFigures();
        for (Figure ignored : selected) {
            Figure behind = drawing.findFigureBehind(p, ignored);
            if (behind != null && behind.isSelectable()) {
                return behind;
            }
        }
        return figure;
    }

    private static Figure findFigureInCurrentSelection(DrawingView view, Point2D.Double p) {
        for (Figure f : view.getSelectedFigures()) {
            if (f.contains(p)) {
                return f;
            }
        }
        return null;
    }

    private static Figure findSelectableFigureInDrawing(DrawingView view, Point2D.Double p) {
        Drawing drawing = view.getDrawing();
        Figure figure = view.findFigure(p);
        return skipNonSelectable(drawing, p, figure);
    }

    private static Figure skipNonSelectable(
            Drawing drawing,
            Point2D.Double p,
            Figure figure
    ) {
        while (figure != null && !figure.isSelectable()) {
            figure = drawing.findFigureBehind(p, figure);
        }
        return figure;
    }
}
