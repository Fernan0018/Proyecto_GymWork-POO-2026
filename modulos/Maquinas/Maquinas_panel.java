package com.mycompany.proyecto_final_poo;
import java.awt.Color;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.Font;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.JLabel;
import javax.swing.JTable;
import java.awt.Component;
import javax.swing.Box;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;


public class Maquinas_panel extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Maquinas_panel.class.getName());
    TableRowSorter<DefaultTableModel> sorter;    
    DefaultTableModel modelo;
    
    public Maquinas_panel() {
        initComponents();
        this.setResizable(false);
        Configuracion();
        CrearCard();
        CrearTabla();
        cargarDatos();
        actualizarDatos();
    
    }
    
    public void Configuracion(){
        imgINICIO.setIcon(new javax.swing.ImageIcon("datos/Images/IconInicio.png"));
        imgINICIO.setText("");
        imgSus.setIcon(new javax.swing.ImageIcon("datos/Images/IconSuscripciones.png"));
        imgSus.setText("");
        imgReg.setIcon(new javax.swing.ImageIcon("datos/Images/IconRegistro.png"));
        imgReg.setText("");
        imgSed.setIcon(new javax.swing.ImageIcon("datos/Images/IconSedes.png"));
        imgSed.setText("");
        imgMaq.setIcon(new javax.swing.ImageIcon("datos/Images/IconMaquinas - copia.png"));
        imgMaq.setText("");
        imgSop.setIcon(new javax.swing.ImageIcon("datos/Images/IconSoporte.png"));
        imgSop.setText("");
        imgAdm.setIcon(new javax.swing.ImageIcon("datos/Images/IconAdministracon.png"));
        imgAdm.setText("");
    }
    public void CrearCard(){
    Card1.setBackground(new Color(30,41,59));
    
    Card1text.setText("Total Activos");
    Card1text.setForeground(new Color(148,163,184));
    Card1text.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    lblTotal.setForeground(new Color(242,242,242));
    lblTotal.setFont(new Font("Segoe UI", Font.PLAIN, 36));
    
    Card2.setBackground(new Color(30,41,59));
    Card2text.setText("Operativos");
    Card2text.setForeground(new Color(148,163,184));
    Card2text.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    lblACTIVOS.setForeground(new Color(242,242,242));
    lblACTIVOS.setFont(new Font("Segoe UI", Font.PLAIN, 36));
    
    Card3.setBackground(new Color(30,41,59));
    Card3text.setText("En Mantenimiento");
    Card3text.setForeground(new Color(148,163,184));
    Card3text.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    lblMantenimiento.setForeground(new Color(242,242,242));
    lblMantenimiento.setFont(new Font("Segoe UI", Font.PLAIN, 36));
    
    
    
    Card4.setBackground(new Color(30,41,59));
    Card4text.setText("Fuera de Servicio");
    Card4text.setForeground(new Color(242,242,242));
    Card4text.setFont(new Font("Segoe UI", Font.PLAIN, 18));
    lblFueraServicio.setForeground(new Color(242,242,242));
    lblFueraServicio.setFont(new Font("Segoe UI", Font.PLAIN, 36));
    }
    
    public void CrearTabla() {
        modelo = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column){
        return false;
        }     
        };
        modelo.addColumn("ID-equipo");
        modelo.addColumn("NOMBRE/MODELO");
        modelo.addColumn("ÚLTIMO MANTENIMINTO");
        modelo.addColumn("ESTADO");
        modelo.addColumn("ACCIONES");
        JMAQUINA.setModel(modelo);
        sorter = new TableRowSorter<>(modelo);
        JMAQUINA.setRowSorter(sorter);
        JMAQUINA.setBackground(new java.awt.Color(30,41,59));
        JMAQUINA.getTableHeader().setBackground(new java.awt.Color(31, 41, 55));
        JMAQUINA.getTableHeader().setForeground(new java.awt.Color(0, 0, 0));
        JMAQUINA.setForeground(new Color(226, 232, 240));
        JMAQUINA.setRowHeight(64);
        JMAQUINA.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 13));
        JMAQUINA.getTableHeader().setBorder(BorderFactory.createEmptyBorder());
        JMAQUINA.getTableHeader().setBackground(new Color(31, 41, 55));
        JMAQUINA.getTableHeader().setPreferredSize(new Dimension(JMAQUINA.getTableHeader().getWidth(), 42));
        JMAQUINA.setFont(new Font("Segoe UI", Font.PLAIN, 16));
        JMAQUINA.setGridColor(new Color(51, 65, 85));
        JMAQUINA.setSelectionBackground(new Color(51, 65, 85));
        JMAQUINA.setSelectionForeground(Color.WHITE);
        JMAQUINA.setShowVerticalLines(false);
        JMAQUINA.setShowHorizontalLines(true);
        JMAQUINA.setIntercellSpacing(new Dimension(0, 1));
        JMAQUINA.setBorder(BorderFactory.createEmptyBorder());
        JMAQUINA.getTableHeader().setReorderingAllowed(false);
        JMAQUINA.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int fila = JMAQUINA.getSelectedRow();
            int columna = JMAQUINA.getSelectedColumn();
        

                String codigo = JMAQUINA.getValueAt(fila, 0).toString();
                
                if(columna == 4) {
                Ventana_Maquina ventana = new Ventana_Maquina(codigo,Maquinas_panel.this);
                ventana.setVisible(true);
        }}
    
        });
        
        JMAQUINA.getTableHeader().setBorder(
        BorderFactory.createMatteBorder(
        0, 0, 1, 0,
        new Color(51,65,85)
         )
        );
        JMAQUINA.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
        @Override
        public Component getTableCellRendererComponent(
            JTable table, Object value,
            boolean isSelected,
            boolean hasFocus,
            int row,
            int column) {

        JLabel label = (JLabel)
        super.getTableCellRendererComponent(
                table, value,
                isSelected, hasFocus,
                row, column);

        label.setBackground(new Color(30,41,59));
        label.setForeground(new Color(226,232,240));
        label.setOpaque(true);

        String estado =
        table.getValueAt(row, 3).toString();

        if(column == 3){

            if(estado.equalsIgnoreCase("Operativo")){

                label.setForeground(
                    new Color(16,185,129));

            }else if(estado.equalsIgnoreCase("En Revisión")){

                label.setForeground(
                    new Color(245,158,11));

            }else{

                label.setForeground(
                    new Color(239,68,68));
            }
        }

        return label;
    }
});
        
    }
    
    public void cargarDatos(){

    try{

        ConexionSQL con = new ConexionSQL();

        Connection cn = con.conectar();

        String sql =
        "SELECT * FROM maquinas";

        PreparedStatement ps =
        cn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();
        
        
        while(rs.next()){

            String codigo =
            rs.getString("ID");

            String nombre =
            rs.getString("NombreModelo");

            String fecha =
            rs.getString(
            "UltimoMantenimiento");

            String ESTADO =
            rs.getString("Estado");
            
            String estado="";
                if(ESTADO.equals("0")){
                  estado = "Operativo";
                }
                else if(ESTADO.equals("1")){
                  estado = "En Revisión";
                }
                else if(ESTADO.equals("2")){
                  estado = "Fuera de Servicio";
                }
            
            modelo.addRow(new Object[]{
                codigo,
                nombre,
                fecha,
                estado,
                "    [accion]"
            });
        }

        rs.close();
        ps.close();
        cn.close();

    }catch(Exception e){

        e.printStackTrace();
    }
}
    public void limpiarTabla(){

    modelo.setRowCount(0);
}
    public void recargarTabla(){

    limpiarTabla();

    cargarDatos();

    actualizarDatos();
}
    public void actualizarDatos() {

    int total = JMAQUINA.getRowCount();

    int operativos = 0;
    int mantenimiento = 0;
    int fueraServicio = 0;

    for(int i = 0; i < JMAQUINA.getRowCount(); i++) {

        String estado =
        JMAQUINA.getValueAt(i, 3).toString();

        if(estado.equalsIgnoreCase("Operativo")) {

            operativos++;

        } else if(estado.equalsIgnoreCase("En Revisión")) {

            mantenimiento++;

        } else if(estado.equalsIgnoreCase("Fuera de Servicio")) {

            fueraServicio++;
        }
    }

    lblTotal.setText(String.valueOf(total));

    lblACTIVOS.setText(
        String.valueOf(operativos));

    lblMantenimiento.setText(
        String.valueOf(mantenimiento));

    lblFueraServicio.setText(
        String.valueOf(fueraServicio));
}
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        JMAQUINA = new javax.swing.JTable();
        Card1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        Card1text = new javax.swing.JLabel();
        lblTotal = new javax.swing.JLabel();
        Card2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        Card2text = new javax.swing.JLabel();
        lblACTIVOS = new javax.swing.JLabel();
        Card3 = new javax.swing.JPanel();
        lblimagencard = new javax.swing.JLabel();
        lblMantenimiento = new javax.swing.JLabel();
        Card3text = new javax.swing.JLabel();
        jPanel19 = new javax.swing.JPanel();
        jLabel64 = new javax.swing.JLabel();
        lblSUSCRIPCION8 = new javax.swing.JLabel();
        lblREGISTRO8 = new javax.swing.JLabel();
        lblSOPORTE8 = new javax.swing.JLabel();
        lblADMINISTRACION8 = new javax.swing.JLabel();
        imgGymWork8 = new javax.swing.JLabel();
        jLabel65 = new javax.swing.JLabel();
        jLabel66 = new javax.swing.JLabel();
        jLabel67 = new javax.swing.JLabel();
        jLabel68 = new javax.swing.JLabel();
        jLabel69 = new javax.swing.JLabel();
        lblSEDES8 = new javax.swing.JLabel();
        IconSedes7 = new javax.swing.JLabel();
        jPanel20 = new javax.swing.JPanel();
        jLabel70 = new javax.swing.JLabel();
        imgMaq = new javax.swing.JLabel();
        lblMAQUINAS8 = new javax.swing.JLabel();
        imgSed = new javax.swing.JLabel();
        imgSop = new javax.swing.JLabel();
        imgAdm = new javax.swing.JLabel();
        imgReg = new javax.swing.JLabel();
        imgINICIO = new javax.swing.JLabel();
        jLabel71 = new javax.swing.JLabel();
        imgSus = new javax.swing.JLabel();
        Card4 = new javax.swing.JPanel();
        imgCard4 = new javax.swing.JLabel();
        lblFueraServicio = new javax.swing.JLabel();
        Card4text = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(15, 23, 42));

        jScrollPane1.setBackground(new java.awt.Color(255, 255, 255));

        JMAQUINA.setBackground(new java.awt.Color(0, 51, 51));
        JMAQUINA.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(JMAQUINA);

        jLabel1.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\card1.png")); // NOI18N

        Card1text.setText("jLabel2");

        lblTotal.setText("jLabel2");

        javax.swing.GroupLayout Card1Layout = new javax.swing.GroupLayout(Card1);
        Card1.setLayout(Card1Layout);
        Card1Layout.setHorizontalGroup(
            Card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Card1Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addGroup(Card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Card1text)
                    .addComponent(jLabel1))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        Card1Layout.setVerticalGroup(
            Card1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Card1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Card1text)
                .addGap(18, 18, 18)
                .addComponent(lblTotal, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel2.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\card2.png")); // NOI18N

        Card2text.setText("jLabel2");

        lblACTIVOS.setText("jLabel3");

        javax.swing.GroupLayout Card2Layout = new javax.swing.GroupLayout(Card2);
        Card2.setLayout(Card2Layout);
        Card2Layout.setHorizontalGroup(
            Card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Card2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addGroup(Card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblACTIVOS, javax.swing.GroupLayout.PREFERRED_SIZE, 66, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2)
                    .addComponent(Card2text))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        Card2Layout.setVerticalGroup(
            Card2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Card2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Card2text)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(lblACTIVOS, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18))
        );

        lblimagencard.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\card3.png")); // NOI18N

        lblMantenimiento.setText("jLabel3");

        Card3text.setText("jLabel3");

        javax.swing.GroupLayout Card3Layout = new javax.swing.GroupLayout(Card3);
        Card3.setLayout(Card3Layout);
        Card3Layout.setHorizontalGroup(
            Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Card3Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 65, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblimagencard)
                    .addComponent(Card3text))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        Card3Layout.setVerticalGroup(
            Card3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Card3Layout.createSequentialGroup()
                .addGap(15, 15, 15)
                .addComponent(lblimagencard)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Card3text)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lblMantenimiento, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel19.setBackground(new java.awt.Color(204, 255, 255));
        jPanel19.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel19.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel64.setFont(new java.awt.Font("Rockwell Condensed", 3, 48)); // NOI18N
        jLabel64.setText("GymWork");
        jPanel19.add(jLabel64, new org.netbeans.lib.awtextra.AbsoluteConstraints(60, 30, 192, -1));

        lblSUSCRIPCION8.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSUSCRIPCION8.setText("Suscripciones");
        lblSUSCRIPCION8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCION8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCION8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCION8MouseExited(evt);
            }
        });
        jPanel19.add(lblSUSCRIPCION8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        lblREGISTRO8.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblREGISTRO8.setText("Registro");
        lblREGISTRO8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblREGISTRO8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblREGISTRO8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblREGISTRO8MouseExited(evt);
            }
        });
        jPanel19.add(lblREGISTRO8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, -1, -1));

        lblSOPORTE8.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSOPORTE8.setText("Soporte y asistencia técnica");
        lblSOPORTE8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSOPORTE8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSOPORTE8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSOPORTE8MouseExited(evt);
            }
        });
        jPanel19.add(lblSOPORTE8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 560, -1, -1));

        lblADMINISTRACION8.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblADMINISTRACION8.setText("Módulo de administración");
        lblADMINISTRACION8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblADMINISTRACION8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblADMINISTRACION8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblADMINISTRACION8MouseExited(evt);
            }
        });
        jPanel19.add(lblADMINISTRACION8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 640, -1, -1));
        jPanel19.add(imgGymWork8, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 27, -1, -1));
        jPanel19.add(jLabel65, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));
        jPanel19.add(jLabel66, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));
        jPanel19.add(jLabel67, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));
        jPanel19.add(jLabel68, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));
        jPanel19.add(jLabel69, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, -1, -1));

        lblSEDES8.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSEDES8.setText("Sedes");
        lblSEDES8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSEDES8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSEDES8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSEDES8MouseExited(evt);
            }
        });
        jPanel19.add(lblSEDES8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, -1, -1));
        jPanel19.add(IconSedes7, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        jPanel20.setBackground(new java.awt.Color(102, 204, 255));
        jPanel20.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        imgMaq.setText("jLabel2");

        lblMAQUINAS8.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblMAQUINAS8.setForeground(new java.awt.Color(0, 102, 255));
        lblMAQUINAS8.setText("Estado de maquinas");
        lblMAQUINAS8.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMAQUINAS8MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMAQUINAS8MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMAQUINAS8MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addGap(6, 6, 6)
                        .addComponent(imgMaq)
                        .addGap(18, 18, 18)
                        .addComponent(lblMAQUINAS8, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jLabel70))
                .addContainerGap(87, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addComponent(jLabel70)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(imgMaq, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblMAQUINAS8))
                .addGap(0, 7, Short.MAX_VALUE))
        );

        jPanel19.add(jPanel20, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 470, 340, -1));

        imgSed.setText("jLabel2");
        jPanel19.add(imgSed, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, 30));

        imgSop.setText("jLabel2");
        jPanel19.add(imgSop, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, 30));

        imgAdm.setText("jLabel2");
        jPanel19.add(imgAdm, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, -1, 30));

        imgReg.setText("jLabel2");
        jPanel19.add(imgReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, 30));

        imgINICIO.setText("jLabel2");
        jPanel19.add(imgINICIO, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, 30));

        jLabel71.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel71.setText("Inicio");
        jLabel71.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel71AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });
        jLabel71.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel71MouseClicked(evt);
            }
        });
        jPanel19.add(jLabel71, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 180, -1, -1));

        imgSus.setText("jLabel2");
        jPanel19.add(imgSus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 30));

        imgCard4.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\card4.png")); // NOI18N

        lblFueraServicio.setText("jLabel3");

        Card4text.setText("jLabel3");

        javax.swing.GroupLayout Card4Layout = new javax.swing.GroupLayout(Card4);
        Card4.setLayout(Card4Layout);
        Card4Layout.setHorizontalGroup(
            Card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Card4Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(Card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lblFueraServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 55, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Card4text)
                    .addComponent(imgCard4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        Card4Layout.setVerticalGroup(
            Card4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Card4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(imgCard4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Card4text)
                .addGap(18, 18, 18)
                .addComponent(lblFueraServicio, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel2.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jPanel2MouseClicked(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Segoe UI", 0, 14)); // NOI18N
        jLabel3.setText("+ Agregar");
        jLabel3.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                jLabel3MouseClicked(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel3)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, 337, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 126, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(Card1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(42, 42, 42)
                        .addComponent(Card2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(Card3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(45, 45, 45)
                        .addComponent(Card4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(71, 71, 71))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 843, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addGap(765, 765, 765)
                                .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGap(103, 103, 103))))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGap(70, 70, 70)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(Card1, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Card2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Card4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(Card3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(71, 71, 71)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 327, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel19, javax.swing.GroupLayout.DEFAULT_SIZE, 708, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSUSCRIPCION8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUSCRIPCION8MouseClicked
        Admin_Panel OBJ1 = new Admin_Panel();
        OBJ1.setVisible(true);
        Suscripciones sus = new Suscripciones();
        sus.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSUSCRIPCION8MouseClicked

    private void lblSUSCRIPCION8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUSCRIPCION8MouseEntered
        lblSUSCRIPCION8.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION8.setForeground(new java.awt.Color(0, 102, 255));
        
    }//GEN-LAST:event_lblSUSCRIPCION8MouseEntered

    private void lblSUSCRIPCION8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUSCRIPCION8MouseExited
        lblSUSCRIPCION8.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION8.setForeground(new java.awt.Color(0, 0, 0));
        
    }//GEN-LAST:event_lblSUSCRIPCION8MouseExited

    private void lblREGISTRO8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblREGISTRO8MouseClicked
        Registro_panel OBJ = new Registro_panel();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblREGISTRO8MouseClicked

    private void lblREGISTRO8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblREGISTRO8MouseEntered
        lblREGISTRO8.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO8.setForeground(new java.awt.Color(0, 102, 255));
        
    }//GEN-LAST:event_lblREGISTRO8MouseEntered

    private void lblREGISTRO8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblREGISTRO8MouseExited
        lblREGISTRO8.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO8.setForeground(new java.awt.Color(0, 0, 0));
        
    }//GEN-LAST:event_lblREGISTRO8MouseExited

    private void lblMAQUINAS8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINAS8MouseClicked
        Maquinas_panel MAQ = new Maquinas_panel();
        MAQ.setVisible(true);
        this.dispose();

    }//GEN-LAST:event_lblMAQUINAS8MouseClicked

    private void lblMAQUINAS8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINAS8MouseEntered
      
        
    }//GEN-LAST:event_lblMAQUINAS8MouseEntered

    private void lblMAQUINAS8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINAS8MouseExited
        
        
    }//GEN-LAST:event_lblMAQUINAS8MouseExited

    private void lblSOPORTE8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTE8MouseEntered
        lblSOPORTE8.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE8.setForeground(new java.awt.Color(0, 102, 255));
        
    }//GEN-LAST:event_lblSOPORTE8MouseEntered

    private void lblSOPORTE8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTE8MouseExited
        lblSOPORTE8.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE8.setForeground(new java.awt.Color(0, 0, 0));
        
    }//GEN-LAST:event_lblSOPORTE8MouseExited

    private void lblADMINISTRACION8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACION8MouseEntered
        lblADMINISTRACION8.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION8.setForeground(new java.awt.Color(0, 102, 255));
     
    }//GEN-LAST:event_lblADMINISTRACION8MouseEntered

    private void lblADMINISTRACION8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACION8MouseExited
        lblADMINISTRACION8.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION8.setForeground(new java.awt.Color(0, 0, 0));
     
    }//GEN-LAST:event_lblADMINISTRACION8MouseExited

    private void lblSEDES8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSEDES8MouseClicked
        Sedes OBJ = new Sedes();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSEDES8MouseClicked

    private void lblSEDES8MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSEDES8MouseEntered
        lblSEDES8.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES8.setForeground(new java.awt.Color(0, 102, 255));
        
    }//GEN-LAST:event_lblSEDES8MouseEntered

    private void lblSEDES8MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSEDES8MouseExited
        lblSEDES8.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES8.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_lblSEDES8MouseExited

    private void jLabel71AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel71AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel71AncestorAdded

    private void jLabel71MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel71MouseClicked
       Admin_Panel OBJ = new Admin_Panel();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_jLabel71MouseClicked

    private void jLabel3MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jLabel3MouseClicked
    Ventana_MaquinaCreator vent = new Ventana_MaquinaCreator(Maquinas_panel.this);
    vent.setVisible(true);    // TODO add your handling code here:
    }//GEN-LAST:event_jLabel3MouseClicked

    private void jPanel2MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jPanel2MouseClicked
       Ventana_MaquinaCreator vent = new Ventana_MaquinaCreator(Maquinas_panel.this);
    vent.setVisible(true);   // TODO add your handling code here:
    }//GEN-LAST:event_jPanel2MouseClicked

    private void lblSOPORTE8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTE8MouseClicked
        Soporte OBJ = new Soporte();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSOPORTE8MouseClicked

    private void lblADMINISTRACION8MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACION8MouseClicked
        Modulo OBJ = new Modulo();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblADMINISTRACION8MouseClicked

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
        java.awt.EventQueue.invokeLater(() -> new Maquinas_panel().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Card1;
    private javax.swing.JLabel Card1text;
    private javax.swing.JPanel Card2;
    private javax.swing.JLabel Card2text;
    private javax.swing.JPanel Card3;
    private javax.swing.JLabel Card3text;
    private javax.swing.JPanel Card4;
    private javax.swing.JLabel Card4text;
    private javax.swing.JLabel IconSedes7;
    private javax.swing.JTable JMAQUINA;
    private javax.swing.JLabel imgAdm;
    private javax.swing.JLabel imgCard4;
    private javax.swing.JLabel imgGymWork8;
    private javax.swing.JLabel imgINICIO;
    private javax.swing.JLabel imgMaq;
    private javax.swing.JLabel imgReg;
    private javax.swing.JLabel imgSed;
    private javax.swing.JLabel imgSop;
    private javax.swing.JLabel imgSus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel64;
    private javax.swing.JLabel jLabel65;
    private javax.swing.JLabel jLabel66;
    private javax.swing.JLabel jLabel67;
    private javax.swing.JLabel jLabel68;
    private javax.swing.JLabel jLabel69;
    private javax.swing.JLabel jLabel70;
    private javax.swing.JLabel jLabel71;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblACTIVOS;
    private javax.swing.JLabel lblADMINISTRACION8;
    private javax.swing.JLabel lblFueraServicio;
    private javax.swing.JLabel lblMAQUINAS8;
    private javax.swing.JLabel lblMantenimiento;
    private javax.swing.JLabel lblREGISTRO8;
    private javax.swing.JLabel lblSEDES8;
    private javax.swing.JLabel lblSOPORTE8;
    private javax.swing.JLabel lblSUSCRIPCION8;
    private javax.swing.JLabel lblTotal;
    private javax.swing.JLabel lblimagencard;
    // End of variables declaration//GEN-END:variables
}
