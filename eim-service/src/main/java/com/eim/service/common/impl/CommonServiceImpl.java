package com.eim.service.common.impl;

import com.eim.dao.helper.DaoHelper;
import com.eim.domain.common.*;
import com.eim.service.common.CommonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private DaoHelper daoHelper;

    @Override
    public ResultEntity query(QueryEntity queryEntity) {

        ResultEntity resultEntity = null;

        try {
            List<Map<String, Object>> list = daoHelper.doQuery(queryEntity.getDsKey(), queryEntity.getSql(), queryEntity.getObjs());

            resultEntity = ResultEntity.ok(list.toArray());
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.error(e);
        }

        return resultEntity;
    }

    @Override
    public ResultEntity update(UpdateEntity updateEntity) {

        ResultEntity resultEntity = null;

        try {
            //单条sql执行
             if (!StringUtils.isEmpty(updateEntity.getSql())) {
                int n = daoHelper.doUpdate(updateEntity.getDsKey(), updateEntity.getSql(), updateEntity.getObjs());
                resultEntity = ResultEntity.ok(new Integer[]{n});
            }//单条sql，批量执行
            else if (!StringUtils.isEmpty(updateEntity.getSql()) && updateEntity.getList().size() > 0) {
                int[] ns = daoHelper.doUpdate(updateEntity.getDsKey(), updateEntity.getSql(), updateEntity.getList());
                resultEntity = ResultEntity.ok(Arrays.asList(ns).toArray());
            }//批量sql执行
            else if (updateEntity.getSqls() != null && updateEntity.getSqls().length > 0) {
                int[] ns = daoHelper.doUpdate(updateEntity.getDsKey(), updateEntity.getSqls());
                resultEntity = ResultEntity.ok(Arrays.asList(ns).toArray());
            } else {
                resultEntity = ResultEntity.fail("请求参数不合法");
            }
        } catch (Exception e) {
            resultEntity = ResultEntity.error(e);
        }

        return resultEntity;
    }

    @Override
    public ResultEntity proc(ProcEntity procEntity) {
        ResultEntity resultEntity = null;

        try {
            List<Object> list = daoHelper.doProc(procEntity.getDsKey(), procEntity.getProc(), procEntity.getObjs(), procEntity.getOutput());

            resultEntity = ResultEntity.ok(list.toArray());
        } catch (Exception e) {
            resultEntity = ResultEntity.error(e);
        }

        return resultEntity;
    }

    @Override
    public ResultEntity download(FileEntity fileEntity) {
        return null;
    }

    @Override
    public ResultEntity upload(FileEntity fileEntity) {
        return null;
    }

    @Override
    public ResultEntity delete(FileEntity fileEntity) {
        return null;
    }


}
