package com.eim.controller.common;

import com.eim.controller.AbstractController;
import com.eim.domain.common.*;
import com.eim.service.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Controller
@RequestMapping("/common/ent/v1")
public class CommonObjectController extends AbstractController {

    @Autowired
    private CommonServiceImpl commonService;

    @RequestMapping("/query")
    public void query(HttpServletRequest request, HttpServletResponse response) {

        ResultEntity resultEntity = null;

        try {
            QueryEntity queryEntity = getRequestEntityByObj(request, QueryEntity.class);

            resultEntity = commonService.query(queryEntity);

        } catch (Exception e) {
            e.printStackTrace();

            resultEntity = ResultEntity.fail(e);
        }

        try {
            sendResultEntityByObj(response, resultEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/update")
    public void update(HttpServletRequest request, HttpServletResponse response) {

        ResultEntity resultEntity = null;

        try {
            UpdateEntity updateEntity = getRequestEntityByObj(request, UpdateEntity.class);

            resultEntity = commonService.update(updateEntity);

        } catch (Exception e) {
            e.printStackTrace();

            resultEntity = ResultEntity.fail(e);
        }

        try {
            sendResultEntityByObj(response, resultEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/proc")
    public void proc(HttpServletRequest request, HttpServletResponse response) {
        ResultEntity resultEntity = null;

        try {
            ProcEntity procEntity = getRequestEntityByObj(request, ProcEntity.class);

            resultEntity = commonService.proc(procEntity);

        } catch (Exception e) {
            e.printStackTrace();

            resultEntity = ResultEntity.fail(e);
        }

        try {
            sendResultEntityByObj(response, resultEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @RequestMapping("/upload")
    public void upload(@RequestParam("file") MultipartFile file, @RequestParam("Key") String key, @RequestParam("Type") String type,
                       @RequestParam("Main") String main, @RequestParam("DsKey") String dsKey,
                       @RequestParam("Company") String company, HttpServletRequest request, HttpServletResponse response) {
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

        try {
            sendResultEntityByObj(response, resultEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    public void delete(HttpServletRequest request, HttpServletResponse response) {
        ResultEntity resultEntity = null;

        try {
            FileEntity fileEntity = getRequestEntityByObj(request, FileEntity.class);
            resultEntity = commonService.delete(fileEntity);

        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.fail(e);
        }

        try {
            sendResultEntityByObj(response, resultEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
