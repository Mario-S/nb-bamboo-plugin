package org.netbeans.modules.bamboo.ui.actions;

import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import static org.netbeans.modules.bamboo.ui.actions.Bundle.*;
import org.netbeans.modules.bamboo.ui.wizard.InstanceDialog;
import org.netbeans.modules.bamboo.ui.wizard.InstancePropertiesDisplayable;
import org.openide.util.NbBundle.Messages;

@Messages({
    "LBL_Add_Instance=Add New Instance..."
})
public class AddInstanceAction extends AbstractAction {

    public AddInstanceAction() {
        super(LBL_Add_Instance());
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        createDialog().show();
    }

    InstancePropertiesDisplayable createDialog() {
        return new InstanceDialog();
    }

}
