import org.gjt.jclasslib.io.ClassFileWriter;
import org.gjt.jclasslib.structures.ClassFile;
import org.gjt.jclasslib.structures.Constant;
import org.gjt.jclasslib.structures.InvalidByteCodeException;
import org.gjt.jclasslib.structures.constants.ConstantUtf8Info;

import java.io.*;

/**
 * @author yanwlb
 * @Date 2020/8/27 16:44
 */
public class Modify {

    public static void main(String[] args) {
        String filePath = "E:\\DCITS-YWL\\project\\untitled1\\out\\production\\untitled1\\Test.class";
        try {
            // 读取class文件
            FileInputStream fis = new FileInputStream(filePath);

            DataInput di = new DataInputStream(fis);
            ClassFile cf = new ClassFile();
            cf.read(di);
            Constant[] infos = cf.getConstantPool(); // 将class文件内容读成数组

            for (int i = 1; i < infos.length; i++) {
                if (infos[i]!=null) { // 取class中代码信息
                    System.out.print(i);
                    System.out.print(" = ");
                    try {
                        System.out.print(infos[i].getVerbose());
                    } catch (InvalidByteCodeException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    System.out.print(" = ");
                    System.out.println(infos[i].getVerbose());
                    if(i==26){
                        ConstantUtf8Info info = (ConstantUtf8Info) infos[i];
                        if (info.getVerbose().equals("baidu.com")) { // 得到要替换的string的位置
                            info.setString("hello"); // 替换为新的string
                            infos[i] = info;
                        }
                    }

                }
            }

            // 保存修改后的class
            cf.setConstantPool(infos);
            fis.close();
            File f = new File(filePath);
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

