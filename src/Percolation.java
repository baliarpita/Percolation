import edu.princeton.cs.algs4.WeightedQuickUnionUF;
import edu.princeton.cs.algs4.StdOut ;

public class Percolation {
	
	protected WeightedQuickUnionUF wqu;
	int n;
	boolean[][] opened;
	int virtualBottom;
	int virtualTop;
	
	public Percolation(int n) {
		// create n-by-n grid, with all sites blocked
		if( n <= 0)
			throw new IllegalArgumentException();
		
		this.n = n;
		// For n = 5, WeightedQuickUnionUF size: 0 - 26
		// Virtual top = 0
		// Virtual bottom = 26
		wqu = new WeightedQuickUnionUF(n*n+2);
		opened = new boolean[n+1][n+1];
		virtualBottom = n*n + 1;
		virtualTop = 0;
	} 

	public void open(int row, int col) {
		// open site (row, col) if it is not open already
		
		if(row >n || row <1 || col>n || col<1)
		{
			throw new IllegalArgumentException();
		}
		
		if(!opened[row][col])
		{
			// Site in top level of grid
			// Connect with Virtual Top (0)
			if(row == 1)
			{
				int p =mapTo1DArray(row,col);
				wqu.union(p,virtualTop);
				opened[row][col] = true;
			}
			
			// Site in bottom level of grid
			// Connect with Virtual Bottom (26)
			if(row == n)
			{
				int p =mapTo1DArray(row,col);
				wqu.union(p,virtualBottom);
				opened[row][col] = true;
			}
			
			// Check upper site
			if(row > 1 && opened[row-1][col])
			{
				int p =mapTo1DArray(row,col);
				int q = mapTo1DArray(row-1, col);
				wqu.union(p,q);
				opened[row][col] = true;
			}
			
			//Check bottom site
			if(row> n && opened[row+1][col])
			{
				int p =mapTo1DArray(row,col);
				int q = mapTo1DArray(row+1, col);
				wqu.union(p,q);
				opened[row][col] = true;
			}
			
			//Check left site
			if(col > 1 && opened[row][col-1])
			{
				int p =mapTo1DArray(row,col);
				int q = mapTo1DArray(row, col-1);
				wqu.union(p,q);
				opened[row][col] = true;
			}
			
			//Check right site
			if(col < n && opened[row][col+1])
			{
				int p =mapTo1DArray(row,col);
				int q = mapTo1DArray(row, col+1);
				wqu.union(p,q);
				opened[row][col] = true;
			}
		}
	} 

	private int mapTo1DArray(int row, int col) {
		// Map to WeightedUnion data type.
		return (n * (row-1) + col);
	}

	public boolean isOpen(int row, int col) {
		// is site (row, col) open?
		
		if(row >n || row <1 || col>n || col<1)
		{
			throw new IllegalArgumentException();
		}
		
		return opened[row][col];
	} 

	public boolean isFull(int row, int col) {
		// is site (row, col) full?
		
		if(row >n || row <1 || col>n || col<1)
		{
			throw new IllegalArgumentException();
		}
		
		int p = mapTo1DArray(row, col);
		return wqu.connected(p, virtualTop);
	} 

	public int numberOfOpenSites() {
		// number of open sites
		int count = 0;
		for(int i=1;i<=n;i++)
		{
			for(int j=1;j<=n;j++)
			{
				if(opened[i][j])
					count++;
			}
		}
		return count;
	} 

	public boolean percolates() {
		// does the system percolate?
		return wqu.connected(virtualTop, virtualBottom);
	} 

	public static void main(String[] args) {
		// test client (optional)
		Percolation test = new Percolation(5);
		
		// For percolating condition:
		test.open(1, 1);
		test.open(1, 2);
		test.open(1, 4);
		test.open(2, 4);
		test.open(3, 2);
		test.open(3, 4);
		test.open(3, 5);
		test.open(4, 1);
		test.open(4, 3);
		test.open(4, 4);
		test.open(5, 1);
		test.open(5, 2);
		test.open(5, 4);
		test.open(5, 5);
		
		/*For non percolating condition:
		test.open(1, 1);
		test.open(1, 2);
		test.open(1, 4);
		test.open(2, 4);
		test.open(3, 2);
		test.open(3, 4);
		test.open(3, 5);
		test.open(4, 1);
		test.open(4, 3);
		test.open(5, 1);
		test.open(5, 2);
		test.open(5, 4);
		test.open(5, 5);*/
		
		StdOut.printf("test.percolates(): "+ test.percolates());
		
	}
}