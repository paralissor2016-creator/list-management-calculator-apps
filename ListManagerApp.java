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
     */
    private void deleteOddItems() {
        Vector<String> toRemove = new Vector<>();
        
        // Собираем все нечётные элементы
        for (int i = 0; i < sourceData.size(); i++) {
            if ((i + 1) % 2 != 0) { // нечётные строки (1, 3, 5, 7...)
                toRemove.add(sourceData.get(i));
            }
        }
        
        // Удаляем все сразу
        sourceData.removeAll(toRemove);
        refreshLists();
    }

    /**
     * Переносит все чётные строки (2-я, 4-я, 6-я, ...) во второй список
     */
    private void moveEvenItemsToSecondList() {
        Vector<String> toMove = new Vector<>();
        
        // Собираем все чётные элементы в правильном порядке
        for (int i = 0; i < sourceData.size(); i++) {
            if ((i + 1) % 2 == 0) { // чётные строки (2, 4, 6, 8...)
                toMove.add(sourceData.get(i));
            }
        }
        
        // ��обавляем во второй список
        secondListData.addAll(toMove);
        
        // Удаляем из основного списка
        sourceData.removeAll(toMove);
        refreshLists();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ListManagerApp app = new ListManagerApp();
            app.setVisible(true);
        });
    }
}
