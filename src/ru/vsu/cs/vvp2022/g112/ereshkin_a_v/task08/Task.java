package ru.vsu.cs.vvp2022.g112.ereshkin_a_v.task08;

public class Task {
	public static int getType(int previous, int current, int type){
		if (type == -2){
			if (current > previous) return 1;
			else if (current < previous) return 3;
			else return 0;
		}
		if (type == 0){
			if (current == previous) return 0;
			else if (current > previous) return 2;
			else return 4;
		}
		if (type == 1){
			if (current > previous) return 1;
			else if (current == previous) return 2;
			else return -1;
		}
		if (type == 2){
			if (current >= previous) return 2;
			else return -1;
		}
		if (type == 3){
			if (current < previous) return 3;
			if (current == previous) return 4;
			else return -1;
		}
		if (type == 4){
			if (current <= previous) return 4;
			else return 1;
		}
		return -1;
	}
	public static boolean doesMatrixIncludeSpiralPattern(int[][] matrix) {
		if (matrix == null || matrix.length == 1 && matrix[0].length == 1){
			return false;
		}
		int rowsRemain = matrix.length;
		int columnsRemain = matrix[0].length;

		int y = 0;
		int x = 0;

		/* По умолчанию неопределённая упорядоченность.
		* -3 - начальная
		* -2 - начальный шаг + 1
		* -1 - упорядоченность отсутствует.
		* 0 - все равны.
		* 1 - следующий больше предыдущего.
		* 2 - следующий больше или равен предыдущему.
		* 3 - следующий меньше предыдущего.
		* 4 - следующий меньше или равен предыдущему.
		*
		* 0 -> 1
		* 1 -> 2
		* 0 -> 3
		* 3 -> 4
		*
		* */
		int type = -3;
		int previous;
		int current = 0;

		while (rowsRemain > 0 && columnsRemain > 0) {

			//if one row/column left, no circle can be formed
			if (rowsRemain == 1) {
				for (int i = 0; i < columnsRemain; i++) {
					if (type == -3) {
						type++;
						current = (matrix[y][x++]);
						continue;
					} else {
						previous = current;
					}
					current = matrix[y][x++];
					type = getType(previous, current, type);
				}
				break;
			} else if (columnsRemain == 1) {
				for (int i = 0; i < rowsRemain; i++) {
					if (type == -3) {
						type++;
						current = (matrix[y++][x]);
						continue;
					} else {
						previous = current;
					}
					current = (matrix[y++][x]);
					type = getType(previous, current, type);

				}
				break;
			}

			//below, process a circle

			//top - move right
			for (int i = 0; i < columnsRemain - 1; i++) {
				if (type == -3) {
					type++;
					current = (matrix[y][x++]);
					continue;
				} else {
					previous = current;
				}
				current = (matrix[y][x++]);
				type = getType(previous, current, type);
			}

			//right - move down
			for (int i = 0; i < rowsRemain - 1; i++) {
				if (type == -3) {
					type++;
					current = (matrix[y++][x]);
					continue;
				} else {
					previous = current;
				}
				current = (matrix[y++][x]);
				type = getType(previous, current, type);
			}

			//bottom - move left
			for (int i = 0; i < columnsRemain - 1; i++) {
				previous = current;
				current = (matrix[y][x--]);
				type = getType(previous, current, type);
			}

			//left - move up
			for (int i = 0; i < rowsRemain - 1 && y - 1 >= 0; i++) {
				previous = current;
				current = (matrix[y--][x]);
				type = getType(previous, current, type);
			}

			y++;
			x++;
			rowsRemain = rowsRemain - 2;
			columnsRemain = columnsRemain - 2;
		}
		return type >= 0;
	}
}
