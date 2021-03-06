package com.senla.logger.copier;

import com.senla.logger.exception.ControllerException;

import javax.servlet.ServletOutputStream;
import javax.servlet.WriteListener;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class ServletOutputStreamCopier extends ServletOutputStream {
    private static final Integer BUFFER_SIZE = 1024;
    private final OutputStream outputStream;
    private final ByteArrayOutputStream copy;
    private WriteListener listener;

    public ServletOutputStreamCopier(OutputStream outputStreamObject) {
        outputStream = outputStreamObject;
        copy = new ByteArrayOutputStream(BUFFER_SIZE);
    }

    @Override
    public void write(int inputByte) {
        try {
            outputStream.write(inputByte);
            copy.write(inputByte);
        } catch (IOException e) {
            throw new ControllerException("Byte write error");
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
            throw new ControllerException("Input WriteListener is null");
        }
        if (listener != null) {
            throw new ControllerException("WriteListener is exist");
        }
        listener = writeListener;
    }

}
