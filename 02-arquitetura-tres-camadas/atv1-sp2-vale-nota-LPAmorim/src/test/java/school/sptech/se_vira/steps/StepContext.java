package school.sptech.se_vira.steps;

import io.cucumber.spring.ScenarioScope;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.ResultActions;

@Component
@ScenarioScope
public class StepContext {

    private ResultActions result;
    private Integer lastCreatedId;

    public ResultActions getResult() {
        return result;
    }

    public void setResult(ResultActions result) {
        this.result = result;
    }

    public Integer getLastCreatedId() {
        return lastCreatedId;
    }

    public void setLastCreatedId(Integer lastCreatedId) {
        this.lastCreatedId = lastCreatedId;
    }
}
