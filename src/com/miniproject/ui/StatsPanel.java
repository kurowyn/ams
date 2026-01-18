package com.miniproject.ui;

import com.miniproject.dao.AbsenceDAO;
import com.miniproject.model.Absence;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class StatsPanel extends JPanel {
    private AbsenceDAO dao;
    private ChartPanel chartPanel;
    private JComboBox<String> viewSelector;

    // Colors for the chart
    private static final Color[] BAR_COLORS = {
            new Color(79, 129, 189), // Blue
            new Color(192, 80, 77), // Red
            new Color(155, 187, 89), // Green
            new Color(128, 100, 162), // Purple
            new Color(75, 172, 198), // Cyan
            new Color(247, 150, 70) // Orange
    };

    public StatsPanel() {
        this.dao = new AbsenceDAO();
        setLayout(new BorderLayout(10, 10));
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        setBackground(new Color(245, 245, 250));

        // Control Panel
        JPanel controls = new JPanel(new FlowLayout(FlowLayout.LEFT));
        controls.setOpaque(false);

        JLabel lblView = new JLabel("View Statistics By:");
        lblView.setFont(new Font("Segoe UI", Font.BOLD, 14));
        controls.add(lblView);

        viewSelector = new JComboBox<>(new String[] { "Subject", "Class Level" });
        viewSelector.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        viewSelector.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                refreshData();
            }
        });
        controls.add(viewSelector);

        JButton btnRefresh = new JButton("Refresh Data");
        btnRefresh.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        btnRefresh.addActionListener(e -> refreshData());
        controls.add(btnRefresh);

        add(controls, BorderLayout.NORTH);

        // Chart Panel
        chartPanel = new ChartPanel();
        add(chartPanel, BorderLayout.CENTER);

        // Initial Load
        refreshData();
    }

    private void refreshData() {
        String mode = (String) viewSelector.getSelectedItem();
        // Always load raw list which now contains classLevel
        List<Absence> list = dao.getAllAbsences();
        Map<String, Integer> data = new HashMap<>();

        if ("Class Level".equals(mode)) {
            for (Absence a : list) {
                String levelKey = "Level " + a.getClassLevel();
                data.put(levelKey, data.getOrDefault(levelKey, 0) + a.getCount());
            }
            chartPanel.setData("Total Absences by Class Level", data);
        } else {
            // By Subject
            for (Absence a : list) {
                String subject = a.getSubjectName();
                if (subject != null) {
                    data.put(subject, data.getOrDefault(subject, 0) + a.getCount());
                }
            }
            chartPanel.setData("Total Absences by Subject", data);
        }
    }

    // Inner class for custom chart rendering
    private class ChartPanel extends JPanel {
        private String title = "";
        private Map<String, Integer> data = new HashMap<>();
        private final int PADDING = 40;
        private final int LABEL_PADDING = 30;

        public ChartPanel() {
            setBackground(Color.WHITE);
            setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 1));
        }

        public void setData(String title, Map<String, Integer> data) {
            this.title = title;
            this.data = data;
            repaint();
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);
            Graphics2D g2 = (Graphics2D) g;
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            if (data == null || data.isEmpty()) {
                drawNoData(g2);
                return;
            }

            int w = getWidth();
            int h = getHeight();

            // Draw Title
            g2.setFont(new Font("Segoe UI", Font.BOLD, 18));
            FontMetrics titleFm = g2.getFontMetrics();
            int titleWidth = titleFm.stringWidth(title);
            g2.setColor(new Color(50, 50, 50));
            g2.drawString(title, (w - titleWidth) / 2, 30);

            // Calculate chart area
            int chartX = PADDING + LABEL_PADDING;
            int chartY = 50;
            int chartW = w - (2 * PADDING) - LABEL_PADDING;
            int chartH = h - PADDING - 50 - LABEL_PADDING;

            // Draw Axes
            g2.setColor(Color.GRAY);
            g2.drawLine(chartX, chartY + chartH, chartX + chartW, chartY + chartH); // X Axis
            g2.drawLine(chartX, chartY, chartX, chartY + chartH); // Y Axis

            // Determine max value for scaling
            int maxValue = data.values().stream().max(Integer::compareTo).orElse(1);
            // Round up max value to next multiple of 5 for nice grid
            int gridMax = (maxValue / 5 + 1) * 5;

            // Draw Grid Lines & Y Axis Labels
            g2.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            FontMetrics fm = g2.getFontMetrics();

            int gridLines = 5;
            for (int i = 0; i <= gridLines; i++) {
                int value = gridMax * i / gridLines;
                int y = chartY + chartH - (int) ((double) value / gridMax * chartH);

                // Grid line
                g2.setColor(new Color(230, 230, 230));
                g2.drawLine(chartX + 1, y, chartX + chartW, y);

                // Label
                g2.setColor(Color.GRAY);
                String label = String.valueOf(value);
                g2.drawString(label, chartX - fm.stringWidth(label) - 5, y + 5);
            }

            // Draw Bars
            int numBars = data.size();
            if (numBars == 0)
                return;

            int barWidth = Math.min(80, (chartW / numBars) - 20); // Max 80px, with padding
            int barSpace = (chartW - (numBars * barWidth)) / (numBars + 1);

            int x = chartX + barSpace;
            int colorIdx = 0;

            for (Map.Entry<String, Integer> entry : data.entrySet()) {
                String label = entry.getKey();
                int value = entry.getValue();

                int barHeight = (int) ((double) value / gridMax * chartH);
                int y = chartY + chartH - barHeight;

                // Bar
                g2.setColor(BAR_COLORS[colorIdx % BAR_COLORS.length]);
                g2.fillRoundRect(x, y, barWidth, barHeight, 5, 5);

                // Value on top
                g2.setColor(Color.BLACK);
                String valStr = String.valueOf(value);
                int valWidth = fm.stringWidth(valStr);
                g2.drawString(valStr, x + (barWidth - valWidth) / 2, y - 5);

                // Label below
                // Check if label fits, if not truncate
                String displayLabel = label;
                if (fm.stringWidth(label) > barWidth + 10) {
                    // Simple truncation for now
                    displayLabel = label.substring(0, Math.min(label.length(), 6)) + "..";
                }

                int lblWidth = fm.stringWidth(displayLabel);
                g2.drawString(displayLabel, x + (barWidth - lblWidth) / 2, chartY + chartH + 20);

                x += barWidth + barSpace;
                colorIdx++;
            }
        }

        private void drawNoData(Graphics2D g2) {
            String msg = "No data available to display";
            g2.setFont(new Font("Segoe UI", Font.ITALIC, 16));
            g2.setColor(Color.GRAY);
            FontMetrics fm = g2.getFontMetrics();
            int w = fm.stringWidth(msg);
            g2.drawString(msg, (getWidth() - w) / 2, getHeight() / 2);
        }
    }
}
