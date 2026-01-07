package org.jhotdraw.draw.bdd.grouping;

import com.tngtech.jgiven.Stage;
import com.tngtech.jgiven.annotation.ScenarioState;
import org.jhotdraw.draw.DrawingView;
import static org.assertj.core.api.Assertions.assertThat;


public class ThenResult extends Stage<ThenResult> {
    @ScenarioState
    protected DrawingView view;

    public ThenResult the_drawing_should_contain_one_group_figure()
    {
        assertThat(view.getSelectionCount()).isEqualTo(1);
        return self();
    }

    public ThenResult there_should_be_two_selected_figures()
    {
        assertThat(view.getSelectionCount()).isEqualTo(2);
        return self();
    }
}