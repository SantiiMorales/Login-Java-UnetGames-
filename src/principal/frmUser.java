/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package principal;

import conexion.conexionMysql;
import java.io.File;
import java.sql.Connection;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import principal.frmMain.Juego;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.swing.JOptionPane;

/**
 *
 * @author dell
 */
public class frmUser extends javax.swing.JFrame {
    private List<Juego> juegosCreados; // Lista de juegos creados
    private frmMain.UserInfo userInfo;
    conexion.conexionMysql con=new conexionMysql();
    Connection cn=con.conectar();
    
    public frmUser() {
        initComponents();
        
    }
    
    public void setUserInfo(frmMain.UserInfo userInfo) {
    this.userInfo = userInfo;
    this.juegosCreados = userInfo.getJuegosCreados(); // Actualizar la lista de juegos
    mostrarInformacionUsuario(userInfo); // Mostrar la información del usuario
}
    
    private void mostrarInformacionUsuario(frmMain.UserInfo userInfo) {
        // Lista de etiquetas para los títulos de los juegos
        List<JLabel> titulos = List.of(jLabel1, jLabel8);

        // Lista de etiquetas para las imágenes de los juegos
        List<JLabel> imagenes = List.of(jLabel12, jLabel9);

        // Mostrar el username y el email
        jLabel2.setText(userInfo.getUsername());
        jLabel3.setText(userInfo.getEmail());

        // Mostrar los juegos creados
        List<Juego> juegosCreados = userInfo.getJuegosCreados();
        for (int i = 0; i < juegosCreados.size(); i++) {
            Juego juego = juegosCreados.get(i);

            // Actualizar el título del juego
            titulos.get(i).setText(juego.getTitulo());

            // Actualizar la imagen del juego
            String coverPath = juego.getCoverPath();
            File file = new File(coverPath);
            if (file.exists()) {
                ImageIcon icon = new ImageIcon(file.getAbsolutePath());
                imagenes.get(i).setIcon(icon);
            } else {
                System.err.println("No se encontró el archivo: " + coverPath);
            }
            jPanel2.revalidate();
            jPanel2.repaint();
            jPanel3.revalidate();
            jPanel3.repaint();
        }
    }
    
    
    private boolean eliminarJuegoDeBaseDeDatos(Juego juego) {
        Connection cn = null;
        PreparedStatement psCategories = null;
        PreparedStatement psGames = null;

        try {
            // Obtener la conexión a la base de datos
            conexionMysql con = new conexionMysql();
            cn = con.conectar();

            // Deshabilitar el autocommit para manejar una transacción
            cn.setAutoCommit(false);

            // Consulta SQL para eliminar los registros relacionados en categories_games
            String sqlCategories = "DELETE FROM categories_games WHERE game_id = ?";
            psCategories = cn.prepareStatement(sqlCategories);
            psCategories.setInt(1, juego.getGameId()); 
            psCategories.executeUpdate(); 

            // Consulta SQL para eliminar el juego de la tabla games
            String sqlGames = "DELETE FROM games WHERE game_id = ?";
            psGames = cn.prepareStatement(sqlGames);
            psGames.setInt(1, juego.getGameId()); // Asignar el ID del juego
            int filasAfectadas = psGames.executeUpdate(); // Ejecutar la consulta

            // Confirmar la transacción
            cn.commit();

            // Verificar si se eliminó correctamente
            return filasAfectadas > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            // Revertir la transacción en caso de error
            try {
                if (cn != null) cn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            return false;
        } finally {
            // Restaurar el autocommit y cerrar la conexión y los PreparedStatement
            try {
                if (cn != null) cn.setAutoCommit(true);
                if (psCategories != null) psCategories.close();
                if (psGames != null) psGames.close();
                if (cn != null) cn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
    
    private boolean eliminarArchivoDelJuego(Juego juego) {
        try {
            // Obtener la ruta del archivo del juego
            String rutaArchivo = juego.getGameFile();
            String rutaCover = juego.getCoverPath();
            File archivo = new File(rutaArchivo);
            File cover = new File(rutaCover);

            // Verificar si el archivo existe y eliminarlo
            if (archivo.exists() && cover.exists()) {
                return cover.delete() && archivo.delete(); // Eliminar archivo archivos
            } else {
                System.err.println("El archivo no existe: " + rutaArchivo);
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BgPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Body = new javax.swing.JPanel();
        contenedor = new javax.swing.JPanel();
        bodyContainer = new javax.swing.JPanel();
        usernameTitle = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        mailPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        gamesTitlePanel = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        gamesPanel = new javax.swing.JPanel();
        jPanelJuegos = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        InternTags = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        deleteGame1 = new javax.swing.JPanel();
        signInTxt3 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        deleteGame2 = new javax.swing.JPanel();
        signInTxt4 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1039, 971));
        setPreferredSize(new java.awt.Dimension(1039, 971));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        BgPanel.setBackground(new java.awt.Color(2, 21, 38));
        BgPanel.setLayout(new java.awt.BorderLayout());

        Body.setBackground(new java.awt.Color(2, 21, 38));
        Body.setBorder(javax.swing.BorderFactory.createEmptyBorder(70, 150, 70, 150));
        Body.setLayout(new javax.swing.BoxLayout(Body, javax.swing.BoxLayout.LINE_AXIS));

        contenedor.setBackground(new java.awt.Color(27, 38, 59));
        contenedor.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        contenedor.setLayout(new java.awt.BorderLayout(0, 5));

        bodyContainer.setBackground(new java.awt.Color(27, 38, 59));
        bodyContainer.setLayout(new java.awt.GridLayout(4, 1));

        usernameTitle.setBackground(new java.awt.Color(27, 38, 59));
        usernameTitle.setPreferredSize(new java.awt.Dimension(717, 80));
        usernameTitle.setLayout(new java.awt.BorderLayout());

        jLabel2.setBackground(new java.awt.Color(27, 38, 59));
        jLabel2.setFont(new java.awt.Font("Samsung Sans", 0, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Username");
        jLabel2.setPreferredSize(new java.awt.Dimension(112, 80));
        usernameTitle.add(jLabel2, java.awt.BorderLayout.CENTER);

        bodyContainer.add(usernameTitle);

        mailPanel.setBackground(new java.awt.Color(27, 38, 59));
        mailPanel.setPreferredSize(new java.awt.Dimension(717, 80));
        mailPanel.setLayout(new java.awt.BorderLayout());

        jLabel3.setBackground(new java.awt.Color(27, 38, 59));
        jLabel3.setFont(new java.awt.Font("Samsung Sans", 0, 16)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("example@Unet.edu.ve");
        jLabel3.setPreferredSize(new java.awt.Dimension(112, 80));
        mailPanel.add(jLabel3, java.awt.BorderLayout.PAGE_START);

        bodyContainer.add(mailPanel);

        gamesTitlePanel.setBackground(new java.awt.Color(27, 38, 59));
        gamesTitlePanel.setLayout(new java.awt.BorderLayout());

        jLabel4.setBackground(new java.awt.Color(27, 38, 59));
        jLabel4.setFont(new java.awt.Font("Samsung Sans", 0, 24)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("Juegos creados por ti:");
        jLabel4.setPreferredSize(new java.awt.Dimension(112, 80));
        gamesTitlePanel.add(jLabel4, java.awt.BorderLayout.CENTER);

        bodyContainer.add(gamesTitlePanel);

        gamesPanel.setBackground(new java.awt.Color(27, 38, 59));
        gamesPanel.setPreferredSize(new java.awt.Dimension(717, 60));
        gamesPanel.setRequestFocusEnabled(false);
        gamesPanel.setLayout(new java.awt.GridLayout());

        jPanelJuegos.setBackground(new java.awt.Color(27, 38, 59));
        jPanelJuegos.setLayout(new java.awt.GridLayout(2, 0));

        jPanel2.setBackground(new java.awt.Color(27, 38, 59));
        jPanel2.setLayout(new java.awt.GridLayout(1, 1));

        jLabel1.setFont(new java.awt.Font("Samsung Sans", 0, 12)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Juego1");
        jPanel2.add(jLabel1);

        jLabel12.setFont(new java.awt.Font("Samsung Sans", 0, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("img1");
        jPanel2.add(jLabel12);

        jPanelJuegos.add(jPanel2);

        jPanel3.setBackground(new java.awt.Color(27, 38, 59));
        jPanel3.setLayout(new java.awt.GridLayout(1, 1));

        jLabel8.setFont(new java.awt.Font("Samsung Sans", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("Juego1");
        jPanel3.add(jLabel8);

        jLabel9.setFont(new java.awt.Font("Samsung Sans", 0, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("img2");
        jPanel3.add(jLabel9);

        jPanelJuegos.add(jPanel3);

        gamesPanel.add(jPanelJuegos);

        InternTags.setBackground(new java.awt.Color(27, 38, 59));
        InternTags.setPreferredSize(new java.awt.Dimension(300, 100));
        InternTags.setLayout(new java.awt.GridLayout(2, 2));

        jPanel4.setBackground(new java.awt.Color(27, 38, 59));
        jPanel4.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 50, 10, 50));
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.LINE_AXIS));

        deleteGame1.setBackground(new java.awt.Color(3, 52, 110));
        deleteGame1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteGame1.setLayout(new java.awt.CardLayout());

        signInTxt3.setFont(new java.awt.Font("Samsung Sans", 0, 18)); // NOI18N
        signInTxt3.setForeground(new java.awt.Color(207, 243, 255));
        signInTxt3.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        signInTxt3.setText("Borrar");
        signInTxt3.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        signInTxt3.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        signInTxt3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signInTxt3MouseClicked(evt);
            }
        });
        deleteGame1.add(signInTxt3, "card2");

        jPanel4.add(deleteGame1);

        InternTags.add(jPanel4);

        jPanel6.setBackground(new java.awt.Color(27, 38, 59));
        jPanel6.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 50, 10, 50));
        jPanel6.setLayout(new javax.swing.BoxLayout(jPanel6, javax.swing.BoxLayout.LINE_AXIS));

        deleteGame2.setBackground(new java.awt.Color(3, 52, 110));
        deleteGame2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        deleteGame2.setLayout(new java.awt.CardLayout());

        signInTxt4.setFont(new java.awt.Font("Samsung Sans", 0, 18)); // NOI18N
        signInTxt4.setForeground(new java.awt.Color(207, 243, 255));
        signInTxt4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        signInTxt4.setText("Borrar");
        signInTxt4.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 1));
        signInTxt4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        signInTxt4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signInTxt4MouseClicked(evt);
            }
        });
        deleteGame2.add(signInTxt4, "card2");

        jPanel6.add(deleteGame2);

        InternTags.add(jPanel6);

        gamesPanel.add(InternTags);

        bodyContainer.add(gamesPanel);

        contenedor.add(bodyContainer, java.awt.BorderLayout.CENTER);

        Body.add(contenedor);

        jScrollPane1.setViewportView(Body);

        BgPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(BgPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void signInTxt3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInTxt3MouseClicked
        int respuesta = JOptionPane.showConfirmDialog(
            this, 
            "¿Estás seguro de que quieres eliminar el juego?", 
            "Advertencia", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE
        );
        
        if(respuesta == JOptionPane.YES_OPTION){
                if (juegosCreados != null && !juegosCreados.isEmpty()) {
                Juego juegoAEliminar = juegosCreados.get(0); // Primer juego

                // Eliminar el juego de la base de datos
                boolean eliminadoDeBD = eliminarJuegoDeBaseDeDatos(juegoAEliminar);

                // Eliminar el archivo del juego del sistema de archivos
                boolean eliminadoDeArchivos = eliminarArchivoDelJuego(juegoAEliminar);

                if (eliminadoDeBD && eliminadoDeArchivos) {
                    juegosCreados.remove(0); 
                    mostrarInformacionUsuario(userInfo); 

                    JOptionPane.showMessageDialog(this, "Juego eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el juego.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay juegos para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
              }   
        }else{
            JOptionPane.showMessageDialog(this, "Se cancelo la solicitud de eliminar juego.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_signInTxt3MouseClicked

    private void signInTxt4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInTxt4MouseClicked
        // Aqui lo mismo pero juegosCreados.get(1)
        int respuesta = JOptionPane.showConfirmDialog(
            this, 
            "¿Estás seguro de que quieres eliminar el juego?", 
            "Advertencia", 
            JOptionPane.YES_NO_OPTION, 
            JOptionPane.WARNING_MESSAGE
        );
        
        if(respuesta == JOptionPane.YES_OPTION){
                if (juegosCreados != null && !juegosCreados.isEmpty()) {
                Juego juegoAEliminar = juegosCreados.get(1); // Primer juego

                // Eliminar el juego de la base de datos
                boolean eliminadoDeBD = eliminarJuegoDeBaseDeDatos(juegoAEliminar);

                // Eliminar el archivo del juego del sistema de archivos
                boolean eliminadoDeArchivos = eliminarArchivoDelJuego(juegoAEliminar);

                if (eliminadoDeBD && eliminadoDeArchivos) {
                    juegosCreados.remove(0); 
                    mostrarInformacionUsuario(userInfo); 

                    JOptionPane.showMessageDialog(this, "Juego eliminado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    JOptionPane.showMessageDialog(this, "Error al eliminar el juego.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No hay juegos para eliminar.", "Advertencia", JOptionPane.WARNING_MESSAGE);
              }   
        }else{
            JOptionPane.showMessageDialog(this, "Se cancelo la solicitud de eliminar juego.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        }
    }//GEN-LAST:event_signInTxt4MouseClicked

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(frmUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmUser.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmUser().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BgPanel;
    private javax.swing.JPanel Body;
    private javax.swing.JPanel InternTags;
    private javax.swing.JPanel bodyContainer;
    private javax.swing.JPanel contenedor;
    private javax.swing.JPanel deleteGame1;
    private javax.swing.JPanel deleteGame2;
    private javax.swing.JPanel gamesPanel;
    private javax.swing.JPanel gamesTitlePanel;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanelJuegos;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JPanel mailPanel;
    private javax.swing.JLabel signInTxt3;
    private javax.swing.JLabel signInTxt4;
    private javax.swing.JPanel usernameTitle;
    // End of variables declaration//GEN-END:variables
}
