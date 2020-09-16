package com.eim.service.common.impl;

import com.eim.service.common.FileService;
import com.eim.utils.FtpUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.*;

@Service
public class FileServiceImpl implements FileService {

    @Value("${file.store.type}")
    private String storeType;

    @Value("${file.store.local}")
    private String localPath;

    @Value("${file.store.ftp.host}")
    private String ftpHost;

    @Value("${file.store.ftp.user}")
    private String ftpUser;

    @Value("${file.store.ftp.pwd}")
    private String ftpPwd;

    @Value("${file.store.ftp.path}")
    private String ftpPath;

    @Override
    public void getFile(String path, String fileName, OutputStream output) throws Exception {
        if (storeType.equals("L")) {
            getFileLocal(path, fileName, output);
        } else if (storeType.equals("F")) {
            getFileFtp(path, fileName, output);
        } else {
            throw new Exception("存储类型不正确");
        }
    }

    @Override
    public void saveFile(String path, String fileName, InputStream input) throws Exception {
        if (storeType.equals("L")) {
            saveFileLocal(path, fileName, input);
        } else if (storeType.equals("F")) {
            saveFileFtp(path, fileName, input);
        } else {
            throw new Exception("存储类型不正确");
        }
    }

    @Override
    public void deleteFile(String path, String fileName) throws Exception {
        if (storeType.equals("L")) {
            deleteFileLocal(path, fileName);
        } else if (storeType.equals("F")) {
            deleteFileFtp(path, fileName);
        } else {
            throw new Exception("存储类型不正确");
        }
    }

    /**
     * 获取本地文件
     *
     * @param path
     * @param fileName
     * @param output
     * @throws IOException
     */
    private void getFileLocal(String path, String fileName, OutputStream output) throws IOException {
        String filePath = localPath + File.separator + path + File.separator + fileName;

        InputStream input = new FileInputStream(filePath);

        int n = 0;
        byte[] buffer = new byte[2048];
        while ((n = input.read(buffer)) > -1) {
            output.write(buffer, 0, n);
        }

        output.flush();
        input.close();
    }

    /**
     * 本地存储
     *
     * @param path
     * @param fileName
     * @param input
     * @throws IOException
     */
    private void saveFileLocal(String path, String fileName, InputStream input) throws IOException {
        String destPath = localPath + File.separator + path;

        File f = new File(destPath);

        if (!f.exists()) f.mkdirs();

        OutputStream output = new FileOutputStream(destPath + File.separator + fileName);

        int n = 0;
        byte[] buffer = new byte[2048];
        while ((n = input.read(buffer)) > -1) {
            output.write(buffer, 0, n);
        }
        output.close();
    }

    /**
     * 删除本地文件
     *
     * @param path
     * @param fileName
     * @throws IOException
     */
    private void deleteFileLocal(String path, String fileName) throws IOException {
        String filePath = localPath + File.separator + path + File.separator + fileName;
        new File(filePath).delete();
    }


    /**
     * 获取Ftp文件
     *
     * @param path
     * @param fileName
     * @param output
     * @throws Exception
     */
    private void getFileFtp(String path, String fileName, OutputStream output) throws Exception {
        FtpUtil.download(ftpHost, ftpPath + path, fileName, ftpUser, ftpPwd, output);
    }

    /**
     * Ftp存储
     *
     * @param path
     * @param fileName
     * @param input
     * @throws Exception
     */
    private void saveFileFtp(String path, String fileName, InputStream input) throws Exception {
        FtpUtil.upload(ftpHost, ftpPath + path, input, fileName, ftpUser, ftpPwd);
    }

    /**
     * 删除Ftp文件
     *
     * @param path
     * @param fileName
     * @throws Exception
     */
    private void deleteFileFtp(String path, String fileName) throws Exception {
        FtpUtil.delete(ftpHost, ftpPath + path, fileName, ftpUser, ftpPwd);
    }
}
