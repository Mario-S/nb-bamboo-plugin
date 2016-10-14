package org.netbeans.modules.bamboo.ui.notification;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Optional;
import org.netbeans.api.annotations.common.NonNull;
import org.netbeans.api.progress.ProgressHandle;
import org.netbeans.api.progress.ProgressHandleFactory;
import org.netbeans.modules.bamboo.model.BambooInstance;
import org.netbeans.modules.bamboo.model.ChangeEvents;

import static org.netbeans.modules.bamboo.ui.notification.Bundle.TXT_SYNC;

import org.openide.util.NbBundle;

import static java.util.Optional.empty;
import static java.util.Optional.of;

/**
 * This calss listenes for changes on the sychronization.
 *
 * @author spindizzy
 */
class SynchronizationListener implements PropertyChangeListener {

    private final BambooInstance instance;

    private Optional<ProgressHandle> progressHandle;

    SynchronizationListener(@NonNull BambooInstance instance) {
        this.instance = instance;
        progressHandle = empty();
        init();
    }

    private void init() {
        instance.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        String propertyName = evt.getPropertyName();
        if (ChangeEvents.Synchronizing.toString().equals(propertyName)) {
            boolean sync = (boolean) evt.getNewValue();
            if (sync) {
                startProgress();
            } else {
                finishProgress();
            }
        }
    }

    private void startProgress() {
        getProgressHandle().get().start();
    }

    private void finishProgress() {
        progressHandle.ifPresent(handle -> {
            handle.finish();
            progressHandle = empty();
        });
    }

    private Optional<ProgressHandle> getProgressHandle() {
        if (!progressHandle.isPresent()) {
            progressHandle = of(newProgressHandle());
        }
        return progressHandle;
    }

    @NbBundle.Messages({"TXT_SYNC=Synchronizing"})
    private ProgressHandle newProgressHandle() {
        return ProgressHandleFactory.createHandle(TXT_SYNC() + " " + getName());
    }

    private String getName() {
        return instance.getName();
    }
}