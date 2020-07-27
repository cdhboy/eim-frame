package com.eim.controller.common;

import com.eim.controller.AbstractController;
import com.eim.domain.common.ResultEntity;
import com.eim.domain.common.QueryEntity;
import com.eim.domain.common.UpdateEntity;
import com.eim.service.common.impl.CommonServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

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

            resultEntity = ResultEntity.error(e);
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

            resultEntity = ResultEntity.error(e);
        }

        try {
            sendResultEntityByObj(response, resultEntity);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
