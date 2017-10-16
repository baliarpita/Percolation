import edu.princeton.cs.algs4.StdRandom;
import edu.princeton.cs.algs4.StdStats;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class PercolationStats {
	
	int n;
	Percolation test;
	double[] threshold;
	int trials;

	public PercolationStats(int n, int trials) {
		// perform trials independent experiments on an n-by-n grid
		
		if(n <=0 || trials <=0)
			throw new IllegalArgumentException();
		
		this.n = n;
		this.trials = trials;
		threshold = new double[trials];
		
		while(trials != 0){
			
			test = new Percolation(n);
			while(!test.percolates())
			{
				// Get random site in 2D
				int siteRow = StdRandom.uniform(1, n+1);
				int siteCol = StdRandom.uniform(1, n+1);
				if(!test.isOpen(siteRow, siteCol))
					test.open(siteRow, siteCol);
			}
			threshold[trials-1] = (double)test.numberOfOpenSites()/(n*n);
			trials--;
		}
	}

	public double mean() {
		// sample mean of percolation threshold
		return StdStats.mean(threshold);
	}

	public double stddev() {
		// sample standard deviation of percolation threshold
		return StdStats.stddev(threshold);
	}

	public double confidenceLo() {
		// low endpoint of 95% confidence interval
		return (mean() - ((1.96 * stddev()))/Math.sqrt(trials));
	}

	public double confidenceHi() {
		// high endpoint of 95% confidence interval
		return (mean() + ((1.96 * stddev()))/Math.sqrt(trials));
	}

	public static void main(String[] args) {
		// test client (described below)
		
		int n;
		int t;
		n = StdIn.readInt();
		t = StdIn.readInt();
		
		PercolationStats ps = new PercolationStats(n, t);
		
		StdOut.println("% java PercolationStats " + n +" " + t);
		StdOut.println("mean\t\t\t= "+ ps.mean());
		StdOut.println("stddev\t\t\t= " + ps.stddev());
		StdOut.println("95% confidence interval\t= " + "[" + ps.confidenceLo() + ", " + ps.confidenceHi() + "]");
		
	}
}
