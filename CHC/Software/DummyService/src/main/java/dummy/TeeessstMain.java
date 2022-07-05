/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dummy;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author lexi
 */
public class TeeessstMain {
    public static void main(String[] args){
        String inputFilePath = "/home/lexi/Desktop/Projects/AJAN_w_MAJAN/executionservice/majanService/JarInput.txt";
        String outputFilePath = "/home/lexi/Desktop/Projects/AJAN_w_MAJAN/executionservice/majanService/JarOutput.txt";        
        try {
            createFileIfNotExists(inputFilePath);
            createFileIfNotExists(outputFilePath);
                  } catch (IOException ex) {
            Logger.getLogger(TeeessstMain.class.getName()).log(Level.SEVERE, null, ex);
        }
            
            try {
            
            String res = runJar("/home/lexi/Desktop/Projects/MAJAN-Files/BOSS.jar",
            "/home/lexi/Desktop/Projects/AJAN_w_MAJAN/executionservice/majanService/JarInput.txt",
            "/home/lexi/Desktop/Projects/AJAN_w_MAJAN/executionservice/majanService/JarOutput.txt", 5);
            String res2 = runJar("/home/lexi/Desktop/Projects/MAJAN-Files/CHC_HDBSCAN_Central.jar",
            "/home/lexi/Desktop/Projects/AJAN_w_MAJAN/executionservice/majanService/JarInput.txt",
            "/home/lexi/Desktop/Projects/AJAN_w_MAJAN/executionservice/majanService/JarOutput.txt",5);
            System.out.println(res);
            } catch (InterruptedException ex) {
            Logger.getLogger(TeeessstMain.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
            Logger.getLogger(TeeessstMain.class.getName()).log(Level.SEVERE, null, ex);
            }
  
        
    }
        private static File createFileIfNotExists(String path) throws IOException{
        File file = new File(path);
        
        //if(!file.isFile()){
            file.getParentFile().mkdirs();
            file.createNewFile();
        //}
        return file;
    }
    private static String runJar(String jarPath, String inputPath, String outputPath, int timeout) throws InterruptedException, IOException{
        //String command = "java -jar \"" + jarPath + "\" \"" + inputPath + "\" \"" + outputPath + "\"";
                   Process pr = new ProcessBuilder("java","-jar",jarPath, inputPath, outputPath).start();

        //String command = "java -jar " + jarPath + " " + inputPath + " " + outputPath;
        String result = "";
       // Process pr2 = Runtime.getRuntime().exec(command);
        if(pr.waitFor(timeout, TimeUnit.SECONDS)){
            InputStream in = pr.getInputStream();
            StringBuilder output = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader(in, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    output.append((char) c);
                }
            }
            result = output.toString();
            
            InputStream errorStream = pr.getErrorStream();
            StringBuilder error = new StringBuilder();
            try (Reader reader = new BufferedReader(new InputStreamReader(errorStream, Charset.forName(StandardCharsets.UTF_8.name())))) {
                int c = 0;
                while ((c = reader.read()) != -1) {
                    error.append((char) c);
                }
            }
            result += error.toString();
        }else{
            result = "Algorithm didn't finish execution before timeout!";
        }
        return result;
    }
}
