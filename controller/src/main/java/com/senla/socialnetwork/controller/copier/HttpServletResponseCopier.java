package com.senla.socialnetwork.controller.copier;

import com.senla.socialnetwork.controller.exception.ControllerException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;

@Slf4j
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
            throw new ControllerException("PrintWriter is exist");
        }
        if (outputStream == null) {
            try {
                outputStream = getResponse().getOutputStream();
                copier = new ServletOutputStreamCopier(outputStream);
            } catch (IOException exception) {
                log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
                throw new ControllerException("Error getting data from stream");
            }
        }
        return copier;
    }

    @Override
    public PrintWriter getWriter() {
        if (outputStream != null) {
            throw new ControllerException("ServletOutputStream is null");
        }
        if (writer == null) {
            try {
                copier = new ServletOutputStreamCopier(getResponse().getOutputStream());
                writer = new PrintWriter(new OutputStreamWriter(copier, getResponse().getCharacterEncoding()), true);
            } catch (IOException exception) {
                log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
                throw new ControllerException("Error getting data from stream");
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
            } catch (IOException exception) {
                log.error("[{}:{}]", exception.getClass().getSimpleName(), exception.getMessage());
                throw new ControllerException("Error flushing");
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
