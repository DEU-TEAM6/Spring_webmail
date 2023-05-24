/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package deu.cse.spring_webmail.model;

import java.util.Properties;
import java.util.concurrent.atomic.AtomicReference;
import lombok.Getter;
import lombok.Setter;

/**
 * 2023.03.31
 * @author Jung-Hyeonsu
 */
@Getter @Setter
public class loadDB {
    private static final AtomicReference<loadDB> INSTANCE = new AtomicReference<>();
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
            } catch (Exception e){
                e.getMessage();
            }
    }
    public static loadDB getInstance() {
        if (INSTANCE.get() == null) {
            synchronized (loadDB.class) {
                if (INSTANCE.get() == null) {
                    INSTANCE.set(new loadDB());
                }
            }
        }
        return INSTANCE.get();
    }
}
