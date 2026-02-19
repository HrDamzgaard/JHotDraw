package org.jhotdraw.draw.tool;

import org.jhotdraw.draw.bdd.grouping.GivenFigure;
import org.jhotdraw.draw.bdd.grouping.ThenResult;
import org.jhotdraw.draw.bdd.grouping.WhenAction;
import org.junit.Test;
import com.tngtech.jgiven.junit.ScenarioTest;

public class BddTextAreaTool extends ScenarioTest<GivenTextAreaTool, WhenActionTextArea, ThenResultTextArea> {

    @Test
    public void user_create_text_area() throws NoSuchFieldException, IllegalAccessException {
        given().empty_drawing()
                .and().the_text_creation_tool_active();

        when().user_creates_text_area()
                .and().user_writes("Test");

        then().text_area_created_with_text("Test");
    }
}
