package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import org.netbeans.modules.bamboo.model.LifeCycleState;
import org.netbeans.modules.bamboo.model.State;



@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Result implements ServiceInfoProvideable{
    
    private String key;
    private Link link;
    private State state;
    private Plan plan;
    private LifeCycleState lifeCycleState;
    private int number;
    private int id;
    private String buildReason;
    
    public Result() {
        state = State.Unknown;
        lifeCycleState = LifeCycleState.NotBuilt;
    }
}
