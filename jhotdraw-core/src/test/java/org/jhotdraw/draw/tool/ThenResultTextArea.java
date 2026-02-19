package org.jhotdraw.draw.tool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.Drawing;
import org.jhotdraw.draw.DrawingView;
import org.jhotdraw.draw.figure.Figure;
import org.jhotdraw.draw.figure.TextHolderFigure;
import static org.junit.Assert.*;

public class ThenResultTextArea extends Stage<ThenResultTextArea> {

    @ScenarioState
    Drawing drawing;
    @ScenarioState
    DrawingView drawingView;
    @ScenarioState
    TextAreaCreationTool textAreaCreationTool;

    public void text_area_created_with_text(String expectedText) {
        Figure figure = drawingView.getDrawing().getChildren().get(0);

        TextHolderFigure textHolderFigure = (TextHolderFigure) figure;

        assertEquals(expectedText, textHolderFigure.getText());
    }

}
