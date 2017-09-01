public class MatrixEntry implements Comparable<MatrixEntry> {
    
    private final int value;    
    private final int row;
    private final int col;
    
    public MatrixEntry(int value, int row, int col) {
        this.value = value;
        this.row = row;
        this.col =  col;
    }

    // TODO: Getters and setters?
    public int getRow() {
    	return this.row;
    }
    public int getCol() {
    	return this.col;
    }
    public int getValue() {
    	return this.value;
    }
    public int getRorC(String s) {
    	if (s == "row") {
    		return this.row;
    	}
    	else return this.col;
    }
    
    public int getValue(int row, int col) {
    	if (this.row == row && this.col == col) {
    		return this.value;
    	}
    	else return 0;
    }
    @Override
    public int compareTo(MatrixEntry e) {
    	if (this.row < e.row) {
    		return -1;
    	}
    	else if (this.row > e.row) {
    		return 1;
    	}
    	else {
    		if (this.col <= e.col) {
    			return -1;
    		}
    		else {
    			return 1;
    		}
    	}
	}
}
