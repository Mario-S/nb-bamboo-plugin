package org.netbeans.modules.bamboo.ui.notification;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.ChangeEvents;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;

/**
 * Listnere for all plans related to a {@link BambooInstance}.
 * 
 * @author spindizzy
 */
public class BuildResultNotify implements PropertyChangeListener {

    private final BambooInstance instance;
    
    private NotifyDelegator delegator;
    
    private final SynchronizationListener synchronizationListener;
    
    public BuildResultNotify(BambooInstance instance) {
        this.instance = instance;
        delegator = new NotifyDelegator();
        synchronizationListener = new SynchronizationListener(instance);
        registerChangeListener();
    }

    private void registerChangeListener() {
        instance.getChildren().forEach(project -> {
            project.getChildren().forEach(plan -> {
                plan.addPropertyChangeListener(this);
            });
        });
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (ChangeEvents.Result.toString().equals(propertyName)) {
            PlanVo plan = (PlanVo) evt.getSource();
            ResultVo oldResult = (ResultVo) evt.getOldValue();
            ResultVo newResult = (ResultVo) evt.getNewValue();
            notify(new BuildResult(plan, oldResult, newResult));
        }
    }

    private void notify(BuildResult buildResult) {
        delegator.notify(buildResult);
    }

    void setDelegator(NotifyDelegator delegator) {
        this.delegator = delegator;
    }
}
