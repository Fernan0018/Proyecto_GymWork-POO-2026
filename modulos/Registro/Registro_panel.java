package com.mycompany.proyecto_final_poo;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import javax.swing.RowFilter;
import java.awt.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Registro_panel extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Registro_panel.class.getName());
    TableRowSorter<DefaultTableModel> sorter;
    DefaultTableModel modelo;
    
    public void Configuracion(){
        imgINICIO.setIcon(new javax.swing.ImageIcon("datos/Images/IconInicio.png"));
        imgSus.setIcon(new javax.swing.ImageIcon("datos/Images/IconSuscripciones.png"));
        imgSus.setText("");
        imgReg.setIcon(new javax.swing.ImageIcon("datos/Images/IconRegistro - copia.png"));
        imgReg.setText("");
        imgSed.setIcon(new javax.swing.ImageIcon("datos/Images/IconSedes.png"));
        imgSed.setText("");
        imgMaq.setIcon(new javax.swing.ImageIcon("datos/Images/IconMaquinas.png"));
        imgMaq.setText("");
        imgSop.setIcon(new javax.swing.ImageIcon("datos/Images/IconSoporte.png"));
        imgSop.setText("");
        imgAdm.setIcon(new javax.swing.ImageIcon("datos/Images/IconAdministracon.png"));
        imgAdm.setText("");
    }
    public Registro_panel() {
        initComponents();
        this.setResizable(false);
        Configuracion();
        ConexionSQL con = new ConexionSQL();
        con.conectar();
        modelo = new DefaultTableModel(){
        @Override
        public boolean isCellEditable(int row, int column){
        return false;
        }
        };
        
        Tabla_Registro.setRowSorter(sorter);
        modelo.addColumn("Codigo");
        modelo.addColumn("Nombre");
        modelo.addColumn("Apellido Paterno");
        modelo.addColumn("Apellido Materno");
        modelo.addColumn("Plan");
        modelo.addColumn("Ver");
        
        
        
        Tabla_Registro.setModel(modelo);
        
        sorter = new TableRowSorter<>(modelo);
        Tabla_Registro.setRowSorter(sorter);
        Tabla_Registro.getTableHeader().setReorderingAllowed(false);
      
        cargarDatos();
        
        Tabla_Registro.addMouseListener(new java.awt.event.MouseAdapter() {
        public void mouseClicked(java.awt.event.MouseEvent evt) {
            int fila = Tabla_Registro.getSelectedRow();
            int columna = Tabla_Registro.getSelectedColumn();
        

                String codigo = Tabla_Registro.getValueAt(fila, 0).toString();
                
                if(columna == 5) {
                VentanaDetalle ventana = new VentanaDetalle(codigo);
                ventana.setVisible(true);
        }}
    
        });
    }
                

    public void cargarDatos() {

    try{

        ConexionSQL con = new ConexionSQL();

        Connection cn = con.conectar();

        String sql = "SELECT * FROM CLIENTES";

        PreparedStatement ps =
        cn.prepareStatement(sql);

        ResultSet rs = ps.executeQuery();

        while(rs.next()){

            String codigo =
            rs.getString("codigo");

            String nombre =
            rs.getString("nombre");

            String paterno =
            rs.getString("apellidoPaterno");

            String materno =
            rs.getString("apellidoMaterno");

            String planCodigo =
            rs.getString("tipoPlan");

            String plan = "";

            if(planCodigo.equals("100")){
                plan = "Basico";
            }
            else if(planCodigo.equals("010")){
                plan = "Fit";
            }
            else if(planCodigo.equals("001")){
                plan = "Premium";
            }

             modelo.addRow(new Object[]{codigo,nombre,paterno,materno,plan, "Ver Más  "});
        }

        rs.close();
        ps.close();
        cn.close();

    }catch(Exception e){

        e.printStackTrace();
    }
}
   
    
    
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblSUSCRIPCION = new javax.swing.JLabel();
        lblSEDES = new javax.swing.JLabel();
        lblSOPORTE = new javax.swing.JLabel();
        lblADMINISTRACION = new javax.swing.JLabel();
        imgGymWork1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        IconSedes = new javax.swing.JLabel();
        lblRegistro = new javax.swing.JLabel();
        imgReg = new javax.swing.JLabel();
        lblInicio = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        lblMAQUINAS = new javax.swing.JLabel();
        imgINICIO = new javax.swing.JLabel();
        imgSus = new javax.swing.JLabel();
        imgSed = new javax.swing.JLabel();
        imgMaq = new javax.swing.JLabel();
        imgSop = new javax.swing.JLabel();
        imgAdm = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        textBuscar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        Tabla_Registro = new javax.swing.JTable();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(new java.awt.Color(204, 204, 204));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Rockwell Condensed", 3, 48)); // NOI18N
        jLabel8.setText("GymWork");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(104, 27, 192, -1));

        lblSUSCRIPCION.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSUSCRIPCION.setText("Suscripciones");
        lblSUSCRIPCION.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCIONMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCIONMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCIONMouseExited(evt);
            }
        });
        jPanel3.add(lblSUSCRIPCION, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

        lblSEDES.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSEDES.setText("Sedes");
        lblSEDES.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSEDESMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSEDESMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSEDESMouseExited(evt);
            }
        });
        jPanel3.add(lblSEDES, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 420, -1, -1));

        lblSOPORTE.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSOPORTE.setText("Soporte y asistencia técnica");
        lblSOPORTE.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSOPORTEMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSOPORTEMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSOPORTEMouseExited(evt);
            }
        });
        jPanel3.add(lblSOPORTE, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 560, -1, -1));

        lblADMINISTRACION.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblADMINISTRACION.setText("Módulo de administración");
        lblADMINISTRACION.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblADMINISTRACIONMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblADMINISTRACIONMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblADMINISTRACIONMouseExited(evt);
            }
        });
        jPanel3.add(lblADMINISTRACION, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 640, -1, -1));
        jPanel3.add(imgGymWork1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 27, -1, -1));
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, -1, -1));

        jPanel4.setBackground(new java.awt.Color(102, 204, 255));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        lblRegistro.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblRegistro.setForeground(new java.awt.Color(0, 102, 255));
        lblRegistro.setText("Registro");
        lblRegistro.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblRegistroMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblRegistroMouseExited(evt);
            }
        });

        imgReg.setText("jLabel3");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(IconSedes))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addComponent(imgReg)
                        .addGap(18, 18, 18)
                        .addComponent(lblRegistro)))
                .addContainerGap(192, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(IconSedes)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 14, Short.MAX_VALUE)
                        .addComponent(lblRegistro)
                        .addContainerGap())
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(imgReg)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 310, 340, -1));

        lblInicio.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblInicio.setText("Inicio");
        lblInicio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblInicioMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblInicioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblInicioMouseExited(evt);
            }
        });
        jPanel3.add(lblInicio, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 170, -1, -1));
        jPanel3.add(jLabel16, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 150, -1, -1));

        lblMAQUINAS.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblMAQUINAS.setText("Estado de maquinas");
        lblMAQUINAS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMAQUINASMouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMAQUINASMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMAQUINASMouseExited(evt);
            }
        });
        jPanel3.add(lblMAQUINAS, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 480, -1, -1));

        imgINICIO.setText("jLabel3");
        jPanel3.add(imgINICIO, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 150, 40, -1));

        imgSus.setText("jLabel3");
        jPanel3.add(imgSus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, -1));

        imgSed.setText("jLabel3");
        jPanel3.add(imgSed, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 390, -1, -1));

        imgMaq.setText("jLabel3");
        jPanel3.add(imgMaq, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 460, 40, 50));

        imgSop.setText("jLabel3");
        jPanel3.add(imgSop, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 540, -1, -1));

        imgAdm.setText("jLabel3");
        jPanel3.add(imgAdm, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 610, -1, -1));

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        textBuscar.setForeground(new java.awt.Color(204, 204, 204));
        textBuscar.setText("🔍Buscar");
        textBuscar.addActionListener(this::textBuscarActionPerformed);

        Tabla_Registro.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(Tabla_Registro);

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        jLabel2.setFont(new java.awt.Font("Segoe UI", 1, 36)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Registro ");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addComponent(jLabel2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(14, 14, 14)
                .addComponent(jLabel2)
                .addContainerGap(96, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(43, 43, 43)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 995, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 165, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(26, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(textBuscar, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(35, 35, 35)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 410, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 339, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void textBuscarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_textBuscarActionPerformed
     String texto = textBuscar.getText();
     
     if(texto.trim().length() == 0){
     sorter.setRowFilter(null);
     }else{
     sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto));
     }
     
    }//GEN-LAST:event_textBuscarActionPerformed

    private void lblSUSCRIPCIONMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUSCRIPCIONMouseEntered
        lblSUSCRIPCION.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION.setForeground(new java.awt.Color(0, 102, 255));
    }//GEN-LAST:event_lblSUSCRIPCIONMouseEntered

    private void lblSUSCRIPCIONMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUSCRIPCIONMouseExited
        lblSUSCRIPCION.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_lblSUSCRIPCIONMouseExited

    private void lblSEDESMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSEDESMouseEntered
        lblSEDES.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES.setForeground(new java.awt.Color(0, 102, 255));
    }//GEN-LAST:event_lblSEDESMouseEntered

    private void lblSEDESMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSEDESMouseExited
        lblSEDES.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_lblSEDESMouseExited

    private void lblSOPORTEMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTEMouseEntered
        lblSOPORTE.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE.setForeground(new java.awt.Color(0, 102, 255));
    }//GEN-LAST:event_lblSOPORTEMouseEntered

    private void lblSOPORTEMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTEMouseExited
        lblSOPORTE.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_lblSOPORTEMouseExited

    private void lblADMINISTRACIONMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACIONMouseEntered
        lblADMINISTRACION.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION.setForeground(new java.awt.Color(0, 102, 255));
    }//GEN-LAST:event_lblADMINISTRACIONMouseEntered

    private void lblADMINISTRACIONMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACIONMouseExited
        lblADMINISTRACION.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_lblADMINISTRACIONMouseExited

    private void lblRegistroMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegistroMouseEntered
       
    }//GEN-LAST:event_lblRegistroMouseEntered

    private void lblRegistroMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblRegistroMouseExited
        
    }//GEN-LAST:event_lblRegistroMouseExited

    private void lblMAQUINASMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINASMouseEntered
        lblMAQUINAS.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS.setForeground(new java.awt.Color(0, 102, 255));
    }//GEN-LAST:event_lblMAQUINASMouseEntered

    private void lblMAQUINASMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINASMouseExited
        lblMAQUINAS.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_lblMAQUINASMouseExited

    private void lblSEDESMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSEDESMouseClicked
        Sedes OBJ = new Sedes();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSEDESMouseClicked

    private void lblInicioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseClicked
        Admin_Panel OBJ = new Admin_Panel();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblInicioMouseClicked

    private void lblInicioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseEntered
    lblInicio.setFont(new java.awt.Font("Sitka Text", 1, 18));
    lblInicio.setForeground(new java.awt.Color(0, 102, 255));
    }//GEN-LAST:event_lblInicioMouseEntered

    private void lblInicioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblInicioMouseExited
    lblInicio.setFont(new java.awt.Font("Sitka Text", 1, 18));
    lblInicio.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_lblInicioMouseExited

    private void lblSUSCRIPCIONMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUSCRIPCIONMouseClicked
    Admin_Panel OBJE = new Admin_Panel();
    OBJE.setVisible(true);
    Suscripciones OBJ = new Suscripciones();
    OBJ.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_lblSUSCRIPCIONMouseClicked

    private void lblMAQUINASMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINASMouseClicked
        Maquinas_panel OBJ = new Maquinas_panel();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblMAQUINASMouseClicked

    private void lblSOPORTEMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTEMouseClicked
        Soporte OBJ = new Soporte();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSOPORTEMouseClicked

    private void lblADMINISTRACIONMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACIONMouseClicked
        Modulo OBJ = new Modulo();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblADMINISTRACIONMouseClicked

    
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
        

        
        java.awt.EventQueue.invokeLater(() -> new Registro_panel().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IconSedes;
    private javax.swing.JTable Tabla_Registro;
    private javax.swing.JLabel imgAdm;
    private javax.swing.JLabel imgGymWork1;
    private javax.swing.JLabel imgINICIO;
    private javax.swing.JLabel imgMaq;
    private javax.swing.JLabel imgReg;
    private javax.swing.JLabel imgSed;
    private javax.swing.JLabel imgSop;
    private javax.swing.JLabel imgSus;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JLabel lblADMINISTRACION;
    private javax.swing.JLabel lblInicio;
    private javax.swing.JLabel lblMAQUINAS;
    private javax.swing.JLabel lblRegistro;
    private javax.swing.JLabel lblSEDES;
    private javax.swing.JLabel lblSOPORTE;
    private javax.swing.JLabel lblSUSCRIPCION;
    private javax.swing.JTextField textBuscar;
    // End of variables declaration//GEN-END:variables
}
