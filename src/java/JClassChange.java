import com.sun.org.apache.bcel.internal.util.ClassPath;

import java.io.DataInput;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.gjt.jclasslib.io.ClassFileWriter;
import org.gjt.jclasslib.structures.ClassFile;
import org.gjt.jclasslib.structures.Constant;
import org.gjt.jclasslib.structures.InvalidByteCodeException;
import org.gjt.jclasslib.structures.constants.ConstantUtf8Info;

/**
 * @author yanwlb
 * @Date 2020/8/27 15:19
 */
public class JClassChange {
    public static void main(String[] args) {
        String filePath = "E:\\DCITS-YWL\\project\\untitled1\\out\\production\\untitled1\\Test.class";
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(filePath);
        } catch (FileNotFoundException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        DataInput di = new DataInputStream(fis);
        ClassFile cf = new ClassFile();
        try {
            cf.read(di);
        } catch (Exception e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }


        Constant[] infos=cf.getConstantPool();

        int count = cf.getConstantPool().length;
        for (int i = 0; i < count; i++) {
            if (cf.getConstantPool()[i] != null) {
                System.out.print(i);
                System.out.print(" = ");
                try {
                    System.out.print(cf.getConstantPool()[i].getVerbose());
                } catch (InvalidByteCodeException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
                System.out.print(" = ");
                if(i == 26){
                    ConstantUtf8Info uInfo = (ConstantUtf8Info)cf.getConstantPool()[i];
                    uInfo.setString("Admin123");
                    cf.getConstantPool()[i]=uInfo;
                }
            }
        }
        cf.setConstantPool(cf.getConstantPool());
        try {
            fis.close();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        File f = new File(filePath);
        try {
            ClassFileWriter.writeToFile(f, cf);
        } catch (InvalidByteCodeException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
