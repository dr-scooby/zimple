package com.jah.Zimple;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Scanner;

@RestController
public class Controller {

    @Autowired
    private MyConfigs myConfigs;

    @Autowired
    private DBModel dbmodel;


    private String filepath ="/data/zimple/shopping.txt";


    @GetMapping("/greeting")
    public String getGreeting(){
        return "hi jah";
    }

    @GetMapping("/config")
    public String getMyConfigs(){
        return myConfigs.getConfigValue();
    }

    @GetMapping("/message")
    public String getMessage(){
        return myConfigs.getMessage();
    }


    @GetMapping("/getAll")
    public String getAllConfigs(){
        return myConfigs.getAll();
    }

    @GetMapping("/getpath")
    public String getPaht(){
        return myConfigs.getPath();
    }

    @GetMapping("/db")
    public String db(){

        dbmodel.connecttoKube();
        String data = dbmodel.selectTable();

        return data;
    }

    @GetMapping("/table")
    public String table(){
        // check if table exists
        dbmodel.connecttoKube();
        try {
            if(dbmodel.tableExists("alien")){
                return "Table alien exists";
            }else
                return "Table alien doesn't exist";
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping("/tablename/{name}")
    public String getTableExists(@PathVariable String name){

        System.out.println("Table name:> "  + name);
        dbmodel.connecttoKube();
        try {
            if(dbmodel.tableExists(name))
                return "Table exists " + name;
            else
                return "Table Doesn't exist";
        }catch(SQLException e){
            throw new RuntimeException(e);
        }
    }

    // end point localhost:30000/saveString
    /*
    curl -H "Accept: application/json" -H "Content-Type: application/json" -X POST --data '{"data": "savedSnake"}' localhost:30000/saveString
     */
    @PostMapping("/saveString")
    public String saveString(@RequestBody Data somedata){
        Data datas = somedata;
        System.out.println("\nGot some data, write to file:> " + datas.getData());
        boolean ok = false;
        try {
            File f = new File(myConfigs.getPath());
            System.out.println("f: " + f.getAbsolutePath());
            if(f.isDirectory()){
                System.out.println("we got only directory " + f.getAbsolutePath());
                File datafile = new File(f.getAbsolutePath()+"/datafile.txt");
                datafile.createNewFile();
                FileWriter fw = new FileWriter(datafile);
                fw.write("\nNew Data: " + datas.getData());
                fw.flush();
                fw.close();
                System.out.println("Successfully wrote to file. the file:\n");
                ok = true;
            }else if(f.isFile()){
            if(f.exists()){
                if(f.canWrite()){
                    FileWriter fw = new FileWriter(f);
                    fw.write("\nNew Data: " + datas.getData() + "\n");
                    fw.flush();
                    fw.close();
                    System.out.println("Successfully wrote to file. the file:\n");
                    ok = true;
                    try {
                        Scanner myscan = new Scanner(f);
                        while(myscan.hasNextLine()){
                            String data = myscan.nextLine();
                            System.out.println(data);

                        }
                        myscan.close();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    }
                }
            }}else{
                System.out.println("File doesn't exist, creating...");
                File datafile = new File(f.getAbsolutePath());
                datafile.createNewFile();
                if(datafile.isFile() && datafile.exists() && datafile.canWrite()){
                   FileWriter fw = new FileWriter(datafile);
                   fw.write("\nNew Data: " + datas.getData() + "\n");
                   fw.flush();
                   fw.close();
                   System.out.println("Successfully wrote to file.\n");
                   ok = true;
               }else{
                   System.out.println("failed to create, read  file");
               }

            }


        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        if(ok)
            return "data saved to file";
        else return "not saved";

    }

    @GetMapping("/data")
    public String getData(){

        String somes = "";

        File datafile = new File(myConfigs.getPath() );
        if(datafile.exists())
            somes = "file exists";
        else
            somes = "File Doesn't Exist";

        if(datafile.canRead()) {
            somes += " , can read file.\n";
            try {
                Scanner myscan = new Scanner(datafile);
                while(myscan.hasNextLine()){
                    String data = myscan.nextLine();
                    System.out.println(data);
                    somes += data + "\n";
                }
                myscan.close();
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else
            somes += " , CAN'T Read File";


        return somes;
    }

}
