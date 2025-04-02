import java.util.*;

record Pair<K, V>(K key, V value) {}

public class SnakeGame {

    /**
     * Direction representing U, D, L, R respectively
     */
    static final List<Pair<Integer, Integer>> dirs = Arrays.asList(new Pair<>(-1, 0), new Pair<>(1, 0), new Pair<>(0, -1), new Pair<>(0, 1));

    // Represents the snake's body
    Deque<Pair<Integer, Integer>> snake;

    // position of snake's head
    Pair<Integer, Integer> head;

    // given information
    int width, height;
    int[][] food;

    // food index/score
    int i;

    public SnakeGame(int width, int height, int[][] food) {
        snake = new ArrayDeque<>();
        head = new Pair<>(0, 0);
        snake.add(head);
        this.width = width;
        this.height = height;
        this.food = food;
        this.i = 0;
    }

    public int move(String direction) {
        Pair<Integer, Integer> dir = getDirection(direction);
        // get the new coordinates for head
        int nr = head.key() + dir.key();
        int nc = head.value() + dir.value();

        // check if out of bounds
        if (outOfBounds(nr, nc)) return -1;

        // if a piece of food is consumed, do not remove tail
        if (i < food.length && nr == food[i][0] && nc == food[i][1]) {
            i++;
        }
        // else, remove the tail from the queue
        else {
            snake.removeFirst();
        }
        // check if the snake collided with itself
        if (collision(nr, nc)) return -1;

        // update the head and add it to the queue
        head = new Pair<>(nr, nc);
        snake.addLast(head);

        // return updated food index = score
        return i;
    }

    /**
     * checks if a given row, column are
     * colliding with the snake body
     *
     * @param r row coordinate
     * @param c column coordinate
     * @return if colliding
     */
    boolean collision(int r, int c) {
        for (Pair<Integer, Integer> pair : snake) {
            if (r == pair.key() && c == pair.value()) {
                return true;
            }
        }
        return false;
    }

    /**
     * checks if a given row, column are
     * out of bounds of the arena
     *
     * @param r row coordinate
     * @param c column coordinate
     * @return whether outOfBounds
     */
    boolean outOfBounds(int r, int c) {
        return r < 0 || c < 0 || r >= height || c >= width;
    }

    /**
     * get direction corresponding to the given string
     *
     * @param direction String dir (U, D, L, R)
     * @return corresponding coordinate Pair
     */
    Pair<Integer, Integer> getDirection(String direction) {
        int dirIndex = switch (direction) {
            case "U" -> 0;
            case "D" -> 1;
            case "L" -> 2;
            case "R" -> 3;
            default -> 0;
        };
        return dirs.get(dirIndex);
    }
}