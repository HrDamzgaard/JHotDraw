package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.junit.Before;
import org.junit.Test;

import java.awt.geom.Point2D;
import java.util.Collections;

import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

public class SelectionHelperTest {

    private DrawingView view;
    private Drawing drawing;
    private Figure figure;

    @Before
    public void setUp() {
        view = mock(DrawingView.class);
        drawing = mock(Drawing.class);
        figure = mock(Figure.class);

        when(view.getDrawing()).thenReturn(drawing);
        when(view.getSelectedFigures()).thenReturn(Collections.emptySet());
    }

    @Test
    public void testSelectSingleFigure() {
        Point2D.Double point = new Point2D.Double(10, 10);

        when(view.findFigure(point)).thenReturn(figure);
        when(figure.isSelectable()).thenReturn(true);

        Figure selected = SelectionHelper.selectFigure(view, point, false, 0);

        assertEquals("Should select the figure at the point", figure, selected);
    }

    @Test
    public void testSelectBehindWithModifier() {
        Point2D.Double point = new Point2D.Double(5, 5);

        Figure front = mock(Figure.class);
        Figure back = mock(Figure.class);

        when(view.findFigure(point)).thenReturn(front);
        when(front.isSelectable()).thenReturn(false);
        when(drawing.findFigureBehind(point, front)).thenReturn(back);
        when(back.isSelectable()).thenReturn(true);

        int altModifier = java.awt.event.InputEvent.ALT_DOWN_MASK;

        Figure selected = SelectionHelper.selectFigure(view, point, true, altModifier);

        assertEquals("Should select the figure behind when modifier is pressed", back, selected);
    }

    @Test
    public void testSelectNoFigureReturnsNull() {
        Point2D.Double point = new Point2D.Double(0, 0);
        when(view.findFigure(point)).thenReturn(null);

        Figure selected = SelectionHelper.selectFigure(view, point, false, 0);

        assertNull("No figure should be selected", selected);
    }
}
