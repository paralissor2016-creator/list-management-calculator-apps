import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;
import java.util.Vector;

public class ListManagerApp extends Frame {
    private final Vector<String> sourceData = new Vector<>();
    private final Vector<String> secondListData = new Vector<>();
    private final Vector<String> originalData = new Vector<>(); // Оригинальные данные

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

        // Начальные данные
        originalData.addAll(Arrays.asList(
                "Элемент 1", "Элемент 2", "Элемент 3", "Элемент 4",
                "Элемент 5", "Элемент 6", "Элемент 7", "Элемент 8",
                "Элемент 9", "Элемент 10"
        ));
        
        sourceData.addAll(originalData); // Копируем в рабочий список

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

        // Восстановление оригинальных данных
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

    private void deleteOddItems() {
        for (int i = sourceData.size() - 1; i >= 0; i--) {
            if ((i + 1) % 2 != 0) { // нечётные строки (1, 3, 5, 7...)
                sourceData.remove(i);
            }
        }
        refreshLists();
    }

    private void moveEvenItemsToSecondList() {
        for (int i = sourceData.size() - 1; i >= 0; i--) {
            if ((i + 1) % 2 == 0) { // чётные строки (2, 4, 6, 8...)
                secondListData.add(sourceData.remove(i));
            }
        }
        refreshLists();
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(() -> {
            ListManagerApp app = new ListManagerApp();
            app.setVisible(true);
        });
    }
}
