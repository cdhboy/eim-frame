package com.eim.controller.api;

import com.eim.service.api.DeliverApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

@Controller
@RequestMapping("/api/deliver")
public class DeliverControllerApi {
    @Autowired
    private DeliverApiService deliverApiService;

    @RequestMapping("/bill")
    public ModelAndView getBill(@RequestParam String key1, @RequestParam String key2)
            throws Exception {

        ModelAndView modelAndView = new ModelAndView("testView");
        modelAndView.addObject("key1", key1);
        modelAndView.addObject("key2", key2);

        return modelAndView;
    }

    @RequestMapping("/pdf")
    public ResponseEntity<byte[]> getBillPdf(@RequestParam String key1, @RequestParam String key2, HttpServletResponse response)
            throws Exception {

        byte[] bytes = deliverApiService.getBill(key1, key2);


//        if (bytes.length > 0) {
//            response.setHeader("content-type", "application/octet-stream");
//            response.setContentType("application/octet-stream");
//            response.setHeader("Content-Disposition",
//                    "attachment;filename=" + URLEncoder.encode(key1 + ".pdf", "UTF-8"));
//            response.getOutputStream().write(bytes);
//        } else {
//            response.setHeader("content-type", "text/plain");
//            response.setCharacterEncoding("utf-8");
//            response.getWriter().write("无法获取资源");
//        }
        HttpHeaders headers = new HttpHeaders();

        if (bytes.length > 0) {
            headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
            headers.setContentDispositionFormData("attachment", URLEncoder.encode(key1 + ".pdf", "UTF-8"));
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("无法获取资源".getBytes(), headers, HttpStatus.NOT_FOUND);
        }
    }

    @RequestMapping("/list")
    public ResponseEntity<byte[]> getList(@RequestParam String key1, @RequestParam String key2,
                                          HttpServletResponse response) throws Exception {
        byte[] bytes = deliverApiService.getBillList(key1, key2);

        HttpHeaders headers = new HttpHeaders();

        if (bytes.length > 0) {
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        } else {
            return new ResponseEntity<>("无法获取资源".getBytes(), headers, HttpStatus.NOT_FOUND);
        }
    }
}
