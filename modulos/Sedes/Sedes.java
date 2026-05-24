package com.mycompany.proyecto_final_poo;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Sedes extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Sedes.class.getName());
    private final TableRowSorter<DefaultTableModel> trsFiltro;
    private int i=1;
    
    public Sedes() {
        initComponents();
        this.setResizable(false);
        java.time.LocalDate fechaActual = java.time.LocalDate.now();
        java.time.format.DateTimeFormatter formato = java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy");
        lblFecha.setText(fechaActual.format(formato));
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        trsFiltro = new TableRowSorter<>(modelo);
        jTable1.setRowSorter(trsFiltro);
        cargarDatosDesdeSQL();
    }

    
    
    private void cargarDatosDesdeSQL() {
        DefaultTableModel modelo = (DefaultTableModel) jTable1.getModel();
        modelo.setRowCount(0);

        try {
            Connection con = ConexionSQL.conectar(); 
            Statement st = con.createStatement();
            ResultSet rs = st.executeQuery("SELECT ID, Nombre, UbiGeo, AforoMaximo, Estado FROM Sedes");

            while (rs.next()) {
                String id = rs.getString("ID"); 
                String nombre = rs.getString("Nombre");
                String ubiGeo = rs.getString("UbiGeo");
                int aforo = rs.getInt("AforoMaximo");
                
               
                int estadoNumero = rs.getInt("Estado"); 
                String estadoTexto = "";
                if (estadoNumero == 0) estadoTexto = "Operativo";
                else if (estadoNumero == 1) estadoTexto = "Mantenimiento";
                else if (estadoNumero == 2) estadoTexto = "Clausurado";

                Object[] filaCargada = new Object[]{id, nombre, ubiGeo, aforo, estadoTexto, "..."};
                modelo.addRow(filaCargada);
            }
            con.close(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar datos de SQL: " + e.getMessage());
        }
    }
    
    private void aplicarFiltros() {
  
    
    String filtroEstado = jbEstado.getSelectedItem().toString();
    String filtroCapacidad = jbCapacidad.getSelectedItem().toString();
    String textoBusqueda = jbBusqueda.getText().trim(); 

    List<RowFilter<Object, Object>> filtros = new ArrayList<>();

    if (!textoBusqueda.isEmpty() && !textoBusqueda.contains("Buscar")) {
        filtros.add(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(textoBusqueda), 1)); 
    }   
    
    if (!filtroEstado.equals("ESTADO")) {
        filtros.add(RowFilter.regexFilter("^" + filtroEstado + "$", 4)); 
    }

    if (!filtroCapacidad.equals("CAPACIDAD")) {
        if (filtroCapacidad.equals("Mayor a 100")) {
            filtros.add(RowFilter.numberFilter(RowFilter.ComparisonType.AFTER, 100, 3));
        
        } else if (filtroCapacidad.equals("Menor a 100")) {
          filtros.add(RowFilter.numberFilter(RowFilter.ComparisonType.BEFORE, 101, 3));
        }
    }

   
    if (filtros.isEmpty()) {
        trsFiltro.setRowFilter(null);
    } else {
        trsFiltro.setRowFilter(RowFilter.andFilter(filtros)); 
    }
}
    
    private void BuscarUbiGeo() {
    String NombreArchivo = "datos/UbiGeo.txt";
    File fichero = new File(NombreArchivo);
    DefaultTableModel model = (DefaultTableModel) jTable1.getModel();

    if (!fichero.exists()) {
        JOptionPane.showMessageDialog(this, "El archivo de UbiGeo no existe.");
        return;
    }
    boolean modoTraducir = (i % 2 != 0);

    try (BufferedReader entrada = new BufferedReader(new FileReader(fichero))) {
        
        List<String> codigos = new ArrayList<>();
        List<String> nombres = new ArrayList<>();
        String linea;
        
        while ((linea = entrada.readLine()) != null) {
            String[] partes = linea.split(" ", 2);
            if (partes.length >= 2) {
                codigos.add(partes[0].trim());
                nombres.add(partes[1].trim());
            }
        }

        for (int fila = 0; fila < model.getRowCount(); fila++) {
            String valorActual = model.getValueAt(fila, 2).toString().trim();

            for (int j = 0; j < codigos.size(); j++) {
                if (modoTraducir) {
                    if (valorActual.equals(codigos.get(j))) {
                        jbTRADUCIR.setText("REGRESAR UBIGEO");
                        model.setValueAt(nombres.get(j), fila, 2);
                        break; 
                    }
                } else {
                    if (valorActual.equals(nombres.get(j))) {
                        jbTRADUCIR.setText("TRADUCIR UBIGEO");
                        model.setValueAt(codigos.get(j), fila, 2);
                        break;
                    }
                }
            }
        }
        i++;

    } catch (IOException e) {
        System.out.println("Error de lectura: " + e.getMessage());
    }
}
    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">                          
    private void initComponents() {

        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblSUSCRIPCION = new javax.swing.JLabel();
        lblREGISTRO = new javax.swing.JLabel();
        lblMAQUINAS = new javax.swing.JLabel();
        lblSEDES = new javax.swing.JLabel();
        lblSOPORTE = new javax.swing.JLabel();
        lblADMINISTRACION = new javax.swing.JLabel();
        imgGymWork = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblSUSCRIPCION1 = new javax.swing.JLabel();
        lblREGISTRO1 = new javax.swing.JLabel();
        lblMAQUINAS1 = new javax.swing.JLabel();
        lblSOPORTE1 = new javax.swing.JLabel();
        lblADMINISTRACION1 = new javax.swing.JLabel();
        imgGymWork1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanelSEDES = new javax.swing.JPanel();
        IconSedes = new javax.swing.JLabel();
        lblSEDES1 = new javax.swing.JLabel();
        lblINICIO1 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jbBusqueda = new javax.swing.JTextField();
        jbEstado = new javax.swing.JComboBox<>();
        jbCapacidad = new javax.swing.JComboBox<>();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTable1 = new javax.swing.JTable();
        jbTRADUCIR = new javax.swing.JButton();
        jPanel5 = new javax.swing.JPanel();
        jLabel11 = new javax.swing.JLabel();
        jbRegistro = new javax.swing.JButton();
        lblFecha = new javax.swing.JLabel();

        jPanel2.setBackground(new java.awt.Color(204, 255, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jLabel1.setFont(new java.awt.Font("Rockwell Condensed", 3, 48)); // NOI18N
        jLabel1.setText("GymWork");

        lblSUSCRIPCION.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSUSCRIPCION.setText("Suscripciones");
        lblSUSCRIPCION.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCIONMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCIONMouseExited(evt);
            }
        });

        lblREGISTRO.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblREGISTRO.setText("Registro");
        lblREGISTRO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblREGISTROMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblREGISTROMouseExited(evt);
            }
        });

        lblMAQUINAS.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblMAQUINAS.setText("Estado de maquinas");
        lblMAQUINAS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMAQUINASMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMAQUINASMouseExited(evt);
            }
        });

        lblSEDES.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSEDES.setText("Sedes");
        lblSEDES.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSEDESMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSEDESMouseExited(evt);
            }
        });

        lblSOPORTE.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSOPORTE.setText("Soporte y asistencia técnica");
        lblSOPORTE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSOPORTEMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSOPORTEMouseExited(evt);
            }
        });

        lblADMINISTRACION.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblADMINISTRACION.setText("Módulo de administración");
        lblADMINISTRACION.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblADMINISTRACIONMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblADMINISTRACIONMouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(imgGymWork)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 192, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(56, 56, 56))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4)
                            .addComponent(jLabel2)
                            .addComponent(jLabel3)
                            .addComponent(jLabel6)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7))
                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblADMINISTRACION)
                    .addComponent(lblREGISTRO)
                    .addComponent(lblSUSCRIPCION)
                    .addComponent(lblSEDES)
                    .addComponent(lblMAQUINAS)
                    .addComponent(lblSOPORTE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(imgGymWork))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(52, 52, 52)
                        .addComponent(lblSUSCRIPCION))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(33, 33, 33)
                        .addComponent(jLabel2)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(61, 61, 61)
                        .addComponent(lblREGISTRO))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(42, 42, 42)
                        .addComponent(jLabel3)))
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGap(71, 71, 71)
                        .addComponent(lblSEDES)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(lblMAQUINAS)
                        .addGap(65, 65, 65)
                        .addComponent(lblSOPORTE)
                        .addGap(77, 77, 77)
                        .addComponent(lblADMINISTRACION)
                        .addGap(70, 70, 70))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 53, Short.MAX_VALUE)
                        .addComponent(jLabel4)
                        .addGap(51, 51, 51)
                        .addComponent(jLabel6)
                        .addGap(38, 38, 38)
                        .addComponent(jLabel7)
                        .addGap(49, 49, 49)
                        .addComponent(jLabel5)
                        .addGap(62, 62, 62))))
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Rockwell Condensed", 3, 48)); // NOI18N
        jLabel8.setText("GymWork");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 192, -1));

        lblSUSCRIPCION1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSUSCRIPCION1.setText("Suscripciones");
        lblSUSCRIPCION1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCION1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCION1MouseExited(evt);
            }
        });
        jPanel3.add(lblSUSCRIPCION1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        lblREGISTRO1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblREGISTRO1.setText("Registro");
        lblREGISTRO1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblREGISTRO1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblREGISTRO1MouseExited(evt);
            }
        });
        jPanel3.add(lblREGISTRO1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, -1, -1));

        lblMAQUINAS1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblMAQUINAS1.setText("Estado de maquinas");
        lblMAQUINAS1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMAQUINAS1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMAQUINAS1MouseExited(evt);
            }
        });
        jPanel3.add(lblMAQUINAS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, -1, -1));

        lblSOPORTE1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSOPORTE1.setText("Soporte y asistencia técnica");
        lblSOPORTE1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSOPORTE1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSOPORTE1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSOPORTE1MouseExited(evt);
            }
        });
        jPanel3.add(lblSOPORTE1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 560, -1, -1));

        lblADMINISTRACION1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblADMINISTRACION1.setText("Módulo de administración");
        lblADMINISTRACION1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblADMINISTRACION1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblADMINISTRACION1MouseExited(evt);
            }
        });
        jPanel3.add(lblADMINISTRACION1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 640, -1, -1));
        jPanel3.add(imgGymWork1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 27, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconSuscripciones.png")); // NOI18N
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        jLabel10.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconRegistro.png")); // NOI18N
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconMaquinas.png")); // NOI18N
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));

        jLabel13.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconSoporte.png")); // NOI18N
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconAdministracon.png")); // NOI18N
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, -1, -1));

        jPanelSEDES.setBackground(new java.awt.Color(102, 204, 255));
        jPanelSEDES.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        IconSedes.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconSedes - copia.png")); // NOI18N

        lblSEDES1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSEDES1.setForeground(new java.awt.Color(0, 102, 255));
        lblSEDES1.setText("Sedes");
        lblSEDES1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSEDES1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSEDES1MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanelSEDESLayout = new javax.swing.GroupLayout(jPanelSEDES);
        jPanelSEDES.setLayout(jPanelSEDESLayout);
        jPanelSEDESLayout.setHorizontalGroup(
            jPanelSEDESLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSEDESLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(IconSedes)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblSEDES1)
                .addContainerGap(215, Short.MAX_VALUE))
        );
        jPanelSEDESLayout.setVerticalGroup(
            jPanelSEDESLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSEDESLayout.createSequentialGroup()
                .addComponent(IconSedes)
                .addGap(0, 5, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSEDESLayout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblSEDES1)
                .addGap(14, 14, 14))
        );

        jPanel3.add(jPanelSEDES, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 390, 340, -1));

        lblINICIO1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblINICIO1.setText("Inicio");
        lblINICIO1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblINICIO1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblINICIO1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblINICIO1MouseExited(evt);
            }
        });
        jPanel3.add(lblINICIO1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconInicio.png")); // NOI18N
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        jPanel6.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel9.setBackground(new java.awt.Color(255, 255, 255));

        jbBusqueda.setForeground(new java.awt.Color(204, 204, 204));
        jbBusqueda.setText("🔍 Buscar por nombre");
        jbBusqueda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                jbBusquedaFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                jbBusquedaFocusLost(evt);
            }
        });
        jbBusqueda.addActionListener(this::jbBusquedaActionPerformed);
        jbBusqueda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                jbBusquedaKeyReleased(evt);
            }
        });

        jbEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "ESTADO", "Operativo", "Mantenimiento", "Clausurado" }));
        jbEstado.addActionListener(this::jbEstadoActionPerformed);

        jbCapacidad.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "CAPACIDAD", "Mayor a 100", "Menor a 100" }));
        jbCapacidad.addActionListener(this::jbCapacidadActionPerformed);

        jTable1.setFont(new java.awt.Font("Segoe UI", 2, 14)); // NOI18N
        jTable1.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID de Sede", "Nombre", "UbiGeo", "Aforo Máximo", "Estado", "Acciones"
            }
        ) {
            Class[] types = new Class [] {
                java.lang.String.class, java.lang.String.class, java.lang.String.class, java.lang.Integer.class, java.lang.String.class, java.lang.String.class
            };
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public Class getColumnClass(int columnIndex) {
                return types [columnIndex];
            }

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jTable1.getTableHeader().setReorderingAllowed(false);
        jTable1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jTable1MouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(jTable1);

        jbTRADUCIR.setText("TRADUCIR UBIGEO");
        jbTRADUCIR.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jbTRADUCIR.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jbTRADUCIRMouseClicked(evt);
            }
        });
        jbTRADUCIR.addActionListener(this::jbTRADUCIRActionPerformed);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jbBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 259, Short.MAX_VALUE)
                        .addComponent(jbTRADUCIR, javax.swing.GroupLayout.PREFERRED_SIZE, 131, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jbCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(19, 19, 19))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(16, 16, 16)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jbTRADUCIR, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jbBusqueda, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jbCapacidad, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 388, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(28, Short.MAX_VALUE))
        );

        jPanel6.add(jPanel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 100, 970, 470));

        jPanel5.setBackground(new java.awt.Color(51, 153, 255));
        jPanel5.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel11.setFont(new java.awt.Font("Segoe UI", 1, 48)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("Gestión de Sedes");
        jPanel5.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, -1, -1));

        jbRegistro.setBackground(new java.awt.Color(51, 51, 255));
        jbRegistro.setFont(new java.awt.Font("Segoe UI", 1, 18)); // NOI18N
        jbRegistro.setForeground(new java.awt.Color(255, 255, 255));
        jbRegistro.setText("+ Registrar Nueva Sede");
        jbRegistro.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));
        jbRegistro.setBorderPainted(false);
        jbRegistro.setFocusPainted(false);
        jbRegistro.addActionListener(this::jbRegistroActionPerformed);
        jPanel5.add(jbRegistro, new org.netbeans.lib.awtextra.AbsoluteConstraints(770, 30, 220, 30));

        jPanel6.add(jPanel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 1035, 146));

        lblFecha.setFont(new java.awt.Font("Segoe UI", 3, 14)); // NOI18N
        lblFecha.setText("jLabel15");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblFecha))
                .addContainerGap(17, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblFecha)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 597, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(29, 29, 29))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>                        

    private void lblSUSCRIPCIONMouseEntered(java.awt.event.MouseEvent evt) {                                            
        lblSUSCRIPCION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION1.setForeground(new java.awt.Color(0, 102, 255));
    }                                           

    private void lblSUSCRIPCIONMouseExited(java.awt.event.MouseEvent evt) {                                           
        lblSUSCRIPCION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION1.setForeground(new java.awt.Color(0, 0, 0));
    }                                          

    private void lblREGISTROMouseEntered(java.awt.event.MouseEvent evt) {                                         
        lblREGISTRO1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO1.setForeground(new java.awt.Color(0, 102, 255));
    }                                        

    private void lblREGISTROMouseExited(java.awt.event.MouseEvent evt) {                                        
        lblREGISTRO1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO1.setForeground(new java.awt.Color(0, 0, 0));
    }                                       

    private void lblMAQUINASMouseEntered(java.awt.event.MouseEvent evt) {                                         
        lblMAQUINAS1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS1.setForeground(new java.awt.Color(0, 102, 255));
    }                                        

    private void lblMAQUINASMouseExited(java.awt.event.MouseEvent evt) {                                        
        lblMAQUINAS1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS1.setForeground(new java.awt.Color(0, 0, 0));
    }                                       

    private void lblSEDESMouseEntered(java.awt.event.MouseEvent evt) {                                      
        lblSEDES1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES1.setForeground(new java.awt.Color(0, 102, 255));
    }                                     

    private void lblSEDESMouseExited(java.awt.event.MouseEvent evt) {                                     
        lblSEDES1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES1.setForeground(new java.awt.Color(0, 0, 0));
    }                                    

    private void lblSOPORTEMouseEntered(java.awt.event.MouseEvent evt) {                                        
        lblSOPORTE1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE1.setForeground(new java.awt.Color(0, 102, 255));
    }                                       

    private void lblSOPORTEMouseExited(java.awt.event.MouseEvent evt) {                                       
        lblSOPORTE1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE1.setForeground(new java.awt.Color(0, 0, 0));
    }                                      

    private void lblADMINISTRACIONMouseEntered(java.awt.event.MouseEvent evt) {                                               
        lblADMINISTRACION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION1.setForeground(new java.awt.Color(0, 102, 255));
    }                                              

    private void lblADMINISTRACIONMouseExited(java.awt.event.MouseEvent evt) {                                              
        lblADMINISTRACION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION1.setForeground(new java.awt.Color(0, 0, 0));
    }                                             

    private void lblSUSCRIPCION1MouseEntered(java.awt.event.MouseEvent evt) {                                             
        lblSUSCRIPCION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION1.setForeground(new java.awt.Color(0, 102, 255));
    }                                            

    private void lblSUSCRIPCION1MouseExited(java.awt.event.MouseEvent evt) {                                            
        lblSUSCRIPCION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION1.setForeground(new java.awt.Color(0, 0, 0));
    }                                           

    private void lblREGISTRO1MouseEntered(java.awt.event.MouseEvent evt) {                                          
        lblREGISTRO1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO1.setForeground(new java.awt.Color(0, 102, 255));
    }                                         

    private void lblREGISTRO1MouseExited(java.awt.event.MouseEvent evt) {                                         
        lblREGISTRO1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO1.setForeground(new java.awt.Color(0, 0, 0));
    }                                        

    private void lblMAQUINAS1MouseEntered(java.awt.event.MouseEvent evt) {                                          
        lblMAQUINAS1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS1.setForeground(new java.awt.Color(0, 102, 255));
    }                                         

    private void lblMAQUINAS1MouseExited(java.awt.event.MouseEvent evt) {                                         
        lblMAQUINAS1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS1.setForeground(new java.awt.Color(0, 0, 0));
    }                                        

    private void lblSEDES1MouseEntered(java.awt.event.MouseEvent evt) {                                       

    }                                      

    private void lblSEDES1MouseExited(java.awt.event.MouseEvent evt) {                                      

    }                                     

    private void lblSOPORTE1MouseEntered(java.awt.event.MouseEvent evt) {                                         
        lblSOPORTE1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE1.setForeground(new java.awt.Color(0, 102, 255));
    }                                        

    private void lblSOPORTE1MouseExited(java.awt.event.MouseEvent evt) {                                        
        lblSOPORTE1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE1.setForeground(new java.awt.Color(0, 0, 0));
    }                                       

    private void lblADMINISTRACION1MouseEntered(java.awt.event.MouseEvent evt) {                                                
        lblADMINISTRACION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION1.setForeground(new java.awt.Color(0, 102, 255));
    }                                               

    private void lblADMINISTRACION1MouseExited(java.awt.event.MouseEvent evt) {                                               
        lblADMINISTRACION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION1.setForeground(new java.awt.Color(0, 0, 0));
    }                                              

    private void jbRegistroActionPerformed(java.awt.event.ActionEvent evt) {                                           
        RegistroSede formulario = new RegistroSede(this,true);
        formulario.setLocationRelativeTo(this);
        formulario.setVisible(true);
        
        if (formulario.isFueGuardado()) {
           
            cargarDatosDesdeSQL(); 
            
            jbEstado.setSelectedIndex(0); 
            jbCapacidad.setSelectedIndex(0);
            jbBusqueda.setText("🔍 Buscar por nombre");
            jbBusqueda.setForeground(java.awt.Color.GRAY);
            aplicarFiltros();
        }
    }                                          

    private void jbBusquedaActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jbEstadoActionPerformed(java.awt.event.ActionEvent evt) {                                         
        aplicarFiltros();
    }                                        

    private void jbBusquedaFocusGained(java.awt.event.FocusEvent evt) {                                       
        if (jbBusqueda.getText().equals("🔍 Buscar por nombre")) {
            jbBusqueda.setText("");
            jbBusqueda.setForeground(Color.black); 
        }
    }                                      

    private void jbBusquedaFocusLost(java.awt.event.FocusEvent evt) {                                     
        if (jbBusqueda.getText().isEmpty()) {
            jbBusqueda.setText("🔍 Buscar por nombre");
            jbBusqueda.setForeground(Color.GRAY); 
            aplicarFiltros();
        }
    }                                    

    private void jbCapacidadActionPerformed(java.awt.event.ActionEvent evt) {                                            
        aplicarFiltros();
    }                                           

    private void jbBusquedaKeyReleased(java.awt.event.KeyEvent evt) {                                       
        aplicarFiltros();
    }                                      

    private void lblINICIO1MouseClicked(java.awt.event.MouseEvent evt) {                                        
        Admin_Panel OBJ = new Admin_Panel();
        OBJ.setVisible(true);
        this.dispose();
    }                                       

    private void lblINICIO1MouseEntered(java.awt.event.MouseEvent evt) {                                        
        lblINICIO1.setFont(new java.awt.Font("Sitka Text",1,18));
        lblINICIO1.setForeground(new java.awt.Color(0,102,255));
    }                                       

    private void lblINICIO1MouseExited(java.awt.event.MouseEvent evt) {                                       
        lblINICIO1.setFont(new java.awt.Font("Sitka Text",1,18));
        lblINICIO1.setForeground(new java.awt.Color(0,0,0));
    }                                      

    private void jbTRADUCIRActionPerformed(java.awt.event.ActionEvent evt) {                                           
        // TODO add your handling code here:
    }                                          

    private void jbTRADUCIRMouseClicked(java.awt.event.MouseEvent evt) {                                        
        BuscarUbiGeo();
    }                                       

    private void lblSOPORTE1MouseClicked(java.awt.event.MouseEvent evt) {                                         
        Soporte OBJ = new Soporte();
        OBJ.setVisible(true);
        this.dispose();
    }                                        

    private void jTable1MouseClicked(java.awt.event.MouseEvent evt) {                                     
        
    int fila = jTable1.rowAtPoint(evt.getPoint());
    int columna = jTable1.columnAtPoint(evt.getPoint());

    
    if (columna == 5) {
        String id = jTable1.getValueAt(fila, 0).toString();
        String nombre = jTable1.getValueAt(fila, 1).toString();
        String ubiGeo = jTable1.getValueAt(fila, 2).toString(); 
        String aforo = jTable1.getValueAt(fila, 3).toString();
        String estado = jTable1.getValueAt(fila, 4).toString();
        
        ModificarSede ventana = new ModificarSede(this, true, id, nombre, aforo, estado);
        ventana.setLocationRelativeTo(this);
        ventana.setVisible(true);

        if (ventana.isFueGuardado()) {
            cargarDatosDesdeSQL();
            aplicarFiltros();
        }
    }
    }                                    

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
        } catch (ReflectiveOperationException | javax.swing.UnsupportedLookAndFeelException ex) {
            logger.log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> new Sedes().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel IconSedes;
    private javax.swing.JLabel imgGymWork;
    private javax.swing.JLabel imgGymWork1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelSEDES;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTable1;
    private javax.swing.JTextField jbBusqueda;
    private javax.swing.JComboBox<String> jbCapacidad;
    private javax.swing.JComboBox<String> jbEstado;
    private javax.swing.JButton jbRegistro;
    private javax.swing.JButton jbTRADUCIR;
    private javax.swing.JLabel lblADMINISTRACION;
    private javax.swing.JLabel lblADMINISTRACION1;
    private javax.swing.JLabel lblFecha;
    private javax.swing.JLabel lblINICIO1;
    private javax.swing.JLabel lblMAQUINAS;
    private javax.swing.JLabel lblMAQUINAS1;
    private javax.swing.JLabel lblREGISTRO;
    private javax.swing.JLabel lblREGISTRO1;
    private javax.swing.JLabel lblSEDES;
    private javax.swing.JLabel lblSEDES1;
    private javax.swing.JLabel lblSOPORTE;
    private javax.swing.JLabel lblSOPORTE1;
    private javax.swing.JLabel lblSUSCRIPCION;
    private javax.swing.JLabel lblSUSCRIPCION1;
    // End of variables declaration                   
}
