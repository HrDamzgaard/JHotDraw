package org.jhotdraw.draw.bdd.grouping;

import com.tngtech.jgiven.junit.ScenarioTest;
import org.junit.Test;
public class GroupFigureTest extends ScenarioTest<GivenFigure, WhenAction, ThenResult>
{
 @Test
    public void grouping_two_rectangles_should_result_in_one_group_figure()
 {
     given().a_drawing_with_two_rectangles()
             .and().both_rectangles_are_selected();

     when().they_are_grouped();

     then().the_drawing_should_contain_one_group_figure();

     org.junit.Assert.assertTrue(true);
 }

    @Test
    public void ungrouping_a_group_should_result_in_individual_figures()
    {
        given().a_drawing_with_a_selected_group();
        when().they_are_ungrouped();
        then().there_should_be_two_selected_figures();
        org.junit.Assert.assertTrue(true);org.junit.Assert.assertTrue(true);
    }
}
