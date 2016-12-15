package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.netbeans.modules.bamboo.model.LifeCycleState;
import org.netbeans.modules.bamboo.model.State;



@Data
@EqualsAndHashCode(of = "key")
@JsonIgnoreProperties(ignoreUnknown = true)
@XmlAccessorType(XmlAccessType.FIELD)
@XmlRootElement
public class Result implements ServiceInfoProvideable{
    @XmlAttribute
    private String key;
    @XmlElement
    private Link link;
    @XmlAttribute
    private State state;
    @XmlElement(name = "plan")
    private Plan plan;
    @XmlAttribute
    private LifeCycleState lifeCycleState;
    @XmlAttribute
    private int number;
    @XmlElement(name = "buildReason", type = String.class)
    private String buildReason;
    private int id;
    private long buildDurationInSeconds;
    private String buildStartedTime;
    private String buildCompletedTime;
    @XmlElement
    private Changes changes;
    
    public Result() {
        state = State.Unknown;
        lifeCycleState = LifeCycleState.NotBuilt;
    }
}
