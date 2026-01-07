package org.jhotdraw.draw.bdd.grouping;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.RectangleFigure;



public class GivenFigure extends Stage<GivenFigure>
{
    @ScenarioState
    protected Drawing drawing;

    @ScenarioState
    protected DrawingView view;

    public GivenFigure a_drawing_with_two_rectangles()
    {
        drawing = new DefaultDrawing();
        view = new DefaultDrawingView();
        view.setDrawing(drawing);

        drawing.add(new RectangleFigure());
        drawing.add(new RectangleFigure());
        return self();
    }

    public GivenFigure both_rectangles_are_selected()
    {
        view.selectAll();
        return self();
    }

    public GivenFigure a_drawing_with_a_selected_group()
    {
        drawing = new DefaultDrawing();
        view = new DefaultDrawingView();
        DrawingEditor editor = new DefaultDrawingEditor();
        editor.add( view );
        view.setDrawing(drawing);

        Figure f1 = new RectangleFigure();
        Figure f2 = new RectangleFigure();
        org.jhotdraw.draw.figure.GroupFigure group = new org.jhotdraw.draw.figure.GroupFigure();
        group.add(f1);
        group.add(f2);

        drawing.add(group);
        view.addToSelection(group);
        return self();
    }
}
