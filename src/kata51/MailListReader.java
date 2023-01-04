
package kata51;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

public class MailListReader{
    String file;
    MailListReader(String file){
        this.file=file;
    }
    private boolean isEmail(String line){
             return EmailPattern.matcher(line).matches();
         }
    private static final Pattern EmailPattern=Pattern.compile(
            "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$");
    
    public List<String> read(){
        List<String> ListaMail=new ArrayList<>();
        Iterable<String> iteradormail = new Iterable<String>(){
            @Override
            public Iterator<String> iterator(){
                return createIterator();
            }
        };
        for (String line: iteradormail) {
            if(isEmail(line)){
                ListaMail.add(line);
            }
            
        }
        return ListaMail;
    }
       
    //crea un iterador que recorra las lineas extraidas por un loader
    private Iterator<String> createIterator(){
        return new Iterator<String>() {
         BufferedReader reader= createReader(file);
         String nextmail=nextmail();
         @Override
         public boolean hasNext(){
          return nextmail!=null;      
         }
         @Override
         public String next(){
          String email=nextmail;
          nextmail=nextmail();
          return email;
         }
         private String nextmail(){
          try{
                  return reader.readLine();
              }
              catch (Exception ex){
                  return null;
              }
         }
         private BufferedReader createReader(String file){
              try{
                  return new BufferedReader(new FileReader(file));
              }
              catch (FileNotFoundException ex){
                  return null;
              }
          }
        };
    }
}
