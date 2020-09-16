package com.eim.service.common.impl;

import com.eim.dao.helper.DaoHelper;
import com.eim.domain.common.*;
import com.eim.oauth.JwtTokenUtil;
import com.eim.service.common.CommonService;
import com.eim.service.common.FileService;
import com.eim.utils.CommonUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class CommonServiceImpl implements CommonService {

    @Autowired
    private DaoHelper daoHelper;

    @Autowired
    private FileService fileService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public ResultEntity query(QueryEntity queryEntity) {

        ResultEntity resultEntity = null;

        try {
            List<Map<String, Object>> list = daoHelper.doQuery(queryEntity.getDsKey(), queryEntity.getSql(), queryEntity.getObjs());

            resultEntity = ResultEntity.ok(list.toArray());
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.fail(e);
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
            resultEntity = ResultEntity.fail(e);
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
            resultEntity = ResultEntity.fail(e);
        }

        return resultEntity;
    }

    @Override
    public ResultEntity download(FileEntity fileEntity, OutputStream os) {
        ResultEntity resultEntity = ResultEntity.ok(new Object[0]);

        try {
            String sql = "select f.file_name, f.file_dir, c.file_path, f.company_id from sys_file f inner join sys_file_config c on f.file_type = c.file_type "
                    + "where f.file_id = " + fileEntity.getId();

            List<Map<String, Object>> list = daoHelper.doQuery(fileEntity.getDsKey(), sql);
            String path = list.get(0).get("file_path").toString();
            String fileName = list.get(0).get("file_name").toString();
            String dir = list.get(0).get("file_dir").toString();
            String company = list.get(0).get("company_id").toString();

            fileService.getFile(company + File.separator + path + File.separator + dir, fileName, os);

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.fail(e);
        }

        return resultEntity;

    }

    @Override
    public ResultEntity upload(FileEntity fileEntity, MultipartFile file) {
        ResultEntity resultEntity = ResultEntity.ok(new Object[]{file.getOriginalFilename()});
        try {
            String sql = "select file_path from sys_file_config where file_type = '" + fileEntity.getType() + "'";

            List<Map<String, Object>> list = daoHelper.doQuery(fileEntity.getDsKey(), sql);
            String path = list.get(0).get("file_path").toString();
            String fileName = CommonUtil.getCurrentTime("yyyyMMddHHmmssSSS") + "-" + file.getOriginalFilename();
            String dir = CommonUtil.getCurrentTime("yyyyMM");

            fileService.saveFile(fileEntity.getCompany() + File.separator + path + File.separator + dir, fileName, file.getInputStream());

            List<String> sqlList = new ArrayList<>();
            if ("1".equals(fileEntity.getMain())) {
                sqlList.add("update sys_file set file_main = 0 where file_type = '" + fileEntity.getType() + "' and file_key = "
                        + fileEntity.getKey() + " and company_id = " + fileEntity.getCompany());
            }

            sql = "insert into sys_file(file_name,file_dir,file_type,file_main,file_key,company_id,add_who,add_time) values ('"
                    + fileName.replace("'", "''") + "','" + dir + "','" + fileEntity.getType() + "'," + fileEntity.getMain() + "," + fileEntity.getKey() + ","
                    + fileEntity.getCompany() + ",'" + jwtTokenUtil.getUser().getUsername() + "',now())";

            sqlList.add(sql);

            daoHelper.doUpdate(fileEntity.getDsKey(), sqlList.toArray(new String[0]));

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.fail(e);
        }

        return resultEntity;
    }

    @Override
    public ResultEntity delete(FileEntity fileEntity) {
        ResultEntity resultEntity = null;
        try {
            String sql = "select f.file_id, f.file_name, f.file_dir, c.file_path from sys_file f inner join sys_file_config c on f.file_type = c.file_type "
                    + "where 1 = 1";
            if (StringUtils.hasText(fileEntity.getId())) {
                sql += " and f.file_id = " + fileEntity.getId();
            } else {
                sql += "f.file_key = " + fileEntity.getKey() + " and f.file_type = '" + fileEntity.getType() + "' and f.company_id = " + fileEntity.getCompany();
            }

            List<Map<String, Object>> list = daoHelper.doQuery(fileEntity.getDsKey(), sql);

            List<Object> idList = new ArrayList<>();
            for (Object obj : list) {

                Map<String, Object> map = (Map<String, Object>) obj;
                String id = list.get(0).get("file_id").toString();
                String path = list.get(0).get("file_path").toString();
                String fileName = list.get(0).get("file_name").toString();
                String dir = list.get(0).get("file_dir").toString();
                String company = list.get(0).get("company_id").toString();

                fileService.deleteFile(company + File.separator + path + File.separator + dir, fileName);
                daoHelper.doUpdate(fileEntity.getDsKey(), "delete from sys_file where file_id =" + id);

                idList.add(id);
            }

            resultEntity = ResultEntity.ok(idList.toArray());
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.fail(e);
        }

        return resultEntity;
    }


}
