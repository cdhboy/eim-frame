package com.eim.service.api.impl;

import com.eim.dao.helper.DaoHelper;
import com.eim.service.api.DeliverApiService;
import com.eim.service.common.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.util.List;
import java.util.Map;

@Service
public class DeliverApiServiceImpl implements DeliverApiService {

    @Autowired
    private DaoHelper daoHelper;

    @Autowired
    private FileService fileService;

    @Override
    public byte[] getBill(String billKey, String dsKey) throws Exception {
        byte[] bs = new byte[0];

        String sql = "select c.file_path, f.file_dir, f.file_name, f.company_id from prd_deliver d left join sys_file f on d.deliver_id = f.file_key and f.file_type = 'DV' left join sys_file_config c on f.file_type = c.file_type where deliver_key = '"
                + billKey + "'";

        List<Map<String, Object>> list = daoHelper.doQuery(dsKey, sql);

        if (list.size() > 0) {
            String path = list.get(0).get("file_path").toString();
            String fileName = list.get(0).get("file_name").toString();
            String dir = list.get(0).get("file_dir").toString();
            String company = list.get(0).get("company_id").toString();

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            fileService.getFile(company + File.separator + path + File.separator + dir, fileName, os);

            bs = os.toByteArray();
        }

        return bs;
    }

    @Override
    public byte[] getBillList(String billKey, String dsKey) throws Exception {

        byte[] bs = new byte[0];

        String sql = "select c.file_path, f.file_dir, f.file_name, f.company_id from sys_file f inner join sys_file_config c on f.file_type = c.file_type where f.file_key = "
                + billKey + " and f.file_type = 'DL' and f.file_main = 1";

        List<Map<String, Object>> list = daoHelper.doQuery(dsKey, sql);

        if (list.size() > 0) {
            String path = list.get(0).get("file_path").toString();
            String fileName = list.get(0).get("file_name").toString();
            String dir = list.get(0).get("file_dir").toString();
            String company = list.get(0).get("company_id").toString();

            // response.setHeader("content-type", "application/octet-stream");
            // response.setContentType("application/octet-stream");
            // // 下载文件能正常显示中文
            // response.setHeader("Content-Disposition",
            // "attachment;filename=" + URLEncoder.encode(fileName.substring(15), "UTF-8"));

//            if (fileName.toLowerCase().endsWith("png"))
//                headers.setContentType(MediaType.IMAGE_PNG);
//            else if (fileName.toLowerCase().endsWith("jpg"))
//                headers.setContentType(MediaType.IMAGE_JPEG);
//            else if (fileName.toLowerCase().endsWith("gif"))
//                headers.setContentType(MediaType.IMAGE_GIF);
//            else
//                headers.setContentType(MediaType.IMAGE_JPEG);

            ByteArrayOutputStream os = new ByteArrayOutputStream();

            fileService.getFile(company + File.separator + path + File.separator + dir, fileName, os);

            bs = os.toByteArray();
            //return new ResponseEntity<>(baos.toByteArray(), headers, HttpStatus.OK);

           // return new ResponseEntity<>("无法获取资源".getBytes(), headers, HttpStatus.NOT_FOUND);
        }

        return bs;
    }
}
