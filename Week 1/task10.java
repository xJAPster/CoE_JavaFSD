import java.util.*;

class MatrixMultiplier extends Thread {
    private int[][] matrixA;
    private int[][] matrixB;
    private int[][] result;
    private int row;

    public MatrixMultiplier(int[][] matrixA, int[][] matrixB, int[][] result, int row) {
        this.matrixA = matrixA;
        this.matrixB = matrixB;
        this.result = result;
        this.row = row;
    }

    @Override
    public void run() {
        for (int j = 0; j < matrixB[0].length; j++) {
            result[row][j] = 0;
            for (int k = 0; k < matrixA[0].length; k++) {
                result[row][j] += matrixA[row][k] * matrixB[k][j];
            }
        }
    }
}

class MatrixMultiplication {
    public int[][] multiplyMatrices(int[][] matrixA, int[][] matrixB) {
        int rowsA = matrixA.length;
        int colsA = matrixA[0].length;
        int colsB = matrixB[0].length;

        if (colsA != matrixB.length) {
            throw new IllegalArgumentException("Matrix dimensions are not compatible for multiplication.");
        }

        int[][] result = new int[rowsA][colsB];
        Thread[] threads = new Thread[rowsA];

        for (int i = 0; i < rowsA; i++) {
            threads[i] = new MatrixMultiplier(matrixA, matrixB, result, i);
            threads[i].start();
        }

        try {
            for (int i = 0; i < rowsA; i++) {
                threads[i].join();
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return result;
    }
}

public class task10 {
    public static void main(String[] args) {
        int[][] matrixA = {{1, 2}, {3, 4}};
        int[][] matrixB = {{2, 0}, {1, 2}};

        MatrixMultiplication multiplication = new MatrixMultiplication();
        int[][] result = multiplication.multiplyMatrices(matrixA, matrixB);

        System.out.println("Result of the multiplication:");
        for (int[] row : result) {
            System.out.println(Arrays.toString(row));
        }
    }
}
