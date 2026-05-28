package com.mycompany.proyecto_final_poo;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.Timer;
import java.util.Date;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.util.Calendar;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.ResultSet;

public class Modulo extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Admin_Panel.class.getName());

public void registrarPago(String codigo, String tipoPlan, java.sql.Date fecha) {

    String sql = "INSERT INTO PAGOS (codigo, tipoPlan, fecha) VALUES (?, ?, ?)";

    try (Connection con = new ConexionSQL().conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, codigo);
        ps.setString(2, tipoPlan);
        ps.setDate(3, fecha);

        ps.executeUpdate();

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al registrar pago");
    }
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
        imgMaq.setIcon(new javax.swing.ImageIcon("datos/Images/IconMaquinas.png"));
        imgMaq.setText("");
        imgSop.setIcon(new javax.swing.ImageIcon("datos/Images/IconSoporte.png"));
        imgSop.setText("");
        imgAdm.setIcon(new javax.swing.ImageIcon("datos/Images/IconAdministracon - copia.png"));
        imgAdm.setText("");
    }
public void cargarMembresias() {

    DefaultTableModel modelo = new DefaultTableModel();

    
    modelo.addColumn("Código");
    modelo.addColumn("Plan");
    modelo.addColumn("Último Pago");
    modelo.addColumn("Fecha Registro");
    modelo.addColumn("Estado");

    // Extraemos de CLIENTES (FechaRegistro) y de PAGOS (fecha)
    String sql = "SELECT p.codigo, p.tipoPlan, c.FechaRegistro, MAX(p.fecha) AS UltimoPago " +
                 "FROM PAGOS p " +
                 "INNER JOIN CLIENTES c ON p.codigo = c.codigo " +
                 "GROUP BY p.codigo, p.tipoPlan, c.FechaRegistro";

    try (Connection con = new ConexionSQL().conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        SimpleDateFormat formateador = new SimpleDateFormat("dd/MM/yyyy");

        while (rs.next()) {

            String codigo = rs.getString("codigo");
            String planCodigo = rs.getString("tipoPlan");
            

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
            
            // 1. Saca la Fecha de Registro de la tabla CLIENTES
            java.sql.Date fechaRegCruda = rs.getDate("FechaRegistro");
            String fechaRegistroFormateada = (fechaRegCruda != null) ? formateador.format(fechaRegCruda) : "N/A";

            // 2. Saca el Último Pago de la tabla PAGOS
            java.sql.Date fechaPago = rs.getDate("UltimoPago");
            String ultimoPagoFormateado = (fechaPago != null) ? formateador.format(fechaPago) : "N/A";

            String estado = "";

            try {
                Calendar cal = Calendar.getInstance();
                cal.setTime(fechaPago);
                cal.add(Calendar.DAY_OF_MONTH, 30);

                java.util.Date fechaVencimiento = cal.getTime();
                java.util.Date hoy = new java.util.Date();

                if (hoy.after(fechaVencimiento)) {
                    estado = "VENCIDO";
                } else {
                    estado = "ACTIVO";
                }

            } catch (Exception e) {
                estado = "ERROR";
            }

            // Metemos los datos a la tabla respetando el orden
            modelo.addRow(new Object[]{
                codigo,
                plan,
                ultimoPagoFormateado,    // Columna 3
                fechaRegistroFormateada, // Columna 4
                estado
            });
        }

        Membresias.setModel(modelo);

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al cargar membresías");
    }
    
}

    public void iniciarReloj() {

    Timer tiempo = new Timer(1000, new ActionListener() {

        @Override
        public void actionPerformed(ActionEvent e) {

            Date fechaActual = new Date();

            SimpleDateFormat formato =
                    new SimpleDateFormat(
                            "dd/MM/yyyy  HH:mm:ss");

            lblReloj.setText(
                    formato.format(fechaActual));
        }
    });

    tiempo.start();
}
public void calcularPlanes() {

    int basico = 0;
    int intermedio = 0;
    int premium = 0;

    String sql = "SELECT tipoPlan, COUNT(*) AS total FROM CLIENTES GROUP BY tipoPlan";

    try (Connection con = new ConexionSQL().conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            String plan = rs.getString("tipoPlan");
            int total = rs.getInt("total");

            if (plan.equals("100")) {
                basico = total;
            } 
            else if (plan.equals("010")) {
                intermedio = total;
            } 
            else if (plan.equals("001")) {
                premium = total;
            }
        }

        lblPlanes1.setText(
            "Basico: " + basico 
        );
        lblPlanes.setText(           
            " Fit: " + intermedio 
        );
        lblPlanes2.setText(
            " Premium: " + premium
        );

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void calcularClientes() {

    String sql = "SELECT COUNT(*) AS total FROM CLIENTES";

    try (Connection con = new ConexionSQL().conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {

            int clientes = rs.getInt("total");

            lblClientes.setText(
                "" + clientes
            );
        }

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void calcularGanancia() {

    int gananciaTotal = 0;

    String sql =
         """
        SELECT 
            SUM(
                CASE 
                    WHEN tipoPlan = '100' THEN 90
                    WHEN tipoPlan = '010' THEN 110
                    WHEN tipoPlan = '001' THEN 130
                    ELSE 0
                END
            ) AS total
        FROM PAGOS
        """;

    try (Connection con = new ConexionSQL().conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        if (rs.next()) {
            gananciaTotal = rs.getInt("total");
        }

        lblGanancia.setText("Ganancia total: S/ " + gananciaTotal);

    } catch (SQLException e) {
        e.printStackTrace();
    }
}
public void cargarUsuarios() {

    DefaultTableModel modelo = new DefaultTableModel();

    modelo.addColumn("Usuario");
    modelo.addColumn("Contraseña");

    String sql = "SELECT usuario, password FROM USUARIOS";

    try (Connection con = new ConexionSQL().conectar();
         PreparedStatement ps = con.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {

            modelo.addRow(new Object[]{
                rs.getString("usuario"),
                rs.getString("password")
            });
        }

        tablaUsuarios.setModel(modelo);

    } catch (SQLException e) {
        System.out.println("Error");
    }
}   
  

  
   
  
  
  
    /**
     * Creates new form Admin_Panel
     */
    public Modulo() {
        initComponents();
        Configuracion();
        this.setResizable(false);
        calcularGanancia();
        cargarUsuarios();    
        calcularPlanes();
        calcularClientes();
        iniciarReloj();
        cargarMembresias();
    }
      


    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel8 = new javax.swing.JLabel();
        lblSUSCRIPCION1 = new javax.swing.JLabel();
        lblREGISTRO1 = new javax.swing.JLabel();
        lblMAQUINAS1 = new javax.swing.JLabel();
        lblSOPORTE1 = new javax.swing.JLabel();
        imgGymWork1 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        lblSEDES1 = new javax.swing.JLabel();
        IconSedes = new javax.swing.JLabel();
        lblINICIO1 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        lblADMINISTRACION1 = new javax.swing.JLabel();
        imgAdm = new javax.swing.JLabel();
        imgReg = new javax.swing.JLabel();
        imgINICIO = new javax.swing.JLabel();
        imgSus = new javax.swing.JLabel();
        imgSed = new javax.swing.JLabel();
        imgMaq = new javax.swing.JLabel();
        imgSop = new javax.swing.JLabel();
        jPanel5 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        lblGanancia = new javax.swing.JLabel();
        jPanel6 = new javax.swing.JPanel();
        lblPlanes = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lblPlanes1 = new javax.swing.JLabel();
        lblPlanes2 = new javax.swing.JLabel();
        jPanel7 = new javax.swing.JPanel();
        lblClientes = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lblReloj = new javax.swing.JLabel();
        jPanel9 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tablaUsuarios = new javax.swing.JTable();
        jLabel2 = new javax.swing.JLabel();
        Reg = new javax.swing.JButton();
        Mod = new javax.swing.JButton();
        Eli = new javax.swing.JButton();
        jPanel10 = new javax.swing.JPanel();
        jScrollPane2 = new javax.swing.JScrollPane();
        Membresias = new javax.swing.JTable();
        jLabel1 = new javax.swing.JLabel();
        Renovar = new javax.swing.JButton();
        jLabel6 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(102, 153, 255));

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Rockwell Condensed", 3, 48)); // NOI18N
        jLabel8.setText("GymWork");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 30, 192, -1));

        lblSUSCRIPCION1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSUSCRIPCION1.setText("Suscripciones");
        lblSUSCRIPCION1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCION1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCION1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSUSCRIPCION1MouseExited(evt);
            }
        });
        jPanel3.add(lblSUSCRIPCION1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 250, -1, -1));

        lblREGISTRO1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblREGISTRO1.setText("Registro");
        lblREGISTRO1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblREGISTRO1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblREGISTRO1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblREGISTRO1MouseExited(evt);
            }
        });
        jPanel3.add(lblREGISTRO1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 330, -1, -1));

        lblMAQUINAS1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblMAQUINAS1.setText("Estado de maquinas");
        lblMAQUINAS1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblMAQUINAS1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblMAQUINAS1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblMAQUINAS1MouseExited(evt);
            }
        });
        jPanel3.add(lblMAQUINAS1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 480, -1, -1));

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
        jPanel3.add(lblSOPORTE1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 560, -1, -1));
        jPanel3.add(imgGymWork1, new org.netbeans.lib.awtextra.AbsoluteConstraints(7, 27, -1, -1));
        jPanel3.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 220, -1, -1));
        jPanel3.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 310, -1, -1));
        jPanel3.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 470, -1, -1));
        jPanel3.add(jLabel13, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 540, -1, -1));
        jPanel3.add(jLabel14, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 620, -1, -1));

        lblSEDES1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblSEDES1.setText("Sedes");
        lblSEDES1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblSEDES1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblSEDES1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblSEDES1MouseExited(evt);
            }
        });
        jPanel3.add(lblSEDES1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 410, -1, -1));
        jPanel3.add(IconSedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

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
        jPanel3.add(lblINICIO1, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 170, -1, -1));

        jPanel4.setBackground(new java.awt.Color(102, 204, 255));
        jPanel4.setPreferredSize(new java.awt.Dimension(288, 49));

        lblADMINISTRACION1.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        lblADMINISTRACION1.setForeground(new java.awt.Color(0, 102, 255));
        lblADMINISTRACION1.setText("Módulo de administración");
        lblADMINISTRACION1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                lblADMINISTRACION1MouseClicked(evt);
            }
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                lblADMINISTRACION1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                lblADMINISTRACION1MouseExited(evt);
            }
        });

        imgAdm.setText("jLabel3");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(imgAdm)
                .addGap(18, 18, 18)
                .addComponent(lblADMINISTRACION1)
                .addContainerGap(44, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(imgAdm)
                .addGap(0, 33, Short.MAX_VALUE))
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblADMINISTRACION1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 620, 350, -1));

        imgReg.setText("jLabel3");
        jPanel3.add(imgReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 310, -1, -1));

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

        jPanel5.setBackground(new java.awt.Color(0, 46, 71));

        jPanel2.setBackground(new java.awt.Color(242, 255, 255));

        lblGanancia.setBackground(new java.awt.Color(126, 235, 126));
        lblGanancia.setFont(new java.awt.Font("Segoe UI Historic", 1, 18)); // NOI18N
        lblGanancia.setText("jLabel2");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(lblGanancia, javax.swing.GroupLayout.DEFAULT_SIZE, 222, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(lblGanancia, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(51, Short.MAX_VALUE))
        );

        jPanel6.setBackground(new java.awt.Color(242, 255, 255));

        lblPlanes.setBackground(new java.awt.Color(126, 235, 126));
        lblPlanes.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        lblPlanes.setText("jLabel2");

        jLabel4.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel4.setText("Membrasias Activas:");

        lblPlanes1.setBackground(new java.awt.Color(126, 235, 126));
        lblPlanes1.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        lblPlanes1.setText("jLabel2");

        lblPlanes2.setBackground(new java.awt.Color(126, 235, 126));
        lblPlanes2.setFont(new java.awt.Font("Segoe UI Historic", 1, 12)); // NOI18N
        lblPlanes2.setText("jLabel2");

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 188, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPlanes, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPlanes1, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lblPlanes2, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 29, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPlanes1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPlanes, javax.swing.GroupLayout.PREFERRED_SIZE, 24, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lblPlanes2)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        jPanel7.setBackground(new java.awt.Color(242, 255, 255));

        lblClientes.setBackground(new java.awt.Color(126, 235, 126));
        lblClientes.setFont(new java.awt.Font("Segoe UI Historic", 1, 24)); // NOI18N
        lblClientes.setText("jLabel2");

        jLabel3.setFont(new java.awt.Font("Segoe UI", 3, 18)); // NOI18N
        jLabel3.setText("Clientes registrados:");

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(8, 8, 8)
                        .addComponent(lblClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 109, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(35, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(lblClientes, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        lblReloj.setFont(new java.awt.Font("Segoe UI Variable", 1, 14)); // NOI18N
        lblReloj.setForeground(new java.awt.Color(255, 255, 255));
        lblReloj.setText("jLabel2");

        jPanel9.setBackground(new java.awt.Color(242, 255, 255));

        tablaUsuarios.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(tablaUsuarios);

        jLabel2.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        jLabel2.setText("ADMINISTRADORES");

        Reg.setBackground(new java.awt.Color(248, 248, 248));
        Reg.setText("Registrar");
        Reg.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RegActionPerformed(evt);
            }
        });

        Mod.setBackground(new java.awt.Color(248, 248, 248));
        Mod.setText("Modificar");
        Mod.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                ModActionPerformed(evt);
            }
        });

        Eli.setBackground(new java.awt.Color(248, 248, 248));
        Eli.setText("Eliminar");
        Eli.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                EliActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                .addContainerGap(32, Short.MAX_VALUE)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addGap(30, 30, 30)
                                .addComponent(Reg, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel9Layout.createSequentialGroup()
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Eli, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(Mod, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(48, 48, 48))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(Reg, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Mod, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(Eli, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel10.setBackground(new java.awt.Color(242, 255, 255));

        Membresias.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane2.setViewportView(Membresias);

        jLabel1.setFont(new java.awt.Font("Segoe UI Historic", 1, 14)); // NOI18N
        jLabel1.setText("MEMBRESIAS");

        Renovar.setBackground(new java.awt.Color(248, 248, 248));
        Renovar.setText("Renovar");
        Renovar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                RenovarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 403, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(Renovar, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(20, 20, 20))
                    .addGroup(jPanel10Layout.createSequentialGroup()
                        .addGap(82, 82, 82)
                        .addComponent(Renovar, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );

        jLabel6.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\software.png")); // NOI18N

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(46, 46, 46)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(32, 32, 32)
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 186, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(113, 113, 113)
                        .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 739, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(63, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(26, 26, 26)
                        .addComponent(lblReloj, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                .addGap(18, 18, 18)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSUSCRIPCION1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUSCRIPCION1MouseEntered
        lblSUSCRIPCION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION1.setForeground(new java.awt.Color(0, 102, 255));
        
    }//GEN-LAST:event_lblSUSCRIPCION1MouseEntered

    private void lblSUSCRIPCION1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUSCRIPCION1MouseExited
        lblSUSCRIPCION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION1.setForeground(new java.awt.Color(0, 0, 0));
     
    }//GEN-LAST:event_lblSUSCRIPCION1MouseExited

    private void lblREGISTRO1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblREGISTRO1MouseEntered
        lblREGISTRO1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO1.setForeground(new java.awt.Color(0, 102, 255));
       
    }//GEN-LAST:event_lblREGISTRO1MouseEntered

    private void lblREGISTRO1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblREGISTRO1MouseExited
        lblREGISTRO1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO1.setForeground(new java.awt.Color(0, 0, 0));
     
    }//GEN-LAST:event_lblREGISTRO1MouseExited

    private void lblMAQUINAS1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINAS1MouseEntered
        lblMAQUINAS1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS1.setForeground(new java.awt.Color(0, 102, 255));
      
    }//GEN-LAST:event_lblMAQUINAS1MouseEntered

    private void lblMAQUINAS1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINAS1MouseExited
        lblMAQUINAS1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS1.setForeground(new java.awt.Color(0, 0, 0));
     
    }//GEN-LAST:event_lblMAQUINAS1MouseExited

    private void lblSOPORTE1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTE1MouseEntered
        lblSOPORTE1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE1.setForeground(new java.awt.Color(0, 102, 255));
       
    }//GEN-LAST:event_lblSOPORTE1MouseEntered

    private void lblSOPORTE1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTE1MouseExited
        lblSOPORTE1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE1.setForeground(new java.awt.Color(0, 0, 0));
     
    }//GEN-LAST:event_lblSOPORTE1MouseExited

    private void lblADMINISTRACION1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACION1MouseEntered
        
        
    }//GEN-LAST:event_lblADMINISTRACION1MouseEntered

    private void lblADMINISTRACION1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACION1MouseExited
        
    }//GEN-LAST:event_lblADMINISTRACION1MouseExited

    private void lblSEDES1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSEDES1MouseExited
        lblSEDES1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES1.setForeground(new java.awt.Color(0, 0, 0));
    
    }//GEN-LAST:event_lblSEDES1MouseExited

    private void lblSEDES1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSEDES1MouseEntered
        lblSEDES1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES1.setForeground(new java.awt.Color(0, 102, 255));
       
    }//GEN-LAST:event_lblSEDES1MouseEntered

    private void lblSEDES1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSEDES1MouseClicked
        Sedes OBJ = new Sedes();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSEDES1MouseClicked

    private void lblREGISTRO1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblREGISTRO1MouseClicked
     Registro_panel OBJ = new Registro_panel();
     OBJ.setVisible(true);
     this.dispose();
    }//GEN-LAST:event_lblREGISTRO1MouseClicked

    private void lblSUSCRIPCION1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUSCRIPCION1MouseClicked
    Admin_Panel OBJ1 = new Admin_Panel();
        OBJ1.setVisible(true);
        Suscripciones sus = new Suscripciones();
        sus.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSUSCRIPCION1MouseClicked

    private void lblINICIO1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblINICIO1MouseClicked
    Admin_Panel AP = new Admin_Panel();
    AP.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_lblINICIO1MouseClicked

    private void lblINICIO1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblINICIO1MouseEntered
        lblINICIO1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblINICIO1.setForeground(new java.awt.Color(0, 102, 255));
        
    }//GEN-LAST:event_lblINICIO1MouseEntered

    private void lblINICIO1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblINICIO1MouseExited
        lblINICIO1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblINICIO1.setForeground(new java.awt.Color(0, 0, 0));
    }//GEN-LAST:event_lblINICIO1MouseExited

    private void RegActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RegActionPerformed
String usuario = JOptionPane.showInputDialog("Ingrese usuario");
String contraseña = JOptionPane.showInputDialog("Ingrese contraseña");

if (usuario == null || contraseña == null ||
    usuario.trim().isEmpty() || contraseña.trim().isEmpty()) {

    JOptionPane.showMessageDialog(this, "Datos inválidos");
    return;
}

String sql = "INSERT INTO USUARIOS (usuario, password) VALUES (?, ?)";

try (Connection con = new ConexionSQL().conectar();
     PreparedStatement ps = con.prepareStatement(sql)) {

    ps.setString(1, usuario.trim());
    ps.setString(2, contraseña.trim());

    ps.executeUpdate();

    JOptionPane.showMessageDialog(this, "Usuario registrado");

    cargarUsuarios(); 

} catch (SQLException e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Error al registrar usuario");
}
    }//GEN-LAST:event_RegActionPerformed

    private void ModActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ModActionPerformed
int fila = tablaUsuarios.getSelectedRow();

if (fila == -1) {
    JOptionPane.showMessageDialog(this, "Seleccione un administrador");
    return;
}

String usuarioActual = tablaUsuarios.getValueAt(fila, 0).toString();

String nuevoUsuario = JOptionPane.showInputDialog("Ingrese nuevo usuario");
String nuevaContra = JOptionPane.showInputDialog("Ingrese nueva contraseña");

if (nuevoUsuario == null || nuevaContra == null ||
    nuevoUsuario.trim().isEmpty() || nuevaContra.trim().isEmpty()) {
    JOptionPane.showMessageDialog(this, "Datos inválidos");
    return;
}

String sql = "UPDATE USUARIOS SET usuario = ?, password = ? WHERE usuario = ?";

try (Connection con = new ConexionSQL().conectar();
     PreparedStatement ps = con.prepareStatement(sql)) {

    ps.setString(1, nuevoUsuario.trim());
    ps.setString(2, nuevaContra.trim());
    ps.setString(3, usuarioActual);

    int filas = ps.executeUpdate();

    if (filas > 0) {
        JOptionPane.showMessageDialog(this, "Administrador modificado");
        cargarUsuarios();
    } else {
        JOptionPane.showMessageDialog(this, "Usuario no encontrado");
    }

} catch (SQLException e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Error al modificar");
}
    }//GEN-LAST:event_ModActionPerformed

    private void EliActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_EliActionPerformed
int fila = tablaUsuarios.getSelectedRow();

if (fila == -1) {
    JOptionPane.showMessageDialog(this, "Seleccione un administrador");
    return;
}

String usuario = tablaUsuarios.getValueAt(fila, 0).toString();

int confirm = JOptionPane.showConfirmDialog(
        this,
        "¿Seguro que desea eliminar este usuario?",
        "Confirmar",
        JOptionPane.YES_NO_OPTION
);

if (confirm != JOptionPane.YES_OPTION) {
    return;
}

String sql = "DELETE FROM USUARIOS WHERE usuario = ?";

try (Connection con = new ConexionSQL().conectar();
     PreparedStatement ps = con.prepareStatement(sql)) {

    ps.setString(1, usuario);

    int filas = ps.executeUpdate();

    if (filas > 0) {
        JOptionPane.showMessageDialog(this, "Administrador eliminado");
        cargarUsuarios();
    } else {
        JOptionPane.showMessageDialog(this, "Usuario no encontrado");
    }

} catch (SQLException e) {
    e.printStackTrace();
    JOptionPane.showMessageDialog(this, "Error al eliminar");
}
    }//GEN-LAST:event_EliActionPerformed

    private void RenovarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_RenovarActionPerformed
 int fila = Membresias.getSelectedRow();

    if (fila == -1) {
        JOptionPane.showMessageDialog(this, "Seleccione una membresía");
        return;
    }

    String codigo = Membresias.getValueAt(fila, 0).toString();

    String sql = "SELECT tipoPlan FROM CLIENTES WHERE codigo = ?";

    try (Connection con = new ConexionSQL().conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, codigo);

        ResultSet rs = ps.executeQuery();

        if (rs.next()) {

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
            
            
            java.sql.Date fecha = new java.sql.Date(
                System.currentTimeMillis()
            );

            registrarPago(codigo, plan, fecha);

            JOptionPane.showMessageDialog(this, "Renovación exitosa");

            cargarMembresias(); 

        } else {
            JOptionPane.showMessageDialog(this, "Cliente no encontrado");
        }

    } catch (SQLException e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al renovar");
    }
    }//GEN-LAST:event_RenovarActionPerformed

    private void lblMAQUINAS1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINAS1MouseClicked
        Maquinas_panel OBJ = new Maquinas_panel();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblMAQUINAS1MouseClicked

    private void lblSOPORTE1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTE1MouseClicked
        Soporte OBJ = new Soporte();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSOPORTE1MouseClicked

    private void lblADMINISTRACION1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACION1MouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_lblADMINISTRACION1MouseClicked

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
    java.awt.EventQueue.invokeLater(() -> new Modulo().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Eli;
    private javax.swing.JLabel IconSedes;
    private javax.swing.JTable Membresias;
    private javax.swing.JButton Mod;
    private javax.swing.JButton Reg;
    private javax.swing.JButton Renovar;
    private javax.swing.JLabel imgAdm;
    private javax.swing.JLabel imgGymWork1;
    private javax.swing.JLabel imgINICIO;
    private javax.swing.JLabel imgMaq;
    private javax.swing.JLabel imgReg;
    private javax.swing.JLabel imgSed;
    private javax.swing.JLabel imgSop;
    private javax.swing.JLabel imgSus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JLabel lblADMINISTRACION1;
    private javax.swing.JLabel lblClientes;
    private javax.swing.JLabel lblGanancia;
    private javax.swing.JLabel lblINICIO1;
    private javax.swing.JLabel lblMAQUINAS1;
    private javax.swing.JLabel lblPlanes;
    private javax.swing.JLabel lblPlanes1;
    private javax.swing.JLabel lblPlanes2;
    private javax.swing.JLabel lblREGISTRO1;
    private javax.swing.JLabel lblReloj;
    private javax.swing.JLabel lblSEDES1;
    private javax.swing.JLabel lblSOPORTE1;
    private javax.swing.JLabel lblSUSCRIPCION1;
    private javax.swing.JTable tablaUsuarios;
    // End of variables declaration//GEN-END:variables
}
