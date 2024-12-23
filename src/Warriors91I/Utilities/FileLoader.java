package Warriors91I.Utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class FileLoader {



    public static int[][] loadCsvFile(int width, int height, String path) {
        int[][] matrix = new int[width][height];

        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            String line;
            int row = 0;

            while ((line = br.readLine()) != null && row < width) {
                String[] values = line.split(",");
                for (int col = 0; col < values.length && col < height; col++) {
                    matrix[row][col] = Integer.parseInt(values[col].trim());
                }
                row++;
            }

            for (int i = 0; i < width; i++) {
                for (int j = 0; j < height; j++) {
                    System.out.print(matrix[i][j] + " ");
                }
                System.out.println();
            }
        } catch (IOException e) {
            System.err.println("Erreur lors de la lecture du fichier : " + e.getMessage());
        } catch (NumberFormatException e) {
            System.err.println("Erreur de format dans le fichier CSV : " + e.getMessage());
        }

        return matrix;
    }


}

