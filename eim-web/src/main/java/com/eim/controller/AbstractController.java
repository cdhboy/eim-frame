package com.eim.controller;

import com.eim.domain.common.ResultEntity;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
public abstract class AbstractController {

	protected<T> T getRequestEntityByObj(HttpServletRequest request, Class<T> clazz) throws IOException, ClassNotFoundException {
        ObjectInputStream input = new ObjectInputStream(request.getInputStream());
        T requestntity = (T)input.readObject();
        return requestntity;
    }

    protected void sendResultEntityByObj(HttpServletResponse response, ResultEntity resultEntity) throws IOException {
        ObjectOutputStream output = new ObjectOutputStream(response.getOutputStream());
        output.writeObject(resultEntity);
        output.flush();
    }
}
