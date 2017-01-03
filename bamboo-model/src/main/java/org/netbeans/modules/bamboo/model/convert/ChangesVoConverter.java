package org.netbeans.modules.bamboo.model.convert;

import java.util.Collection;

import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rest.Changes;

import static java.util.Collections.emptyList;
import static java.util.stream.Collectors.toList;

/**
 *
 * @author spindizzy
 */
public class ChangesVoConverter implements VoConverter<Changes, Collection<ChangeVo>> {

    @Override
    public Collection<ChangeVo> convert(Changes src) {
        if (src != null) {
            ChangeVoConverter converter = new ChangeVoConverter();
            return src.asCollection().stream().map(
                    c -> {
                        return converter.convert(c);
                    }).collect(toList());
        }
        return emptyList();
    }
}
