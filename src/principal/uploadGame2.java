/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package principal;
import conexion.conexionMysql;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.sql.Connection;
import java.util.logging.Level;
import java.util.logging.Logger;
import raven.scroll.win11.ScrollPaneWin11;
import javax.swing.JOptionPane;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
/**
 *
 * @author dell
 */
public class uploadGame2 extends javax.swing.JFrame {
    conexion.conexionMysql con=new conexionMysql();
    Connection cn=con.conectar();
    
    private int obtenerUserId = 0;
    // Método setter para asignar el user_id
    public void setUserId(int userId) {
        this.obtenerUserId = userId;
    }
    public int getUserId() {
        return obtenerUserId;
    }
    
    
    private boolean usuarioInteractuo = false;
    private String coverPath; // Ruta de la imagen de portada
    private List<String> screenshotPaths = new ArrayList<>(); // Lista de rutas de capturas de pantalla
    private String gameFilePath; // Ruta del archivo del juego
    /**
     * Creates new form uploadGame2
     */
    
    public uploadGame2() {
        initComponents();
        cargarCategorias();
        usuarioInteractuo = true;
        
        setLocationRelativeTo(null);
        setTitle("UnetGames");
    }
    
    private void cargarCategorias() {
        try {
            // Limpiar el JComboBox antes de cargar las categorías
            jComboBox1.removeAllItems();

            // Consulta SQL para obtener las categorías
            String query = "SELECT category FROM categories";
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            // Recorrer el ResultSet y agregar las categorías al JComboBox
            while (rs.next()) {
                String categoria = rs.getString("category");
                jComboBox1.addItem(categoria); // Agregar cada categoría al JComboBox
            }

            // Cerrar el ResultSet y el PreparedStatement
            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al cargar las categorías desde la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private void guardarCategoriasEnBaseDeDatos(int gameId, String categoriasSeleccionadas) {
        // Dividir las categorías seleccionadas en un arreglo
        String[] categorias = categoriasSeleccionadas.split(";");

        // Verificar que no haya más de 4 categorías
        if (categorias.length > 4) {
            JOptionPane.showMessageDialog(this, "Solo puedes seleccionar un máximo de 4 categorías.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            // Recorrer cada categoría seleccionada
            for (String categoria : categorias) {
                categoria = categoria.trim(); // Eliminar espacios en blanco

                // Obtener el category_id de la categoría
                String query = "SELECT category_id FROM categories WHERE category = ?";
                PreparedStatement pstmt = cn.prepareStatement(query);
                pstmt.setString(1, categoria);
                ResultSet rs = pstmt.executeQuery();

                if (rs.next()) {
                    int categoryId = rs.getInt("category_id");

                    // Insertar en la tabla categories_games
                    String insertQuery = "INSERT INTO categories_games (category_id, game_id) VALUES (?, ?)";
                    PreparedStatement insertStmt = cn.prepareStatement(insertQuery);
                    insertStmt.setInt(1, categoryId);
                    insertStmt.setInt(2, gameId);
                    insertStmt.executeUpdate();

                    insertStmt.close();
                }

                rs.close();
                pstmt.close();
            }

            JOptionPane.showMessageDialog(this, "Categorías guardadas correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al guardar las categorías en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private String subirCover() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String destinationFolder = "C:\\Users\\dell\\OneDrive\\Documentos\\NetBeansProjects\\LoginJava\\UnetGames-Media\\gameCover\\";
            String destinationPath = destinationFolder + selectedFile.getName();

            try {
                // Copiar el archivo a la carpeta de destino
                Files.copy(selectedFile.toPath(), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
                return destinationPath; // Devolver la ruta de la imagen
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al subir la imagen de portada.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return null; // Si no se seleccionó un archivo o hubo un error
    }
    
    private int insertarJuegoEnBaseDeDatos(String title, String subtitle, String description, String coverPath, String gameFilePath) {
        try {
            // Consulta SQL para insertar el juego en la tabla Games
            String query = "INSERT INTO Games (user_id, title, subtitle, description, cover, game) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = cn.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            pstmt.setInt(1, obtenerUserId); // user_id del usuario actual
            pstmt.setString(2, title); // título
            pstmt.setString(3, subtitle); // subtítulo
            pstmt.setString(4, description); // descripción
            pstmt.setString(5, coverPath); // ruta de la portada
            pstmt.setString(6, gameFilePath); // ruta del archivo del juego

            // Ejecutar la consulta
            int rowsInserted = pstmt.executeUpdate();

            if (rowsInserted > 0) {
                // Obtener el game_id del juego recién insertado
                ResultSet rs = pstmt.getGeneratedKeys();
                if (rs.next()) {
                    return rs.getInt(1); // Devolver el game_id
                }
            }

            pstmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al insertar el juego en la base de datos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return -1; // Si hubo un error
    }
    
    private String subirArchivo(String destinationFolder) {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(this);

        if (result == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            String destinationPath = destinationFolder + selectedFile.getName();

            try {
                Files.copy(selectedFile.toPath(), Paths.get(destinationPath), StandardCopyOption.REPLACE_EXISTING);
                return destinationPath;
            } catch (IOException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al subir el archivo.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }

        return null;
    }

    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        BgPanel = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Body = new javax.swing.JPanel();
        Contenedor = new javax.swing.JPanel();
        headerContenedor = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        formularioContenedor = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        Title = new javax.swing.JLabel();
        titleTxt = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        subtitle = new javax.swing.JLabel();
        titleTxt1 = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        descriptionTxt = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        areaTxt = new javax.swing.JTextArea();
        jPanel15 = new javax.swing.JPanel();
        jPanel16 = new javax.swing.JPanel();
        categoria = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        jScrollPane3 = new javax.swing.JScrollPane();
        categoryTextArea = new javax.swing.JTextArea();
        jPanel3 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        Title5 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        textAreaPortada = new javax.swing.JTextArea();
        uploadPortada = new javax.swing.JPanel();
        uploadCover = new javax.swing.JLabel();
        jPanel17 = new javax.swing.JPanel();
        jPanel18 = new javax.swing.JPanel();
        Title9 = new javax.swing.JLabel();
        titleTxt2 = new javax.swing.JTextField();
        uploadGame = new javax.swing.JPanel();
        signInTxt2 = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        saveGame = new javax.swing.JPanel();
        upload = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setMinimumSize(new java.awt.Dimension(1139, 687));
        getContentPane().setLayout(new javax.swing.BoxLayout(getContentPane(), javax.swing.BoxLayout.LINE_AXIS));

        BgPanel.setBackground(new java.awt.Color(2, 21, 38));
        BgPanel.setLayout(new java.awt.BorderLayout());

        jScrollPane1.setMaximumSize(new java.awt.Dimension(1139, 687));
        jScrollPane1.setMinimumSize(new java.awt.Dimension(1139, 687));
        jScrollPane1.setPreferredSize(new java.awt.Dimension(1139, 687));

        Body.setBackground(new java.awt.Color(2, 21, 38));
        Body.setBorder(javax.swing.BorderFactory.createEmptyBorder(80, 200, 50, 200));
        Body.setLayout(new javax.swing.BoxLayout(Body, javax.swing.BoxLayout.LINE_AXIS));

        Contenedor.setBackground(new java.awt.Color(27, 38, 59));
        Contenedor.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 10, 10, 10));
        Contenedor.setLayout(new java.awt.BorderLayout());

        headerContenedor.setBackground(new java.awt.Color(27, 38, 59));
        headerContenedor.setLayout(new java.awt.BorderLayout(7, 0));

        jLabel2.setBackground(new java.awt.Color(27, 38, 59));
        jLabel2.setFont(new java.awt.Font("Samsung Sans", 1, 30)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Crear Nuevo Proyecto");
        jLabel2.setBorder(javax.swing.BorderFactory.createEmptyBorder(5, 45, 5, 5));
        jLabel2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        headerContenedor.add(jLabel2, java.awt.BorderLayout.CENTER);

        Contenedor.add(headerContenedor, java.awt.BorderLayout.PAGE_START);

        formularioContenedor.setBackground(new java.awt.Color(27, 38, 59));
        formularioContenedor.setLayout(new java.awt.GridLayout(7, 1));

        jPanel1.setBackground(new java.awt.Color(27, 38, 59));
        jPanel1.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 30));

        jPanel2.setBackground(new java.awt.Color(27, 38, 59));
        jPanel2.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 9));

        Title.setFont(new java.awt.Font("Samsung Sans", 0, 24)); // NOI18N
        Title.setForeground(new java.awt.Color(255, 255, 255));
        Title.setText("Titulo");
        Title.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 80));
        jPanel2.add(Title);

        titleTxt.setBackground(new java.awt.Color(27, 38, 59));
        titleTxt.setFont(new java.awt.Font("Samsung Sans", 0, 20)); // NOI18N
        titleTxt.setForeground(new java.awt.Color(255, 255, 255));
        titleTxt.setText("Ingrese el Titulo de su proyecto (Videojuego)");
        titleTxt.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        titleTxt.setMinimumSize(new java.awt.Dimension(188, 22));
        titleTxt.setPreferredSize(new java.awt.Dimension(420, 40));
        titleTxt.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                titleTxtMouseClicked(evt);
            }
        });
        titleTxt.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleTxtActionPerformed(evt);
            }
        });
        jPanel2.add(titleTxt);

        jPanel1.add(jPanel2);

        formularioContenedor.add(jPanel1);

        jPanel9.setBackground(new java.awt.Color(27, 38, 59));
        jPanel9.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 30));

        jPanel10.setBackground(new java.awt.Color(27, 38, 59));
        jPanel10.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 9));

        subtitle.setFont(new java.awt.Font("Samsung Sans", 0, 24)); // NOI18N
        subtitle.setForeground(new java.awt.Color(255, 255, 255));
        subtitle.setText("Subtitulo");
        subtitle.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 45));
        jPanel10.add(subtitle);

        titleTxt1.setBackground(new java.awt.Color(27, 38, 59));
        titleTxt1.setFont(new java.awt.Font("Samsung Sans", 0, 20)); // NOI18N
        titleTxt1.setForeground(new java.awt.Color(255, 255, 255));
        titleTxt1.setText("Añada un subtitulo o tagline");
        titleTxt1.setBorder(javax.swing.BorderFactory.createMatteBorder(0, 0, 1, 0, new java.awt.Color(255, 255, 255)));
        titleTxt1.setMinimumSize(new java.awt.Dimension(188, 22));
        titleTxt1.setPreferredSize(new java.awt.Dimension(420, 40));
        titleTxt1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                titleTxt1MouseClicked(evt);
            }
        });
        titleTxt1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleTxt1ActionPerformed(evt);
            }
        });
        jPanel10.add(titleTxt1);

        jPanel9.add(jPanel10);

        formularioContenedor.add(jPanel9);

        jPanel11.setBackground(new java.awt.Color(27, 38, 59));
        jPanel11.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 30));

        jPanel12.setBackground(new java.awt.Color(27, 38, 59));
        jPanel12.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 9));

        descriptionTxt.setFont(new java.awt.Font("Samsung Sans", 0, 24)); // NOI18N
        descriptionTxt.setForeground(new java.awt.Color(255, 255, 255));
        descriptionTxt.setText("Descripción");
        descriptionTxt.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 15));
        jPanel12.add(descriptionTxt);

        jPanel11.add(jPanel12);

        areaTxt.setBackground(new java.awt.Color(27, 38, 59));
        areaTxt.setColumns(38);
        areaTxt.setFont(new java.awt.Font("Samsung Sans", 0, 13)); // NOI18N
        areaTxt.setForeground(new java.awt.Color(255, 255, 255));
        areaTxt.setLineWrap(true);
        areaTxt.setRows(5);
        areaTxt.setTabSize(15);
        areaTxt.setToolTipText("");
        areaTxt.setWrapStyleWord(true);
        areaTxt.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        areaTxt.setMinimumSize(new java.awt.Dimension(232, 84));
        jScrollPane2.setViewportView(areaTxt);

        jPanel11.add(jScrollPane2);

        formularioContenedor.add(jPanel11);

        jPanel15.setBackground(new java.awt.Color(27, 38, 59));
        jPanel15.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 30));

        jPanel16.setBackground(new java.awt.Color(27, 38, 59));
        jPanel16.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 9));

        categoria.setFont(new java.awt.Font("Samsung Sans", 0, 24)); // NOI18N
        categoria.setForeground(new java.awt.Color(255, 255, 255));
        categoria.setText("Categoría");
        categoria.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 45));
        jPanel16.add(categoria);

        jPanel15.add(jPanel16);

        jComboBox1.setBackground(new java.awt.Color(27, 38, 59));
        jComboBox1.setFont(new java.awt.Font("Samsung Sans", 0, 14)); // NOI18N
        jComboBox1.setForeground(new java.awt.Color(255, 255, 255));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });
        jPanel15.add(jComboBox1);

        categoryTextArea.setEditable(false);
        categoryTextArea.setBackground(new java.awt.Color(27, 38, 59));
        categoryTextArea.setColumns(21);
        categoryTextArea.setFont(new java.awt.Font("Samsung Sans", 0, 12)); // NOI18N
        categoryTextArea.setForeground(new java.awt.Color(255, 255, 255));
        categoryTextArea.setLineWrap(true);
        categoryTextArea.setRows(3);
        categoryTextArea.setWrapStyleWord(true);
        categoryTextArea.setBorder(javax.swing.BorderFactory.createMatteBorder(1, 1, 1, 1, new java.awt.Color(255, 255, 255)));
        jScrollPane3.setViewportView(categoryTextArea);

        jPanel15.add(jScrollPane3);

        formularioContenedor.add(jPanel15);

        jPanel3.setBackground(new java.awt.Color(27, 38, 59));
        jPanel3.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 30));

        jPanel4.setBackground(new java.awt.Color(27, 38, 59));
        jPanel4.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 9));

        Title5.setFont(new java.awt.Font("Samsung Sans", 0, 24)); // NOI18N
        Title5.setForeground(new java.awt.Color(255, 255, 255));
        Title5.setText("Portada");
        Title5.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 60));
        jPanel4.add(Title5);

        jPanel3.add(jPanel4);

        jScrollPane4.setHorizontalScrollBar(null);
        jScrollPane4.setWheelScrollingEnabled(false);

        textAreaPortada.setEditable(false);
        textAreaPortada.setBackground(new java.awt.Color(27, 38, 59));
        textAreaPortada.setColumns(26);
        textAreaPortada.setFont(new java.awt.Font("Samsung Sans", 0, 12)); // NOI18N
        textAreaPortada.setForeground(new java.awt.Color(255, 255, 255));
        textAreaPortada.setRows(3);
        textAreaPortada.setText("Añada la imagen de portada para su \nvideojuego(Mínimo: 480x270, Máximo: 1280x720\nIntenta tener una relacion 16:9 )");
        textAreaPortada.setAutoscrolls(false);
        textAreaPortada.setBorder(null);
        jScrollPane4.setViewportView(textAreaPortada);

        jPanel3.add(jScrollPane4);

        uploadPortada.setBackground(new java.awt.Color(3, 52, 110));
        uploadPortada.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        uploadCover.setFont(new java.awt.Font("Samsung Sans", 0, 18)); // NOI18N
        uploadCover.setForeground(new java.awt.Color(207, 243, 255));
        uploadCover.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        uploadCover.setText("Subir");
        uploadCover.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        uploadCover.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                uploadCoverMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout uploadPortadaLayout = new javax.swing.GroupLayout(uploadPortada);
        uploadPortada.setLayout(uploadPortadaLayout);
        uploadPortadaLayout.setHorizontalGroup(
            uploadPortadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(uploadCover, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );
        uploadPortadaLayout.setVerticalGroup(
            uploadPortadaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadPortadaLayout.createSequentialGroup()
                .addComponent(uploadCover, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel3.add(uploadPortada);

        formularioContenedor.add(jPanel3);

        jPanel17.setBackground(new java.awt.Color(27, 38, 59));
        jPanel17.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 10, 30));

        jPanel18.setBackground(new java.awt.Color(27, 38, 59));
        jPanel18.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 20, 9));

        Title9.setFont(new java.awt.Font("Samsung Sans", 0, 24)); // NOI18N
        Title9.setForeground(new java.awt.Color(255, 255, 255));
        Title9.setText("Subir archivos");
        Title9.setBorder(javax.swing.BorderFactory.createEmptyBorder(1, 1, 1, 8));
        jPanel18.add(Title9);

        jPanel17.add(jPanel18);

        titleTxt2.setBackground(new java.awt.Color(27, 38, 59));
        titleTxt2.setFont(new java.awt.Font("Samsung Sans", 0, 12)); // NOI18N
        titleTxt2.setForeground(new java.awt.Color(255, 255, 255));
        titleTxt2.setText("Tamaño máximo de archivos: 100mb. ");
        titleTxt2.setBorder(null);
        titleTxt2.setMinimumSize(new java.awt.Dimension(188, 22));
        titleTxt2.setPreferredSize(new java.awt.Dimension(272, 30));
        titleTxt2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                titleTxt2ActionPerformed(evt);
            }
        });
        jPanel17.add(titleTxt2);

        uploadGame.setBackground(new java.awt.Color(3, 52, 110));
        uploadGame.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        signInTxt2.setFont(new java.awt.Font("Samsung Sans", 0, 18)); // NOI18N
        signInTxt2.setForeground(new java.awt.Color(207, 243, 255));
        signInTxt2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        signInTxt2.setText("Subir");
        signInTxt2.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        signInTxt2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                signInTxt2MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout uploadGameLayout = new javax.swing.GroupLayout(uploadGame);
        uploadGame.setLayout(uploadGameLayout);
        uploadGameLayout.setHorizontalGroup(
            uploadGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(signInTxt2, javax.swing.GroupLayout.DEFAULT_SIZE, 116, Short.MAX_VALUE)
        );
        uploadGameLayout.setVerticalGroup(
            uploadGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(uploadGameLayout.createSequentialGroup()
                .addComponent(signInTxt2, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel17.add(uploadGame);

        formularioContenedor.add(jPanel17);

        jPanel19.setBackground(new java.awt.Color(27, 38, 59));
        jPanel19.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 30));

        saveGame.setBackground(new java.awt.Color(3, 52, 110));
        saveGame.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));

        upload.setFont(new java.awt.Font("Samsung Sans", 0, 18)); // NOI18N
        upload.setForeground(new java.awt.Color(207, 243, 255));
        upload.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        upload.setText("Finalizar");
        upload.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        upload.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                uploadMouseClicked(evt);
            }
        });

        javax.swing.GroupLayout saveGameLayout = new javax.swing.GroupLayout(saveGame);
        saveGame.setLayout(saveGameLayout);
        saveGameLayout.setHorizontalGroup(
            saveGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveGameLayout.createSequentialGroup()
                .addComponent(upload, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        saveGameLayout.setVerticalGroup(
            saveGameLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(saveGameLayout.createSequentialGroup()
                .addComponent(upload, javax.swing.GroupLayout.PREFERRED_SIZE, 52, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jPanel19.add(saveGame);

        formularioContenedor.add(jPanel19);

        Contenedor.add(formularioContenedor, java.awt.BorderLayout.CENTER);

        Body.add(Contenedor);

        jScrollPane1.setViewportView(Body);

        BgPanel.add(jScrollPane1, java.awt.BorderLayout.CENTER);

        getContentPane().add(BgPanel);

        pack();
    }// </editor-fold>//GEN-END:initComponents

    
    
    private void titleTxtActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleTxtActionPerformed
        titleTxt.setText("");
    }//GEN-LAST:event_titleTxtActionPerformed

    private void titleTxt1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleTxt1ActionPerformed
        titleTxt.setText("");
    }//GEN-LAST:event_titleTxt1ActionPerformed

    private void uploadCoverMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_uploadCoverMouseClicked
        coverPath = subirArchivo("C:\\Users\\dell\\OneDrive\\Documentos\\NetBeansProjects\\LoginJava\\UnetGames-Media\\gameCover\\");
        if (coverPath != null) {
            JOptionPane.showMessageDialog(this, "Portada seleccionada correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se seleccionó ninguna imagen.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_uploadCoverMouseClicked

    private void signInTxt2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_signInTxt2MouseClicked
       gameFilePath = subirArchivo("C:\\Users\\dell\\OneDrive\\Documentos\\NetBeansProjects\\LoginJava\\UnetGames-Media\\gameArchive\\");
        if (gameFilePath != null) {
            JOptionPane.showMessageDialog(this, "Archivo del juego seleccionado correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "No se seleccionó ningún archivo.", "Advertencia", JOptionPane.WARNING_MESSAGE);
        }
    }//GEN-LAST:event_signInTxt2MouseClicked

    private void titleTxt2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_titleTxt2ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_titleTxt2ActionPerformed

    private void uploadMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_uploadMouseClicked
        String title = titleTxt.getText();
        String subtitle = titleTxt1.getText();
        String description = areaTxt.getText();
        // gameId predeterminado
        String categoriasSeleccionadas = categoryTextArea.getText();
        // Verificar que los campos obligatorios no estén vacíos
        if (title.isEmpty() || subtitle.isEmpty() || description.isEmpty() || categoriasSeleccionadas.isEmpty() || coverPath == null || gameFilePath == null) {
            JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        // Insertar los datos del juego en la tabla Games y obtener el game_id
        int gameId = insertarJuegoEnBaseDeDatos(title, subtitle, description, coverPath, gameFilePath);

        if (gameId != -1) {
            // Guardar las categorías seleccionadas en la tabla categories_games
            guardarCategoriasEnBaseDeDatos(gameId, categoriasSeleccionadas);

            JOptionPane.showMessageDialog(this, "Juego subido correctamente.", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } else {
            JOptionPane.showMessageDialog(this, "Error al subir el juego.", "Error", JOptionPane.ERROR_MESSAGE);
        }
        
        
        
         
    }//GEN-LAST:event_uploadMouseClicked

    private void titleTxtMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleTxtMouseClicked
        titleTxt.setText("");
    }//GEN-LAST:event_titleTxtMouseClicked

    private void titleTxt1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_titleTxt1MouseClicked
        titleTxt1.setText("");
    }//GEN-LAST:event_titleTxt1MouseClicked

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (usuarioInteractuo) {
            String selectedCategory = (String) jComboBox1.getSelectedItem();
            if (selectedCategory != null) { // Verificar que se haya seleccionado una categoría válida
                String currentText = categoryTextArea.getText();
                if (currentText.split(";").length >= 4) {
                    JOptionPane.showMessageDialog(this, "Solo puedes seleccionar un máximo de 4 categorías.", "Límite alcanzado", JOptionPane.WARNING_MESSAGE);
                    return; // Salir del método si ya hay 4 categorías
                }
                if (currentText.isEmpty()) {
                    categoryTextArea.setText(selectedCategory);
                } else {
                    categoryTextArea.setText(currentText + "; " + selectedCategory);
                }
            }
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {

        try {
            /* Set the Nimbus look and feel */
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());//<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
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
                java.util.logging.Logger.getLogger(uploadGame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (InstantiationException ex) {
                java.util.logging.Logger.getLogger(uploadGame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (IllegalAccessException ex) {
                java.util.logging.Logger.getLogger(uploadGame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            } catch (javax.swing.UnsupportedLookAndFeelException ex) {
                java.util.logging.Logger.getLogger(uploadGame2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
            }
            //</editor-fold>
            
            /* Create and display the form */
            java.awt.EventQueue.invokeLater(new Runnable() {
                public void run() {
                    new uploadGame2().setVisible(true);
                }
            });
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
            Logger.getLogger(uploadGame2.class.getName()).log(Level.SEVERE, null, ex);
        }
        //</editor-fold>
        
        //</editor-fold>
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BgPanel;
    private javax.swing.JPanel Body;
    private javax.swing.JPanel Contenedor;
    private javax.swing.JLabel Title;
    private javax.swing.JLabel Title5;
    private javax.swing.JLabel Title9;
    private javax.swing.JTextArea areaTxt;
    private javax.swing.JLabel categoria;
    private javax.swing.JTextArea categoryTextArea;
    private javax.swing.JLabel descriptionTxt;
    private javax.swing.JPanel formularioContenedor;
    private javax.swing.JPanel headerContenedor;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JPanel saveGame;
    private javax.swing.JLabel signInTxt2;
    private javax.swing.JLabel subtitle;
    private javax.swing.JTextArea textAreaPortada;
    private javax.swing.JTextField titleTxt;
    private javax.swing.JTextField titleTxt1;
    private javax.swing.JTextField titleTxt2;
    private javax.swing.JLabel upload;
    private javax.swing.JLabel uploadCover;
    private javax.swing.JPanel uploadGame;
    private javax.swing.JPanel uploadPortada;
    // End of variables declaration//GEN-END:variables
}
