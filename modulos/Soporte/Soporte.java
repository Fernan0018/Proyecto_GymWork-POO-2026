package com.mycompany.proyecto_final_poo;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.DefaultTableCellRenderer;
import java.awt.Component;
import javax.swing.JTable;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.util.ArrayList;
import java.util.List;
import java.awt.Color;
import javax.swing.JOptionPane;
import java.sql.Connection;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Soporte extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Soporte.class.getName());
    private TableRowSorter<DefaultTableModel> sorter;
    private TableRowSorter<DefaultTableModel> trsFiltro;
    /**
     * Creates new form Soporte
     */
    public Soporte() {
        initComponents();
        this.setResizable(false);
        DefaultTableModel modelo = (DefaultTableModel) tablaTickets.getModel();
        trsFiltro = new TableRowSorter<>(modelo);
        tablaTickets.setRowSorter(trsFiltro);
        cargarDatosDesdeSQL();
        actualizarKPIs();
        actualizarGraficoCategorias();
    }

    
    // NUEVO MÉTODO SQL
    private void cargarDatosDesdeSQL() {
        DefaultTableModel modelo = (DefaultTableModel) tablaTickets.getModel();
        modelo.setRowCount(0); // Limpia la tabla visual

        try (Connection con = ConexionSQL.conectar();
             Statement st = con.createStatement();
             // Leemos la tabla SOPORTE
             ResultSet rs = st.executeQuery("SELECT ID, Asunto, Remitente, Categoria, Prioridad, Estado FROM SOPORTE")) {

            while (rs.next()) {
                String id = rs.getString("ID");
                String asunto = rs.getString("Asunto");
                String remitente = rs.getString("Remitente");
                String categoria = rs.getString("Categoria");
                
                // TRADUCTOR DE PRIORIDAD: SQL(0,1) -> Visual(Baja,Alta)
                int prioridadNum = rs.getInt("Prioridad");
                String prioridadTexto = "Baja";
                if (prioridadNum == 1) {
                    prioridadTexto = "Alta";
                }

                // TRADUCTOR DE ESTADO: SQL(0,1,2) -> Visual(Open, In Progress, Resolved)
                int estadoNum = rs.getInt("Estado");
                String estadoTexto = "Open";
                if (estadoNum == 1) estadoTexto = "In Progress";
                else if (estadoNum == 2) estadoTexto = "Resolved";

                // Insertamos en la tabla visual
                Object[] filaCargada = new Object[]{id, asunto, remitente, categoria, prioridadTexto, estadoTexto};
                modelo.addRow(filaCargada);
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar tickets de SQL: " + e.getMessage());
        }
        
        // Mantiene tus colores en la columna de Prioridad
        tablaTickets.getColumnModel().getColumn(4).setCellRenderer(new RenderizadorPrioridad());
    }
    
    private void Filtro(){
        String textoBusqueda = txtBuscar.getText().trim();
        List<RowFilter<Object, Object>> filtros = new ArrayList<>();
        if (!textoBusqueda.isEmpty() && !textoBusqueda.contains("Busqueda por Ticket")) {
            filtros.add(RowFilter.regexFilter("(?i)" + java.util.regex.Pattern.quote(textoBusqueda), 0)); 
        }
        if (filtros.isEmpty()) {
            trsFiltro.setRowFilter(null);
        } else {
            trsFiltro.setRowFilter(RowFilter.andFilter(filtros)); 
        }
    }
    private void actualizarKPIs() {
    DefaultTableModel modelo = (DefaultTableModel) tablaTickets.getModel();
    int total = modelo.getRowCount();
    int pendientes = 0;
    int altaPrioridad = 0;
    int Resueltos = 0;

    for (int i = 0; i < total; i++) {
        String estado = modelo.getValueAt(i, 5).toString(); // Columna 5 es Estado
        String prioridad = modelo.getValueAt(i, 4).toString(); // Columna 4 es Prioridad
        String resuelto = modelo.getValueAt(i,5).toString();

        if (estado.equals("Open") || estado.equals("In Progress")) {
            pendientes++;
        }
        if (prioridad.equals("Alta") && !estado.equals("Resolved")) {
            altaPrioridad++;
        }
        if(resuelto.equals("Resolved")){
            Resueltos++;
        }
    }

    // Actualizamos los JLabels en la interfaz
    lblTotal.setText(String.valueOf(total));
    lblPendientes.setText(String.valueOf(pendientes));
    lblPrioridadAlta.setText(String.valueOf(altaPrioridad));
    lblResueltos.setText(String.valueOf(Resueltos));
}
    
    private void actualizarGraficoCategorias() {
    DefaultTableModel modelo = (DefaultTableModel) tablaTickets.getModel();
    int totalTickets = modelo.getRowCount();
    
    // Si no hay tickets, las barras se quedan en 0% para evitar error matemático (dividir entre cero)
    if (totalTickets == 0) {
        barSedes.setValue(0);
        barMaquinas.setValue(0);
        barSoftware.setValue(0);
        barRegistro.setValue(0);
        barSuscripciones.setValue(0);
        return;
    }
    
    // Contadores para cada categoría
    int cSedes = 0, cMaquinas = 0, cSoftware = 0, cRegistro = 0, cSuscripciones=0;
    
    // Recorremos la tabla sumando
    for (int i = 0; i < totalTickets; i++) {
        // La columna 3 es la de "Categoría"
        Object objCategoria = modelo.getValueAt(i, 3); 
        
        if (objCategoria != null) {
            // Pasamos a minúsculas para evitar errores si alguien escribió "Sedes" o "sedes"
            String cat = objCategoria.toString().toLowerCase(); 
            
            if (cat.contains("sedes")) cSedes++;
            else if (cat.contains("máquinas") || cat.contains("maquinas")) cMaquinas++;
            else if (cat.contains("software")) cSoftware++;
            else if (cat.contains("registro")) cRegistro++;
            else if (cat.contains("suscripciones")) cSuscripciones++;
        }
    }
    
    // Convertimos a porcentaje (Regla de 3 simple)
    int pSedes = (cSedes * 100) / totalTickets;
    int pMaquinas = (cMaquinas * 100) / totalTickets;
    int pSoftware = (cSoftware * 100) / totalTickets;
    int pRegistro = (cRegistro * 100) / totalTickets;
    int pSucripciones = (cSuscripciones * 100) / totalTickets;
    
    // Le mandamos el porcentaje a las barras visuales
    barSedes.setValue(pSedes);
    barMaquinas.setValue(pMaquinas);
    barSoftware.setValue(pSoftware);
    barRegistro.setValue(pRegistro);
    barSuscripciones.setValue(pSucripciones);
}

    class RenderizadorPrioridad extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            
            String prioridad = (String) value;
            
            if (!isSelected) {
                if ("Alta".equals(prioridad)) {
                    c.setBackground(new Color(231, 76, 60)); // Rojo
                    c.setForeground(Color.WHITE); 
                } else if ("Baja".equals(prioridad)) {
                    c.setBackground(new Color(0, 188, 212)); // Cian
                    c.setForeground(Color.BLACK); 
                } else {
                    c.setBackground(table.getBackground()); 
                    c.setForeground(table.getForeground()); 
                }
            }
            return c; 
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

        jPanel4 = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblSUSCRIPCION2 = new javax.swing.JLabel();
        lblREGISTRO2 = new javax.swing.JLabel();
        lblMAQUINAS2 = new javax.swing.JLabel();
        lblSOPORTE2 = new javax.swing.JLabel();
        lblADMINISTRACION2 = new javax.swing.JLabel();
        imgGymWork1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanelSEDES = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        lblINICIO2 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        IconSedes = new javax.swing.JLabel();
        lblSEDES2 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel2 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanelSeleccionTickets = new javax.swing.JPanel();
        PanelTotal = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        PanelPendientes = new javax.swing.JPanel();
        lblPendientes = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        PanelPrioridadAlta = new javax.swing.JPanel();
        lblPrioridadAlta = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        lblResueltos = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        PanelTicket = new javax.swing.JPanel();
        txtBuscar = new javax.swing.JTextField();
        btnCrearTicket = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        tablaTickets = new javax.swing.JTable();
        PanelSeleccionAnaliticas = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        barSedes = new javax.swing.JProgressBar();
        jLabel11 = new javax.swing.JLabel();
        barMaquinas = new javax.swing.JProgressBar();
        jLabel17 = new javax.swing.JLabel();
        barSuscripciones = new javax.swing.JProgressBar();
        Software = new javax.swing.JLabel();
        barSoftware = new javax.swing.JProgressBar();
        barRegistro = new javax.swing.JProgressBar();
        jLabel20 = new javax.swing.JLabel();

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 100, Short.MAX_VALUE)
        );

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(153, 204, 255));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Rockwell Condensed", 3, 48)); // NOI18N
        jLabel8.setText("GymWork");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 192, -1));

        lblSUSCRIPCION2.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSUSCRIPCION2.setText("Suscripciones");
        lblSUSCRIPCION2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCION2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCION2MouseExited(evt);
            }
        });
        jPanel3.add(lblSUSCRIPCION2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        lblREGISTRO2.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblREGISTRO2.setText("Registro");
        lblREGISTRO2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblREGISTRO2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblREGISTRO2MouseExited(evt);
            }
        });
        jPanel3.add(lblREGISTRO2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, -1, -1));

        lblMAQUINAS2.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblMAQUINAS2.setText("Estado de maquinas");
        lblMAQUINAS2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMAQUINAS2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMAQUINAS2MouseExited(evt);
            }
        });
        jPanel3.add(lblMAQUINAS2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, -1, -1));

        lblSOPORTE2.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSOPORTE2.setForeground(new java.awt.Color(0, 102, 255));
        lblSOPORTE2.setText("Soporte y asistencia técnica");
        lblSOPORTE2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSOPORTE2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSOPORTE2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSOPORTE2MouseExited(evt);
            }
        });
        jPanel3.add(lblSOPORTE2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 560, -1, -1));

        lblADMINISTRACION2.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblADMINISTRACION2.setText("Módulo de administración");
        lblADMINISTRACION2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblADMINISTRACION2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblADMINISTRACION2MouseExited(evt);
            }
        });
        jPanel3.add(lblADMINISTRACION2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 640, -1, -1));
        jPanel3.add(imgGymWork1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 27, -1, -1));

        jLabel9.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconSuscripciones.png")); // NOI18N
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));

        jLabel12.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconMaquinas.png")); // NOI18N
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));

        jLabel14.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconAdministracon.png")); // NOI18N
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, -1, -1));

        jPanelSEDES.setBackground(new java.awt.Color(102, 204, 255));
        jPanelSEDES.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel10.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconSoporte - copia.png")); // NOI18N

        javax.swing.GroupLayout jPanelSEDESLayout = new javax.swing.GroupLayout(jPanelSEDES);
        jPanelSEDES.setLayout(jPanelSEDESLayout);
        jPanelSEDESLayout.setHorizontalGroup(
            jPanelSEDESLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSEDESLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(278, Short.MAX_VALUE))
        );
        jPanelSEDESLayout.setVerticalGroup(
            jPanelSEDESLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSEDESLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel10)
                .addContainerGap(8, Short.MAX_VALUE))
        );

        jPanel3.add(jPanelSEDES, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 530, 340, 70));

        lblINICIO2.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblINICIO2.setText("Inicio");
        lblINICIO2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblINICIO2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblINICIO2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblINICIO2MouseExited(evt);
            }
        });
        jPanel3.add(lblINICIO2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));

        jLabel16.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconInicio.png")); // NOI18N
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));
        jPanel3.add(IconSedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        lblSEDES2.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSEDES2.setText("Sedes");
        lblSEDES2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSEDES2MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSEDES2MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSEDES2MouseExited(evt);
            }
        });
        jPanel3.add(lblSEDES2, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, -1, -1));

        jLabel18.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconRegistro.png")); // NOI18N
        jPanel3.add(jLabel18, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));

        jLabel21.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\IconSedes.png")); // NOI18N
        jPanel3.add(jLabel21, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setBorder(null);
        jScrollPane1.setForeground(new java.awt.Color(255, 255, 255));
        jScrollPane1.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        jScrollPane1.setVerticalScrollBarPolicy(javax.swing.ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);

        jPanel2.setBackground(new java.awt.Color(255, 255, 255));

        jPanel5.setBackground(new java.awt.Color(153, 204, 255));

        jPanelSeleccionTickets.setBackground(new java.awt.Color(255, 255, 255));
        jPanelSeleccionTickets.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(220, 220, 220)));
        jPanelSeleccionTickets.setOpaque(false);

        PanelTotal.setBackground(new java.awt.Color(242, 255, 255));
        PanelTotal.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));

        jLabel1.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel1.setText("Total de Tickets de Soporte");

        lblTotal.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        lblTotal.setText("0");

        jLabel2.setFont(new java.awt.Font("Segoe UI Emoji", 2, 12)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Tickets de Soporte");

        javax.swing.GroupLayout PanelTotalLayout = new javax.swing.GroupLayout(PanelTotal);
        PanelTotal.setLayout(PanelTotalLayout);
        PanelTotalLayout.setHorizontalGroup(
            PanelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTotalLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addGroup(PanelTotalLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(PanelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblTotal))))
                .addContainerGap(33, Short.MAX_VALUE))
        );
        PanelTotalLayout.setVerticalGroup(
            PanelTotalLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTotalLayout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addContainerGap(12, Short.MAX_VALUE))
        );

        PanelPendientes.setBackground(new java.awt.Color(242, 255, 255));
        PanelPendientes.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));

        lblPendientes.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        lblPendientes.setText("0");

        jLabel6.setFont(new java.awt.Font("Segoe UI Emoji", 2, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(153, 153, 153));
        jLabel6.setText("Tickets Pendientes");

        jLabel4.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel4.setText("Tickets Pendientes");

        javax.swing.GroupLayout PanelPendientesLayout = new javax.swing.GroupLayout(PanelPendientes);
        PanelPendientes.setLayout(PanelPendientesLayout);
        PanelPendientesLayout.setHorizontalGroup(
            PanelPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPendientesLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(PanelPendientesLayout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addGroup(PanelPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblPendientes))))
                .addContainerGap(93, Short.MAX_VALUE))
        );
        PanelPendientesLayout.setVerticalGroup(
            PanelPendientesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPendientesLayout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPendientes, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelPrioridadAlta.setBackground(new java.awt.Color(242, 255, 255));
        PanelPrioridadAlta.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));

        lblPrioridadAlta.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        lblPrioridadAlta.setText("0");

        jLabel15.setFont(new java.awt.Font("Segoe UI Emoji", 2, 12)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(153, 153, 153));
        jLabel15.setText("Tickets de Alta Prioridad");

        jLabel7.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel7.setText("Tickets de Alta Prioridad");

        javax.swing.GroupLayout PanelPrioridadAltaLayout = new javax.swing.GroupLayout(PanelPrioridadAlta);
        PanelPrioridadAlta.setLayout(PanelPrioridadAltaLayout);
        PanelPrioridadAltaLayout.setHorizontalGroup(
            PanelPrioridadAltaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrioridadAltaLayout.createSequentialGroup()
                .addGroup(PanelPrioridadAltaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelPrioridadAltaLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(PanelPrioridadAltaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lblPrioridadAlta)
                            .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(PanelPrioridadAltaLayout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel7)))
                .addContainerGap(60, Short.MAX_VALUE))
        );
        PanelPrioridadAltaLayout.setVerticalGroup(
            PanelPrioridadAltaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelPrioridadAltaLayout.createSequentialGroup()
                .addComponent(jLabel7)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPrioridadAlta, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel15)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel12.setBackground(new java.awt.Color(242, 255, 255));
        jPanel12.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(220, 220, 220), 1, true));

        lblResueltos.setFont(new java.awt.Font("Segoe UI Black", 1, 48)); // NOI18N
        lblResueltos.setText("0");

        jLabel19.setFont(new java.awt.Font("Segoe UI Emoji", 2, 12)); // NOI18N
        jLabel19.setForeground(new java.awt.Color(153, 153, 153));
        jLabel19.setText("Tickets Resueltos");

        jLabel30.setFont(new java.awt.Font("Segoe UI Black", 1, 14)); // NOI18N
        jLabel30.setText("Tickets Resueltos");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lblResueltos)))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(jLabel30)))
                .addContainerGap(112, Short.MAX_VALUE))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblResueltos, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel19)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelTicket.setBackground(new java.awt.Color(242, 255, 255));
        PanelTicket.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(153, 153, 153), 1, true));

        txtBuscar.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        txtBuscar.setForeground(new java.awt.Color(204, 204, 204));
        txtBuscar.setText("Busqueda por Ticket");
        txtBuscar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txtBuscarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txtBuscarFocusLost(evt);
            }
        });
        txtBuscar.addActionListener(this::txtBuscarActionPerformed);
        txtBuscar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtBuscarKeyReleased(evt);
            }
        });

        btnCrearTicket.setBackground(new java.awt.Color(51, 51, 255));
        btnCrearTicket.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        btnCrearTicket.setForeground(new java.awt.Color(255, 255, 255));
        btnCrearTicket.setText("Crear nuevo Ticket");
        btnCrearTicket.addActionListener(this::btnCrearTicketActionPerformed);

        tablaTickets.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "Ticket ID", "Asunto del Ticket", "Remitente", "Categoria", "Prioridad", "Estado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tablaTickets.getTableHeader().setReorderingAllowed(false);
        jScrollPane2.setViewportView(tablaTickets);
        if (tablaTickets.getColumnModel().getColumnCount() > 0) {
            tablaTickets.getColumnModel().getColumn(0).setResizable(false);
            tablaTickets.getColumnModel().getColumn(1).setResizable(false);
            tablaTickets.getColumnModel().getColumn(2).setResizable(false);
            tablaTickets.getColumnModel().getColumn(3).setResizable(false);
            tablaTickets.getColumnModel().getColumn(4).setResizable(false);
            tablaTickets.getColumnModel().getColumn(5).setResizable(false);
        }

        javax.swing.GroupLayout PanelTicketLayout = new javax.swing.GroupLayout(PanelTicket);
        PanelTicket.setLayout(PanelTicketLayout);
        PanelTicketLayout.setHorizontalGroup(
            PanelTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTicketLayout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(PanelTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(PanelTicketLayout.createSequentialGroup()
                        .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 405, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(370, 370, 370)
                        .addComponent(btnCrearTicket))
                    .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 980, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(20, Short.MAX_VALUE))
        );
        PanelTicketLayout.setVerticalGroup(
            PanelTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTicketLayout.createSequentialGroup()
                .addGap(11, 11, 11)
                .addGroup(PanelTicketLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnCrearTicket))
                .addGap(26, 26, 26)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanelSeleccionTicketsLayout = new javax.swing.GroupLayout(jPanelSeleccionTickets);
        jPanelSeleccionTickets.setLayout(jPanelSeleccionTicketsLayout);
        jPanelSeleccionTicketsLayout.setHorizontalGroup(
            jPanelSeleccionTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanelSeleccionTicketsLayout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanelSeleccionTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(PanelTicket, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanelSeleccionTicketsLayout.createSequentialGroup()
                        .addComponent(PanelTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PanelPendientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(PanelPrioridadAlta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(10, 10, 10))
        );
        jPanelSeleccionTicketsLayout.setVerticalGroup(
            jPanelSeleccionTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelSeleccionTicketsLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addGroup(jPanelSeleccionTicketsLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelTotal, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelPendientes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelPrioridadAlta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel12, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addComponent(PanelTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        PanelSeleccionAnaliticas.setBackground(new java.awt.Color(255, 255, 255));
        PanelSeleccionAnaliticas.setBorder(new javax.swing.border.LineBorder(new java.awt.Color(204, 204, 204), 1, true));
        PanelSeleccionAnaliticas.setOpaque(false);

        jPanel7.setBackground(new java.awt.Color(255, 255, 255));

        jLabel3.setFont(new java.awt.Font("Segoe UI Black", 2, 18)); // NOI18N
        jLabel3.setText("Distribucion de Tickets por Categoria");

        jLabel5.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel5.setText("Sedes");

        barSedes.setBackground(new java.awt.Color(255, 255, 255));
        barSedes.setForeground(new java.awt.Color(255, 255, 204));
        barSedes.setStringPainted(true);

        jLabel11.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel11.setText("Maquinas");

        barMaquinas.setBackground(new java.awt.Color(255, 255, 255));
        barMaquinas.setForeground(new java.awt.Color(204, 255, 204));
        barMaquinas.setStringPainted(true);

        jLabel17.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel17.setText("Suscripciones");

        barSuscripciones.setBackground(new java.awt.Color(255, 255, 255));
        barSuscripciones.setForeground(new java.awt.Color(255, 204, 204));
        barSuscripciones.setStringPainted(true);

        Software.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        Software.setText("Software");

        barSoftware.setBackground(new java.awt.Color(255, 255, 255));
        barSoftware.setForeground(new java.awt.Color(204, 204, 255));
        barSoftware.setStringPainted(true);

        barRegistro.setBackground(new java.awt.Color(255, 255, 255));
        barRegistro.setForeground(new java.awt.Color(204, 255, 255));
        barRegistro.setStringPainted(true);

        jLabel20.setFont(new java.awt.Font("Segoe UI Black", 1, 18)); // NOI18N
        jLabel20.setText("Registro");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5)
                            .addComponent(jLabel11))
                        .addGap(87, 87, 87)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(barMaquinas, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)
                            .addComponent(barSedes, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Software)
                            .addComponent(jLabel17)
                            .addComponent(jLabel20))
                        .addGap(51, 51, 51)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(barRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(barSuscripciones, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(barSoftware, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(123, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addGap(54, 54, 54)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barSedes, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(28, 28, 28)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barMaquinas, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(31, 31, 31)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(Software, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barSoftware, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel17, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barSuscripciones, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(34, 34, 34)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(barRegistro, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(55, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout PanelSeleccionAnaliticasLayout = new javax.swing.GroupLayout(PanelSeleccionAnaliticas);
        PanelSeleccionAnaliticas.setLayout(PanelSeleccionAnaliticasLayout);
        PanelSeleccionAnaliticasLayout.setHorizontalGroup(
            PanelSeleccionAnaliticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSeleccionAnaliticasLayout.createSequentialGroup()
                .addGap(127, 127, 127)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(178, Short.MAX_VALUE))
        );
        PanelSeleccionAnaliticasLayout.setVerticalGroup(
            PanelSeleccionAnaliticasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelSeleccionAnaliticasLayout.createSequentialGroup()
                .addGap(25, 25, 25)
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(PanelSeleccionAnaliticas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addComponent(jPanelSeleccionTickets, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jPanelSeleccionTickets, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(PanelSeleccionAnaliticas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 16, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jScrollPane1.setViewportView(jPanel2);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)
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

    private void lblSUSCRIPCION2MouseEntered(java.awt.event.MouseEvent evt) {                                             
        lblSUSCRIPCION2.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION2.setForeground(new java.awt.Color(0, 102, 255));
    }                                            

    private void lblSUSCRIPCION2MouseExited(java.awt.event.MouseEvent evt) {                                            
        lblSUSCRIPCION2.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION2.setForeground(new java.awt.Color(0, 0, 0));
    }                                           

    private void lblREGISTRO2MouseEntered(java.awt.event.MouseEvent evt) {                                          
        lblREGISTRO2.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO2.setForeground(new java.awt.Color(0, 102, 255));
    }                                         

    private void lblREGISTRO2MouseExited(java.awt.event.MouseEvent evt) {                                         
        lblREGISTRO2.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO2.setForeground(new java.awt.Color(0, 0, 0));
    }                                        

    private void lblMAQUINAS2MouseEntered(java.awt.event.MouseEvent evt) {                                          
        lblMAQUINAS2.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS2.setForeground(new java.awt.Color(0, 102, 255));
    }                                         

    private void lblMAQUINAS2MouseExited(java.awt.event.MouseEvent evt) {                                         
        lblMAQUINAS2.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS2.setForeground(new java.awt.Color(0, 0, 0));
    }                                        

    private void lblSOPORTE2MouseClicked(java.awt.event.MouseEvent evt) {                                         

    }                                        

    private void lblSOPORTE2MouseEntered(java.awt.event.MouseEvent evt) {                                         
        
    }                                        

    private void lblSOPORTE2MouseExited(java.awt.event.MouseEvent evt) {                                        
        
    }                                       

    private void lblADMINISTRACION2MouseEntered(java.awt.event.MouseEvent evt) {                                                
        lblADMINISTRACION2.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION2.setForeground(new java.awt.Color(0, 102, 255));
    }                                               

    private void lblADMINISTRACION2MouseExited(java.awt.event.MouseEvent evt) {                                               
        lblADMINISTRACION2.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION2.setForeground(new java.awt.Color(0, 0, 0));
    }                                              

    private void lblSEDES2MouseEntered(java.awt.event.MouseEvent evt) {                                       
        lblSEDES2.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES2.setForeground(new java.awt.Color(0, 102, 255));
    }                                      

    private void lblSEDES2MouseExited(java.awt.event.MouseEvent evt) {                                      
        lblSEDES2.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES2.setForeground(new java.awt.Color(0, 0, 0));
    }                                     

    private void lblINICIO2MouseClicked(java.awt.event.MouseEvent evt) {                                        
        Admin_Panel OBJ = new Admin_Panel();
        OBJ.setVisible(true);
        this.dispose();
    }                                       

    private void lblINICIO2MouseEntered(java.awt.event.MouseEvent evt) {                                        
        lblINICIO2.setFont(new java.awt.Font("Sitka Text",1,18));
        lblINICIO2.setForeground(new java.awt.Color(0,102,255));
    }                                       

    private void lblINICIO2MouseExited(java.awt.event.MouseEvent evt) {                                       
        lblINICIO2.setFont(new java.awt.Font("Sitka Text",1,18));
        lblINICIO2.setForeground(new java.awt.Color(0,0,0));
    }                                      

    private void txtBuscarFocusGained(java.awt.event.FocusEvent evt) {                                      
        if (txtBuscar.getText().equals("Busqueda por Ticket")) {
            txtBuscar.setText("");
            txtBuscar.setForeground(Color.black); 
        }
    }                                     

    private void txtBuscarFocusLost(java.awt.event.FocusEvent evt) {                                    
        if (txtBuscar.getText().isEmpty()) {
            txtBuscar.setText("Busqueda por Ticket");
            txtBuscar.setForeground(Color.GRAY); 
            Filtro();
        }
    }                                   

    private void txtBuscarKeyReleased(java.awt.event.KeyEvent evt) {                                      
        Filtro();
    }                                     

    private void txtBuscarActionPerformed(java.awt.event.ActionEvent evt) {                                          
        // TODO add your handling code here:
    }                                         

    private void btnCrearTicketActionPerformed(java.awt.event.ActionEvent evt) {                                               
        RegistroTicket formulario = new RegistroTicket(this,true);
        formulario.setLocationRelativeTo(this);
        formulario.setVisible(true);
        if(formulario.isFueGuardado()){
            // Volvemos a leer de SQL
            cargarDatosDesdeSQL();
            txtBuscar.setText("Busqueda por Ticket");
            txtBuscar.setForeground(java.awt.Color.GRAY);
            Filtro();
            actualizarKPIs();
            actualizarGraficoCategorias();
        }
    }                                              

    private void lblSEDES2MouseClicked(java.awt.event.MouseEvent evt) {                                       
        Sedes OBJ = new Sedes();
        OBJ.setVisible(true);
        this.dispose();;
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
        java.awt.EventQueue.invokeLater(() -> new Soporte().setVisible(true));
    }

    // Variables declaration - do not modify                     
    private javax.swing.JLabel IconSedes;
    private javax.swing.JPanel PanelPendientes;
    private javax.swing.JPanel PanelPrioridadAlta;
    private javax.swing.JPanel PanelSeleccionAnaliticas;
    private javax.swing.JPanel PanelTicket;
    private javax.swing.JPanel PanelTotal;
    private javax.swing.JLabel Software;
    private javax.swing.JProgressBar barMaquinas;
    private javax.swing.JProgressBar barRegistro;
    private javax.swing.JProgressBar barSedes;
    private javax.swing.JProgressBar barSoftware;
    private javax.swing.JProgressBar barSuscripciones;
    private javax.swing.JButton btnCrearTicket;
    private javax.swing.JLabel imgGymWork1;
    private javax.swing.JLabel jLabel1;
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
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanelSEDES;
    private javax.swing.JPanel jPanelSeleccionTickets;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblADMINISTRACION2;
    private javax.swing.JLabel lblINICIO2;
    private javax.swing.JLabel lblMAQUINAS2;
    private javax.swing.JLabel lblPendientes;
    private javax.swing.JLabel lblPrioridadAlta;
    private javax.swing.JLabel lblREGISTRO2;
    private javax.swing.JLabel lblResueltos;
    private javax.swing.JLabel lblSEDES2;
    private javax.swing.JLabel lblSOPORTE2;
    private javax.swing.JLabel lblSUSCRIPCION2;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JTable tablaTickets;
    private javax.swing.JTextField txtBuscar;
    // End of variables declaration                   
}
