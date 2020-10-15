package com.senla.carservice.controller.config;

import com.senla.carservice.controller.exception.ControllerException;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

public class HttpServletResponseCopier extends HttpServletResponseWrapper {

    private ServletOutputStream outputStream;
    private PrintWriter writer;
    private ServletOutputStreamCopier copier;

    public HttpServletResponseCopier(HttpServletResponse response) {
        super(response);
    }

    @Override
    public ServletOutputStream getOutputStream() {
        if (writer != null) {
            throw new ControllerException("Response error");
        }

        if (outputStream == null) {
            try {
                outputStream = getResponse().getOutputStream();
                copier = new ServletOutputStreamCopier(outputStream);
            } catch (IOException e) {
                throw new ControllerException("Response error");
            }
        }
        return copier;
    }

    @Override
    public PrintWriter getWriter() {
        if (outputStream != null) {
            throw new ControllerException("Response error");
        }
        if (writer == null) {
            try {
                copier = new ServletOutputStreamCopier(getResponse().getOutputStream());
                writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
            } catch (IOException e) {
                throw new ControllerException("Response error");
            }
        }
        return writer;
    }

    @Override
    public void flushBuffer() {
        if (writer != null) {
            writer.flush();
        } else if (outputStream != null) {
            try {
                copier.flush();
            } catch (IOException e) {
                throw new ControllerException("Response error");
            }
        }
    }

    public byte[] getCopy() {
        if (copier != null) {
            return copier.getCopy();
        } else {
            return new byte[0];
        }
    }

}
