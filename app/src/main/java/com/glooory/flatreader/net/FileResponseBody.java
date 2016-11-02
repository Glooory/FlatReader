package com.glooory.flatreader.net;

import com.glooory.flatreader.entity.FileLoadingBean;
import com.glooory.flatreader.rx.RxBus;

import java.io.IOException;

import okhttp3.MediaType;
import okhttp3.Response;
import okhttp3.ResponseBody;
import okio.Buffer;
import okio.BufferedSource;
import okio.ForwardingSource;
import okio.Okio;

/**
 * Created by Glooory on 2016/11/1 0001 13:10.
 */

public class FileResponseBody extends ResponseBody {
    Response originalResponse;

    public FileResponseBody(Response originalResponse) {
        this.originalResponse = originalResponse;
    }

    @Override
    public MediaType contentType() {
        return originalResponse.body().contentType();
    }

    @Override
    public long contentLength() {
        return originalResponse.body().contentLength(); //返回文件的总长度，也就是进度条的max
    }

    @Override
    public BufferedSource source() {
        return Okio.buffer(new ForwardingSource(originalResponse.body().source()) {
            long bytesReaded = 0;

            @Override
            public long read(Buffer sink, long byteCount) throws IOException {
                long bytesRead = super.read(sink, byteCount);
                bytesReaded += bytesRead == -1 ? 0 : bytesRead;
                //通过 RxBus 发布进度信息
                RxBus.getDefault().post(new FileLoadingBean(contentLength(), bytesReaded));
                return bytesRead;
            }
        });
    }
}
