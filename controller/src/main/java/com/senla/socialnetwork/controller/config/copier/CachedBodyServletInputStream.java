package com.senla.socialnetwork.controller.config.copier;

import com.senla.socialnetwork.controller.exception.ControllerException;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;

@Slf4j
public class CachedBodyServletInputStream extends ServletInputStream {

    private final InputStream cachedBodyInputStream;
    private ReadListener listener;

    public CachedBodyServletInputStream(byte[] cachedBody) {
        this.cachedBodyInputStream = new ByteArrayInputStream(cachedBody);
    }

    @Override
    public int read() {
        try {
            return cachedBodyInputStream.read();
        } catch (IOException exception) {
            log.error("[{}]", exception.getMessage());
            throw new ControllerException("Error reading data from InputStream");
        }
    }

    @Override
    public boolean isFinished() {
        try {
            return cachedBodyInputStream.available() == 0;
        } catch (IOException exception) {
            log.error("[{}]", exception.getMessage());
            throw new ControllerException("Error getting the number of bytes of input");
        }
    }

    @Override
    public boolean isReady() {
        return true;
    }

    @Override
    public void setReadListener(final ReadListener readListener) {
        log.debug("[setReadListener]");
        if (readListener == null) {
            throw new ControllerException("Input ReadListener is null");
        }
        if (listener != null) {
            throw new ControllerException("ReadListener is exist");
        }
        listener = readListener;
    }

}
