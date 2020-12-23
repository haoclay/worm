package com.sgq.tools;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Author : sgq
 * Date : 2020/12/23 20:41
 */
public class FileTool {
    private static List<File> list = new ArrayList();
    public static String getDir(File file){
        String file_name = file.getName();
        String file_dir = file.getPath();
        String[] dir_arr = file_dir.split(file_name);
        String dir = dir_arr[0].trim();
        System.out.println(dir);
        return dir;
    }

    public static List<File> searchFile(List<File> fileList){
              for(File file : fileList){
                  if(!file.isFile()){
                      searchFile(Arrays.asList(file.listFiles()));
                  }else {
                      list.add(file);
                  }
              }

           return list;
    }
    
}
