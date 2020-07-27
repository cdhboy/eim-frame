package com.eim.service.common;

import com.eim.domain.common.ResultEntity;
import com.eim.domain.common.FileEntity;
import com.eim.domain.common.ProcEntity;
import com.eim.domain.common.QueryEntity;
import com.eim.domain.common.UpdateEntity;

public interface CommonService {

    public ResultEntity query(QueryEntity queryEntity);

    public ResultEntity update(UpdateEntity updateEntity);

    public ResultEntity download(FileEntity fileEntity);

    public ResultEntity upload(FileEntity fileEntity);

    public ResultEntity delete(FileEntity fileEntity);

    public ResultEntity proc(ProcEntity procEntity);

}
