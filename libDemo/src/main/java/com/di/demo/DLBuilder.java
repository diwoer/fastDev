package com.di.demo;

import java.io.FileOutputStream;
import java.io.IOException;

/**
 * 链式结构调用，控制了调用流程
 */
public class DLBuilder {

    private String url;

    private FileCreator creator;
    private ClientCaller clientCaller;
    private FileCopier copier;

    public DLBuilder(String url) {
        this.url = url;
    }

    /**
     * 本地创建要下载文件
     */
    public DLBuilder createFile() {
        creator = new FileCreator(url);
        return this;
    }

    /**
     * 本地客户端请求文件数据
     */
    public DLBuilder clientCallServer() {
        Preconditions.checkNull(creator, "需要先调用 ----> createFile()");
        clientCaller = new OkHttpClientCaller(url);
        return this;
    }

    /**
     * 开始拷贝数据
     */
    public DLBuilder copyFile(FileCopier.CopyProgressListener listener)  {
        Preconditions.checkNull(clientCaller, "需要先调用 ----> clientCall()");
        copier = new FileCopier(clientCaller.totalLength());
        if(listener != null){
            copier.setCopyProgressListener(listener);
        }
        return this;
    }

    public void build() throws IOException{
        copier.copy(clientCaller.streamResponse(), new FileOutputStream(creator.create()));
    }

    public final FileCopier getFileCopier(){
        return copier;
    }

    public final FileCreator getFileCreator(){
        return creator;
    }
}
