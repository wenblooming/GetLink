package com.bgm.getlink.utils;

import android.text.TextUtils;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Administrator on 2016/11/9.
 */
public class Utilities {
    public static String AUTOOP = "autoTask";


    public static String opCmd = "uiautomator runtest stars.jar -c com.bgm.stars.StarMobile#operate -e cmd ";
    public static String packagePath = "http://www.hongdaoit.com/jar/stars.jar";
    public static String baseUrl = "http://www.hongdaoit.com";

    public static String basepath = "/sdcard/starcloud/";
    public static String apkPath = basepath + "packages/";

    public static void initDir() {
        createDir(basepath);
        createDir(apkPath);//所有的下载的apk
    }

    /**
     * 复制单个文件
     *
     * @param oldPath String 原文件路径 如：c:/fqf.txt
     * @param newPath String 复制后路径 如：f:/fqf.txt
     * @return boolean
     */
    public static void copyFile(String oldPath, String newPath) {
        try {
            int bytesum = 0;
            int byteread = 0;
            File oldfile = new File(oldPath);
            if (oldfile.exists()) { //文件存在时
                InputStream inStream = new FileInputStream(oldPath); //读入原文件
                FileOutputStream fs = new FileOutputStream(newPath);
                byte[] buffer = new byte[1444];
                int length;
                while ((byteread = inStream.read(buffer)) != -1) {
                    bytesum += byteread; //字节数 文件大小
                    System.out.println(bytesum);
                    fs.write(buffer, 0, byteread);
                }
                inStream.close();
            }
        } catch (Exception e) {
            System.out.println("复制单个文件操作出错");
            e.printStackTrace();

        }

    }

    public static void writeListFile(List<String> contents, String path) {
        try {
            File f = new File(path);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            if (contents.size() == 0) {
                output.write("");
            }
            for (int i = 0; i < contents.size(); i++) {
                String content = contents.get(i);
                output.write(content + "\r");
            }

            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void deleteFile(String filePath) {
        File f = new File(filePath);
        if (f.exists()) { // 判断是否存在
            //deleteAllFiles(f);
            try {
                f.delete();
            } catch (Exception e) {
            }
        }
    }

    public static void deleteInitFile() {
        deleteFile(basepath + "zhucema/AutoRunner.jaryunk.jar");
        deleteFile("data/local/tmp/yunk.jar");
        /*deleteFile(basepath+"txt/backup.txt");
        deleteFile(basepath+"txt/recovery.txt");
        deleteFile(basepath+"txt/baselocation.txt");
        deleteFile(basepath+"zhucema/TitaniumBackup.apk");*/
    }

    public static void deleteAllFiles(String filePath) {
        File root = new File(filePath);
        File files[] = root.listFiles();
        if (files != null)
            for (File f : files) {
                if (f.exists()) { // 判断是否存在
                    try {
                        f.delete();
                    } catch (Exception e) {
                    }
                }
            }
    }

    public static void createDir(String path) {
        File file = new File(path);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("//不存在");
            file.mkdir();
        } else {
            System.out.println("//目录存在");
        }
    }

    public static void createFile(String path) throws IOException {
        File file = new File(path);
        //如果文件夹不存在则创建
        if (!file.exists() && !file.isDirectory()) {
            System.out.println("//不存在");
            file.createNewFile();// 不存在则创建
        } else {
            System.out.println("//文件存在");
        }
    }

    /**
     * 功能：Java读取txt文件的内容
     * 步骤：1：先获得文件句柄
     * 2：获得文件句柄当做是输入一个字节码流，需要对这个输入流进行读取
     * 3：读取到输入流后，需要读取生成字节流
     * 4：一行一行的输出。readline()。
     * 备注：需要考虑的是异常情况
     *
     * @param filePath
     */
    public static ArrayList readTxtFile(String filePath) {
        ArrayList txtList = new ArrayList();
        try {
            String encoding = "UTF-8";
            //String encoding=getCharset(filePath);
            File file = new File(filePath);
            if (file.isFile() && file.exists()) { //判断文件是否存在
                InputStreamReader read = new InputStreamReader(
                        new FileInputStream(file), encoding);//考虑到编码格式
                BufferedReader bufferedReader = new BufferedReader(read);
                String lineTxt = null;
                while ((lineTxt = bufferedReader.readLine()) != null) {
                    txtList.add(lineTxt);
                    //System.out.println(lineTxt);
                }
                read.close();
            } else {
                System.out.println("找不到指定的文件");
            }
        } catch (Exception e) {
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
        return txtList;
    }

    public static void mobileRegWrite(String txtfile, String flag) {
        String filePath = basepath + "zhucema/" + txtfile + ".txt";
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(flag);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static String mobileRegRead(String filename) {
        String filePath = basepath + "zhucema/" + filename + ".txt";
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));
            String str = input.readLine();
            /*while ((str = input.readLine()) != null) {
                s1 += str + "\n";
            }
            System.out.println(s1);*/
            input.close();
            if (str == null)
                return "";
            if (str != "")
                return str;
            else
                return "";
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    public static void mobileFlagWrite(String txtfile, String flag) {
        String filePath = basepath + "mobile/" + txtfile + ".txt";
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(flag);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void contentToTxt(String filePath, String content) {
        Log.d("wbin", "write file: " + filePath);
        try {
            File f = new File(filePath);
            if (f.exists()) {
                Log.d("wbin","文件存在");
            } else {
                Log.d("wbin","文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(content);
            output.close();
            Log.d("wbin","write file success!");
        } catch (Exception e) {
            Log.e("wbin","write file exception! " + Log.getStackTraceString(new Throwable(e)));
        }
    }

    public static void writeListTxt(String filePath, List<String> contents) {
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            for (int i = 0; i < contents.size(); i++) {
                String content = contents.get(i);
                output.write(content + "\r");
            }
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void stopFlagWrite(String flag) {
        String filePath = basepath + "txt/stop.txt";

        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(flag);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static List<String> readList(String txtPath) {
        String str; //原有txt内容
        List<String> s1 = new ArrayList<String>();//内容更新
        try {
            File f = new File(txtPath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader input = new BufferedReader(new FileReader(f));
            while ((str = input.readLine()) != null) {
                s1.add(str);
            }
            input.close();
            return s1;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String readString(String txtPath) {
        StringBuilder result = new StringBuilder();
        try {
            File f = new File(txtPath);
            if (f.exists()) {
                System.out.print("文件存在");
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            BufferedReader br = new BufferedReader(new FileReader(f));//构造一个BufferedReader类来读取文件
            String s = null;
            while ((s = br.readLine()) != null) {//使用readLine方法，一次读一行
                result.append(System.lineSeparator() + s);
            }
            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result.toString().trim();
    }

    public static void mobileLocationWrite(String location) {
        String filePath = basepath + "txt/location.txt";
        try {
            File f = new File(filePath);
            if (f.exists()) {
                System.out.print("文件存在");
                f.delete();
            } else {
                System.out.print("文件不存在");
                f.createNewFile();// 不存在则创建
            }
            f.createNewFile();// 不存在则创建
            String[] locs = location.split(",");
            BufferedWriter output = new BufferedWriter(new FileWriter(f));
            output.write(locs[0]);
            output.newLine();
            output.write(locs[1]);
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static Map<String, String> object2Map(Object obj) {
        Map<String, String> map = new HashMap<>();
        if (obj == null) {
            return map;
        }
        Class clazz = obj.getClass();
        Field[] fields = clazz.getDeclaredFields();
        try {
            for (Field field : fields) {
                field.setAccessible(true);
                map.put(field.getName(), (String) field.get(obj));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return map;
    }
}
