package com.senla.carservice.controller.config;

import com.senla.carservice.controller.exception.ControllerException;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

public class CachedBodyServletInputStream extends ServletInputStream {

    private final InputStream cachedBodyInputStream;

    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public int read() {
        try {
            return cachedBodyInputStream.read();
        } catch (IOException exception) {
            throw new ControllerException("Request error");
        }
    }

    @Override
    public boolean isFinished() {
        try {
            return cachedBodyInputStream.available() == 0;
        } catch (IOException exception) {
            throw new ControllerException("Request error");
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(final ReadListener readListener) {
    }

}
