import java.io.*;

class FileReader {
    public void readFromFile(String file_name) throws Exception
    {
        ByteArrayInputStream bais = new ByteArrayInputStream(null, 0, 0);
        DataOutputStream dos = new DataOutputStream(null);
        
        
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        DataInputStream dis = new DataInputStream(null);
        


        File file = new File(file_name);
        RandomAccessFile raf = new RandomAccessFile(file, "r");

    
        //o .writeInt sobreescreve os dados
        //como abrir o excel
        //numero de registros



    }
}
