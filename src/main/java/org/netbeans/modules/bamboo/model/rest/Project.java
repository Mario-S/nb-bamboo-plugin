package org.netbeans.modules.bamboo.model.rest;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.Collection;
import java.util.Collections;
import lombok.Data;

/**
 *
 * @author spindizzy
 */
@Data
@JsonRootName(value = "project")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Project {

    private String key;
    private Link link;
    private String name;
    private transient Plans plans;

    public Collection<Plan> plansAsCollection() {
        return (plans == null) ? Collections.emptyList() : plans.getPlan();
    }

}