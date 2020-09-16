package com.eim.service.common;

import java.io.InputStream;
import java.io.OutputStream;

public interface FileService {

    public void getFile(String path, String fileName, OutputStream output) throws Exception;

    public void saveFile(String path, String fileName, InputStream input) throws Exception;

    public void deleteFile(String path, String fileName) throws Exception;
}
