package com.eim.service.common;

import com.eim.domain.common.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.OutputStream;

public interface CommonService {

    public ResultEntity query(QueryEntity queryEntity);

    public ResultEntity update(UpdateEntity updateEntity);

    public ResultEntity download(FileEntity fileEntity, OutputStream os);

    public ResultEntity upload(FileEntity fileEntity, MultipartFile file);

    public ResultEntity delete(FileEntity fileEntity);

    public ResultEntity proc(ProcEntity procEntity);

}
