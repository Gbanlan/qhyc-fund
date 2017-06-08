package com.qhyc.fund.uitl;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import javax.servlet.http.HttpServletResponse;

public class OutputFileUtils {
	/**
     * 把数据输出到输出流中
     * @param file
     * @param response
     * @throws IOException
     */
    public static void outputFile(File file, HttpServletResponse response) throws IOException {
        OutputStream out=null;
        FileInputStream in=null;
        try {
        byte[] src = new byte[1024];
         out = response.getOutputStream();
         in = new FileInputStream(file);
        int len=0;
        while ((len = in.read(src)) > 0) {
            out.write(src, 0, len);
        }
        out.flush();
        out.close();
        in.close();
        } catch (IOException e) {
            throw new IOException(e);
        }finally{
            if(null!=out){
                out.close();
            }
            if(null!=in){
            	in.close();
            }
        }
    
    }
}
