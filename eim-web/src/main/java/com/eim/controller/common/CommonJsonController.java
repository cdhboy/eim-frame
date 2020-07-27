package com.eim.controller.common;

import com.eim.annotation.SecurityParameter;
import com.eim.controller.AbstractController;
import com.eim.domain.common.QueryEntity;
import com.eim.domain.common.ResultEntity;
import com.eim.domain.common.UpdateEntity;
import com.eim.service.common.impl.CommonServiceImpl;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/common/json/v1")
public class CommonJsonController extends AbstractController {

    @Autowired
    private CommonServiceImpl commonService;

    @SecurityParameter( outEncode = false)
    @RequestMapping("/query")
    public ResultEntity query(@RequestBody  QueryEntity queryEntity) throws JsonProcessingException {

//        ObjectMapper objectMapper = new ObjectMapper();
//        QueryEntity queryEntity = objectMapper.readValue(json, QueryEntity.class);
        ResultEntity resultEntity = null;

        try {
            resultEntity = commonService.query(queryEntity);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.error(e);
        }

        return resultEntity;
    }


    @SecurityParameter(outEncode = false)
    @RequestMapping("/update")
    public ResultEntity update(@RequestBody UpdateEntity updateEntity) {

        ResultEntity resultEntity = null;

        try {
            resultEntity = commonService.update(updateEntity);
        } catch (Exception e) {
            e.printStackTrace();
            resultEntity = ResultEntity.error(e);
        }

        return resultEntity;
    }
}
