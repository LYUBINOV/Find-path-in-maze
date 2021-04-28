package PathInputReader;

import java.io.*;

public class FindPathInputReaderFile extends AbstractFindPathInputReader {
    public String[][] read(String filePath) throws IOException {
        File file = new File(filePath);

        BufferedReader br = new BufferedReader(new FileReader(file));

        String candidate = "";
        int rows = 0;
        int cols = 0;

        {
            String stringReader = "";
            while ((stringReader = br.readLine()) != null) {
                candidate += stringReader;

                if(cols == 0) {
                    cols = stringReader.length();
                }
                ++rows;
            }
        }

        return super.read(candidate, rows, cols);
    }
}
