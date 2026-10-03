import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Vector;

public class ListManagerApp extends Frame {
    private final Vector<String> sourceData = new Vector<>();
    private final Vector<String> secondListData = new Vector<>();
    private final Vector<String> originalData = new Vector<>();

    private final List sourceList = new List(10, false);
    private final List secondList = new List(10, false);

    private final Checkbox oddDeleteCheck = new Checkbox("Удалить нечётные строки");
    private final Checkbox evenMoveCheck = new Checkbox("Перенести чётные строки");
    private final Button refreshButton = new Button("Обновить");
    private final Button clearSecondButton = new Button("Очистить второй список");

    public ListManagerApp() {
        super("Управление списком");

        setLayout(new BorderLayout(10, 10));
        setSize(600, 400);
        setLocationRelativeTo(null);

        originalData.addAll(Arrays.asList(
                "Элемент 1", "Элемент 2", "Элемент 3", "Элемент 4",
                "Элемент 5", "Элемент 6", "Элемент 7", "Элемент 8",
                "Элемент 9", "Элемент 10"
        ));
        sourceData.addAll(originalData);

        Panel topPanel = new Panel(new FlowLayout(FlowLayout.LEFT, 15, 10));
        topPanel.add(oddDeleteCheck);
        topPanel.add(evenMoveCheck);
        topPanel.add(refreshButton);
        topPanel.add(clearSecondButton);

        Panel centerPanel = new Panel(new GridLayout(1, 2, 20, 10));

        Panel leftPanel = new Panel(new BorderLayout());
        leftPanel.add(new Label("Основной список", Label.CENTER), BorderLayout.NORTH);
        leftPanel.add(sourceList, BorderLayout.CENTER);

        Panel rightPanel = new Panel(new BorderLayout());
        rightPanel.add(new Label("Второй список", Label.CENTER), BorderLayout.NORTH);
        rightPanel.add(secondList, BorderLayout.CENTER);

        centerPanel.add(leftPanel);
        centerPanel.add(rightPanel);

        add(topPanel, BorderLayout.NORTH);
        add(centerPanel, BorderLayout.CENTER);

        refreshLists();

        oddDeleteCheck.addItemListener(e -> {
            if (oddDeleteCheck.getState()) {
                deleteOddItems();
                oddDeleteCheck.setState(false);
            }
        });

        evenMoveCheck.addItemListener(e -> {
            if (evenMoveCheck.getState()) {
                moveEvenItemsToSecondList();
                evenMoveCheck.setState(false);
            }
        });

        refreshButton.addActionListener(e -> {
            sourceData.clear();
            sourceData.addAll(originalData);
            secondListData.clear();
            refreshLists();
        });

        clearSecondButton.addActionListener(e -> {
            secondListData.clear();
            refreshLists();
        });

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                dispose();
            }
        });
    }

    private void refreshLists() {
        sourceList.removeAll();
        secondList.removeAll();

        for (String item : sourceData) {
            sourceList.add(item);
        }

        for (String item : secondListData) {
            secondList.add(item);
        }
    }

    /**
     * Удаляет все нечётные строки (1-я, 3-я, 5-я, ...)
     * Используем новый вектор для хранения только чётных элементов
     */
    private void deleteOddItems() {
        Vector<String> newData = new Vector<>();

        // Копируем только чётные элементы (по позиции)
        for (int i = 0; i < sourceData.size(); i++) {
            if ((i + 1) % 2 == 0) { // чётные позиции: 2, 4, 6, 8...
                newData.add(sourceData.get(i));
            }
        }

        sourceData.clear();
        sourceData.addAll(newData);
        refreshLists();
    }

    /**
     * Переносит все чётные строки (2-я, 4-я, 6-я, ...) во второй список
     */
    private void moveEvenItemsToSecondList() {
        Vector<String> newData = new Vector<>();
        Vector<String> toMove = new Vector<>();

        // Разделяем на нечётные (остаются) и чётные (переносятся)
        for (int i = 0; i < sourceData.size(); i++) {
            if ((i + 1) % 2 == 0) { // чётные позиции: 2, 4, 6, 8...
                toMove.add(sourceData.get(i));
            } else { // нечётные позиции: 1, 3, 5, 7...
                newData.add(sourceData.get(i));
            }
        }

        // Обновляем оба списка
        sourceData.clear();
        sourceData.addAll(newData);
        secondListData.addAll(toMove);
        refreshLists();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ListManagerApp app = new ListManagerApp();
            app.setVisible(true);
        });
    }
}
