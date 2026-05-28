package com.mycompany.proyecto_final_poo;
import javax.swing.Timer;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;


public class Admin_Panel extends javax.swing.JFrame {
    
    private static final java.util.logging.Logger logger = java.util.logging.Logger.getLogger(Admin_Panel.class.getName());
    
    private Timer timerAnimacion;
    private float valorColor = 255.0f;
    private boolean oscureciendo = true;
    private float velocidadTransicion = 1.0f;
     private Timer timer;
    private float alpha = 1f;
    Marca_de_agua obj= new Marca_de_agua();
    /**
     * Creates new form Admin_Panel
     */
    public Admin_Panel() {
        initComponents();
                 ConexionSQL cx =
            new ConexionSQL();

    cx.conectar();
        this.setResizable(false);
        Configuracion();
        iniciarAnimacionFondo();
        iniciarLogoFondo();
    obj.setBounds(110, 80, 612, 612);
    jPanel1.add(obj);
    jPanel1.setComponentZOrder(obj, jPanel1.getComponentCount()-1);
    }
      
    private void iniciarLogoFondo() {
        timer = new Timer(40, e -> {

                if (oscureciendo) {
                    alpha -= 0.02f;
                    
                    if (alpha <= 0f) {
                        alpha = 0f;
                        oscureciendo = false; 
                    }
                } else {
                    alpha += 0.02f;
                    if (alpha >= 0.8f) {
                        alpha = 0.8f;
                        oscureciendo = true; 
                    }
                }
                
                obj.setAlpha(alpha);
                obj.repaint();
            
        });
        timer.start();
    }
    private void iniciarAnimacionFondo() {
    timerAnimacion = new Timer(16, new ActionListener() {
        @Override
        public void actionPerformed(ActionEvent e) {
         
            if (oscureciendo) {
                valorColor -= velocidadTransicion;
                if (valorColor <= 0) {
                    valorColor = 0;
                    oscureciendo = false; 
                }
            } else {
                valorColor += velocidadTransicion;
                if (valorColor >= 255) {
                    valorColor = 255;
                    oscureciendo = true; 
                }
            }

           
            int rOscuro = 65, gOscuro = 105, bOscuro = 225; 
            
        
            int rClaro = 135, gClaro = 206, bClaro = 250;

         
            float proporcion = valorColor / 255f;

         
            int r = (int) (rOscuro + (rClaro - rOscuro) * proporcion);
            int g = (int) (gOscuro + (gClaro - gOscuro) * proporcion);
            int b = (int) (bOscuro + (bClaro - bOscuro) * proporcion);

       
            jPanel1.setBackground(new Color(r, g, b));
        }
    });
    timerAnimacion.start();
}
   
    
    
    
    public void Configuracion(){
        imgSUSCRIPCION.setIcon(new javax.swing.ImageIcon("datos/Images/Suscripciones.png"));
        imgSUSCRIPCION.setText("");
        imgSUSCRIPCION.setLocation(200,200);
        imgSUSCRIPCION.setVisible(false);
        imgREGISTRO.setIcon(new javax.swing.ImageIcon("datos/Images/Registro.png"));
        imgREGISTRO.setText("");
        imgREGISTRO.setLocation(200,200);
        imgREGISTRO.setVisible(false);
        imgSEDES.setIcon(new javax.swing.ImageIcon("datos/Images/Sede.png"));
        imgSEDES.setText("");
        imgSEDES.setLocation(200,200);
        imgSEDES.setVisible(false);
        imgMAQUINAS.setIcon(new javax.swing.ImageIcon("datos/Images/Maquinas.png"));
        imgMAQUINAS.setText("");
        imgMAQUINAS.setLocation(200,200);
        imgMAQUINAS.setVisible(false);
        imgSOPORTE.setIcon(new javax.swing.ImageIcon("datos/Images/Soporte.png"));
        imgSOPORTE.setText("");
        imgSOPORTE.setLocation(200,200);
        imgSOPORTE.setVisible(false);
        imgADMINISTRACION.setIcon(new javax.swing.ImageIcon("datos/Images/Administracion.png"));
        imgADMINISTRACION.setText("");
        imgADMINISTRACION.setLocation(200,200);
        imgADMINISTRACION.setVisible(false);
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
        imgAdm.setIcon(new javax.swing.ImageIcon("datos/Images/IconAdministracon.png"));
        imgAdm.setText("");
    }
    
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        imgSUSCRIPCION = new javax.swing.JLabel();
        imgREGISTRO = new javax.swing.JLabel();
        imgSEDES = new javax.swing.JLabel();
        imgMAQUINAS = new javax.swing.JLabel();
        imgSOPORTE = new javax.swing.JLabel();
        imgADMINISTRACION = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
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
        lblSEDES1 = new javax.swing.JLabel();
        IconSedes = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel16 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        imgSed = new javax.swing.JLabel();
        imgMaq = new javax.swing.JLabel();
        imgSop = new javax.swing.JLabel();
        imgAdm = new javax.swing.JLabel();
        imgReg = new javax.swing.JLabel();
        imgSus = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        imgSUSCRIPCION.setText("jLabel8");

        imgREGISTRO.setText("jLabel2");

        imgSEDES.setText("jLabel2");

        imgMAQUINAS.setText("jLabel2");

        imgSOPORTE.setText("jLabel3");

        imgADMINISTRACION.setText("jLabel4");

        jPanel2.setBackground(new java.awt.Color(51, 153, 255));

        jLabel1.setFont(new java.awt.Font("Segoe UI", 3, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Bienvenido de nuevo");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(20, 20, 20)
                .addComponent(jLabel1)
                .addContainerGap(689, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addComponent(jLabel1)
                .addContainerGap(31, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(208, 208, 208)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(imgSEDES)
                    .addComponent(imgREGISTRO)
                    .addComponent(imgSUSCRIPCION)
                    .addComponent(imgSOPORTE)
                    .addComponent(imgMAQUINAS)
                    .addComponent(imgADMINISTRACION))
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(121, 121, 121)
                .addComponent(imgSOPORTE)
                .addGap(28, 28, 28)
                .addComponent(imgMAQUINAS)
                .addGap(18, 18, 18)
                .addComponent(imgADMINISTRACION)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(imgSUSCRIPCION)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(imgREGISTRO)
                .addGap(18, 18, 18)
                .addComponent(imgSEDES))
        );

        jPanel3.setBackground(new java.awt.Color(204, 255, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jLabel8.setFont(new java.awt.Font("Rockwell Condensed", 3, 48)); // NOI18N
        jLabel8.setText("GymWork");
        jPanel3.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 30, 192, -1));

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
        jPanel3.add(lblSUSCRIPCION1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 240, -1, -1));

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
        jPanel3.add(lblREGISTRO1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 330, -1, -1));

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
        jPanel3.add(lblADMINISTRACION1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 640, -1, -1));
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
        jPanel3.add(lblSEDES1, new org.netbeans.lib.awtextra.AbsoluteConstraints(70, 410, -1, -1));
        jPanel3.add(IconSedes, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 390, -1, -1));

        jPanel4.setBackground(new java.awt.Color(102, 204, 255));
        jPanel4.setBorder(new javax.swing.border.SoftBevelBorder(javax.swing.border.BevelBorder.RAISED));

        jLabel15.setFont(new java.awt.Font("Sitka Text", 1, 18)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(0, 102, 255));
        jLabel15.setText("Inicio");
        jLabel15.addAncestorListener(new javax.swing.event.AncestorListener() {
            public void ancestorAdded(javax.swing.event.AncestorEvent evt) {
                jLabel15AncestorAdded(evt);
            }
            public void ancestorMoved(javax.swing.event.AncestorEvent evt) {
            }
            public void ancestorRemoved(javax.swing.event.AncestorEvent evt) {
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel16)
                .addGap(10, 10, 10)
                .addComponent(jLabel15)
                .addContainerGap(267, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel16)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGap(20, 20, 20)
                        .addComponent(jLabel15)))
                .addGap(0, 6, Short.MAX_VALUE))
        );

        jPanel3.add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 150, 340, -1));

        imgSed.setText("jLabel2");
        jPanel3.add(imgSed, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 400, -1, 30));

        imgMaq.setText("jLabel2");
        jPanel3.add(imgMaq, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 470, -1, 30));

        imgSop.setText("jLabel2");
        jPanel3.add(imgSop, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 550, -1, 30));

        imgAdm.setText("jLabel2");
        jPanel3.add(imgAdm, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 630, -1, 30));

        imgReg.setText("jLabel2");
        jPanel3.add(imgReg, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 320, -1, 30));

        imgSus.setText("jLabel2");
        jPanel3.add(imgSus, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, -1, 30));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, 345, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, 720, Short.MAX_VALUE)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void lblSUSCRIPCION1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUSCRIPCION1MouseEntered
        lblSUSCRIPCION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION1.setForeground(new java.awt.Color(0, 102, 255));
        imgSUSCRIPCION.setVisible(true);
    }//GEN-LAST:event_lblSUSCRIPCION1MouseEntered

    private void lblSUSCRIPCION1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSUSCRIPCION1MouseExited
        lblSUSCRIPCION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSUSCRIPCION1.setForeground(new java.awt.Color(0, 0, 0));
        imgSUSCRIPCION.setVisible(false);
    }//GEN-LAST:event_lblSUSCRIPCION1MouseExited

    private void lblREGISTRO1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblREGISTRO1MouseEntered
        lblREGISTRO1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO1.setForeground(new java.awt.Color(0, 102, 255));
        imgREGISTRO.setVisible(true);
    }//GEN-LAST:event_lblREGISTRO1MouseEntered

    private void lblREGISTRO1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblREGISTRO1MouseExited
        lblREGISTRO1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblREGISTRO1.setForeground(new java.awt.Color(0, 0, 0));
        imgREGISTRO.setVisible(false);
    }//GEN-LAST:event_lblREGISTRO1MouseExited

    private void lblMAQUINAS1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINAS1MouseEntered
        lblMAQUINAS1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS1.setForeground(new java.awt.Color(0, 102, 255));
        imgMAQUINAS.setVisible(true);
    }//GEN-LAST:event_lblMAQUINAS1MouseEntered

    private void lblMAQUINAS1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINAS1MouseExited
        lblMAQUINAS1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblMAQUINAS1.setForeground(new java.awt.Color(0, 0, 0));
        imgMAQUINAS.setVisible(false);
    }//GEN-LAST:event_lblMAQUINAS1MouseExited

    private void lblSOPORTE1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTE1MouseEntered
        lblSOPORTE1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE1.setForeground(new java.awt.Color(0, 102, 255));
        imgSOPORTE.setVisible(true);
    }//GEN-LAST:event_lblSOPORTE1MouseEntered

    private void lblSOPORTE1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTE1MouseExited
        lblSOPORTE1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSOPORTE1.setForeground(new java.awt.Color(0, 0, 0));
        imgSOPORTE.setVisible(false);
    }//GEN-LAST:event_lblSOPORTE1MouseExited

    private void lblADMINISTRACION1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACION1MouseEntered
        lblADMINISTRACION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION1.setForeground(new java.awt.Color(0, 102, 255));
        imgADMINISTRACION.setVisible(true);
    }//GEN-LAST:event_lblADMINISTRACION1MouseEntered

    private void lblADMINISTRACION1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACION1MouseExited
        lblADMINISTRACION1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblADMINISTRACION1.setForeground(new java.awt.Color(0, 0, 0));
        imgADMINISTRACION.setVisible(false);
    }//GEN-LAST:event_lblADMINISTRACION1MouseExited

    private void lblSEDES1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSEDES1MouseExited
        lblSEDES1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES1.setForeground(new java.awt.Color(0, 0, 0));
        imgSEDES.setVisible(false);
    }//GEN-LAST:event_lblSEDES1MouseExited

    private void lblSEDES1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSEDES1MouseEntered
        lblSEDES1.setFont(new java.awt.Font("Sitka Text", 1, 18));
        lblSEDES1.setForeground(new java.awt.Color(0, 102, 255));
        imgSEDES.setVisible(true);
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
    Suscripciones sus = new Suscripciones();
    sus.setVisible(true);
    }//GEN-LAST:event_lblSUSCRIPCION1MouseClicked

    private void jLabel15AncestorAdded(javax.swing.event.AncestorEvent evt) {//GEN-FIRST:event_jLabel15AncestorAdded
        // TODO add your handling code here:
    }//GEN-LAST:event_jLabel15AncestorAdded

    private void lblADMINISTRACION1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblADMINISTRACION1MouseClicked
    Modulo MD = new Modulo();
    MD.setVisible(true);
    this.dispose();
    }//GEN-LAST:event_lblADMINISTRACION1MouseClicked

    private void lblSOPORTE1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblSOPORTE1MouseClicked
        Soporte obj = new Soporte();
        obj.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblSOPORTE1MouseClicked

    private void lblMAQUINAS1MouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_lblMAQUINAS1MouseClicked
        Maquinas_panel OBJ = new Maquinas_panel();
        OBJ.setVisible(true);
        this.dispose();
    }//GEN-LAST:event_lblMAQUINAS1MouseClicked

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
        java.awt.EventQueue.invokeLater(() -> new Admin_Panel().setVisible(true));
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel IconSedes;
    private javax.swing.JLabel imgADMINISTRACION;
    private javax.swing.JLabel imgAdm;
    private javax.swing.JLabel imgGymWork1;
    private javax.swing.JLabel imgMAQUINAS;
    private javax.swing.JLabel imgMaq;
    private javax.swing.JLabel imgREGISTRO;
    private javax.swing.JLabel imgReg;
    private javax.swing.JLabel imgSEDES;
    private javax.swing.JLabel imgSOPORTE;
    private javax.swing.JLabel imgSUSCRIPCION;
    private javax.swing.JLabel imgSed;
    private javax.swing.JLabel imgSop;
    private javax.swing.JLabel imgSus;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JLabel lblADMINISTRACION1;
    private javax.swing.JLabel lblMAQUINAS1;
    private javax.swing.JLabel lblREGISTRO1;
    private javax.swing.JLabel lblSEDES1;
    private javax.swing.JLabel lblSOPORTE1;
    private javax.swing.JLabel lblSUSCRIPCION1;
    // End of variables declaration//GEN-END:variables
}
