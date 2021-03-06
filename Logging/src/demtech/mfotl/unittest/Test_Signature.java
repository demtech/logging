package demtech.mfotl.unittest;

import static org.junit.Assert.*;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import demtech.mfotl.Predicate;
import demtech.mfotl.Signature;

public class Test_Signature {
    private Signature my_signature = null;
    private final Set<Predicate> my_pred_set;
    
    public Test_Signature() {
        my_pred_set = new HashSet<Predicate>();
        
        try {
            FileInputStream fstream = new FileInputStream("./src/demtech/mfotl/unittest/e1.sig");
            DataInputStream in = new DataInputStream(fstream);
            BufferedReader br = new BufferedReader(new InputStreamReader(in));
            String str_line = br.readLine();
            
            String[] str_uni = str_line.substring(1, str_line.length()-1).split(", ");
            HashSet<Integer> int_uni = new HashSet();
            for (int i = 0; i < str_uni.length; i++)
                int_uni.add(Integer.parseInt(str_uni[i]));
            my_signature = new Signature(int_uni);
            
            while ((str_line = br.readLine()) != null) {
                System.out.println("Relation: " + str_line);
                String[] str_tokens = str_line.split(" ");
                int an_arity = str_tokens[1].split(",").length;
                final Predicate tmp = new Predicate(str_tokens[0], an_arity);
                my_pred_set.add(tmp);
                my_signature.addPredicate(tmp, null);
            }
            br.close();
            in.close();
            fstream.close();
        } catch (Exception e) {
            System.out.println("Current dir using System:" + System.getProperty("user.dir"));
            System.err.println("Error: " + e.getMessage());
        }
    }
    
    @Test
    public void test() {
        for (Predicate p_i : my_pred_set) {
            assertTrue(my_signature.contains(p_i));
            assertTrue(my_signature.contains(p_i.getName(), p_i.getArity()));
        }
    }
}
