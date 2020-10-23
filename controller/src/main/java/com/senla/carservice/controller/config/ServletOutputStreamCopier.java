package com.senla.carservice.controller.config;

import com.senla.carservice.controller.exception.ControllerException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ServletOutputStreamCopier extends ServletOutputStream {

    private final OutputStream outputStream;
    private final ByteArrayOutputStream copy;
    private WriteListener listener;

    public ServletOutputStreamCopier(OutputStream outputStream) {
        this.outputStream = outputStream;
        this.copy = new ByteArrayOutputStream(1024);
    }

    @Override
    public void write(int b) {
        try {
            outputStream.write(b);
            copy.write(b);
        } catch (IOException e) {
            throw new ControllerException("Response error");
        }
    }

    public byte[] getCopy() {
        return copy.toByteArray();
    }

    @Override
    public boolean isReady() {
        return false;
    }

    @Override
    public void setWriteListener(final WriteListener writeListener) {
        if (writeListener == null) {
            throw new ControllerException("Response error");
        }
        if (listener != null) {
            throw new ControllerException("Response error");
        }
        listener = writeListener;
    }

}
