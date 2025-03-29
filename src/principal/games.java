/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package principal;
import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import raven.scroll.win11.ScrollPaneWin11;
import principal.frmMain.Juego;
import static principal.frmMain.gmIndividual;
/**
 *
 * @author dell
 */
public class games extends javax.swing.JFrame {
    private frmMain mainFrame;
    /**
     * Creates new form games
     */
    public games(frmMain mainFrame) {
        this.mainFrame = mainFrame;
        initComponents();
        
        
        setLocationRelativeTo(null);
        setTitle("UnetGames");
        
    }

    public void actualizarTarjetas(List<Juego> juegos) {
        // Lista de paneles que representan las tarjetas
        List<JPanel> tarjetas = List.of(jPanel4, jPanel10, jPanel13, jPanel9,
                                        jPanel17, jPanel22, jPanel25, jPanel28,
                                        jPanel43, jPanel46, jPanel49, jPanel52,
                                        jPanel56, jPanel59, jPanel62, jPanel65);

        // Lista de etiquetas para las imágenes de los juegos
        List<JLabel> imagenes = List.of(jLabel12, jLabel15, jLabel24, jLabel17,
                                        jLabel20, jLabel27, jLabel30, jLabel35,
                                        jLabel38, jLabel41, jLabel44, jLabel48,
                                        jLabel45, jLabel53, jLabel56, jLabel59);

        // Lista de etiquetas para los títulos de los juegos
        List<JLabel> titulos = List.of(jLabel11, jLabel16, jLabel25, jLabel18,
                                       jLabel21, jLabel28, jLabel31, jLabel36,
                                       jLabel39, jLabel42, jLabel46, jLabel49,
                                       jLabel51, jLabel54, jLabel57, jLabel60);

        // Lista de etiquetas para los nombres de los creadores
        List<JLabel> creadores = List.of(jLabel14, jLabel23, jLabel26, jLabel19,
                                         jLabel22, jLabel29, jLabel32, jLabel37,
                                         jLabel40, jLabel43, jLabel47, jLabel50,
                                         jLabel52, jLabel55, jLabel58, jLabel61);

        // Actualizar las tarjetas con los juegos
        for (int i = 0; i < juegos.size(); i++) {
            Juego juego = juegos.get(i);
            final int index = i; // Declarar index como final

            // Actualizar el título del juego
            titulos.get(i).setText(juego.getTitulo());

            // Actualizar el nombre del creador
            creadores.get(i).setText("@" + juego.getUsername());

            // Actualizar la imagen del juego
            String coverPath = juego.getCoverPath();
            File file = new File(coverPath);
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                imagenes.get(i).setIcon(icon);
            } else {
                System.err.println("No se encontró el archivo: " + coverPath);
            }

            // Agregar un MouseListener a la tarjeta
            JPanel tarjeta = tarjetas.get(i);
            tarjeta.addMouseListener(new MouseAdapter() {
                @Override
                public void mouseClicked(MouseEvent e) {
                    // Obtener el juego seleccionado usando index (efectivamente final)
                    Juego juegoSeleccionado = juegos.get(index); // 

                    // Llamar al método de frmMain para mostrar la vista individual
                    mainFrame.mostrarVistaIndividual(juegoSeleccionado);
                }
            });
        }

        // Ocultar los paneles que no tienen información
        for (int i = juegos.size(); i < tarjetas.size(); i++) {
            tarjetas.get(i).setVisible(false); // Ocultar el panel
        }

        // Mostrar los paneles que tienen información
        for (int i = 0; i < juegos.size(); i++) {
            tarjetas.get(i).setVisible(true); // Mostrar el panel
        }
    }
    
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        bgBodyPanel = new javax.swing.JPanel();
        jScrollPane1 = new raven.scroll.win11.ScrollPaneWin11();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel8 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jPanel14 = new javax.swing.JPanel();
        jLabel24 = new javax.swing.JLabel();
        jPanel15 = new javax.swing.JPanel();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jPanel19 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jPanel16 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jPanel21 = new javax.swing.JPanel();
        jLabel21 = new javax.swing.JLabel();
        jLabel22 = new javax.swing.JLabel();
        jPanel22 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jLabel27 = new javax.swing.JLabel();
        jPanel24 = new javax.swing.JPanel();
        jLabel28 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        jPanel25 = new javax.swing.JPanel();
        jPanel26 = new javax.swing.JPanel();
        jLabel30 = new javax.swing.JLabel();
        jPanel27 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        jLabel32 = new javax.swing.JLabel();
        jPanel28 = new javax.swing.JPanel();
        jPanel40 = new javax.swing.JPanel();
        jLabel35 = new javax.swing.JLabel();
        jPanel41 = new javax.swing.JPanel();
        jLabel36 = new javax.swing.JLabel();
        jLabel37 = new javax.swing.JLabel();
        jPanel42 = new javax.swing.JPanel();
        jPanel43 = new javax.swing.JPanel();
        jPanel44 = new javax.swing.JPanel();
        jLabel38 = new javax.swing.JLabel();
        jPanel45 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        jPanel46 = new javax.swing.JPanel();
        jPanel47 = new javax.swing.JPanel();
        jLabel41 = new javax.swing.JLabel();
        jPanel48 = new javax.swing.JPanel();
        jLabel42 = new javax.swing.JLabel();
        jLabel43 = new javax.swing.JLabel();
        jPanel49 = new javax.swing.JPanel();
        jPanel50 = new javax.swing.JPanel();
        jLabel44 = new javax.swing.JLabel();
        jPanel51 = new javax.swing.JPanel();
        jLabel46 = new javax.swing.JLabel();
        jLabel47 = new javax.swing.JLabel();
        jPanel52 = new javax.swing.JPanel();
        jPanel53 = new javax.swing.JPanel();
        jLabel48 = new javax.swing.JLabel();
        jPanel54 = new javax.swing.JPanel();
        jLabel49 = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        jPanel55 = new javax.swing.JPanel();
        jPanel56 = new javax.swing.JPanel();
        jPanel57 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
        jPanel58 = new javax.swing.JPanel();
        jLabel51 = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        jPanel59 = new javax.swing.JPanel();
        jPanel60 = new javax.swing.JPanel();
        jLabel53 = new javax.swing.JLabel();
        jPanel61 = new javax.swing.JPanel();
        jLabel54 = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        jPanel62 = new javax.swing.JPanel();
        jPanel63 = new javax.swing.JPanel();
        jLabel56 = new javax.swing.JLabel();
        jPanel64 = new javax.swing.JPanel();
        jLabel57 = new javax.swing.JLabel();
        jLabel58 = new javax.swing.JLabel();
        jPanel65 = new javax.swing.JPanel();
        jPanel66 = new javax.swing.JPanel();
        jLabel59 = new javax.swing.JLabel();
        jPanel67 = new javax.swing.JPanel();
        jLabel60 = new javax.swing.JLabel();
        jLabel61 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1139, 637));

        bgBodyPanel.setBackground(new java.awt.Color(2, 21, 38));

        jPanel1.setBackground(new java.awt.Color(2, 21, 38));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel3.setBackground(new java.awt.Color(2, 21, 38));
        jPanel3.setMinimumSize(new java.awt.Dimension(200, 120));
        jPanel3.setPreferredSize(new java.awt.Dimension(952, 160));
        jPanel3.setLayout(new java.awt.GridBagLayout());

        jPanel4.setBackground(new java.awt.Color(25, 40, 56));
        jPanel4.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel4.setMinimumSize(new java.awt.Dimension(220, 110));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.X_AXIS));

        jPanel8.setOpaque(false);
        java.awt.FlowLayout flowLayout1 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout1.setAlignOnBaseline(true);
        jPanel8.setLayout(flowLayout1);

        jLabel12.setBackground(new java.awt.Color(0, 0, 0));
        jLabel12.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel12.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel12.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel12.setOpaque(true);
        jLabel12.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel8.add(jLabel12);

        jPanel4.add(jPanel8);

        jPanel6.setOpaque(false);
        jPanel6.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel6.setLayout(new java.awt.GridLayout(2, 1));

        jLabel11.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(234, 250, 255));
        jLabel11.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel11.setText("pacman");
        jLabel11.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(jLabel11);

        jLabel14.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(234, 250, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("@tiagoddd279");
        jLabel14.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel6.add(jLabel14);

        jPanel4.add(jPanel6);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel3.add(jPanel4, gridBagConstraints);

        jPanel10.setBackground(new java.awt.Color(25, 40, 56));
        jPanel10.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel10.setMinimumSize(new java.awt.Dimension(200, 110));
        jPanel10.setLayout(new javax.swing.BoxLayout(jPanel10, javax.swing.BoxLayout.X_AXIS));

        jPanel11.setOpaque(false);
        java.awt.FlowLayout flowLayout2 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout2.setAlignOnBaseline(true);
        jPanel11.setLayout(flowLayout2);

        jLabel15.setBackground(new java.awt.Color(0, 0, 0));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel15.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel15.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel15.setOpaque(true);
        jLabel15.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel11.add(jLabel15);

        jPanel10.add(jPanel11);

        jPanel12.setOpaque(false);
        jPanel12.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel12.setLayout(new java.awt.GridLayout(2, 1));

        jLabel16.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(234, 250, 255));
        jLabel16.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel16.setText("pacman");
        jLabel16.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel12.add(jLabel16);

        jLabel23.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel23.setForeground(new java.awt.Color(234, 250, 255));
        jLabel23.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel23.setText("@tiagoddd279");
        jLabel23.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel12.add(jLabel23);

        jPanel10.add(jPanel12);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel3.add(jPanel10, gridBagConstraints);

        jPanel13.setBackground(new java.awt.Color(25, 40, 56));
        jPanel13.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel13.setMinimumSize(new java.awt.Dimension(200, 110));
        jPanel13.setLayout(new javax.swing.BoxLayout(jPanel13, javax.swing.BoxLayout.X_AXIS));

        jPanel14.setOpaque(false);
        java.awt.FlowLayout flowLayout3 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout3.setAlignOnBaseline(true);
        jPanel14.setLayout(flowLayout3);

        jLabel24.setBackground(new java.awt.Color(0, 0, 0));
        jLabel24.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel24.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel24.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel24.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel24.setOpaque(true);
        jLabel24.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel14.add(jLabel24);

        jPanel13.add(jPanel14);

        jPanel15.setOpaque(false);
        jPanel15.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel15.setLayout(new java.awt.GridLayout(2, 1));

        jLabel25.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel25.setForeground(new java.awt.Color(234, 250, 255));
        jLabel25.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel25.setText("pacman");
        jLabel25.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel15.add(jLabel25);

        jLabel26.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel26.setForeground(new java.awt.Color(234, 250, 255));
        jLabel26.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel26.setText("@tiagoddd279");
        jLabel26.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel15.add(jLabel26);

        jPanel13.add(jPanel15);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel3.add(jPanel13, gridBagConstraints);

        jPanel9.setBackground(new java.awt.Color(25, 40, 56));
        jPanel9.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel9.setMinimumSize(new java.awt.Dimension(220, 110));
        jPanel9.setLayout(new javax.swing.BoxLayout(jPanel9, javax.swing.BoxLayout.X_AXIS));

        jPanel19.setOpaque(false);
        java.awt.FlowLayout flowLayout4 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout4.setAlignOnBaseline(true);
        jPanel19.setLayout(flowLayout4);

        jLabel17.setBackground(new java.awt.Color(0, 0, 0));
        jLabel17.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel17.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel17.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel17.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel17.setOpaque(true);
        jLabel17.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel19.add(jLabel17);

        jPanel9.add(jPanel19);

        jPanel20.setOpaque(false);
        jPanel20.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel20.setLayout(new java.awt.GridLayout(2, 1));

        jLabel18.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(234, 250, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("pacman");
        jLabel18.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel20.add(jLabel18);

        jLabel19.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(234, 250, 255));
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("@tiagoddd279");
        jLabel19.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel20.add(jLabel19);

        jPanel9.add(jPanel20);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel3.add(jPanel9, gridBagConstraints);

        jPanel1.add(jPanel3);

        jPanel16.setBackground(new java.awt.Color(2, 21, 38));
        jPanel16.setMinimumSize(new java.awt.Dimension(200, 120));
        jPanel16.setPreferredSize(new java.awt.Dimension(952, 160));
        jPanel16.setLayout(new java.awt.GridBagLayout());

        jPanel17.setBackground(new java.awt.Color(25, 40, 56));
        jPanel17.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel17.setMinimumSize(new java.awt.Dimension(220, 110));
        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.X_AXIS));

        jPanel18.setOpaque(false);
        java.awt.FlowLayout flowLayout5 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout5.setAlignOnBaseline(true);
        jPanel18.setLayout(flowLayout5);

        jLabel20.setBackground(new java.awt.Color(0, 0, 0));
        jLabel20.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel20.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel20.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel20.setOpaque(true);
        jLabel20.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel18.add(jLabel20);

        jPanel17.add(jPanel18);

        jPanel21.setOpaque(false);
        jPanel21.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel21.setLayout(new java.awt.GridLayout(2, 1));

        jLabel21.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(234, 250, 255));
        jLabel21.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel21.setText("pacman");
        jLabel21.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel21.add(jLabel21);

        jLabel22.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(234, 250, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("@tiagoddd279");
        jLabel22.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel21.add(jLabel22);

        jPanel17.add(jPanel21);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel16.add(jPanel17, gridBagConstraints);

        jPanel22.setBackground(new java.awt.Color(25, 40, 56));
        jPanel22.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel22.setMinimumSize(new java.awt.Dimension(200, 110));
        jPanel22.setLayout(new javax.swing.BoxLayout(jPanel22, javax.swing.BoxLayout.X_AXIS));

        jPanel23.setOpaque(false);
        java.awt.FlowLayout flowLayout6 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout6.setAlignOnBaseline(true);
        jPanel23.setLayout(flowLayout6);

        jLabel27.setBackground(new java.awt.Color(0, 0, 0));
        jLabel27.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel27.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel27.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel27.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel27.setOpaque(true);
        jLabel27.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel23.add(jLabel27);

        jPanel22.add(jPanel23);

        jPanel24.setOpaque(false);
        jPanel24.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel24.setLayout(new java.awt.GridLayout(2, 1));

        jLabel28.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(234, 250, 255));
        jLabel28.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel28.setText("pacman");
        jLabel28.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel24.add(jLabel28);

        jLabel29.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(234, 250, 255));
        jLabel29.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel29.setText("@tiagoddd279");
        jLabel29.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel24.add(jLabel29);

        jPanel22.add(jPanel24);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel16.add(jPanel22, gridBagConstraints);

        jPanel25.setBackground(new java.awt.Color(25, 40, 56));
        jPanel25.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel25.setMinimumSize(new java.awt.Dimension(200, 110));
        jPanel25.setLayout(new javax.swing.BoxLayout(jPanel25, javax.swing.BoxLayout.X_AXIS));

        jPanel26.setOpaque(false);
        java.awt.FlowLayout flowLayout7 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout7.setAlignOnBaseline(true);
        jPanel26.setLayout(flowLayout7);

        jLabel30.setBackground(new java.awt.Color(0, 0, 0));
        jLabel30.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel30.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel30.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel30.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel30.setOpaque(true);
        jLabel30.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel26.add(jLabel30);

        jPanel25.add(jPanel26);

        jPanel27.setOpaque(false);
        jPanel27.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel27.setLayout(new java.awt.GridLayout(2, 1));

        jLabel31.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel31.setForeground(new java.awt.Color(234, 250, 255));
        jLabel31.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel31.setText("pacman");
        jLabel31.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel27.add(jLabel31);

        jLabel32.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel32.setForeground(new java.awt.Color(234, 250, 255));
        jLabel32.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel32.setText("@tiagoddd279");
        jLabel32.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel27.add(jLabel32);

        jPanel25.add(jPanel27);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel16.add(jPanel25, gridBagConstraints);

        jPanel28.setBackground(new java.awt.Color(25, 40, 56));
        jPanel28.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel28.setMinimumSize(new java.awt.Dimension(220, 110));
        jPanel28.setLayout(new javax.swing.BoxLayout(jPanel28, javax.swing.BoxLayout.X_AXIS));

        jPanel40.setOpaque(false);
        java.awt.FlowLayout flowLayout8 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout8.setAlignOnBaseline(true);
        jPanel40.setLayout(flowLayout8);

        jLabel35.setBackground(new java.awt.Color(0, 0, 0));
        jLabel35.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel35.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel35.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel35.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel35.setOpaque(true);
        jLabel35.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel40.add(jLabel35);

        jPanel28.add(jPanel40);

        jPanel41.setOpaque(false);
        jPanel41.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel41.setLayout(new java.awt.GridLayout(2, 1));

        jLabel36.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel36.setForeground(new java.awt.Color(234, 250, 255));
        jLabel36.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel36.setText("pacman");
        jLabel36.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel41.add(jLabel36);

        jLabel37.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel37.setForeground(new java.awt.Color(234, 250, 255));
        jLabel37.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel37.setText("@tiagoddd279");
        jLabel37.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel41.add(jLabel37);

        jPanel28.add(jPanel41);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel16.add(jPanel28, gridBagConstraints);

        jPanel1.add(jPanel16);

        jPanel42.setBackground(new java.awt.Color(2, 21, 38));
        jPanel42.setMinimumSize(new java.awt.Dimension(200, 120));
        jPanel42.setPreferredSize(new java.awt.Dimension(952, 160));
        jPanel42.setLayout(new java.awt.GridBagLayout());

        jPanel43.setBackground(new java.awt.Color(25, 40, 56));
        jPanel43.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel43.setMinimumSize(new java.awt.Dimension(220, 110));
        jPanel43.setLayout(new javax.swing.BoxLayout(jPanel43, javax.swing.BoxLayout.X_AXIS));

        jPanel44.setOpaque(false);
        java.awt.FlowLayout flowLayout9 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout9.setAlignOnBaseline(true);
        jPanel44.setLayout(flowLayout9);

        jLabel38.setBackground(new java.awt.Color(0, 0, 0));
        jLabel38.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel38.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel38.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel38.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel38.setOpaque(true);
        jLabel38.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel44.add(jLabel38);

        jPanel43.add(jPanel44);

        jPanel45.setOpaque(false);
        jPanel45.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel45.setLayout(new java.awt.GridLayout(2, 1));

        jLabel39.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(234, 250, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("pacman");
        jLabel39.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel45.add(jLabel39);

        jLabel40.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel40.setForeground(new java.awt.Color(234, 250, 255));
        jLabel40.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel40.setText("@tiagoddd279");
        jLabel40.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel45.add(jLabel40);

        jPanel43.add(jPanel45);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel42.add(jPanel43, gridBagConstraints);

        jPanel46.setBackground(new java.awt.Color(25, 40, 56));
        jPanel46.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel46.setMinimumSize(new java.awt.Dimension(200, 110));
        jPanel46.setLayout(new javax.swing.BoxLayout(jPanel46, javax.swing.BoxLayout.X_AXIS));

        jPanel47.setOpaque(false);
        java.awt.FlowLayout flowLayout10 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout10.setAlignOnBaseline(true);
        jPanel47.setLayout(flowLayout10);

        jLabel41.setBackground(new java.awt.Color(0, 0, 0));
        jLabel41.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel41.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel41.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel41.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel41.setOpaque(true);
        jLabel41.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel47.add(jLabel41);

        jPanel46.add(jPanel47);

        jPanel48.setOpaque(false);
        jPanel48.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel48.setLayout(new java.awt.GridLayout(2, 1));

        jLabel42.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(234, 250, 255));
        jLabel42.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel42.setText("pacman");
        jLabel42.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel48.add(jLabel42);

        jLabel43.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel43.setForeground(new java.awt.Color(234, 250, 255));
        jLabel43.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel43.setText("@tiagoddd279");
        jLabel43.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel48.add(jLabel43);

        jPanel46.add(jPanel48);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel42.add(jPanel46, gridBagConstraints);

        jPanel49.setBackground(new java.awt.Color(25, 40, 56));
        jPanel49.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel49.setMinimumSize(new java.awt.Dimension(200, 110));
        jPanel49.setLayout(new javax.swing.BoxLayout(jPanel49, javax.swing.BoxLayout.X_AXIS));

        jPanel50.setOpaque(false);
        java.awt.FlowLayout flowLayout11 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout11.setAlignOnBaseline(true);
        jPanel50.setLayout(flowLayout11);

        jLabel44.setBackground(new java.awt.Color(0, 0, 0));
        jLabel44.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel44.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel44.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel44.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel44.setOpaque(true);
        jLabel44.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel50.add(jLabel44);

        jPanel49.add(jPanel50);

        jPanel51.setOpaque(false);
        jPanel51.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel51.setLayout(new java.awt.GridLayout(2, 1));

        jLabel46.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel46.setForeground(new java.awt.Color(234, 250, 255));
        jLabel46.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel46.setText("pacman");
        jLabel46.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel51.add(jLabel46);

        jLabel47.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel47.setForeground(new java.awt.Color(234, 250, 255));
        jLabel47.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel47.setText("@tiagoddd279");
        jLabel47.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel51.add(jLabel47);

        jPanel49.add(jPanel51);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel42.add(jPanel49, gridBagConstraints);

        jPanel52.setBackground(new java.awt.Color(25, 40, 56));
        jPanel52.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel52.setMinimumSize(new java.awt.Dimension(220, 110));
        jPanel52.setLayout(new javax.swing.BoxLayout(jPanel52, javax.swing.BoxLayout.X_AXIS));

        jPanel53.setOpaque(false);
        java.awt.FlowLayout flowLayout12 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout12.setAlignOnBaseline(true);
        jPanel53.setLayout(flowLayout12);

        jLabel48.setBackground(new java.awt.Color(0, 0, 0));
        jLabel48.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel48.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel48.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel48.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel48.setOpaque(true);
        jLabel48.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel53.add(jLabel48);

        jPanel52.add(jPanel53);

        jPanel54.setOpaque(false);
        jPanel54.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel54.setLayout(new java.awt.GridLayout(2, 1));

        jLabel49.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel49.setForeground(new java.awt.Color(234, 250, 255));
        jLabel49.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel49.setText("pacman");
        jLabel49.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel54.add(jLabel49);

        jLabel50.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(234, 250, 255));
        jLabel50.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel50.setText("@tiagoddd279");
        jLabel50.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel54.add(jLabel50);

        jPanel52.add(jPanel54);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel42.add(jPanel52, gridBagConstraints);

        jPanel1.add(jPanel42);

        jPanel55.setBackground(new java.awt.Color(2, 21, 38));
        jPanel55.setMinimumSize(new java.awt.Dimension(200, 120));
        jPanel55.setPreferredSize(new java.awt.Dimension(952, 160));
        jPanel55.setLayout(new java.awt.GridBagLayout());

        jPanel56.setBackground(new java.awt.Color(25, 40, 56));
        jPanel56.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel56.setMinimumSize(new java.awt.Dimension(220, 110));
        jPanel56.setLayout(new javax.swing.BoxLayout(jPanel56, javax.swing.BoxLayout.X_AXIS));

        jPanel57.setOpaque(false);
        java.awt.FlowLayout flowLayout25 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout25.setAlignOnBaseline(true);
        jPanel57.setLayout(flowLayout25);

        jLabel45.setBackground(new java.awt.Color(0, 0, 0));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel45.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel45.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel45.setOpaque(true);
        jLabel45.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel57.add(jLabel45);

        jPanel56.add(jPanel57);

        jPanel58.setOpaque(false);
        jPanel58.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel58.setLayout(new java.awt.GridLayout(2, 1));

        jLabel51.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(234, 250, 255));
        jLabel51.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel51.setText("pacman");
        jLabel51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel58.add(jLabel51);

        jLabel52.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(234, 250, 255));
        jLabel52.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel52.setText("@tiagoddd279");
        jLabel52.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel58.add(jLabel52);

        jPanel56.add(jPanel58);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel55.add(jPanel56, gridBagConstraints);

        jPanel59.setBackground(new java.awt.Color(25, 40, 56));
        jPanel59.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel59.setMinimumSize(new java.awt.Dimension(200, 110));
        jPanel59.setLayout(new javax.swing.BoxLayout(jPanel59, javax.swing.BoxLayout.X_AXIS));

        jPanel60.setOpaque(false);
        java.awt.FlowLayout flowLayout26 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout26.setAlignOnBaseline(true);
        jPanel60.setLayout(flowLayout26);

        jLabel53.setBackground(new java.awt.Color(0, 0, 0));
        jLabel53.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel53.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel53.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel53.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel53.setOpaque(true);
        jLabel53.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel60.add(jLabel53);

        jPanel59.add(jPanel60);

        jPanel61.setOpaque(false);
        jPanel61.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel61.setLayout(new java.awt.GridLayout(2, 1));

        jLabel54.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel54.setForeground(new java.awt.Color(234, 250, 255));
        jLabel54.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel54.setText("pacman");
        jLabel54.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel61.add(jLabel54);

        jLabel55.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(234, 250, 255));
        jLabel55.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel55.setText("@tiagoddd279");
        jLabel55.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel61.add(jLabel55);

        jPanel59.add(jPanel61);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel55.add(jPanel59, gridBagConstraints);

        jPanel62.setBackground(new java.awt.Color(25, 40, 56));
        jPanel62.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel62.setMinimumSize(new java.awt.Dimension(200, 110));
        jPanel62.setLayout(new javax.swing.BoxLayout(jPanel62, javax.swing.BoxLayout.X_AXIS));

        jPanel63.setOpaque(false);
        java.awt.FlowLayout flowLayout27 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout27.setAlignOnBaseline(true);
        jPanel63.setLayout(flowLayout27);

        jLabel56.setBackground(new java.awt.Color(0, 0, 0));
        jLabel56.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel56.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel56.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel56.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel56.setOpaque(true);
        jLabel56.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel63.add(jLabel56);

        jPanel62.add(jPanel63);

        jPanel64.setOpaque(false);
        jPanel64.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel64.setLayout(new java.awt.GridLayout(2, 1));

        jLabel57.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel57.setForeground(new java.awt.Color(234, 250, 255));
        jLabel57.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel57.setText("pacman");
        jLabel57.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel64.add(jLabel57);

        jLabel58.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel58.setForeground(new java.awt.Color(234, 250, 255));
        jLabel58.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel58.setText("@tiagoddd279");
        jLabel58.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel64.add(jLabel58);

        jPanel62.add(jPanel64);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel55.add(jPanel62, gridBagConstraints);

        jPanel65.setBackground(new java.awt.Color(25, 40, 56));
        jPanel65.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel65.setMinimumSize(new java.awt.Dimension(220, 110));
        jPanel65.setLayout(new javax.swing.BoxLayout(jPanel65, javax.swing.BoxLayout.X_AXIS));

        jPanel66.setOpaque(false);
        java.awt.FlowLayout flowLayout28 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout28.setAlignOnBaseline(true);
        jPanel66.setLayout(flowLayout28);

        jLabel59.setBackground(new java.awt.Color(0, 0, 0));
        jLabel59.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel59.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jLabel59.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel59.setMinimumSize(new java.awt.Dimension(90, 90));
        jLabel59.setOpaque(true);
        jLabel59.setPreferredSize(new java.awt.Dimension(90, 90));
        jPanel66.add(jLabel59);

        jPanel65.add(jPanel66);

        jPanel67.setOpaque(false);
        jPanel67.setPreferredSize(new java.awt.Dimension(110, 110));
        jPanel67.setLayout(new java.awt.GridLayout(2, 1));

        jLabel60.setFont(new java.awt.Font("Segoe UI", 0, 18)); // NOI18N
        jLabel60.setForeground(new java.awt.Color(234, 250, 255));
        jLabel60.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel60.setText("pacman");
        jLabel60.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel67.add(jLabel60);

        jLabel61.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel61.setForeground(new java.awt.Color(234, 250, 255));
        jLabel61.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel61.setText("@tiagoddd279");
        jLabel61.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel67.add(jLabel61);

        jPanel65.add(jPanel67);

        gridBagConstraints = new java.awt.GridBagConstraints();
        gridBagConstraints.fill = java.awt.GridBagConstraints.HORIZONTAL;
        gridBagConstraints.insets = new java.awt.Insets(9, 9, 9, 9);
        jPanel55.add(jPanel65, gridBagConstraints);

        jPanel1.add(jPanel55);

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout bgBodyPanelLayout = new javax.swing.GroupLayout(bgBodyPanel);
        bgBodyPanel.setLayout(bgBodyPanelLayout);
        bgBodyPanelLayout.setHorizontalGroup(
            bgBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        bgBodyPanelLayout.setVerticalGroup(
            bgBodyPanelLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 540, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bgBodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(bgBodyPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents
    
    

    
    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        
    }//GEN-LAST:event_jPanel4MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(games.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(games.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(games.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(games.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                // Crear una instancia de frmMain
                frmMain mainFrame = new frmMain();

                // Pasar la referencia de frmMain al constructor de games
                new games(mainFrame).setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel bgBodyPanel;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel43;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel45;
    private javax.swing.JLabel jLabel46;
    private javax.swing.JLabel jLabel47;
    private javax.swing.JLabel jLabel48;
    private javax.swing.JLabel jLabel49;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel53;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel56;
    private javax.swing.JLabel jLabel57;
    private javax.swing.JLabel jLabel58;
    private javax.swing.JLabel jLabel59;
    private javax.swing.JLabel jLabel60;
    private javax.swing.JLabel jLabel61;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel40;
    private javax.swing.JPanel jPanel41;
    private javax.swing.JPanel jPanel42;
    private javax.swing.JPanel jPanel43;
    private javax.swing.JPanel jPanel44;
    private javax.swing.JPanel jPanel45;
    private javax.swing.JPanel jPanel46;
    private javax.swing.JPanel jPanel47;
    private javax.swing.JPanel jPanel48;
    private javax.swing.JPanel jPanel49;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel55;
    private javax.swing.JPanel jPanel56;
    private javax.swing.JPanel jPanel57;
    private javax.swing.JPanel jPanel58;
    private javax.swing.JPanel jPanel59;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel60;
    private javax.swing.JPanel jPanel61;
    private javax.swing.JPanel jPanel62;
    private javax.swing.JPanel jPanel63;
    private javax.swing.JPanel jPanel64;
    private javax.swing.JPanel jPanel65;
    private javax.swing.JPanel jPanel66;
    private javax.swing.JPanel jPanel67;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    // End of variables declaration//GEN-END:variables
}
