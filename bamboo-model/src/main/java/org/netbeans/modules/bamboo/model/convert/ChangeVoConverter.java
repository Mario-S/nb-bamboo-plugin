package org.netbeans.modules.bamboo.model.convert;

import org.netbeans.modules.bamboo.model.rcp.ChangeVo;
import org.netbeans.modules.bamboo.model.rest.Change;

/**
 *
 * @author spindizzy
 */
class ChangeVoConverter extends AbstractVoConverter<Change, ChangeVo> {
    
    @Override
    public ChangeVo convert(Change src) {
        ChangeVo target = new ChangeVo();
        
        target.setChangesetId(src.getChangesetId());
        target.setAuthor(src.getAuthor());
        target.setFullName(src.getFullName());
        target.setUserName(src.getUserName());
        target.setComment(src.getComment());
        target.setCommitUrl(src.getCommitUrl());
        toDate(src.getDate()).ifPresent((date) -> target.setDate(date));
        
        FilesVoConverter converter = new FilesVoConverter();
        target.setFiles(converter.convert(src.getFiles()));
        
        return target;
    }
    
}