package org.jhotdraw.draw.bdd.grouping;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.CompositeFigure;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.GroupFigure;

import java.util.ArrayList;
import java.util.List;

public class WhenAction extends Stage<WhenAction>
{
    @ScenarioState
    protected DrawingView view;
    public WhenAction they_are_grouped()
    {
        List<Figure> selectedFigures = new ArrayList<>(view.getSelectedFigures());
        CompositeFigure group = new GroupFigure();

        for (Figure f : selectedFigures)
        {
            view.getDrawing().remove(f);
            group.add(f);
        }
        view.getDrawing().add(group);
        view.clearSelection();
        view.addToSelection(group);

        return self();
    }

    public WhenAction they_are_ungrouped()
    {
        List<Figure> seleted = new ArrayList<>(view.getSelectedFigures());

        for (Figure f : seleted)
        {
            if (f instanceof CompositeFigure)
            {
             CompositeFigure group = (CompositeFigure) f;
                List<Figure> children = new ArrayList<>(group.getChildren());
                view.getDrawing().remove(group);
                for (Figure child : children)
                {
                    view.getDrawing().add(child);
                    view.addToSelection(child);
                }
            }
        }
        return self();
    }
}
