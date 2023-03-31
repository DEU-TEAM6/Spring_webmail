/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import java.io.IOException;
import java.util.Properties;
import lombok.Getter;
import lombok.Setter;

/**
 * ³¯Â¥:2023.03.31
 * @author Jung-Hyeonsu
 */
@Getter @Setter
public class loadDB {
    private volatile static loadDB dbInstance = null; 
    private String path = "db-connection.properties";
    private String id = "";
    private String pw= "";
    private String url= "";
    private String driver= "";
    
    private loadDB(){ 
        try {
                Properties props = new Properties();
                Properties session = System.getProperties();
                props.load(this.getClass().getClassLoader().getResourceAsStream(path));
                setId(props.getProperty("id"));
                setPw(props.getProperty("pw"));
                setUrl(url = props.getProperty("url"));
                setDriver(props.getProperty("driver"));
            } catch (IOException e){
                System.out.println(e);
            }
    }
    public static synchronized loadDB getInstance() { 
        if (dbInstance == null) {
            synchronized (loadDB.class){
                if(dbInstance == null){
                    dbInstance = new loadDB();
                }
            }    
        }
        return dbInstance;
    }
}
