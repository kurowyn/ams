package com.miniproject.ui;

import com.miniproject.dao.AbsenceDAO;
import com.miniproject.model.Absence;
import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsPanel extends JPanel {
    private JPanel chartPanel;
    private AbsenceDAO dao;

    public StatsPanel() {
        this.dao = new AbsenceDAO();
        setLayout(new BorderLayout());

        JButton btnRefresh = new JButton("Actualiser Graphique");
        btnRefresh.addActionListener(e -> refreshStats());
        add(btnRefresh, BorderLayout.NORTH);

        chartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                drawChart(g);
            }
        };
        chartPanel.setBackground(Color.WHITE);
        add(chartPanel, BorderLayout.CENTER);
    }

    private void refreshStats() {
        chartPanel.repaint();
    }

    private void drawChart(Graphics g) {
        List<Absence> list = dao.getAllAbsences();
        Map<String, Integer> counts = new HashMap<>();

        for (Absence a : list) {
            // Count total absences (sum of absence_count, or just count occurrences? User
            // usually wants total sessions missed)
            // Using absence_count field:
            counts.put(a.getSubjectName(), counts.getOrDefault(a.getSubjectName(), 0) + a.getCount());
        }

        if (counts.isEmpty()) {
            g.drawString("Aucune donnée disponible", 50, 50);
            return;
        }

        int x = 50;
        int barWidth = 60;
        int maxVal = counts.values().stream().max(Integer::compareTo).orElse(1);
        int maxHeight = getHeight() - 100;

        for (Map.Entry<String, Integer> entry : counts.entrySet()) {
            String subject = entry.getKey();
            int count = entry.getValue();

            int barHeight = (int) ((double) count / maxVal * maxHeight);
            int y = getHeight() - 50 - barHeight;

            g.setColor(new Color(100, 149, 237)); // Cornflower Blue
            g.fillRect(x, y, barWidth, barHeight);

            g.setColor(Color.BLACK);
            g.drawRect(x, y, barWidth, barHeight);

            g.drawString(subject, x, getHeight() - 30);
            g.drawString(String.valueOf(count), x + barWidth / 2 - 5, y - 5);

            x += barWidth + 40;
        }
    }
}
