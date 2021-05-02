package com.micro.seller.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.List;

public class TXTUtils {
    public static void writeToTxt(List list, String path) {
        FileOutputStream outSTr = null;
        BufferedOutputStream Buff = null;
        String path1 = path;
        String tab = "  ";
        String enter = "\r\n";
        StringBuffer write ;
        try {
            outSTr = new FileOutputStream(new File(path));
            Buff = new BufferedOutputStream(outSTr);
            for (int i = 0; i < list.size(); i++) {
                write = new StringBuffer();
                write.append(list.get(i));
                write.append(enter);
                Buff.write(write.toString().getBytes("UTF-8"));
            }
            Buff.flush();
            Buff.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                Buff.close();
                outSTr.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
