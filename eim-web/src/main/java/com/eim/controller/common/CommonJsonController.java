package com.eim.controller.common;

import com.eim.annotation.SecurityParameter;
import com.eim.controller.AbstractController;
import com.eim.domain.common.*;
import com.eim.service.common.impl.CommonServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/common/json/v1")
public class CommonJsonController extends AbstractController {

    @Autowired
    private CommonServiceImpl commonService;


    @SecurityParameter( outEncode = false)
    @RequestMapping("/query")
    public ResultEntity query(@RequestBody QueryEntity queryEntity) throws JsonProcessingException {

//        ObjectMapper objectMapper = new ObjectMapper();
//        QueryEntity queryEntity = objectMapper.readValue(json, QueryEntity.class);
        ResultEntity resultEntity = null;

        try {
            resultEntity = commonService.query(queryEntity);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.fail(e);
        }

        return resultEntity;
    }

    @SecurityParameter( outEncode = false)
    @RequestMapping("/update")
    public ResultEntity update(@RequestBody UpdateEntity updateEntity) {

        ResultEntity resultEntity = null;

        try {
            resultEntity = commonService.update(updateEntity);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.fail(e);
        }

        return resultEntity;
    }

    @SecurityParameter( outEncode = false)
    @RequestMapping("/proc")
    public ResultEntity proc(@RequestBody ProcEntity procEntity) {
        ResultEntity resultEntity = null;

        try {
            resultEntity = commonService.proc(procEntity);

        } catch (Exception e) {
            e.printStackTrace();

            resultEntity = ResultEntity.fail(e);
        }

       return resultEntity;
    }

    @RequestMapping("/upload")
    public ResultEntity upload(@RequestParam("file") MultipartFile file, @RequestParam("Key") String key, @RequestParam("Type") String type,
                       @RequestParam("Main") String main, @RequestParam("DsKey") String dsKey,
                       @RequestParam("Company") String company) {
        ResultEntity resultEntity = null;

        try {
            FileEntity fileEntity = new FileEntity();
            fileEntity.setMain(main);
            fileEntity.setType(type);
            fileEntity.setKey(key);
            fileEntity.setDsKey(dsKey);
            fileEntity.setCompany(company);

            resultEntity = commonService.upload(fileEntity, file);

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.fail(e);
        }

       return resultEntity;
    }

    @RequestMapping("/download")
    public void download(HttpServletRequest request, HttpServletResponse response) {

        try {
            FileEntity fileEntity = getRequestEntityByObj(request, FileEntity.class);
            ResultEntity resultEntity = commonService.download(fileEntity,response.getOutputStream());

            response.setHeader("code",resultEntity.getCode());
            response.setHeader("desc",new String(resultEntity.getDesc().getBytes(),"ISO-8859-1"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/delete")
    public ResultEntity delete(FileEntity fileEntity) {
        ResultEntity resultEntity = null;

        try {
            resultEntity = commonService.delete(fileEntity);

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.fail(e);
        }

        return resultEntity;
    }
}
