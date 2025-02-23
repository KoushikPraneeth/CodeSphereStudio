package com.codesphere.service;

import com.github.dockerjava.api.async.ResultCallback;
import com.github.dockerjava.api.model.Frame;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class OutputStreamLoggingCallback extends ResultCallback.Adapter<Frame> {
    private final ByteArrayOutputStream stdout;
    private final ByteArrayOutputStream stderr;

    public OutputStreamLoggingCallback(ByteArrayOutputStream stdout, ByteArrayOutputStream stderr) {
        this.stdout = stdout;
        this.stderr = stderr;
    }

    @Override
    public void onNext(Frame frame) {
        try {
            switch (frame.getStreamType()) {
                case STDOUT:
                    stdout.write(frame.getPayload());
                    break;
                case STDERR:
                    stderr.write(frame.getPayload());
                    break;
                default:
                    break;
            }
        } catch (IOException e) {
            onError(e);
        }
    }
}