/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/GUIForms/JFrame.java to edit this template
 */
package principal;
import conexion.conexionMysql;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.util.List;
import java.util.ArrayList;
import raven.scroll.win11.ScrollPaneWin11;
import java.io.IOException;
import java.net.URL;
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
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JLabel;



/**
 *
 * @author dell
 */
public class frmMain extends javax.swing.JFrame {
    private List<Component> savedComponents = new ArrayList<>();
    conexion.conexionMysql con=new conexionMysql();
    Connection cn=con.conectar();
    
    //Obtener userid
    private int userId; 
    // Método setter para asignar el user_id
    public void setUserId(int userId) {
        this.userId = userId;
    }
    public int getUserId() {
        return userId;
    }
    
    
    public static games gm;
    public static uploadGame2 upGm;
    public static game gmIndividual;
    public static frmUser user;
    
    private List<Juego> juegos;
    private List<Juego> juegos_Plataforma;
    private List<Juego> juegos_Deportes;
    private List<Juego> buscarJuegos;
    private List<Juego> juegos_FPS;
    private List<Juego> juegos_Arcade;
    private List<Juego> juegos_Aventura;
    /**
     * Creates new form frmMain
     */
    public frmMain() {
        gm = new games(this);
        upGm = new uploadGame2();
        gmIndividual = new game();
        user = new frmUser();
        
        
        initComponents();
        this.setExtendedState(frmMain.MAXIMIZED_BOTH);
        
        juegos = obtenerUltimosJuegosSubidos();
        juegos_Plataforma = obtenerJuegosPlataforma();
        juegos_Deportes = obtenerJUegosDeportes();
        juegos_FPS = obtenerJuegosFPS();
        juegos_Arcade =  obtenerJuegosArcade();
        juegos_Aventura = obtenerJuegosAventura();
        
        
        setLocationRelativeTo(null);
        setTitle("UnetGames");
        
        // Actualizar las tarjetas con la información de los juegos
        actualizarTarjetas();
        actualizarTarjetasPlataforma();
        actualizarTarjetasDeportes();
    }
    
    public void mostrarVistaIndividual(Juego juego) {
        // Limpiar el BgPanel
        Component[] components = BgPanel.getComponents();
        for (Component component : components) {
            if (component != header) { 
                BgPanel.remove(component);
            }
        }

        // Crear o actualizar la vista individual del juego
        gmIndividual.actualizarInformacion(juego);

        // Agregar la vista individual al BgPanel
        BgPanel.add(gmIndividual.getContentPane());

        // Revalidar y repintar el BgPanel
        BgPanel.revalidate();
        BgPanel.repaint();
    }
    
    private void mostrarGames(List<Juego> juegos) {
        games ventanaGames = new games(this); // Pasar una referencia de frmMain
        ventanaGames.actualizarTarjetas(juegos);
    }
    
    public class Juego {
        private int gameId;
        private String titulo;
        private String coverPath;
        private String subtitulo;
        private String descripcion;
        private String gameFile;
        private String username;
        private String categories;
        

        public Juego(int gameId, String titulo, String coverPath, String subtitulo, String descripcion, String gameFile, String username, String categories) {
            this.gameId = gameId;
            this.titulo = titulo;
            this.coverPath = coverPath;
            this.subtitulo = subtitulo;
            this.descripcion = descripcion;
            this.gameFile = gameFile;
            this.username = username;
            this.categories = categories;
        }

        // Getters
        public int getGameId(){
            return gameId;
        }
        public String getTitulo() {
            return titulo;
        }

        public String getCoverPath() {
            return coverPath;
        }

        public String getSubtitulo() {
            return subtitulo;
        }

        public String getDescripcion() {
            return descripcion;
        }

        public String getGameFile() {
            return gameFile;
        }

        public String getUsername() {
            return username;
        }
        public String getCategories() {
            return categories;
        }
    }
    
    public class UserInfo {
        private String username;
        private String email;
        private List<Juego> juegosCreados;

        // Constructor, getters y setters
        public UserInfo(String username, String email, List<Juego> juegosCreados) {
                this.username = username;
                this.email = email;
                this.juegosCreados = juegosCreados;
            }

            public String getUsername() {
                return username;
            }

            public String getEmail() {
                return email;
            }

            public List<Juego> getJuegosCreados() {
                return juegosCreados;
            }
        }
    
    private List<Juego> obtenerUltimosJuegosSubidos() {
        List<Juego> juegos = new ArrayList<>();

        try {
            // Consulta SQL para obtener los últimos juegos subidos
            String query = "SELECT g.game_id, g.title, g.cover, g.subtitle, g.description, g.game, u.username, " +
                       "GROUP_CONCAT(c.category SEPARATOR \", \") AS categories " + // Usar comillas dobles
                       "FROM Games g " +
                       "JOIN Users u ON g.user_id = u.user_id " +
                       "JOIN categories_Games cg ON g.game_id = cg.game_id " +
                       "JOIN categories c ON cg.category_id = c.category_id " +
                       "GROUP BY g.game_id " +
                       "ORDER BY g.game_id DESC " +
                       "LIMIT 4";
            
            
            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            // Recorrer el ResultSet y crear objetos Juego
            while (rs.next()) {
                int gameId = rs.getInt("game_id");
                String titulo = rs.getString("title");
                String coverPath = rs.getString("cover");
                String username = rs.getString("username");
                String subtitle = rs.getString("subtitle");
                String description = rs.getString("description");
                String game = rs.getString("game");
                String categories = rs.getString("categories");
                

                Juego juego = new Juego(gameId, titulo, coverPath, subtitle, description, game, username, categories);
                juegos.add(juego);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los últimos juegos subidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return juegos;
    }
 
    private void actualizarTarjetas() {
        // Lista de paneles que representan las tarjetas
        List<JPanel> tarjetas = List.of(jPanel4, jPanel10, jPanel13, jPanel9);

        // Lista de etiquetas para las imágenes de los juegos
        List<JLabel> imagenes = List.of(jLabel12, jLabel15, jLabel24, jLabel17);

        // Lista de etiquetas para los títulos de los juegos
        List<JLabel> titulos = List.of(jLabel11, jLabel16, jLabel25, jLabel18);

        // Lista de etiquetas para los nombres de los creadores
        List<JLabel> creadores = List.of(jLabel14, jLabel23, jLabel26, jLabel19);

        // Iterar sobre la lista de juegos y actualizar las tarjetas
        for (int i = 0; i < juegos.size(); i++) {
            Juego juego = juegos.get(i);
            final int index=i;
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
                JPanel tarjeta = tarjetas.get(i);
                tarjeta.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Obtener el juego seleccionado
                        Juego juegoSeleccionado = juegos.get(index);

                        // Actualizar la información en el JFrame individual
                        gmIndividual.actualizarInformacion(juegoSeleccionado);

                        // Limpiar el BgPanel y mostrar la vista individual
                        mostrarVistaIndividual(juegoSeleccionado);
                    }
                });
            }
    }
    
    private List<Juego> obtenerJuegosPlataforma() {
        List<Juego> juegos_Plataforma = new ArrayList<>();

        try {
            // Consulta SQL para obtener los últimos juegos subidos
            String query = "SELECT g.game_id, g.title, g.cover, g.subtitle, g.description, g.game, u.username, " +
               "GROUP_CONCAT(c.category SEPARATOR \", \") AS categories " + // Usar comillas dobles
               "FROM Games g " +
               "JOIN Users u ON g.user_id = u.user_id " +
               "JOIN categories_Games cg ON g.game_id = cg.game_id " +
               "JOIN categories c ON cg.category_id = c.category_id " +
               "WHERE c.category = 'Plataformas' " +
               "GROUP BY g.game_id " + // Agregar GROUP BY
               "ORDER BY g.game_id DESC " +
               "LIMIT 4"; // Limitar a los últimos 4 juegos

            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            // Recorrer el ResultSet y crear objetos Juego
            while (rs.next()) {
                int gameId = rs.getInt("game_id");
                String titulo = rs.getString("title");
                String coverPath = rs.getString("cover");
                String username = rs.getString("username");
                String subtitle = rs.getString("subtitle");
                String description = rs.getString("description");
                String game = rs.getString("game");
                String categories = rs.getString("categories");
                
                Juego juego = new Juego(gameId, titulo, coverPath, subtitle, description, game, username, categories);
                juegos_Plataforma.add(juego);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los últimos juegos subidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return juegos_Plataforma;
    }
    
    private void actualizarTarjetasPlataforma() {
        try {
            // Obtener la lista de juegos de deportes
            List<Juego> juegos_Plataforma = obtenerJuegosPlataforma();

            // Verificar si la lista está vacía
            if (juegos_Plataforma.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay juegos de Plataforma disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return; // Salir del método si no hay juegos
            }

            // Lista de paneles que representan las tarjetas
            List<JPanel> tarjetas = List.of(jPanel17, jPanel22, jPanel25, jPanel28);

            // Lista de etiquetas para las imágenes de los juegos
            List<JLabel> imagenes = List.of(jLabel20, jLabel27, jLabel30, jLabel35);

            // Lista de etiquetas para los títulos de los juegos
            List<JLabel> titulos = List.of(jLabel21, jLabel28, jLabel31, jLabel36);

            // Lista de etiquetas para los nombres de los creadores
            List<JLabel> creadores = List.of(jLabel22, jLabel29, jLabel32, jLabel37);

            // Iterar sobre la lista de juegos de deportes y actualizar las tarjetas
            for (int i = 0; i < juegos_Plataforma.size(); i++) {
                Juego juego = juegos_Plataforma.get(i);
                final int index=i;
                
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
                JPanel tarjeta = tarjetas.get(i);
                tarjeta.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Obtener el juego seleccionado
                        Juego juegoSeleccionado = juegos_Plataforma.get(index);

                        // Actualizar la información en el JFrame individual
                        gmIndividual.actualizarInformacion(juegoSeleccionado);

                        // Limpiar el BgPanel y mostrar la vista individual
                        mostrarVistaIndividual(juegoSeleccionado);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error al cargar los juegos de Plataformas.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    private List<Juego> obtenerJUegosDeportes() {
        List<Juego> juegos_Deportes = new ArrayList<>();

        try {
            // Consulta SQL para obtener los últimos juegos subidos
            String query = "SELECT g.game_id, g.title, g.cover, g.subtitle, g.description, g.game, u.username, " +
                       "GROUP_CONCAT(c.category SEPARATOR \", \") AS categories " + // Usar comillas dobles
                       "FROM Games g " +
                       "JOIN Users u ON g.user_id = u.user_id " +
                       "JOIN categories_Games cg ON g.game_id = cg.game_id " +
                       "JOIN categories c ON cg.category_id = c.category_id " +
                       "WHERE c.category = 'Deportes' " +
                       "GROUP BY g.game_id " + // Agregar GROUP BY
                       "ORDER BY g.game_id DESC " +
                       "LIMIT 4"; // 

            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            // Recorrer el ResultSet y crear objetos Juego
            while (rs.next()) {
                int gameId = rs.getInt("game_id");
                String titulo = rs.getString("title");
                String coverPath = rs.getString("cover");
                String username = rs.getString("username");
                String subtitle = rs.getString("subtitle");
                String description = rs.getString("description");
                String game = rs.getString("game");
                String categories = rs.getString("categories");

                Juego juego = new Juego(gameId, titulo, coverPath, subtitle, description, game, username, categories);
                juegos_Deportes.add(juego);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los últimos juegos subidos.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return juegos_Deportes;
    }
    
    private void actualizarTarjetasDeportes() {
        try {
            // Obtener la lista de juegos de deportes
            List<Juego> juegos_Deportes = obtenerJUegosDeportes();

            // Verificar si la lista está vacía
            if (juegos_Deportes.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No hay juegos de deportes disponibles.", "Información", JOptionPane.INFORMATION_MESSAGE);
                return; // Salir del método si no hay juegos
            }

            // Lista de paneles que representan las tarjetas
            List<JPanel> tarjetas = List.of(jPanel43, jPanel46, jPanel49, jPanel52);

            // Lista de etiquetas para las imágenes de los juegos
            List<JLabel> imagenes = List.of(jLabel38, jLabel41, jLabel44, jLabel48);

            // Lista de etiquetas para los títulos de los juegos
            List<JLabel> titulos = List.of(jLabel39, jLabel42, jLabel46, jLabel49);

            // Lista de etiquetas para los nombres de los creadores
            List<JLabel> creadores = List.of(jLabel40, jLabel43, jLabel47, jLabel50);

            // Iterar sobre la lista de juegos de deportes y actualizar las tarjetas
            for (int i = 0; i < juegos_Deportes.size(); i++) {
                Juego juego = juegos_Deportes.get(i);
                final int index = i;
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
                JPanel tarjeta = tarjetas.get(i);
                tarjeta.addMouseListener(new MouseAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        // Obtener el juego seleccionado
                        Juego juegoSeleccionado = juegos_Deportes.get(index);

                        // Actualizar la información en el JFrame individual
                        gmIndividual.actualizarInformacion(juegoSeleccionado);

                        // Limpiar el BgPanel y mostrar la vista individual
                        mostrarVistaIndividual(juegoSeleccionado);
                    }
                });
            }
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Ocurrió un error al cargar los juegos de deportes.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    
    public List<Juego> buscarJuegos(String searchText) {
        List<Juego> juegosResultado = new ArrayList<>();

        try {
                // Consulta SQL para buscar juegos
                String query = "SELECT g.game_id, g.title, g.cover, g.subtitle, g.description, g.game, u.username, GROUP_CONCAT(c.category) AS categories " +
                                "FROM Games g " +
                                "JOIN Users u ON g.user_id = u.user_id " +
                                "LEFT JOIN categories_Games cg ON g.game_id = cg.game_id " +
                                "LEFT JOIN categories c ON cg.category_id = c.category_id " +
                                "WHERE g.title LIKE ? " +
                                "GROUP BY g.game_id " +
                                "ORDER BY g.game_id DESC ";

                PreparedStatement pstmt = cn.prepareStatement(query);
                pstmt.setString(1, "%" + searchText + "%"); // Usar % para búsqueda parcial
                ResultSet rs = pstmt.executeQuery();

                // Recorrer el ResultSet y crear objetos Juego
                while (rs.next()) {
                    int gameId = rs.getInt("game_id");
                    String titulo = rs.getString("title");
                    String coverPath = rs.getString("cover");
                    String username = rs.getString("username");
                    String subtitle = rs.getString("subtitle");
                    String description = rs.getString("description");
                    String game = rs.getString("game");
                    String categories = rs.getString("categories");

                    Juego juego = new Juego(gameId, titulo, coverPath, subtitle, description, game, username, categories);
                    juegosResultado.add(juego);
                }

                rs.close();
                pstmt.close();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Error al buscar juegos.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            return juegosResultado;
    }
    
    //Obtener informacion para frmUser 
    
    //Obtener informacion para frmUser 
    
    public UserInfo obtenerInformacionUsuario(int userId) {
            String username = "";
            String email = "";
            List<Juego> juegosCreados = new ArrayList<>();

            try {
                // Consulta SQL para obtener el username y el email del usuario
                String queryUser = "SELECT username, email FROM Users WHERE user_id = ?";
                PreparedStatement pstmtUser = cn.prepareStatement(queryUser);
                pstmtUser.setInt(1, userId);
                ResultSet rsUser = pstmtUser.executeQuery();

                if (rsUser.next()) {
                    username = rsUser.getString("username");
                    email = rsUser.getString("email");
                }

                rsUser.close();
                pstmtUser.close();

                // Consulta SQL para obtener los juegos creados por el usuario
                String queryGames = "SELECT g.game_id, g.title, g.cover, g.subtitle, g.description, g.game, " +
                    "GROUP_CONCAT(c.category SEPARATOR ', ') AS categories " +
                    "FROM Games g " +
                    "LEFT JOIN categories_Games cg ON g.game_id = cg.game_id " +
                    "LEFT JOIN categories c ON cg.category_id = c.category_id " +
                    "WHERE g.user_id = ? " +
                    "GROUP BY g.game_id";

                PreparedStatement pstmtGames = cn.prepareStatement(queryGames);
                pstmtGames.setInt(1, userId);
                ResultSet rsGames = pstmtGames.executeQuery();

                int contador = 0;
                while (rsGames.next() && contador < 2) {
                    int gameId = rsGames.getInt("game_id");
                    String titulo = rsGames.getString("title");
                    String coverPath = rsGames.getString("cover");
                    String subtitle = rsGames.getString("subtitle");
                    String description = rsGames.getString("description");
                    String game = rsGames.getString("game");
                    String categories = rsGames.getString("categories");

                    Juego juego = new Juego(gameId, titulo, coverPath, subtitle, description, game, username, categories);
                    juegosCreados.add(juego);

                    contador++;
                }

                rsGames.close();
                pstmtGames.close();

            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Error al obtener la información del usuario.", "Error", JOptionPane.ERROR_MESSAGE);
            }

            return new UserInfo(username, email, juegosCreados);
    }    
   
    
    private List<Juego> obtenerJuegosFPS() {
        List<Juego> juegos_FPS = new ArrayList<>();

        try {
            String query = "SELECT g.game_id, g.title, g.cover, g.subtitle, g.description, g.game, u.username, " +
                           "GROUP_CONCAT(c.category SEPARATOR ', ') AS categories " +
                           "FROM Games g " +
                           "JOIN Users u ON g.user_id = u.user_id " +
                           "JOIN categories_Games cg ON g.game_id = cg.game_id " +
                           "JOIN categories c ON cg.category_id = c.category_id " +
                           "WHERE c.category = 'FPS' " +
                           "GROUP BY g.game_id " +
                           "ORDER BY g.game_id DESC " +
                           "LIMIT 16";

            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int gameId = rs.getInt("game_id");
                String titulo = rs.getString("title");
                String coverPath = rs.getString("cover");
                String username = rs.getString("username");
                String subtitle = rs.getString("subtitle");
                String description = rs.getString("description");
                String game = rs.getString("game");
                String categories = rs.getString("categories");

                Juego juego = new Juego(gameId, titulo, coverPath, subtitle, description, game, username, categories);
                juegos_FPS.add(juego);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los juegos de FPS.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return juegos_FPS;
    }

    private List<Juego> obtenerJuegosArcade() {
        List<Juego> juegos_Arcade = new ArrayList<>();

        try {
            String query = "SELECT g.game_id, g.title, g.cover, g.subtitle, g.description, g.game, u.username, " +
                           "GROUP_CONCAT(c.category SEPARATOR ', ') AS categories " +
                           "FROM Games g " +
                           "JOIN Users u ON g.user_id = u.user_id " +
                           "JOIN categories_Games cg ON g.game_id = cg.game_id " +
                           "JOIN categories c ON cg.category_id = c.category_id " +
                           "WHERE c.category = 'Arcade' " +
                           "GROUP BY g.game_id " +
                           "ORDER BY g.game_id DESC " +
                           "LIMIT 16";

            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int gameId = rs.getInt("game_id");
                String titulo = rs.getString("title");
                String coverPath = rs.getString("cover");
                String username = rs.getString("username");
                String subtitle = rs.getString("subtitle");
                String description = rs.getString("description");
                String game = rs.getString("game");
                String categories = rs.getString("categories");

                Juego juego = new Juego(gameId, titulo, coverPath, subtitle, description, game, username, categories);
                juegos_Arcade.add(juego);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los juegos Arcade.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return juegos_Arcade;
    }

    private List<Juego> obtenerJuegosAventura() {
        List<Juego> juegos_Aventura = new ArrayList<>();

        try {
            String query = "SELECT g.game_id, g.title, g.cover, g.subtitle, g.description, g.game, u.username, " +
                           "GROUP_CONCAT(c.category SEPARATOR ', ') AS categories " +
                           "FROM Games g " +
                           "JOIN Users u ON g.user_id = u.user_id " +
                           "JOIN categories_Games cg ON g.game_id = cg.game_id " +
                           "JOIN categories c ON cg.category_id = c.category_id " +
                           "WHERE c.category = 'Aventura' " +
                           "GROUP BY g.game_id " +
                           "ORDER BY g.game_id DESC " +
                           "LIMIT 16";

            PreparedStatement pstmt = cn.prepareStatement(query);
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                int gameId = rs.getInt("game_id");
                String titulo = rs.getString("title");
                String coverPath = rs.getString("cover");
                String username = rs.getString("username");
                String subtitle = rs.getString("subtitle");
                String description = rs.getString("description");
                String game = rs.getString("game");
                String categories = rs.getString("categories");

                Juego juego = new Juego(gameId, titulo, coverPath, subtitle, description, game, username, categories);
                juegos_Aventura.add(juego);
            }

            rs.close();
            pstmt.close();

        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error al obtener los juegos de Aventura.", "Error", JOptionPane.ERROR_MESSAGE);
        }

        return juegos_Aventura;
    }
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {
        java.awt.GridBagConstraints gridBagConstraints;

        BgPanel = new javax.swing.JPanel();
        header = new javax.swing.JPanel();
        Icon = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jTextField1 = new javax.swing.JTextField();
        jLabel13 = new javax.swing.JLabel();
        userIcon = new javax.swing.JLabel();
        Body = new javax.swing.JPanel();
        columnCategories = new javax.swing.JPanel();
        title = new javax.swing.JLabel();
        listPanel = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel51 = new javax.swing.JLabel();
        jScrollPane = new raven.scroll.win11.ScrollPaneWin11();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        jPanel31 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jPanel30 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jLabel34 = new javax.swing.JLabel();
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
        jPanel7 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
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
        jPanel29 = new javax.swing.JPanel();
        jLabel45 = new javax.swing.JLabel();
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
        jSeparator1 = new javax.swing.JSeparator();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(2, 21, 38));
        setExtendedState(MAXIMIZED_BOTH);
        setMinimumSize(new java.awt.Dimension(1139, 637));

        BgPanel.setBackground(new java.awt.Color(2, 21, 38));
        BgPanel.setMinimumSize(new java.awt.Dimension(1139, 637));
        BgPanel.setLayout(new java.awt.BorderLayout());

        header.setBackground(new java.awt.Color(2, 21, 38));
        header.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.LEFT, 8, 5));

        Icon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logo header.png"))); // NOI18N
        Icon.setToolTipText("");
        Icon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        Icon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                IconMouseClicked(evt);
            }
        });
        header.add(Icon);

        jLabel2.setFont(new java.awt.Font("Samsung Sans", 0, 18)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(234, 250, 255));
        jLabel2.setText("Subir Juego");
        jLabel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel2MouseClicked(evt);
            }
        });
        header.add(jLabel2);

        jTextField1.setBackground(new java.awt.Color(2, 21, 38));
        jTextField1.setFont(new java.awt.Font("Samsung Sans", 0, 14)); // NOI18N
        jTextField1.setForeground(new java.awt.Color(234, 250, 255));
        jTextField1.setText("Buscar Juego");
        jTextField1.setBorder(null);
        jTextField1.setCursor(new java.awt.Cursor(java.awt.Cursor.TEXT_CURSOR));
        jTextField1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTextField1MouseClicked(evt);
            }
        });
        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        header.add(jTextField1);

        jLabel13.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/searchIcon.png"))); // NOI18N
        jLabel13.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel13.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel13MouseClicked(evt);
            }
        });
        header.add(jLabel13);

        userIcon.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/userIcon.png"))); // NOI18N
        userIcon.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        userIcon.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                userIconMouseClicked(evt);
            }
        });
        header.add(userIcon);

        BgPanel.add(header, java.awt.BorderLayout.PAGE_START);

        Body.setBackground(new java.awt.Color(2, 21, 38));
        Body.setForeground(new java.awt.Color(2, 21, 38));

        columnCategories.setBackground(new java.awt.Color(2, 35, 63));

        title.setBackground(new java.awt.Color(2, 35, 63));
        title.setFont(new java.awt.Font("Samsung Sans", 0, 18)); // NOI18N
        title.setForeground(new java.awt.Color(207, 243, 255));
        title.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        title.setText("Etiquetas Populares");

        listPanel.setBackground(new java.awt.Color(2, 35, 63));
        listPanel.setLayout(new javax.swing.BoxLayout(listPanel, javax.swing.BoxLayout.Y_AXIS));

        jLabel3.setFont(new java.awt.Font("Samsung Sans", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(234, 250, 255));
        jLabel3.setText("Juegos Plataforma");
        jLabel3.setAlignmentY(0.8F);
        jLabel3.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 6, 8, 0));
        jLabel3.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });
        listPanel.add(jLabel3);

        jLabel8.setFont(new java.awt.Font("Samsung Sans", 0, 14)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(234, 250, 255));
        jLabel8.setText("FPS");
        jLabel8.setAlignmentY(0.8F);
        jLabel8.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 6, 8, 100));
        jLabel8.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel8MouseClicked(evt);
            }
        });
        listPanel.add(jLabel8);

        jLabel9.setFont(new java.awt.Font("Samsung Sans", 0, 14)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(234, 250, 255));
        jLabel9.setText("Juegos Arcade");
        jLabel9.setAlignmentY(0.8F);
        jLabel9.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 6, 8, 0));
        jLabel9.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel9.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel9MouseClicked(evt);
            }
        });
        listPanel.add(jLabel9);

        jLabel10.setFont(new java.awt.Font("Samsung Sans", 0, 14)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(234, 250, 255));
        jLabel10.setText("Juegos Aventura");
        jLabel10.setAlignmentY(0.8F);
        jLabel10.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 6, 8, 0));
        jLabel10.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel10.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel10MouseClicked(evt);
            }
        });
        listPanel.add(jLabel10);

        jLabel51.setFont(new java.awt.Font("Samsung Sans", 0, 14)); // NOI18N
        jLabel51.setForeground(new java.awt.Color(234, 250, 255));
        jLabel51.setText("Juegos Deportes");
        jLabel51.setAlignmentY(0.8F);
        jLabel51.setBorder(javax.swing.BorderFactory.createEmptyBorder(10, 6, 8, 0));
        jLabel51.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jLabel51.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel51MouseClicked(evt);
            }
        });
        listPanel.add(jLabel51);

        javax.swing.GroupLayout columnCategoriesLayout = new javax.swing.GroupLayout(columnCategories);
        columnCategories.setLayout(columnCategoriesLayout);
        columnCategoriesLayout.setHorizontalGroup(
            columnCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(title, javax.swing.GroupLayout.DEFAULT_SIZE, 180, Short.MAX_VALUE)
            .addComponent(listPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        columnCategoriesLayout.setVerticalGroup(
            columnCategoriesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(columnCategoriesLayout.createSequentialGroup()
                .addComponent(title, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(listPanel, javax.swing.GroupLayout.DEFAULT_SIZE, 547, Short.MAX_VALUE))
        );

        jScrollPane.setBackground(new java.awt.Color(2, 21, 38));
        jScrollPane.setForeground(new java.awt.Color(2, 21, 38));
        jScrollPane.setOpaque(false);
        jScrollPane.setViewportView(jPanel1);

        jPanel1.setBackground(new java.awt.Color(2, 21, 38));
        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.Y_AXIS));

        jPanel2.setBackground(new java.awt.Color(2, 21, 38));
        jPanel2.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jPanel2.setPreferredSize(new java.awt.Dimension(1007, 200));
        jPanel2.setLayout(new javax.swing.BoxLayout(jPanel2, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel31.setMinimumSize(new java.awt.Dimension(129, 62));
        jPanel31.setOpaque(false);
        jPanel31.setPreferredSize(new java.awt.Dimension(1007, 79));
        jPanel31.setLayout(new java.awt.BorderLayout(0, 30));

        jLabel4.setFont(new java.awt.Font("Samsung Sans", 0, 22)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(234, 250, 255));
        jLabel4.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel4.setText("<html><body>Juego Más Destacado de la Plataforma:</body></html>");
        jLabel4.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jLabel4.setMaximumSize(new java.awt.Dimension(750, 55));
        jLabel4.setMinimumSize(new java.awt.Dimension(129, 49));
        jLabel4.setPreferredSize(new java.awt.Dimension(179, 60));
        jPanel31.add(jLabel4, java.awt.BorderLayout.CENTER);

        jPanel2.add(jPanel31);

        jPanel30.setOpaque(false);
        jPanel30.setPreferredSize(new java.awt.Dimension(1007, 170));
        jPanel30.setLayout(new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 35, 5));

        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img.png"))); // NOI18N
        jPanel30.add(jLabel5);

        jLabel6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img2.jpg"))); // NOI18N
        jPanel30.add(jLabel6);

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/img3.jpg"))); // NOI18N
        jPanel30.add(jLabel7);

        jPanel2.add(jPanel30);
        jPanel30.getAccessibleContext().setAccessibleName("");

        jPanel1.add(jPanel2);

        jPanel5.setMinimumSize(new java.awt.Dimension(100, 62));
        jPanel5.setOpaque(false);
        jPanel5.setPreferredSize(new java.awt.Dimension(962, 72));
        jPanel5.setLayout(new java.awt.BorderLayout());

        jLabel34.setFont(new java.awt.Font("Samsung Sans", 0, 18)); // NOI18N
        jLabel34.setForeground(new java.awt.Color(234, 250, 255));
        jLabel34.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel34.setText("Últimos juegos subidos a la plataforma: ");
        jLabel34.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel34.setMaximumSize(new java.awt.Dimension(123123123, 24));
        jLabel34.setMinimumSize(new java.awt.Dimension(322, 62));
        jLabel34.setPreferredSize(new java.awt.Dimension(322, 79));
        jPanel5.add(jLabel34, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel5);

        jPanel3.setBackground(new java.awt.Color(2, 21, 38));
        jPanel3.setMinimumSize(new java.awt.Dimension(200, 120));
        jPanel3.setPreferredSize(new java.awt.Dimension(952, 160));
        java.awt.GridBagLayout jPanel3Layout = new java.awt.GridBagLayout();
        jPanel3Layout.columnWidths = new int[] {220};
        jPanel3Layout.rowHeights = new int[] {110};
        jPanel3.setLayout(jPanel3Layout);

        jPanel4.setBackground(new java.awt.Color(25, 40, 56));
        jPanel4.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel4.setMinimumSize(new java.awt.Dimension(220, 110));
        jPanel4.setPreferredSize(new java.awt.Dimension(220, 110));
        jPanel4.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel4MouseClicked(evt);
            }
        });
        jPanel4.setLayout(new javax.swing.BoxLayout(jPanel4, javax.swing.BoxLayout.X_AXIS));

        jPanel8.setOpaque(false);
        jPanel8.setPreferredSize(new java.awt.Dimension(110, 110));
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
        java.awt.FlowLayout flowLayout5 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout5.setAlignOnBaseline(true);
        jPanel11.setLayout(flowLayout5);

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
        java.awt.FlowLayout flowLayout6 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout6.setAlignOnBaseline(true);
        jPanel14.setLayout(flowLayout6);

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
        java.awt.FlowLayout flowLayout12 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout12.setAlignOnBaseline(true);
        jPanel19.setLayout(flowLayout12);

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

        jPanel7.setMinimumSize(new java.awt.Dimension(100, 62));
        jPanel7.setOpaque(false);
        jPanel7.setPreferredSize(new java.awt.Dimension(962, 72));
        jPanel7.setLayout(new java.awt.BorderLayout());

        jLabel33.setFont(new java.awt.Font("Samsung Sans", 0, 18)); // NOI18N
        jLabel33.setForeground(new java.awt.Color(234, 250, 255));
        jLabel33.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel33.setText("PLATAFORMAS");
        jLabel33.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jPanel7.add(jLabel33, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel7);

        jPanel16.setBackground(new java.awt.Color(2, 21, 38));
        jPanel16.setMinimumSize(new java.awt.Dimension(200, 120));
        jPanel16.setPreferredSize(new java.awt.Dimension(952, 160));
        jPanel16.setLayout(new java.awt.GridBagLayout());

        jPanel17.setBackground(new java.awt.Color(25, 40, 56));
        jPanel17.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel17.setMinimumSize(new java.awt.Dimension(220, 110));
        jPanel17.setLayout(new javax.swing.BoxLayout(jPanel17, javax.swing.BoxLayout.X_AXIS));

        jPanel18.setOpaque(false);
        java.awt.FlowLayout flowLayout13 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout13.setAlignOnBaseline(true);
        jPanel18.setLayout(flowLayout13);

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
        java.awt.FlowLayout flowLayout14 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout14.setAlignOnBaseline(true);
        jPanel23.setLayout(flowLayout14);

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
        java.awt.FlowLayout flowLayout15 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout15.setAlignOnBaseline(true);
        jPanel26.setLayout(flowLayout15);

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
        java.awt.FlowLayout flowLayout16 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout16.setAlignOnBaseline(true);
        jPanel40.setLayout(flowLayout16);

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

        jPanel29.setMinimumSize(new java.awt.Dimension(100, 62));
        jPanel29.setOpaque(false);
        jPanel29.setPreferredSize(new java.awt.Dimension(962, 72));
        jPanel29.setLayout(new java.awt.BorderLayout());

        jLabel45.setFont(new java.awt.Font("Samsung Sans", 0, 18)); // NOI18N
        jLabel45.setForeground(new java.awt.Color(234, 250, 255));
        jLabel45.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel45.setText("DEPORTES");
        jLabel45.setVerticalAlignment(javax.swing.SwingConstants.BOTTOM);
        jLabel45.setHorizontalTextPosition(javax.swing.SwingConstants.CENTER);
        jPanel29.add(jLabel45, java.awt.BorderLayout.CENTER);

        jPanel1.add(jPanel29);

        jPanel42.setBackground(new java.awt.Color(2, 21, 38));
        jPanel42.setMinimumSize(new java.awt.Dimension(200, 120));
        jPanel42.setPreferredSize(new java.awt.Dimension(952, 160));
        jPanel42.setLayout(new java.awt.GridBagLayout());

        jPanel43.setBackground(new java.awt.Color(25, 40, 56));
        jPanel43.setMaximumSize(new java.awt.Dimension(220, 110));
        jPanel43.setMinimumSize(new java.awt.Dimension(220, 110));
        jPanel43.setLayout(new javax.swing.BoxLayout(jPanel43, javax.swing.BoxLayout.X_AXIS));

        jPanel44.setOpaque(false);
        java.awt.FlowLayout flowLayout21 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout21.setAlignOnBaseline(true);
        jPanel44.setLayout(flowLayout21);

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
        java.awt.FlowLayout flowLayout22 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout22.setAlignOnBaseline(true);
        jPanel47.setLayout(flowLayout22);

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
        java.awt.FlowLayout flowLayout23 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout23.setAlignOnBaseline(true);
        jPanel50.setLayout(flowLayout23);

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
        java.awt.FlowLayout flowLayout24 = new java.awt.FlowLayout(java.awt.FlowLayout.CENTER, 10, 10);
        flowLayout24.setAlignOnBaseline(true);
        jPanel53.setLayout(flowLayout24);

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

        jSeparator1.setBackground(new java.awt.Color(2, 21, 38));
        jSeparator1.setForeground(new java.awt.Color(2, 21, 38));
        jSeparator1.setOrientation(javax.swing.SwingConstants.VERTICAL);
        jSeparator1.setToolTipText("");
        jSeparator1.setMinimumSize(new java.awt.Dimension(50, 20));
        jSeparator1.setPreferredSize(new java.awt.Dimension(50, 40));
        jPanel1.add(jSeparator1);

        jScrollPane.setViewportView(jPanel1);

        javax.swing.GroupLayout BodyLayout = new javax.swing.GroupLayout(Body);
        Body.setLayout(BodyLayout);
        BodyLayout.setHorizontalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyLayout.createSequentialGroup()
                .addComponent(columnCategories, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, 0)
                .addComponent(jScrollPane, javax.swing.GroupLayout.DEFAULT_SIZE, 959, Short.MAX_VALUE))
        );
        BodyLayout.setVerticalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(columnCategories, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
        );

        BgPanel.add(Body, java.awt.BorderLayout.CENTER);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BgPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(BgPanel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jTextField1MouseClicked
        jTextField1.setText("");
    }//GEN-LAST:event_jTextField1MouseClicked
    
    private void IconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_IconMouseClicked
           // Obtén todos los componentes del BgPanel
         Component[] components = BgPanel.getComponents();

         // Itera sobre los componentes
         for (Component component : components) {
             // Verifica si el componente no es el header
             if (component != header) {
                 // Elimina el componente del BgPanel
                 BgPanel.remove(component);
             }
         }

         // Revalida y repinta el panel para reflejar los cambios
        BgPanel.add(Body); // Agrega el content pane del JFrame
        BgPanel.revalidate();
        BgPanel.repaint();
    }//GEN-LAST:event_IconMouseClicked

    private void jLabel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel2MouseClicked
            // Obtén todos los componentes del BgPanel
         Component[] components = BgPanel.getComponents();

         // Itera sobre los componentes
         for (Component component : components) {
             // Verifica si el componente no es el header
             if (component != header) {
                 // Elimina el componente del BgPanel
                 BgPanel.remove(component);
             }
         }

         // Revalida y repinta el panel para reflejar los cambios
        BgPanel.add(upGm.getContentPane()); // Agrega el content pane del JFrame
        BgPanel.revalidate();
        BgPanel.repaint();
    }//GEN-LAST:event_jLabel2MouseClicked

    private void jPanel4MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel4MouseClicked
        
    }//GEN-LAST:event_jPanel4MouseClicked

    private void jLabel13MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel13MouseClicked
        String juegoBusqueda = jTextField1.getText();
        List<Juego> juegosEncontrados = buscarJuegos(juegoBusqueda); // Buscar juegos

        if (juegosEncontrados != null && !juegosEncontrados.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Se ha(n) encontrado " + juegosEncontrados.size() + " juego(s)");

            // Limpiar el BgPanel (excepto el header)
            Component[] components = BgPanel.getComponents();
            for (Component component : components) {
                if (component != header) {
                    BgPanel.remove(component);
                }
            }

            // Crear una instancia del jFrame games

            // Pasar los juegos encontrados al jFrame games
            gm.actualizarTarjetas(juegosEncontrados);

            // Agregar el jFrame games al BgPanel
            BgPanel.add(gm.getContentPane());
            BgPanel.revalidate();
            BgPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No hay ningún juego que coincida");
        }
     
    }//GEN-LAST:event_jLabel13MouseClicked

    private void userIconMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_userIconMouseClicked
        // Limpiar el BgPanel (excepto el header)
        Component[] components = BgPanel.getComponents();
        for (Component component : components) {
            if (component != header) {
                BgPanel.remove(component);
            }
        }

        // Obtener la información del usuario
        UserInfo userInfo = obtenerInformacionUsuario(userId); // Cambia el 1 por el ID del usuario actual

        // Pasar la información al frmUser
        user.setUserInfo(userInfo);

        // Agregar el frmUser al BgPanel
        BgPanel.add(user.getContentPane());
        BgPanel.revalidate();
        BgPanel.repaint();
    }//GEN-LAST:event_userIconMouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
          if (juegos_Plataforma != null && !juegos_Plataforma.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Se ha(n) encontrado " + juegos_Plataforma.size() + " juego(s)");

            // Limpiar el BgPanel (excepto el header)
            Component[] components = BgPanel.getComponents();
            for (Component component : components) {
                if (component != header) {
                    BgPanel.remove(component);
                }
            }

            // Crear una instancia del jFrame games

            // Pasar los juegos encontrados al jFrame games
            gm.actualizarTarjetas(juegos_Plataforma);

            // Agregar el jFrame games al BgPanel
            BgPanel.add(gm.getContentPane());
            BgPanel.revalidate();
            BgPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No hay ningún juego que coincida");
        }
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jLabel8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel8MouseClicked
        // Juegos Fps
        if (juegos_FPS != null && !juegos_FPS.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Se ha(n) encontrado " + juegos_FPS.size() + " juego(s)");

            // Limpiar el BgPanel (excepto el header)
            Component[] components = BgPanel.getComponents();
            for (Component component : components) {
                if (component != header) {
                    BgPanel.remove(component);
                }
            }

            // Crear una instancia del jFrame games

            // Pasar los juegos encontrados al jFrame games
            gm.actualizarTarjetas(juegos_FPS);

            // Agregar el jFrame games al BgPanel
            BgPanel.add(gm.getContentPane());
            BgPanel.revalidate();
            BgPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No hay ningún juego que coincida");
        }
    }//GEN-LAST:event_jLabel8MouseClicked

    private void jLabel9MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel9MouseClicked
        // Arcade
        if (juegos_Arcade != null && !juegos_Arcade.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Se ha(n) encontrado " + juegos_Arcade.size() + " juego(s)");

            // Limpiar el BgPanel (excepto el header)
            Component[] components = BgPanel.getComponents();
            for (Component component : components) {
                if (component != header) {
                    BgPanel.remove(component);
                }
            }

            // Crear una instancia del jFrame games

            // Pasar los juegos encontrados al jFrame games
            gm.actualizarTarjetas(juegos_Arcade);

            // Agregar el jFrame games al BgPanel
            BgPanel.add(gm.getContentPane());
            BgPanel.revalidate();
            BgPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No hay ningún juego que coincida");
        }             
    }//GEN-LAST:event_jLabel9MouseClicked

    private void jLabel10MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel10MouseClicked
        // Aventura
        if (juegos_Aventura != null && !juegos_Aventura.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Se ha(n) encontrado " + juegos_Aventura.size() + " juego(s)");

            // Limpiar el BgPanel (excepto el header)
            Component[] components = BgPanel.getComponents();
            for (Component component : components) {
                if (component != header) {
                    BgPanel.remove(component);
                }
            }

            // Crear una instancia del jFrame games

            // Pasar los juegos encontrados al jFrame games
            gm.actualizarTarjetas(juegos_Aventura);

            // Agregar el jFrame games al BgPanel
            BgPanel.add(gm.getContentPane());
            BgPanel.revalidate();
            BgPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No hay ningún juego que coincida");
        }    
    }//GEN-LAST:event_jLabel10MouseClicked

    private void jLabel51MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel51MouseClicked
        //Deportes
        if (juegos_Deportes != null && !juegos_Deportes.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Se ha(n) encontrado " + juegos_Deportes.size() + " juego(s)");

            // Limpiar el BgPanel (excepto el header)
            Component[] components = BgPanel.getComponents();
            for (Component component : components) {
                if (component != header) {
                    BgPanel.remove(component);
                }
            }

            // Crear una instancia del jFrame games

            // Pasar los juegos encontrados al jFrame games
            gm.actualizarTarjetas(juegos_Deportes);

            // Agregar el jFrame games al BgPanel
            BgPanel.add(gm.getContentPane());
            BgPanel.revalidate();
            BgPanel.repaint();
        } else {
            JOptionPane.showMessageDialog(this, "No hay ningún juego que coincida");
        }    
    }//GEN-LAST:event_jLabel51MouseClicked

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
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(frmMain.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new frmMain().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel BgPanel;
    private javax.swing.JPanel Body;
    private javax.swing.JLabel Icon;
    private javax.swing.JPanel columnCategories;
    private javax.swing.JPanel header;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
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
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel51;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
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
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel21;
    private javax.swing.JPanel jPanel22;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
    private javax.swing.JPanel jPanel25;
    private javax.swing.JPanel jPanel26;
    private javax.swing.JPanel jPanel27;
    private javax.swing.JPanel jPanel28;
    private javax.swing.JPanel jPanel29;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel30;
    private javax.swing.JPanel jPanel31;
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
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel50;
    private javax.swing.JPanel jPanel51;
    private javax.swing.JPanel jPanel52;
    private javax.swing.JPanel jPanel53;
    private javax.swing.JPanel jPanel54;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JPanel listPanel;
    private javax.swing.JLabel title;
    private javax.swing.JLabel userIcon;
    // End of variables declaration//GEN-END:variables
}
