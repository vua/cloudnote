package com.cooooode.service;

import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

/**
 * @Author: vua(杨晓迪)
 * @Date: Created on 2019-09-23
 * @Description:
 **/

@Service
public class SQLDumpService {

    public StringBuffer getCommand(String username,
                                  String password,
                                  String host,
                                  String port,
                                  String exportDatabaseName,
                                  String exportPath){
        StringBuffer command=new StringBuffer();
        command.append("mysqldump -u").append(username).append(" -p").append(password)
                .append(" -h").append(host).append(" -P").append(port)
                .append(" ").append(exportDatabaseName).append(" -r ").append(exportPath);
        return command;
    }
    private  String command="cmd.exe /c mysqldump -uroot -p841103 cloudnote > ";
    public String dump(String path){
        File file=new File(path+"WEB-INF/sql");
        if(!file.exists()) file.mkdirs();
        String file_path=path+"WEB-INF/sql/cloudnote.sql";
        String newCommand=command+file_path;

        Runtime runtime = Runtime.getRuntime();

        try {
            Process exec = runtime.exec(newCommand);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        return file_path;
    }

    public void download(){

    }
    public void sendToEmail(){

    }


}
