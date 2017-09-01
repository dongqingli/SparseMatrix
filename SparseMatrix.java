import java.util.Arrays;

public class SparseMatrix {

    private MatrixEntry [] array;
	private int numEntries;
    
    private final int m;
    private final int n;
    public int testnum = 0;
	
    
    public SparseMatrix(int m, int n) {
        this.m = m;
        this.n = n;
		
		// You will need to "grow" the array efficiently 
		// as more values are added.
		this.array = new MatrixEntry[4]; 
    }
    public SparseMatrix(int m, int n, String type) {
    	this.m = m;
        this.n = n;
        this.numEntries = 0;
    	//Ai,j = i*j,  (i+j)%2 = 0   5X6
        if (type.equals("C")) {
        	for (int i = 1; i <= m; i++) {
        		for (int j = 1; j <= n; j++) {
        			if (((i + j) % 2) == 0) {
        				this.numEntries++;
        			}
        		}
        	}
        	this.array = new MatrixEntry[this.numEntries];
        	int ct = 0;
        	for (int i = 1; i <= m; i++) {
        		for (int j = 1; j <= n; j++) {
        			if (((i + j) % 2) == 0) {
        				array[ct++] = new MatrixEntry((i * j), i, j);
        			}
        		}
        	}
        }
        //Ai,j =i+j (i*j)%4=0     6X5
        else if (type.equals("D")) {
        	for (int i = 1; i <= m; i++) {
        		for (int j = 1; j <= n; j++) {
        			if (((i * j) % 4) == 0) {
        				this.numEntries++;
        			}
        		}
        	}
        	this.array = new MatrixEntry[this.numEntries];
        	int ct = 0;
        	for (int i = 1; i <= m; i++) {
        		for (int j = 1; j <= n; j++) {
        			if (((i * j) % 4) == 0) {
        				array[ct++] = new MatrixEntry((i + j), i, j);
        			}
        		}
        	}
        }
        //Ai,j =i+2j i%10=0     200X200
        else if (type.equals("E")) {
        	for (int i = 10; i <= m; i = i + 10) {
        		for (int j = 1; j <= n; j++) {
        			this.numEntries++;
        		}
        	}
        	this.array = new MatrixEntry[this.numEntries];
        	int ct = 0;
        	for (int i = 10; i <= m; i = i + 10) {
        		for (int j = 1; j <= n; j++) {
        			array[ct++] = new MatrixEntry((i + (2 * j)), i, j);
        		}
        	}
        }
        //Ai,j =5*i i%5=0     200X1
        else if (type.equals("F")) {
        	for (int i = 5; i <= m; i = i + 5) {
        		for (int j = 1; j <= n; j++) {
        			this.numEntries++;
        		}
        	}
        	this.array = new MatrixEntry[this.numEntries];
        	int ct = 0;
        	for (int i = 5; i <= m; i = i + 5) {
        		for (int j = 1; j <= n; j++) {
        				array[ct++] = new MatrixEntry((5 * i), i, j);
        		}
        	}
        }
        //Ai,j =i+j i%j=0     30000X30000
        else if (type.equals("G")) {
        	int arraylen = 4;
        	this.array = new MatrixEntry[arraylen];
        	MatrixEntry [] temparray;
        	int ct = 0;
        	for (int j = 1; j <= n; j++) {
        		for (int k = 1; k <= m; k++) {
        			int i = j * k;
        			if (i <= m ) {
        				if (ct >= arraylen) {
        					temparray = array;
//        					System.out.println(temparray[0].getValue());
        					arraylen = arraylen * 2;
        					array = new MatrixEntry[arraylen];
        					System.arraycopy(temparray, 0, array, 0, arraylen / 2);
//        					System.out.println(array[0].getValue());
        					
        				}
        				
        				array[ct++] = new MatrixEntry((i + j), i, j);
//        				System.out.println(array[ct].getValue());
//        				ct++;
        			}
        			else break;
        		}
        	}
        this.numEntries = ct;
        this.Sort();
//        System.out.println("Entry number = " + this.numEntries);
        }
    }
    
    public SparseMatrix(String str) {
    	String [] elements = str.split(",");
    	this.numEntries = elements.length;
    	this.array = new MatrixEntry[numEntries];
    	for (int i = 0; i < numEntries; i++) {
//    		System.out.println("i=" + i);
    		String m [] = elements[i].split("r|c");
    		
    		array[i] = new MatrixEntry(Integer.parseInt(m[0].trim()), 
    				Integer.parseInt(m[1].trim()),Integer.parseInt(m[2].trim()));
//    		System.out.println("value=" + tempArray[i].getValue());
    	}
    	//Sort array according to row number
    	this.Sort();
    	this.m = findMax(array, "row"); 
    	this.n = findMax(array, "col");
//    	for (int i = 0; i < numEntries; i++) {
//    		System.out.println(array[i].getValue());
//    	} 
    }
    public void Sort () {
    	Arrays.sort(this.array, 0, this.numEntries);
    	return;
    }
    public void eliminateZero () {
    	SparseMatrix temp = new SparseMatrix(this.m, this.n);
    	temp.numEntries = this.numEntries;
    	temp.array = new MatrixEntry[numEntries];
    	int ct = 0;
    	for (int i = 0; i < numEntries; i++) {
    		if (this.array[i].getValue() != 0) {
    			temp.array[ct++] = new MatrixEntry(this.array[i].getValue(), this.array[i].getRow(), this.array[i].getCol());
    		}
    	}
    	this.array = temp.array;
    	this.numEntries = ct;
    	return;
    }
/*    
    public void Sort () {
    	this.quickSort(this.array, 0, numEntries-1, "row");
    	
    	// Sort array according to col number
    	int low = 0;
    	int high = 0;
    	int ct = 0;
    	while (ct < numEntries - 1) {
    		if (array[ct].getRow() != array[ct+1].getRow()) {
    			this.quickSort(array, low, high, "col");
    			low = ct + 1;
    		}
    		else {
    			high = ct + 1;
    		}
    		ct++;
    	}
    	this.quickSort(array, low, high, "col");
    }
*/
    public int findMax (MatrixEntry [] mergeA, String axis) {
		int max = mergeA[0].getRorC(axis);
    	for (int i = 1; i < numEntries; i++) {
    		if (mergeA[i].getRorC(axis) > max) {
    			max = mergeA[i].getRorC(axis);
    		}
    	}
    	return max;
    }
    public int getRowNum () {
    	return this.m;
    }
    public int getColNum () {
    	return this.n;
    }
    public void quickSort (MatrixEntry [] mergeA, int low, int high, String axis) {

    	if (low < high) {    	
//    		System.out.println(testnum++);
    		int p = partition(mergeA, low, high, axis);
    		quickSort (mergeA, low, p-1, axis);
    		quickSort (mergeA, p + 1, high, axis);
    		
    	}
    	
    	return;
    }
    public int partition (MatrixEntry [] mergeA, int low, int high, String axis) { 
    	int pivot = mergeA[low].getRorC(axis);
//    	System.out.println("pivot = " + pivot);
    	MatrixEntry swap = mergeA[low];
    	mergeA[low] = mergeA[high];
    	mergeA[high] = swap;
    	int p = low;
    	for (int i = low; i < high; i++) {
    		if (mergeA[i].getRorC(axis) < pivot) {
    			swap = mergeA[i];
    			mergeA[i] = mergeA[p];
    	    	mergeA[p] = swap;
    	    	p++;
//    	    	System.out.println("p = " + p);
    		}
    	}
    	swap = mergeA[p];
    	mergeA[p] = mergeA[high];
    	mergeA[high] = swap;
//    	System.out.println("return p = " + p);
    	return p;
    }
    
    public int binarySearch (int low, int high, int row, int col) {
    	if (high < low) return -1;
    	int mid = (low + high) / 2;
    	if (array[mid].getRow() == row && array[mid].getCol() == col) return mid;
    	else if (array[mid].getRow() < row) return binarySearch(mid + 1, high, row, col);
    	else if (array[mid].getRow() > row) return binarySearch(low, mid - 1, row, col);
    	else {
    		if (array[mid].getCol() < col) return binarySearch(mid + 1, high, row, col);
    		else return binarySearch(low, mid - 1, row, col);
    	}
    }
    public int getIndex(int row, int col) {
    	return binarySearch(0, this.numEntries - 1, row, col);
    }

    public int value(int row, int col) {
    	int index = binarySearch(0, numEntries - 1, row, col);
    	if (index == -1) return 0;
    	else return array[index].getValue();
    }
    
    public void print() {
    	if (this.m == 0) return;
    	String e = "...";
        if (m > 6 && n > 6) {
     		System.out.printf("%13d%13d%13s%13d%13d\n",this.value(1, 1),this.value(1, 2),e,this.value(1, n-1),this.value(1, n));
     		System.out.printf("%13d%13d%13s%13d%13d\n",this.value(2, 1),this.value(2, 2),e,this.value(2, n-1),this.value(2, n));
//    		System.out.printf("%21s\n",e);
    		for (int j = 1; j < 6; j++) {
				System.out.printf("%13s", e);
			}
			System.out.println();
    		System.out.printf("%13d%13d%13s%13d%13d\n",this.value(m-1, 1),this.value(m-1, 2),e,this.value(m-1, n-1),this.value(m-1, n));
    		System.out.printf("%13d%13d%13s%13d%13d\n",this.value(m, 1),this.value(m, 2),e,this.value(m, n-1),this.value(m, n));
        }
        else if (m > 6 && n <= 6) {
        	for (int i = 1; i < 6; i++) {
        		if (i == 3) {
//        			System.out.printf("%21s\n",e);
        			for (int j = 1; j <= n; j++) {
        				System.out.printf("%13s", e);
        			}
        			System.out.println();
        		}
        		else if (i < 3) {
        			for (int j = 1; j <= n; j++) {
        				System.out.printf("%13d", this.value(i, j));
        			}
        			System.out.println();
        		}
        		else {
        			for (int j = 1; j <= n; j++) {
        				System.out.printf("%13d", this.value(m-5+i, j));
        			}
        			System.out.println();       			
        		}
        	}
        }
        else if (m <= 6 && n > 6) {
        	for (int i = 1; i <= m; i++) {
        		for (int j = 1; j < 6; j++) {
        			if (j == 3) {
        				System.out.printf("%13s",e);
        			}
        			else if (j < 3) {
        				System.out.printf("%13d",this.value(i, j));
        			}
        			else {
        				System.out.printf("%13d",this.value(i, n-5+j));
        			}
        		}
        		System.out.println();
        	}
        }
        else {
        	for (int i = 1; i <= m; i++) {
        		for (int j = 1; j <= n; j++) {
        			System.out.printf("%13d",this.value(i, j));
        		}
        		System.out.println();
        	}
        }
        return;
    }
    public SparseMatrix scalarMultiply(int c) {
        SparseMatrix cM = new SparseMatrix(this.m, this.n);
        cM.numEntries = this.numEntries;
        cM.array = new MatrixEntry[cM.numEntries];
        for (int i = 0; i < cM.numEntries; i++) {
        	cM.array[i] = new MatrixEntry((this.array[i].getValue() * c), this.array[i].getRow(), this.array[i].getCol());
        }
        return cM;
    }
    public boolean equals(SparseMatrix M) {
        if ((M.numEntries == this.numEntries) && (M.m == this.m) && (M.n == this.n)) {
        	for (int i = 0; i < numEntries; i++) {
            	if ((M.array[i].getValue() != this.array[i].getValue()) || 
            		(M.array[i].getRow() != this.array[i].getRow()) || (M.array[i].getCol() != this.array[i].getCol())) {
            		return false;
            	}
            }
        	return true;
        }
        else return false;
    }
    public SparseMatrix add(SparseMatrix M) {
//       	System.out.println("Start add" + System.currentTimeMillis());
    	MatrixEntry [] temparray;
    	int row = this.m;
    	int col = this.n;
    	int len = 4;
    	int ct = 0;
    	SparseMatrix resultM = new SparseMatrix(0, 0);
    	resultM.numEntries = 0;
    	try {
    		
        	if (this.m != M.getRowNum() || this.n != M.getColNum()) {
        		throw new Exception("The row and column number of the matrices to be added are not equal.");
        	}
        	resultM = new SparseMatrix(row, col);
        	int row1, col1;
        	for (int i = 0; i < this.numEntries; i++) {
        		row1 = this.array[i].getRow();
        		col1 = this.array[i].getCol();
        		if (ct >= len) {
					temparray = resultM.array;
					len = len * 2;
					resultM.array = new MatrixEntry[len];
					System.arraycopy(temparray, 0, resultM.array, 0, len / 2);        					
				}
        		resultM.array[ct++] = new MatrixEntry((this.array[i].getValue() + M.value(row1, col1)), row1, col1);
        	}
        	resultM.numEntries = ct;
        	for (int i = 0; i < M.numEntries; i++) {
        		row1 = M.array[i].getRow();
        		col1 = M.array[i].getCol();
        		if (ct >= len) {
					temparray = resultM.array;
					len = len * 2;
					resultM.array = new MatrixEntry[len];
					System.arraycopy(temparray, 0, resultM.array, 0, len / 2);
				}
        		if (resultM.getIndex(row1, col1) == -1 ) {
        			resultM.array[ct++] = new MatrixEntry(M.array[i].getValue(), row1, col1);
        		}
        		
        	}
//        	System.out.println("start eliminate zero" + System.currentTimeMillis());
        	resultM.numEntries = ct;
        	resultM.eliminateZero();
//        	System.out.println("Start sort" + System.currentTimeMillis());
        	resultM.Sort();
        	return resultM;
        }
//        SparseMatrix tempMatrix;
    	catch (Exception excpt) {
    		System.out.println(excpt.getMessage());
    		return resultM;
    	}
    }
    public SparseMatrix subtract(SparseMatrix M) {
    	SparseMatrix resultM = new SparseMatrix(0, 0);
    	try {
    		
        	if (this.m != M.getRowNum() || this.n != M.getColNum()) {
        		throw new Exception("The row and column number of the matrices to do subtraction are not equal.");
        	}
//        	resultM = new SparseMatrix(row, col);
        	resultM = this.add(M.scalarMultiply(-1));
           	resultM.eliminateZero();
//        	System.out.println("Start sort" + System.currentTimeMillis());
        	resultM.Sort();
        	return resultM;
        }
//        SparseMatrix tempMatrix;
    	catch (Exception excpt) {
    		System.out.println(excpt.getMessage());
    		return resultM;
    	}
    }
    public SparseMatrix multiply(SparseMatrix M) {
       	MatrixEntry [] temparray;
    	int row = this.m;
    	int col = M.getColNum();
    	int len = 4;
    	int ct = 0;
    	SparseMatrix resultM = new SparseMatrix(0, 0);
    	resultM.numEntries = 0;
    	try {
    		
        	if (this.n != M.getRowNum()) {
        		throw new Exception("The column number of multiplied matrix is not equal to the row number of multiplier matrix.");
        	}
        	resultM = new SparseMatrix(row, col);
        	for (int i = 0; i < this.numEntries; i++) {
        		int row1 = this.array[i].getRow();
        		int col1 = this.array[i].getCol();
        		for (int j = 0; j < M.numEntries; j++) {
        			int row2 = M.array[j].getRow();
        			int col2 = M.array[j].getCol();
        			if (ct >= len) {
    					temparray = resultM.array;
    					len = len * 2;
    					resultM.array = new MatrixEntry[len];
    					System.arraycopy(temparray, 0, resultM.array, 0, len / 2);    
    				}
        			if (col1 == row2) {
        				int result = this.array[i].getValue() * M.array[j].getValue();
        				int index = resultM.getIndex(row1, col2);
        				if (index != -1 ) {
        					resultM.array[index] = new MatrixEntry((resultM.array[index].getValue() + result), row1, col2);
        				}
        				else {
        					resultM.array[ct++] = new MatrixEntry(result, row1, col2);
        					resultM.numEntries = ct;
        				}
        			}
        		}
        	}
        	resultM.numEntries = ct;
        	resultM.Sort();
        	return resultM;
        }
//        SparseMatrix tempMatrix;
    	catch (Exception excpt) {
    		System.out.println(excpt.getMessage());
    		return resultM;
    	}
    }
    public SparseMatrix transpose() {
    	int m = this.n;
    	int n = this.m;
    	SparseMatrix transM = new SparseMatrix(m, n);
    	transM.array = new MatrixEntry[numEntries];
    	transM.numEntries = numEntries;
    	for (int i = 0; i < numEntries; i++) {
    		transM.array[i] = new MatrixEntry(this.array[i].getValue(), this.array[i].getCol(), this.array[i].getRow());
//    		System.out.println("r" + transM.array[i].getRow() +"c" + transM.array[i].getRow());
    	}
    	transM.Sort();
    	return transM;    	
    }
    public SparseMatrix power(int p) {
    	SparseMatrix resultM = new SparseMatrix(0, 0);
    	resultM.numEntries = 0;
    	try {
    		
        	if (m != n) {
        		throw new Exception("The matrix is not a square matrix.");
        	}
        	String bi = Integer.toBinaryString(p);
        	String[] bits = bi.split("");
        	int bLen = bits.length;
        	SparseMatrix [] arrayMatrix = new SparseMatrix[bLen];
        	arrayMatrix[bLen - 1] = this;
        	for (int i = bLen - 2; i >= 0; i--) {
        		arrayMatrix[i] = arrayMatrix[i + 1].multiply(arrayMatrix[i + 1]);
        	}
//        	System.out.println("length = " + bits.length);
        	resultM = arrayMatrix[0];
        	for (int i = 1; i < bLen; i++) {
        		if (bits[i].equals("1")) {
        			resultM = resultM.multiply(arrayMatrix[i]);
        		}        		
        	}
        	resultM.eliminateZero();
        	resultM.Sort();
        	return resultM;
        }
//        SparseMatrix tempMatrix;
    	catch (Exception excpt) {
    		System.out.println(excpt.getMessage());
    		return resultM;
    	}
	}
	/*
    public boolean equals(SparseMatrix m) {
        
    }
    
    public SparseMatrix scalarMultiply(int c) {
        
    }
    
    public SparseMatrix add(SparseMatrix m) {
        
    }
    
    public SparseMatrix subtract(SparseMatrix m) {
        
    }
    
    public SparseMatrix multiply(SparseMatrix m) {
        
    }
	
	public SparseMatrix power(int p) {
	
	}
    
    public SparseMatrix transpose() {
        
    }
	*/
    public static void main(String args[] ) {
    	String sA = "-5r4c4, 5r1c4, 2r2c2, 5r3c1, -3r3c2, 6r4c2, -7r2c3, 3r1c1";
    	String sB = "1r4c4, 1r4c1, 1r3c3, 1r2c6, 1r2c2, 1r1c5, 1r1c1";
    	String mine1 = "2r1c1, 1r1c2, 1r2c1, 1r2c2";
    	String mine3 = "333r6c5, 10000r5c7, 5000r5c2, 99r4c4, 4r4c2, 1r4c1, -10r3c2, 9r2c6, 8r2c5, 7r1c3";
    	String mine4 = "6r7c6, 333r6c5, 5000r5c2, 99r4c4, 4r4c2, 1r4c1, -10r3c2, 9r2c6, 8r2c5, 7r1c3";
    	System.out.println("##########################################");
    	System.out.println("Test Case 1: Initialize matrices A – G.");
    	System.out.println("##########################################");
    	long stime = System.nanoTime();
//    	System.out.println("Start time: " + stime + "ms");
    	SparseMatrix A = new SparseMatrix(sA);
    	SparseMatrix B = new SparseMatrix(sB);
    	SparseMatrix C = new SparseMatrix(5, 6, "C");
    	SparseMatrix D = new SparseMatrix(6, 5, "D");
    	SparseMatrix E = new SparseMatrix(200, 200, "E");
    	SparseMatrix F = new SparseMatrix(200, 1, "F");
    	SparseMatrix G = new SparseMatrix(30000, 30000, "G");
    	SparseMatrix resultM;
    	SparseMatrix mine = new SparseMatrix(mine1);
    	long etime = System.nanoTime();
//    	System.out.println("End time:   " + etime + "ms");
    	System.out.println("Time for initialization: " + (etime - stime) + " ns");
    	
    	SparseMatrix [] matrixArray = new SparseMatrix[]{A, B, C, D, E, F, G};
    	String [] matrixName = new String[]{"A", "B", "C", "D", "E", "F", "G"};
    	System.out.println("###################################################");
    	System.out.println("Test Case 2: Print matrices A – G using print().");
    	System.out.println("###################################################");
    	for (int i = 0; i < 7; i++) {
    		System.out.printf("Matrix %s:\n",matrixName[i]);
    		matrixArray[i].print();
    		System.out.println();
    	}

    	System.out.println("###################################################################");
    	System.out.println("Test Case 3: Scalar multiply each matrix by 5 and print the result.");
    	System.out.println("###################################################################");
    	for (int i = 0; i < 7; i++) {
    		System.out.printf("Matrix %s scalar multiply 5:\n", matrixName[i]);
//    		matrixArray[i].scalarMultiply(5).print();
    		stime = System.nanoTime();
    		resultM = matrixArray[i].scalarMultiply(5);
    		etime = System.nanoTime();
    		System.out.println("Running time of scalar multiply: " + (etime - stime) + " ns\n\n");
    		resultM.print();
    		System.out.println();
    	}

    	System.out.println("##########################################################");
    	System.out.println("Test Case 4: Apply equals() pairwise among matrices A – G.");
    	System.out.println("##########################################################");
    	for (int i = 0; i < 7; i++) {
    		for (int j = 0; j < 7; j++) {
    			System.out.printf("%s.equals(%s) is: %b\n", matrixName[i], matrixName[j], matrixArray[i].equals(matrixArray[j]));
    		}    		
    	}
    	System.out.println("############################################################");
    	System.out.println("Test Case 5: Add each matrix to itself and print the result.");
    	System.out.println("############################################################");
    	for (int i = 0; i < 7; i++) {
    		System.out.printf("Matrix %s add to itself:\n", matrixName[i]);
    		stime = System.nanoTime();
    		resultM = matrixArray[i].add(matrixArray[i]);
    		etime = System.nanoTime();
    		System.out.println("Running time of add: " + (etime - stime) + " ns\n\n");
    		resultM.print();
    		System.out.println();
    	}
    	System.out.println("###################################################################");
    	System.out.println("Test Case 6: Subtract each matrix from itself and print the result.");
    	System.out.println("###################################################################");
    	for (int i = 0; i < 7; i++) {
    		System.out.printf("Matrix %s subtract itself:\n", matrixName[i]); 
    		stime = System.nanoTime();
    		resultM = matrixArray[i].subtract(matrixArray[i]);
    		etime = System.nanoTime();
    		System.out.println("Running time of subtraction: " + (etime - stime) + " ns\n\n");
    		resultM.print();
    		System.out.println();
    	}
    	System.out.println("###################################################################");
    	System.out.println("Test Case 7: Scalar multiply each matrix by five and then subtract the matrix (e.g., 5A – A) and print the result.");
    	System.out.println("###################################################################");
    	for (int i = 0; i < 7; i++) {
    		System.out.printf("Matrix %s scalar multiply 5 and subtract itself:\n", matrixName[i]); 
    		stime = System.nanoTime();
    		resultM = matrixArray[i].scalarMultiply(5);
    		resultM = resultM.subtract(matrixArray[i]);
    		etime = System.nanoTime();
    		System.out.println("Running time of scalar multiply and subtraction: " + (etime - stime) + " ns\n\n");
    		resultM.print();
    		System.out.println();
    	}
    	System.out.println("##########################################################################");
    	System.out.println("Test Case 8: Multiply Matrix B with the transpose of Matrix C,");
    	System.out.println("Matrix C with Matrix D, Matrix D with Matrix C, and Matrix E with Matrix F");
    	System.out.println("##########################################################################");
     	System.out.println("Matrix B multiply the transpose of Matrix C."); 
    	stime = System.nanoTime();
    	resultM = B.multiply(C.transpose());
    	etime = System.nanoTime();
    	System.out.println("Running time of Matrix B multiply the transpose of Matrix C: " + (etime - stime) + " ns\n\n");
    	resultM.print();
    	System.out.println();
    	
    	System.out.println("Matrix C multiply Matrix D."); 
    	stime = System.nanoTime();
    	resultM = C.multiply(D);
    	etime = System.nanoTime();
    	System.out.println("Running time of Matrix C multiply Matrix D: " + (etime - stime) + " ns\n\n");
    	resultM.print();
    	System.out.println();
    	
    	System.out.println("Matrix D multiply Matrix C."); 
    	stime = System.nanoTime();
    	resultM = D.multiply(C);
    	etime = System.nanoTime();
    	System.out.println("Running time of Matrix D multiply Matrix C: " + (etime - stime) + " ns\n\n");
    	resultM.print();
    	System.out.println();
    	
    	System.out.println("Matrix E multiply Matrix F."); 
    	stime = System.nanoTime();
    	resultM = E.multiply(F);
    	etime = System.nanoTime();
    	System.out.println("Running time of Matrix E multiply Matrix F: " + (etime - stime) + " ns\n\n");
    	resultM.print();
    	System.out.println();
    	
    	System.out.println("##########################################################################");
    	System.out.println("Test Case 9: Multiply Matrix A with itself and Matrix E with itself.");
    	System.out.println("##########################################################################");
     	System.out.println("Multiply Matrix A with itself."); 
    	stime = System.nanoTime();
    	resultM = A.multiply(A);
    	etime = System.nanoTime();
    	System.out.println("Running time of Multiply Matrix A with itself: " + (etime - stime) + " ns\n\n");
    	resultM.print();
    	System.out.println();
    	
    	System.out.println("Multiply Matrix E with itself."); 
    	stime = System.nanoTime();
    	resultM = E.multiply(E);
    	etime = System.nanoTime();
    	System.out.println("Running time of Multiply Matrix E with itself: " + (etime - stime) + " ns\n\n");
    	resultM.print();
    	System.out.println();
    	
    	System.out.println("##########################################################################");
    	System.out.println("Test Case 10: For Matrices A and E raise each matrix to the power of 5 and 25 using power().");
    	System.out.println("##########################################################################");
     	System.out.println("Raise Matrix A to power of 5."); 
     	stime = System.nanoTime();
     	resultM = A.power(5);
     	etime = System.nanoTime();
    	System.out.println("Running time of raise Matrix A to power of 5: " + (etime - stime) + " ns\n\n");
    	resultM.print();
    	System.out.println();
    	
    	System.out.println("Raise Matrix A to power of 25."); 
    	stime = System.nanoTime();
     	resultM = A.power(25);
     	etime = System.nanoTime();
    	System.out.println("Running time of raise Matrix A to power of 25: " + (etime - stime) + " ns\n\n");
    	resultM.print();
    	System.out.println();

     	System.out.println("Raise Matrix E to power of 5."); 
     	stime = System.nanoTime();
     	resultM = E.power(5);
     	etime = System.nanoTime();
    	System.out.println("Running time of raise Matrix E to power of 5: " + (etime - stime) + " ns\n\n");
    	resultM.print();
    	System.out.println();
    	
    	System.out.println("Raise Matrix E to power of 25."); 
    	stime = System.nanoTime();
     	resultM = E.power(25);
     	etime = System.nanoTime();
    	System.out.println("Running time of raise Matrix E to power of 25: " + (etime - stime) + " ns\n\n");
    	resultM.print();
    	System.out.println();

    	
     	
    	System.out.println("########################################################################");
    	System.out.println("Test Case 11: Compute the transpose of each matrix and print the result.");
    	System.out.println("########################################################################");
    	for (int i = 0; i < 7; i++) {
    		System.out.printf("Transpose of Matrix %s :\n", matrixName[i]); 
    		stime = System.nanoTime();
    		resultM = matrixArray[i].transpose();
    		etime = System.nanoTime();
    		System.out.printf("Running time of Matrix %s transpose: %d ns\n\n", matrixName[i], (etime - stime));
    		resultM.print();
    		System.out.println();
    	}
    	System.out.println("####################################################################################");
    	System.out.println("Test Case 12: Multiply each matrix with its transpose and its transpose with itself.");
    	System.out.println("####################################################################################");
    	for (int i = 0; i < 6; i++) {
    		System.out.printf("Multiply Matrix %s with its transpose: \n", matrixName[i]); 
    		stime = System.nanoTime();
    		resultM = matrixArray[i].multiply(matrixArray[i].transpose());
    		etime = System.nanoTime();
    		System.out.printf("Running time of Matrix %s multiply with its transpose: %d ns\n\n", matrixName[i], (etime - stime));
    		resultM.print();
    		System.out.println();
    	}

    	for (int i = 0; i < 6; i++) {
    		System.out.printf("Multiply Matrix %s's transpose with itself: \n", matrixName[i]); 
    		stime = System.nanoTime();
    		resultM = matrixArray[i].transpose().multiply(matrixArray[i]);
    		etime = System.nanoTime();
    		System.out.printf("Running time of Matrix %s's transpose multiply with itself: %d ns\n\n", matrixName[i], (etime - stime));
    		resultM.print();
    		System.out.println();
    	}		
    	
		
		return;
	}    
}