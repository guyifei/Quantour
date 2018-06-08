package anyeight.serviceImpl;

import anyeight.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by T5-SK on 2017/6/10.
 */
public class NameList {
    public static Map<String,String> map = new HashMap();
    public static Map<String,String> mapChange = new HashMap();
    private static NameList nameList = null;

    @Autowired
    StockService stockService;

    private NameList(){}

    public static NameList getNameList(){
        if(nameList == null){
            nameList = new NameList();
        }

//        String ROOT_FILE_PATH = new File(ClassLoader.getSystemClassLoader().getResource(".").getPath()).getParentFile().getAbsolutePath();
        String path=NameList.class.getClassLoader().getResource(".")+"NameCodeMap.txt";
        path=path.substring(6);
        File file = new File(path);

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader(new FileInputStream(file),"UTF-8"));
            String line = "";
            while((line = br.readLine()) != null){
                String name = line.split("_")[0];
                String code = line.split("_")[1];
                map.put(name,code);
                mapChange.put(code,name);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }


        return nameList;
    }

    public Map<String,String> getMap(){
        return map;
    }

    public Map<String,String> getMapChange(){
        return mapChange;
    }

}
