package PathInputReader;

public abstract class AbstractFindPathInputReader implements MazeReaderInterface {
    @Override
    public String[][] read(String candidate, int rows, int cols) {
        String[][] retVal = new String[rows][cols];
        int cnt = 0;

        for(int i = 0; i < rows; ++i) {
            for(int j = 0; j < cols; ++j) {
                retVal[i][j] = String.valueOf(candidate.charAt(cnt));
                ++cnt;
            }
        }

        return retVal;
    }
}
