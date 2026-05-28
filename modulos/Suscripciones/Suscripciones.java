
package com.mycompany.proyecto_final_poo;
import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;


public class Suscripciones extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Suscripciones.class.getName());
    

    public Suscripciones() {
        initComponents();
        this.setLocation(500, 100);
        this.setResizable(false);
        ConfigurarControles();
    }
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
public String replicate(char a, int b){
    String T;
    T="";
    for(int k=1;k<=b;k++){
    T=T+a;
    }
    return T;
    }
private String alignmentright(String m,int n){
        int p;
        m=m.trim();
        p=m.length();
        return replicate(' ',n - p);
    }    
private void ConfigurarControles (){
        
        Codigo.setText("");
        Edad.setText("");
        Nom.setText("");
        Ap_Pat.setText("");
        Ap_Mat.setText("");
        Correo.setText("");
        Num.setText("");
        Departamento.setText("");
        Ciudad.setText("");
        Calle.setText("");
        Fecha.setDate(null);
        
        Sexo.removeAllItems();
        Sexo.addItem("Masculino");
        Sexo.addItem("Femenino");
        Sexo.addItem("No especifico");
        
        
    }
private String obtenerPlan() {

    if (ButBasico.isSelected()) return "100";
    if (ButFit.isSelected()) return "010";
    return "001";
}
private void GrabarRegistro() {

    String plan = obtenerPlan();

    String sql = "INSERT INTO CLIENTES (" +
            "codigo, nombre, apellidoPaterno, apellidoMaterno, edad, FechaNacimiento," +
            "sexo, correo, telefono, tipoPlan, departamento, ciudad, calle" +
            ") VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

    try (Connection con = new ConexionSQL().conectar();
         PreparedStatement ps = con.prepareStatement(sql)) {

        ps.setString(1, Codigo.getText());
        ps.setString(2, Nom.getText());
        ps.setString(3, Ap_Pat.getText());
        ps.setString(4, Ap_Mat.getText());
        ps.setInt(5, Integer.parseInt(Edad.getText()));

        java.util.Date utilDate = Fecha.getDate();
        java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
        ps.setDate(6, sqlDate);

        ps.setString(7, Sexo.getSelectedItem().toString());
        ps.setString(8, Correo.getText());
        ps.setString(9, Num.getText());
        ps.setString(10, plan);
        ps.setString(11, Departamento.getText());
        ps.setString(12, Ciudad.getText());
        ps.setString(13, Calle.getText());

        ps.executeUpdate();

        registrarPago(Codigo.getText(), plan, sqlDate);

        JOptionPane.showMessageDialog(this, "Registro correcto");

    } catch (Exception e) {
        e.printStackTrace();
        JOptionPane.showMessageDialog(this, "Error al registrar");
    }
}
private boolean Validar() {
    if (!ButBasico.isSelected() &&
    !ButFit.isSelected() &&
    !ButPremium.isSelected()) {

    JOptionPane.showMessageDialog(this, "Seleccione un plan");
    return false;
}

   if(Fecha.getDate() == null){

    JOptionPane.showMessageDialog(
            this,
            "Seleccione una fecha");

    return false;
}
    if (!Nom.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
        JOptionPane.showMessageDialog(this, "Nombre inválido");
        Nom.requestFocus();
        return false;
    }
    if (!Ap_Pat.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
        JOptionPane.showMessageDialog(this, "Apellido inválido");
        Ap_Pat.requestFocus();
        return false;
    }
    if (!Ap_Mat.getText().matches("[a-zA-ZáéíóúÁÉÍÓÚñÑ ]+")) {
        JOptionPane.showMessageDialog(this, "Apellido inválido");
        Ap_Mat.requestFocus();
        return false;
    }    
    if (!Edad.getText().matches("\\d+")) {
        JOptionPane.showMessageDialog(this, "Edad inválida");
        Edad.requestFocus();
        return false;
    }

  
    if (!Correo.getText().matches("^[\\w.-]+@[\\w.-]+\\.\\w+$")) {
        JOptionPane.showMessageDialog(this, "Correo inválido");
        Correo.requestFocus();
        return false;
    }

    
    if (Codigo.getText().isEmpty()) {
        JOptionPane.showMessageDialog(this, "Código obligatorio");
        Codigo.requestFocus();
        return false;
    }

    return true; 
}
private void limpiar(){
        
        Codigo.setText("");
        Edad.setText("");
        Nom.setText("");
        Ap_Pat.setText("");
        Ap_Mat.setText("");
        Correo.setText("");
        Num.setText("");
        Departamento.setText("");
        Ciudad.setText("");
        Calle.setText("");
        Fecha.setDate(null);
        
        Sexo.removeAllItems();
        Sexo.addItem("Masculino");
        Sexo.addItem("Femenino");
        Sexo.addItem("No especifico");
        buttonGroup1.clearSelection();
        


}
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel10 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        Codigo = new javax.swing.JTextField();
        Nom = new javax.swing.JTextField();
        Ap_Pat = new javax.swing.JTextField();
        Ap_Mat = new javax.swing.JTextField();
        Edad = new javax.swing.JTextField();
        Correo = new javax.swing.JTextField();
        Sexo = new javax.swing.JComboBox<>();
        Num = new javax.swing.JTextField();
        imge = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        Fecha = new com.toedter.calendar.JDateChooser();
        ButBasico = new javax.swing.JRadioButton();
        ButFit = new javax.swing.JRadioButton();
        ButPremium = new javax.swing.JRadioButton();
        jPanel3 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        Departamento = new javax.swing.JTextField();
        Ciudad = new javax.swing.JTextField();
        Calle = new javax.swing.JTextField();
        Aceptar = new javax.swing.JButton();
        Cancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 233, 167));
        jPanel1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102)));

        jLabel10.setText("Paquete");

        jPanel2.setBackground(new java.awt.Color(255, 233, 167));
        jPanel2.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102)));

        jLabel6.setText("Sexo");

        jLabel7.setText("Correo");

        jLabel8.setText("Teléfono");

        jLabel9.setText("Fecha de Suscripcion:");

        jLabel1.setText("Código");

        jLabel2.setText("Nombre");

        jLabel3.setText("Apellido Paterno ");

        jLabel4.setText("Apellido  Materno ");

        jLabel5.setText("Edad");

        Codigo.setText("jTextField1");
        Codigo.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CodigoKeyTyped(evt);
            }
        });

        Nom.setText("jTextField2");
        Nom.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                NomKeyTyped(evt);
            }
        });

        Ap_Pat.setText("jTextField3");
        Ap_Pat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Ap_PatKeyTyped(evt);
            }
        });

        Ap_Mat.setText("jTextField4");
        Ap_Mat.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                Ap_MatKeyTyped(evt);
            }
        });

        Edad.setText("jTextField5");
        Edad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                EdadKeyTyped(evt);
            }
        });

        Correo.setText("jTextField6");

        Sexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        Num.setText("jTextField7");
        Num.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                NumKeyTyped(evt);
            }
        });

        imge.setIcon(new javax.swing.ImageIcon("C:\\Users\\137540\\OneDrive\\Desktop\\Fernando\\POO\\PC4-POO\\Proyecto_Final_POO\\datos\\Images\\MancuernaRegistro.jpg")); // NOI18N
        imge.setText("jLabel11");

        jLabel16.setText("Datos del cliente");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(28, 28, 28)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(71, 71, 71)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Edad, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(41, 41, 41)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(Fecha, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jLabel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4)
                            .addComponent(Nom, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Ap_Pat, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Ap_Mat, javax.swing.GroupLayout.PREFERRED_SIZE, 71, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(42, 42, 42)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel6, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Correo, javax.swing.GroupLayout.PREFERRED_SIZE, 185, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                                .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(Num, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, 71, Short.MAX_VALUE)))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 80, Short.MAX_VALUE)
                .addComponent(imge, javax.swing.GroupLayout.PREFERRED_SIZE, 352, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(38, 38, 38))
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16, javax.swing.GroupLayout.PREFERRED_SIZE, 112, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(jLabel16)
                        .addGap(21, 21, 21)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel1)
                            .addComponent(jLabel5)
                            .addComponent(jLabel9))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(Codigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(Edad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(Fecha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(13, 13, 13)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jLabel2))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Nom, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Sexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel7)
                            .addComponent(jLabel3))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Ap_Pat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Correo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(9, 9, 9)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jLabel4))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(Ap_Mat, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(Num, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 16, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(imge, javax.swing.GroupLayout.PREFERRED_SIZE, 256, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );

        buttonGroup1.add(ButBasico);
        ButBasico.setText("Paquete Basico");
        ButBasico.addActionListener(this::ButBasicoActionPerformed);

        buttonGroup1.add(ButFit);
        ButFit.setText("Paquete Fit");

        buttonGroup1.add(ButPremium);
        ButPremium.setText("Plan Premium");

        jPanel3.setBackground(new java.awt.Color(255, 233, 167));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(255, 153, 102)));

        jLabel12.setText("Domicilio");

        jLabel13.setText("Departamento");

        jLabel14.setText("Calle");

        jLabel15.setText("Ciudad");

        Departamento.setText("jTextField1");
        Departamento.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                DepartamentoKeyTyped(evt);
            }
        });

        Ciudad.setText("jTextField1");
        Ciudad.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                CiudadKeyTyped(evt);
            }
        });

        Calle.setText("jTextField1");
        Calle.addActionListener(this::CalleActionPerformed);

        Aceptar.setBackground(new java.awt.Color(153, 255, 153));
        Aceptar.setText("Aceptar");
        Aceptar.addActionListener(this::AceptarActionPerformed);

        Cancelar.setBackground(new java.awt.Color(255, 51, 51));
        Cancelar.setText("Cancelar");
        Cancelar.addActionListener(this::CancelarActionPerformed);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(158, 158, 158)
                .addComponent(Aceptar)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(Cancelar)
                .addGap(153, 153, 153))
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(34, 34, 34)
                        .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel3Layout.createSequentialGroup()
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel13)
                                    .addComponent(Departamento, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(82, 82, 82)
                                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(Ciudad, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(Calle)))
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(14, 14, 14)
                        .addComponent(jLabel12, javax.swing.GroupLayout.PREFERRED_SIZE, 62, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel12)
                .addGap(12, 12, 12)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jLabel15))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Departamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Ciudad, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jLabel14)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Calle, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 38, Short.MAX_VALUE)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(Aceptar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Cancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel10, javax.swing.GroupLayout.PREFERRED_SIZE, 49, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(ButBasico, javax.swing.GroupLayout.PREFERRED_SIZE, 159, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(147, 147, 147)
                        .addComponent(ButFit, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(ButPremium, javax.swing.GroupLayout.PREFERRED_SIZE, 98, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(114, 114, 114))))
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel10)
                .addGap(24, 24, 24)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(ButBasico)
                    .addComponent(ButFit)
                    .addComponent(ButPremium))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void ButBasicoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_ButBasicoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_ButBasicoActionPerformed

    private void CalleActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CalleActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_CalleActionPerformed

    private void AceptarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AceptarActionPerformed
    if(Validar()){
    GrabarRegistro();
    limpiar();
    JOptionPane.showMessageDialog(this, "Registro guardado correctamente");
    }
    }//GEN-LAST:event_AceptarActionPerformed

    private void CancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_CancelarActionPerformed
    this.dispose();
    }//GEN-LAST:event_CancelarActionPerformed

    private void CodigoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CodigoKeyTyped

    }//GEN-LAST:event_CodigoKeyTyped

    private void EdadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_EdadKeyTyped
    char c = evt.getKeyChar();

    if (!Character.isDigit(c)) {
        evt.consume();
    }
    }//GEN-LAST:event_EdadKeyTyped

    private void NomKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NomKeyTyped
    char c = evt.getKeyChar();

    if (!Character.isLetter(c) && c != ' ') {
        evt.consume();
    }
    }//GEN-LAST:event_NomKeyTyped

    private void DepartamentoKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_DepartamentoKeyTyped
    char c = evt.getKeyChar();

    if (!Character.isLetter(c) && c != ' ') {
        evt.consume();
    }
    }//GEN-LAST:event_DepartamentoKeyTyped

    private void CiudadKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_CiudadKeyTyped
        char c = evt.getKeyChar();

    if (!Character.isLetter(c) && c != ' ') {
        evt.consume();
    }
    }//GEN-LAST:event_CiudadKeyTyped

    private void NumKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_NumKeyTyped
    char c = evt.getKeyChar();

    if (!Character.isDigit(c)) {
        evt.consume();
    }
    }//GEN-LAST:event_NumKeyTyped

    private void Ap_PatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Ap_PatKeyTyped
    char c = evt.getKeyChar();

    if (!Character.isLetter(c) && c != ' ') {
        evt.consume();
    }
    }//GEN-LAST:event_Ap_PatKeyTyped

    private void Ap_MatKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_Ap_MatKeyTyped
    char c = evt.getKeyChar();

    if (!Character.isLetter(c) && c != ' ') {
        evt.consume();
    }
    }//GEN-LAST:event_Ap_MatKeyTyped

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
        java.awt.EventQueue.invokeLater(() -> new Suscripciones().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton Aceptar;
    private javax.swing.JTextField Ap_Mat;
    private javax.swing.JTextField Ap_Pat;
    private javax.swing.JRadioButton ButBasico;
    private javax.swing.JRadioButton ButFit;
    private javax.swing.JRadioButton ButPremium;
    private javax.swing.JTextField Calle;
    private javax.swing.JButton Cancelar;
    private javax.swing.JTextField Ciudad;
    private javax.swing.JTextField Codigo;
    private javax.swing.JTextField Correo;
    private javax.swing.JTextField Departamento;
    private javax.swing.JTextField Edad;
    private com.toedter.calendar.JDateChooser Fecha;
    private javax.swing.JTextField Nom;
    private javax.swing.JTextField Num;
    private javax.swing.JComboBox<String> Sexo;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JLabel imge;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
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
    // End of variables declaration//GEN-END:variables
}
