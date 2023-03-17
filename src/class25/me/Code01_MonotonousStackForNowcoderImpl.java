package class25.me;

import class25.Code01_MonotonousStackForNowcoder;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StreamTokenizer;
import java.util.ArrayList;
import java.util.List;

/**
 * @author lei
 */
public class Code01_MonotonousStackForNowcoderImpl extends Code01_MonotonousStackForNowcoder {


    public static void main(String[] args) throws IOException {
        StreamTokenizer in = new StreamTokenizer(new BufferedReader(new InputStreamReader(System.in)));
        PrintWriter out = new PrintWriter(new OutputStreamWriter(System.out));
        List<String> data = new ArrayList<>();
        while (in.nextToken() != StreamTokenizer.TT_EOL) {
            data.add(in.sval);
        }
        out.println(data);
    }
}
