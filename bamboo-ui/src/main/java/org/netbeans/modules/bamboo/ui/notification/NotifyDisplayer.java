package org.netbeans.modules.bamboo.ui.notification;

import java.util.logging.Level;
import javax.swing.Icon;
import javax.swing.JComponent;
import lombok.extern.java.Log;
import org.netbeans.modules.bamboo.model.PlanVo;
import org.netbeans.modules.bamboo.model.ResultVo;
import org.netbeans.modules.bamboo.model.State;
import org.netbeans.modules.bamboo.ui.HtmlPane;

import org.openide.awt.NotificationDisplayer;
import org.openide.awt.NotificationDisplayer.Category;
import org.openide.awt.NotificationDisplayer.Priority;
import org.openide.util.NbBundle;
import org.openide.util.Pair;

import static org.netbeans.modules.bamboo.ui.notification.Bundle.Build;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Result_Failed;
import static org.netbeans.modules.bamboo.ui.notification.Bundle.Result_Successful;
import static org.openide.util.Pair.of;


/**
 * This class displays the notification in the status bar.
 *
 * @author spindizzy
 */
@Log
class NotifyDisplayer implements Runnable {

    private static final Pair<Priority, Category> INFO = of(Priority.NORMAL, Category.INFO);

    private static final Pair<Priority, Category> ERROR = of(Priority.HIGH, Category.ERROR);

    private final Icon instanceIcon;

    private final BuildResult buildResult;

    NotifyDisplayer(Icon instanceIcon, BuildResult buildResult) {
        this.instanceIcon = instanceIcon;
        this.buildResult = buildResult;
    }

    @Override
    public void run() {
        if (isRelevant()) {
            PlanVo plan = getPlan();
            String name = plan.getName();

            if (log.isLoggable(Level.INFO)) {
                log.info(String.format("state of plan %s has changed", name));
            }

            JComponent resultPanel = newBuildResultPanel();
            Pair<Priority, Category> cat = getCategory();

            getNotificationDisplayer().notify(name, instanceIcon, resultPanel, resultPanel, cat.first(), cat.second());
        }
    }

    private JComponent newBuildResultPanel() {
        PlanVo plan = getPlan();
        String summary = getSummary(plan);
        ResultVo resultVo = buildResult.getNewResult();
        HtmlPane reasonComp = new HtmlPane();
        reasonComp.setText(resultVo.getBuildReason());
        return new BuildResultPanel(summary, reasonComp);
    }

    private Pair<Priority, Category> getCategory() {
        PlanVo plan = getPlan();
        return (isFailed(plan)) ? ERROR : INFO;
    }

    private PlanVo getPlan() {
        return buildResult.getPlan();
    }

    /**
     * Is the result change relevant for a notification? We are not interested in a change from success to success.<br/>
     * It is similar to Bamboo's "Failed Builds And First Successful" notification.
     */
    private boolean isRelevant() {
        boolean relevant = true;

        ResultVo oldResult = buildResult.getOldResult();
        ResultVo newResult = buildResult.getNewResult();

        if (State.Successful.equals(oldResult.getState()) && State.Successful.equals(newResult.getState())) {
            relevant = false;
        }

        if (log.isLoggable(Level.INFO)) {
            log.info(String.format("result change is relevant: %s", relevant));
        }

        return relevant;
    }

    @NbBundle.Messages({
        "Build=The Build",
        "Result_Failed=failed",
        "Result_Successful=was successful"
    })
    private String getSummary(PlanVo plan) {
        ResultVo result = plan.getResult();
        int number = result.getNumber();
        String strState = (isFailed(plan)) ? Result_Failed() : Result_Successful();

        return String.format("%s %s %s", Build(), number, strState);
    }

    private boolean isFailed(PlanVo plan) {
        ResultVo result = plan.getResult();
        return isFailed(result);
    }

    private boolean isFailed(ResultVo result) {
        State state = result.getState();
        return State.Failed.equals(state);
    }

    NotificationDisplayer getNotificationDisplayer() {
        return NotificationDisplayer.getDefault();
    }
}
