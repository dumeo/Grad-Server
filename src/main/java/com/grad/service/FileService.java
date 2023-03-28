package com.grad.service;

import com.grad.util.DefaultVals;
import org.csource.common.MyException;
import org.csource.fastdfs.ClientGlobal;
import org.csource.fastdfs.StorageClient;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class FileService {
    public StorageClient client;
    public FileService() throws MyException, IOException {
        ClientGlobal.initByProperties("src/main/resources/fastdfs-client.properties");
        client = new StorageClient();
    }

    public String getFileAddr(String[] s){
        if(s == null || s.length == 0) return null;
        String res = "";
        for(int i = 1; i < s.length; i ++)
            res += s[i];
        return DefaultVals.FILE_SERVER_URL + s[0] + "/" + res;
    }
}
