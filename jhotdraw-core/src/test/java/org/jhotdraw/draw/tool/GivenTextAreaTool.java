package org.jhotdraw.draw.tool;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.*;
import org.jhotdraw.draw.figure.TextAreaFigure;
import org.jhotdraw.draw.figure.TextHolderFigure;

public class GivenTextAreaTool extends Stage<GivenTextAreaTool> {

    @ScenarioState
    Drawing drawing;
    @ScenarioState
    DrawingEditor drawingEditor;
    @ScenarioState
    DrawingView drawingView;
    @ScenarioState
    TextAreaCreationTool textAreaCreationTool;

    public GivenTextAreaTool empty_drawing() {
        drawing = new DefaultDrawing();
        drawingView = new DefaultDrawingView();
        drawingView.setDrawing(drawing);
        drawingEditor = new DefaultDrawingEditor();
        return self();
    }

    public GivenTextAreaTool the_text_creation_tool_active() {
        drawingEditor.add(drawingView);
        drawingEditor.setActiveView(drawingView);
        TextHolderFigure textHolderFigure = new TextAreaFigure();
        textHolderFigure.setText("Test");
        textAreaCreationTool = new TextAreaCreationTool(textHolderFigure);
        drawingEditor.setTool(textAreaCreationTool);
        return self();
    }

}
